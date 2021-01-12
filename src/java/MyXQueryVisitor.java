import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.TerminalNode;
import org.w3c.dom.*;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.util.*;

import com.sun.org.apache.xerces.internal.dom.DocumentImpl;

public class MyXQueryVisitor extends XQueryBaseVisitor {

    class TableNode {
        Set<TableNode> neighbors;
        String root;
        int height;
        String query;

        TableNode(String root, int height, String query) {
            this.root = root;
            neighbors = new HashSet<>();
            this.height = height;
            this.query = query;
        }
    }

    // variable dependency graph
    Map<String, String> graph = new HashMap<>();
    // (variable,root variable) relationship graph
    Map<String, String> cluster = new HashMap<>();

    // equality conditions within the same table
    Map<String, String> constant = new HashMap<>();
    Map<String, List<String>> variables = new HashMap<>();

    // equality conditions between different tables
    Map<String, List<String>> diff_variables = new HashMap<>();

    // number of roots/seperate tables
    int numOfRoot = 0;

    // boolean value indicating if a rewriting should be performed
    boolean rewriting = false;

    // boolean value indicating a left-deep join plan
    boolean isL = true;

    @Override
    public Object visitR(XQueryParser.RContext ctx) {
        /* Default function called by visit() function within XQueryBaseVisitor */

        // if needed, rewrite
        if (rewriting) {
            System.out.println("Traversing..");
            rewritingTraverse(ctx); // build the variable dependency graphs
            System.out.println("constructing each table..");
            HashMap<String, String> result = constructSeperateTable(ctx); // construct rewriting for each table

            String joined = null;
            if (isL) {
                joined = constructLJoin(result); // join tables
            } else {
                joined = constructBJoin(result);
            }

            String finalResult = wrapJoin(joined, ctx.rc()); // warp joins, complete rewriting
            return finalResult;
        }
        HashMap<String, List<Node>> context = new HashMap<>();
        return visitXQ(ctx, context);
    }

    /*** 1 ***/
    public void rewritingTraverse(XQueryParser.RContext ctx) {
    /* Parse the input query and build the variable dependency graph */

        XQueryParser.FcContext fc = ctx.fc(); // flwr clauses
        List<XQueryParser.RContext> paths = fc.r(); // path queries
        List<TerminalNode> variables = fc.FN(); // variables

        // record (child,parent) variable relationships
        for (int i = 0; i < paths.size(); i++) {
            // root variable indicating a new table
            if (paths.get(i).ap() != null) {
                graph.put(variables.get(i).getText(), variables.get(i).getText());
                numOfRoot++;
                // record a variable with its parent
            } else {
                String father = paths.get(i).r(0).getChild(1).getText();
                graph.put(variables.get(i).getText(), father);
            }
        }
        // traverse dependency graph to connect each variable with its root variable
        for (String key : graph.keySet()) {
            String curr = key;
            while (!graph.get(curr).equals(curr)) {
                curr = graph.get(curr);
            }
            cluster.put(key, curr);
        }
        // record all equality conditions in the where clause
        if (ctx.wc() != null) {
            cTraverse(ctx.wc().c());
        }
    }

    public void cTraverse(XQueryParser.CContext ctx) {
    /*  Traverse the where condition and record all variable/constant equalities */

        if (ctx.getChild(1).getText().equals("eq") || ctx.getChild(1).getText().equals("=")) {
            String first = ctx.r(0).getChild(1).getText();

            if (ctx.r(1).getChildCount() == 1) {
                // variable = constant case
                String second = ctx.r(1).getText();
                constant.put(first, second);
            } else {
                String second = ctx.r(1).getChild(1).getText();
                // variable = variable within one table case
                if (cluster.get(first) == null) {
                    System.out.println(first);
                    for (String key : cluster.keySet()) {
                        System.out.println(key);
                        System.out.println(cluster.get(key));
                    }
                }
                if (cluster.get(first).equals(cluster.get(second))) {
                    variables.putIfAbsent(first, new ArrayList<>());
                    variables.get(first).add(second);
                    // variable = variable between two tables case
                } else {
                    diff_variables.putIfAbsent(first, new ArrayList<>());
                    diff_variables.get(first).add(second);
                }
            }
            return;
        }
        // c and c case
        cTraverse(ctx.c(0));
        cTraverse(ctx.c(1));
    }

    /*** 2 ***/
    public HashMap<String, String> constructSeperateTable(XQueryParser.RContext ctx) {
    /* Construct rewriting for each table 
        Return a map from each root variable to its corresponding table rewriting */

        XQueryParser.FcContext fc = ctx.fc(); // flwr clause
        List<XQueryParser.RContext> paths = fc.r(); // path queries
        List<TerminalNode> variables = fc.FN(); // variables
        HashMap<String, String> tables = new HashMap<>(); // map a root variable to its table rewriting
        HashMap<String, Boolean> isWhere = new HashMap<>(); // indicating whether a variable is 

        /* rewrite the for clause */
        for (int i = 0; i < paths.size(); i++) {
            String curr_var = variables.get(i).getText();

            // if the path query is an absolute path, create a new table and map the root variable to it
            if (paths.get(i).ap() != null) {
                tables.put(curr_var, "for $" + variables.get(i).getText() + " in " + paths.get(i).ap().getText());
                isWhere.put(curr_var, false);
                // if it's a relative path, find the correct root variable and extend its corresponding table
            } else {
                String table = ", \n $" + curr_var + " in " + paths.get(i).getText();
                String final_father = cluster.get(curr_var);
                tables.put(final_father, tables.get(final_father) + table);
            }
        }

        // rewrite variable = constant conditions within a table in the  where clause
        for (String key : constant.keySet()) {
            String final_father = cluster.get(key); // root variable pointing to one of the tables
            String currTable = tables.get(final_father); // retrieve the appropriate table
            String where = ""; // string extension

            if (!isWhere.get(final_father)) { //HMM DO WE EVER UPDATE isWhere?
                // first condition in the table
                where += "\nwhere $" + key + " eq " + constant.get(key);
            } else {
                where += "\nand $" + key + " eq " + constant.get(key);
            }
            tables.put(final_father, currTable + where);
        }

        // rewrite variable=variable conditions within a table in the  where clause
        for (String key : this.variables.keySet()) {
            String final_father = cluster.get(key); // root variable pointing to one of the tables
            String currTable = tables.get(final_father); // retrieve the appropriate table
            String where = ""; // string extension

            for (String rhs : this.variables.get(key)) {
                if (!isWhere.get(final_father)) {
                    // first condition in the table
                    where += "\nwhere $" + key + " eq $" + rhs;
                } else {
                    where += "\nand $" + key + " eq $" + rhs;
                }
                tables.put(final_father, currTable + where);
            }
        }

        /* rewrite the return clause */
        for (int i = 0; i < paths.size(); i++) {
            String curr_var = variables.get(i).getText(); // root variable pointing to one of the tables
            String final_father = cluster.get(curr_var); // retrieve the appropriate table
            String result = ""; // string extension

            if (paths.get(i).ap() != null) {
                // root variable, first variable in the return clause
                result += "\nreturn <tuple>" + "\n<" + curr_var + ">{$" + curr_var + "}</" + curr_var + ">";
            } else {
                result += "\n<" + curr_var + ">{$" + curr_var + "}</" + curr_var + ">";
            }
            tables.put(final_father, tables.get(final_father) + result);
        }
        // complete the return clause
        for (String key : tables.keySet()) {
            tables.put(key, tables.get(key) + "\n</tuple>");
        }
        return tables;
    }

    /*** 3 ***/
    public String constructLJoin(HashMap<String, String> tables) {
        /* Join the input tables using equalties on variables from different tables */

        HashMap<String, List<String>> edgeCluster = new HashMap<>(); // all pairs of tables to be joined

        List<Set<String>> connected_component = new ArrayList<>();

        for (String key : diff_variables.keySet()) {
            for (String rhs : diff_variables.get(key)) {
                String left = key; // first variable in the equality
                String right = rhs; // second variable in the equality
                String finalLeft = cluster.get(left); // root variable of the first variable
                String finalRight = cluster.get(right); // root variable of the second variable
                String combinedKey = ""; // combined key defining a pair of tables to be joined

                // constructing connected table cluster
                boolean isNewComp = true;
                for (Set<String> component : connected_component) {
                    if (component.contains(finalLeft) || component.contains(finalRight)) {
                        component.add(finalLeft);
                        component.add(finalRight);
                        isNewComp = false;
                        break;
                    }
                }
                if (isNewComp) {
                    connected_component.add(new HashSet<>());
                    connected_component.get(connected_component.size() - 1).add(finalLeft);
                    connected_component.get(connected_component.size() - 1).add(finalRight);
                }

                // build edge cluster
                // fix the order of tables in a join
                if (finalLeft.compareTo(finalRight) < 0) {
                    combinedKey += finalLeft;
                    combinedKey += ",";
                    combinedKey += finalRight;
                    edgeCluster.putIfAbsent(combinedKey, new ArrayList<>());
                    edgeCluster.get(combinedKey).add(left);
                    edgeCluster.get(combinedKey).add(right);
                } else {
                    combinedKey += finalRight;
                    combinedKey += ",";
                    combinedKey += finalLeft;
                    edgeCluster.putIfAbsent(combinedKey, new ArrayList<>());
                    edgeCluster.get(combinedKey).add(right);
                    edgeCluster.get(combinedKey).add(left);
                }
            }
        }

        for (String key : tables.keySet()) {
            boolean isInConnectedComp = false;
            for (Set<String> set : connected_component) {
//                String root = cluster.get(key);
                if (set.contains(key)) {
                    isInConnectedComp = true;
                    break;
                }
            }
            if (!isInConnectedComp) {
                connected_component.add(new HashSet<>());
                connected_component.get(connected_component.size() - 1).add(key);
            }

        }
        //refine edge cluster to connect all disconnected components
        for (int i = 0; i < connected_component.size() - 1; i++) {
            String root1 = connected_component.get(i).iterator().next();
            String root2 = connected_component.get(i + 1).iterator().next();
            String combinedKey = "";
            if (root1.compareTo(root2) < 0) {
                combinedKey += root1;
                combinedKey += ",";
                combinedKey += root2;
                edgeCluster.putIfAbsent(combinedKey, new ArrayList<>());

            } else {
                combinedKey += root2;
                combinedKey += ",";
                combinedKey += root1;
                edgeCluster.putIfAbsent(combinedKey, new ArrayList<>());
            }
        }

        for (String key : edgeCluster.keySet()) {
            System.out.println("Key:" + key);
            System.out.println("Values:" + edgeCluster.get(key));
        }

        String result = ""; // string extension
        Set<String> joined = new HashSet<>(); // joined tables

        while (edgeCluster.size() > 0) {
            String tempResult = "";

            // first join
            if (joined.size() == 0) {
                //get one key
                Iterator<Map.Entry<String, List<String>>> iterator = edgeCluster.entrySet().iterator();
                Map.Entry<String, List<String>> entry = edgeCluster.entrySet().iterator().next();

                String key = entry.getKey();
                String[] splited = key.split(",");
                String firstKey = splited[0];
                String secondKey = splited[1];

                tempResult += "join(";
                tempResult += tables.get(firstKey);
                tempResult += ",\n";
                tempResult += tables.get(secondKey);
                tempResult += ",\n[";
                tempResult += readFromList(edgeCluster.get(key), true);
                tempResult += "],";
                tempResult += "[";
                tempResult += readFromList(edgeCluster.get(key), false);
                tempResult += "]";
                tempResult += ")";
                joined.add(firstKey);
                joined.add(secondKey);
                edgeCluster.remove(key);

            } else {
                String selectedKey = "";

                List<String> orderList = new ArrayList<>();
                List<String> nonOrderList = new ArrayList<>();
                List<String> toBeDeleted = new ArrayList<>();

                //split key and check which one is already joined
                for (String k : edgeCluster.keySet()) {
                    String[] splited = k.split(",");
                    String firstKey = splited[0];
                    String secondKey = splited[1];
                    if (joined.contains(firstKey)) {
                        selectedKey = secondKey;
                        break;
                    } else if (joined.contains(secondKey)) {
                        selectedKey = firstKey;
                        break;
                    }
                }

                for (String k : edgeCluster.keySet()) {
                    String[] splited = k.split(",");
                    String firstKey = splited[0];
                    String secondKey = splited[1];
                    if (secondKey.equals(selectedKey) && joined.contains(firstKey)) {
                        orderList.addAll(edgeCluster.get(k));
                        toBeDeleted.add(k);
                    } else if (firstKey.equals(selectedKey) && joined.contains(secondKey)) {
                        nonOrderList.addAll(edgeCluster.get(k));
                        toBeDeleted.add(k);
                    }
                }
                for (String d : toBeDeleted) {
                    edgeCluster.remove(d);
                }

                tempResult += "join(";
                tempResult += result;
                tempResult += ",\n";
                tempResult += tables.get(selectedKey);
                tempResult += ",\n[";
                tempResult += readFromList(orderList, true); // joined variables from the table that is already in the join
                tempResult += readFromList(nonOrderList, false);
                tempResult += "],";
                tempResult += "[";
                tempResult += readFromList(orderList, false);
                tempResult += readFromList(nonOrderList, true); // joined variables from the newly joined table
                tempResult += "]";
                tempResult += ")";
                joined.add(selectedKey);
            }
            result = tempResult;
        }
        return result;
    }

    public String constructBJoin(HashMap<String, String> tables) {

        HashMap<String, TableNode> tableGraph = new HashMap<>();
        TreeMap<Integer, Set<TableNode>> neighborOrderedGraph = new TreeMap<>();
        List<Set<TableNode>> connectedComponent = new ArrayList<>();
        TreeMap<Integer, Set<TableNode>> heightToNode = new TreeMap<>();

        //create new table nodes
        for (String key : tables.keySet()) {
            tableGraph.put(key, new TableNode(key, 1, tables.get(key)));
        }

        //create edges
        for (String key : diff_variables.keySet()) {
            String leftroot = cluster.get(key);
            for (String neighbor : diff_variables.get(key)) {
                String rightroot = cluster.get(neighbor);
                TableNode neighborNode = tableGraph.get(rightroot);
                TableNode curr = tableGraph.get(leftroot);
                curr.neighbors.add(neighborNode);
                neighborNode.neighbors.add(curr);
            }
        }

        for (String key : tableGraph.keySet()) {
            TableNode curr = tableGraph.get(key);
            heightToNode.putIfAbsent(1, new HashSet<>());
            heightToNode.get(1).add(curr);
        }

        for (String key : tableGraph.keySet()) {
            TableNode curr = tableGraph.get(key);
            neighborOrderedGraph.putIfAbsent(curr.neighbors.size(), new HashSet<>());
            neighborOrderedGraph.get(curr.neighbors.size()).add(curr);
        }

        //init connectedComponent
        for (String key : tableGraph.keySet()) {
            TableNode curr = tableGraph.get(key);
            boolean isNewCluster = true;

            for(TableNode neigh: curr.neighbors){
                for(Set<TableNode> cluster: connectedComponent){
                    if(cluster.contains(curr) || cluster.contains(neigh)){
                        isNewCluster = false;
                        cluster.add(curr);
                        cluster.add(neigh);
                    }
                }
                if(isNewCluster){
                    connectedComponent.add(new HashSet<>());
                    connectedComponent.get(connectedComponent.size()-1).add(curr);
                    connectedComponent.get(connectedComponent.size()-1).add(neigh);
                }
            }

            if(isNewCluster){
                connectedComponent.add(new HashSet<>());
                connectedComponent.get(connectedComponent.size()-1).add(curr);
            }
        }
        System.out.println("init size:" + connectedComponent.size());

        for (String key : tableGraph.keySet()) {
            System.out.println("key:" + key);
        }

        //leaves
        int firstkey = neighborOrderedGraph.firstKey();

        if (neighborOrderedGraph.get(firstkey) != null) {
            for (TableNode node : neighborOrderedGraph.get(firstkey)) {
                if (tableGraph.get(node.root) == null) {
                    continue;
                }
                int min = 100000;
                TableNode joinTarget = null;

                //find neighbor with min-neigh
                boolean canJoin = false;
                for (TableNode neigh : node.neighbors) {
                    System.out.println(node.root+":"+neigh.root);
                    if (neigh.height > 1) {
                        continue;
                    }
                    if (neigh.neighbors.size() < min) {
                        canJoin = true;
                        min = neigh.neighbors.size();
                        joinTarget = neigh;
                    }
                }
                if (canJoin) {
                    joinNodes(node, joinTarget, tableGraph, heightToNode, connectedComponent);
                }
            }
        }

        while(tableGraph.size() > 1){
            int connectMin = 199999;
            TableNode targetA = null;
            TableNode targetB = null;

            for(String key: tableGraph.keySet()){
                TableNode curr = tableGraph.get(key);
                for(TableNode neigh: curr.neighbors){
                    int currMin = curr.height + neigh.height;
                    if(currMin < connectMin){
                        targetA = curr;
                        targetB = neigh;
                        connectMin = currMin;
                    }
                }
            }

            int disConnectMin = 199999;
            TableNode disTargetA = null;
            TableNode disTargetB = null;

            for(String key: tableGraph.keySet()){
                TableNode curr = tableGraph.get(key);
                for(String key2: tableGraph.keySet()){
                    TableNode target = tableGraph.get(key2);
                    if(curr == target){
                        continue;
                    }

                    boolean isSameCluster = false;

                    for(int i = 0 ; i < connectedComponent.size(); i++) {
                        Set<TableNode> cluster = connectedComponent.get(i);
                        if(cluster.contains(curr) && cluster.contains(target)){
                            isSameCluster = true;
                        }
                    }
                    if(isSameCluster){
                        continue;
                    }
                    int currMin = curr.height + target.height;
                    if(currMin < disConnectMin){
                        disTargetA = curr;
                        disTargetB = target;
                        disConnectMin = currMin;
                    }
                }
            }

            System.out.println("conn:"+ connectMin);
            System.out.println("dis:" + disConnectMin);
            if(connectMin <= disConnectMin){
                joinNodes(targetA, targetB,tableGraph,heightToNode,connectedComponent);
            }else{
                joinNodes(disTargetA, disTargetB, tableGraph,heightToNode,connectedComponent);
            }

            for (String key : tableGraph.keySet()) {
                System.out.println("key:" + key);
            }
            System.out.println("size:" + connectedComponent.size());
        }
        String finalQuery = "";
        for (String key : tableGraph.keySet()) {
            System.out.println("final query:" + tableGraph.get(key).query);
            finalQuery = tableGraph.get(key).query;
        }
        return finalQuery;
    }

    public void joinNodes(TableNode a, TableNode b,
                          HashMap<String, TableNode> tableGraph,
                          TreeMap<Integer, Set<TableNode>> heightToNode,
                          List<Set<TableNode>> connectedComponent) {

        String lhs = "";
        String rhs = "";

        Set<String> aroots = new HashSet<>();
        String[] aroots_splited = a.root.split(",");
        for(String root : aroots_splited){
            aroots.add(root);
        }
        Set<String> broots = new HashSet<>();
        String[] broots_splited = b.root.split(",");
        for(String root : broots_splited){
            broots.add(root);
        }

        for (String left : diff_variables.keySet()) {
            List<String> rightList = diff_variables.get(left);
            String leftRoot = cluster.get(left);

            if(aroots.contains(leftRoot)){
                for(String right: rightList) {
                    String rightRoot = cluster.get(right);
                    if(broots.contains(rightRoot)){
                        lhs += left;
                        lhs += ",";
                        rhs += right;
                        rhs += ",";
                    }
                }
            }else if(broots.contains(leftRoot)){
                for(String right: rightList) {
                    String rightRoot = cluster.get(right);
                    if(aroots.contains(rightRoot)){
                        lhs += right;
                        lhs += ",";
                        rhs += left;
                        rhs += ",";
                    }
                }
            }
        }

        if(rhs.length() > 0){
            rhs = rhs.substring(0,rhs.length()-1);
        }
        if(lhs.length() > 0){
            lhs = lhs.substring(0,lhs.length()-1);
        }

        String joinedQuery = "";
        joinedQuery += "join(";
        joinedQuery += a.query;
        joinedQuery += ",\n";
        joinedQuery += b.query;
        joinedQuery += ",\n[";
        joinedQuery += lhs; // joined variables from the table that is already in the join
        joinedQuery += "],";
        joinedQuery += "[";
        joinedQuery += rhs; // joined variables from the newly joined table
        joinedQuery += "]";
        joinedQuery += ")";

        TableNode joinedNode = new TableNode(a.root + "," + b.root, Math.max(a.height, b.height) + 1,joinedQuery);

        for (TableNode neigh : a.neighbors) {
            if (neigh == b) {
                continue;
            }
            neigh.neighbors.remove(a);
            neigh.neighbors.add(joinedNode);
            joinedNode.neighbors.add(neigh);
        }

        for (TableNode neigh : b.neighbors) {
            if (neigh == a) {
                continue;
            }
            neigh.neighbors.remove(b);
            neigh.neighbors.add(joinedNode);
            neigh.neighbors.add(joinedNode);
            joinedNode.neighbors.add(neigh);
        }

        tableGraph.remove(a.root);
        tableGraph.remove(b.root);
        tableGraph.put(joinedNode.root, joinedNode);

        heightToNode.get(a.height).remove(a);
        heightToNode.get(b.height).remove(b);
        heightToNode.putIfAbsent(joinedNode.height, new HashSet<>());
        heightToNode.get(joinedNode.height).add(joinedNode);

        int clusterAIndex = 0;
        int clusterBIndex = 0;
        boolean isSameCluster = false;

        for(int i = 0 ; i < connectedComponent.size(); i++){
            Set<TableNode> cluster = connectedComponent.get(i);
            if(cluster.contains(a) && cluster.contains(b)){
                cluster.remove(a);
                cluster.remove(b);
                cluster.add(joinedNode);
                isSameCluster = true;
                break;
            }else if(cluster.contains(a)){
                clusterAIndex = i;
            }else if(cluster.contains(b)){
                clusterBIndex = i;
            }
        }

        if(!isSameCluster){
            Set<TableNode> newCluster = new HashSet<>();
            newCluster.addAll(connectedComponent.get(clusterAIndex));
            newCluster.addAll(connectedComponent.get(clusterBIndex));
            newCluster.remove(a);
            newCluster.remove(b);
            newCluster.add(joinedNode);

            if(clusterAIndex > clusterBIndex){
                connectedComponent.remove(clusterAIndex);
                connectedComponent.remove(clusterBIndex);
            }else{
                connectedComponent.remove(clusterBIndex);
                connectedComponent.remove(clusterAIndex);
            }
            connectedComponent.add(newCluster);
        }
    }

    // helper function to obtain joined variables from each table in a join
    public String readFromList(List<String> list, boolean first) {
        if (list.size() == 0) {
            return "";
        }
        int start = 0; // the first table in an edgeCluster pair
        if (!first) { // the second table in an edgeCluster pair
            start = 1;
        }
        StringBuilder r = new StringBuilder();
        for (int i = start; i < list.size(); i += 2) {
            r.append(list.get(i));
            if (i + 2 < list.size()) {
                r.append(",");
            }
        }
        return r.toString();
    }

    /*** 4 ***/
    public String wrapJoin(String joinQuery, XQueryParser.RcContext rcContext) {
    /* Complete the rewriting. Warp all joins in a FLWR clause. */

        StringBuilder sb = new StringBuilder();
        sb.append("for $tuple in ");
        sb.append(joinQuery);
        sb.append("\nreturn " + dfs(rcContext.getChild(1)));
        return sb.toString();
    }

    /* rewrite the return statement of the outermost FLWR clause */
    // traverse the parse tree of the return statement and substite each variable with a correct path query
    private String dfs(ParseTree tree) {

        StringBuilder sb = new StringBuilder();
        if (tree.getChildCount() == 0) {
            return tree.getText();
        }
        if (tree.getChildCount() == 2 && tree.getChild(0).getText().equals("$")) {
            sb.append("$tuple/");
            sb.append(tree.getChild(1).getText());
            sb.append("/*");
            return sb.toString();
        }
        for (int i = 0; i < tree.getChildCount(); i++) {
            sb.append(dfs(tree.getChild(i)));
        }
        return sb.toString();
    }

    public Object visitXQ(XQueryParser.RContext ctx, Map<String, List<Node>> context) {
    /* evaluates an XQuery */

        List<Node> result = new ArrayList<>();

        // $var case
        if (ctx.getChildCount() == 2 && ctx.getChild(0).getText().equals("$")) {
            return context.get(ctx.getChild(1).getText());

            // StringConstant case
        } else if (ctx.getChildCount() == 1 && ctx.STRING() != null) {
            System.out.println("XQ Reach string constant / make text");
            String constant = ctx.STRING().getText();
            Node textNode = makeText(constant.substring(1, constant.length() - 1));
            result.add(textNode);
            return result;

            // ap case
        } else if (ctx.getChildCount() == 1 && ctx.ap() != null) {
            System.out.println("XQ Reach ap");
            return visitAP(ctx.ap());

            // tagName case
        } else if (ctx.getChildCount() == 9 && ctx.getChild(0).getText().equals("<")
                && ctx.getChild(2).getText().equals(">")
                && ctx.getChild(3).getText().equals("{")
                && ctx.getChild(5).getText().equals("}")) {
            System.out.println("XQ Reach make tag:" + ctx.FN(0).getText());
            List<Node> list1 = (List<Node>) visitXQ(ctx.r(0), context);

            result.add(makeTag(ctx.FN(0).getText(), list1));
            return result;

            // (XQ) case
        } else if (ctx.getChildCount() == 3 && ctx.getChild(0).getText().equals("(") && ctx.getChild(2).getText().equals(")")) {
            System.out.println("XQ Reach ()");
            return visitXQ(ctx.r(0), context);

            // XQ1,XQ2 case
        } else if (ctx.getChildCount() == 3 && ctx.getChild(1).getText().equals(",")) {
            System.out.println("XQ Reach ,");
            List<Node> list1 = (List<Node>) visitXQ(ctx.r(0), context);
            List<Node> list2 = (List<Node>) visitXQ(ctx.r(1), context);
            list1.addAll(list2);
            return list1;

            // XQ1/rp case
        } else if (ctx.getChildCount() == 3 && ctx.r().size() == 1 && ctx.getChild(1).getText().equals("/") && ctx.rp() != null) {

            List<Node> list1 = (List<Node>) visitXQ(ctx.r(0), context);
            List<Node> temp = unique(visitRp(ctx.rp(), list1));
            System.out.println("XQ Reach /:" + ctx.getText());
            for (Node n : temp) {
                System.out.println("??" + ParserTest.nodeToString(n));
            }
            return temp;

            // XQ1//rp case
        } else if (ctx.getChildCount() == 3 && ctx.r().size() == 1 && ctx.getChild(1).getText().equals("//") && ctx.rp() != null) {

            List<Node> list1 = (List<Node>) visitXQ(ctx.r(0), context);
            System.out.println("XQ Reach //");
            List<Node> all_dec = new ArrayList<>();

            for (Node n : list1) {
                all_dec.add(n);
            }
            Queue<Node> queue = new LinkedList<>();
            // get all descendants
            for (Node n : list1) {
                NodeList nodeList = n.getChildNodes();
                for (int i = 0; i < nodeList.getLength(); i++) {
                    queue.offer(nodeList.item(i));
                }
            }
            while (!queue.isEmpty()) {
                Node curr = queue.poll();
                all_dec.add(curr);
                NodeList nodeList1 = curr.getChildNodes();
                for (int j = 0; j < nodeList1.getLength(); j++) {
                    queue.offer(nodeList1.item(j));
                }
            }
            all_dec = unique(all_dec);
            List<Node> temp = unique(visitRp(ctx.rp(), all_dec));
            return temp;

            // FLWR case
        } else if (ctx.fc() != null) {
            System.out.println("Reach For");
            Map<String, List<Node>> shallowCopyContext = new HashMap<>(context);
            visitFor(ctx, shallowCopyContext, 0, 0, result);
            return result;

            // letClause case
        } else if (ctx.getChildCount() == 2 && ctx.lc() != null && ctx.r().size() == 1) {
            System.out.println("Reach Let solo");
            visitLet(ctx, context, result);
            return visitXQ(ctx.r(0), context);

            // returnClause for tuples case
        } else if (ctx.getChildCount() >= 3 &&
                ctx.getChild(0).getText().equals("<") &&
                ctx.getChild(1).getText().equals("tuple")) {
            System.out.println("reach return t");
            List<Node> nested = new ArrayList<>();

            for (int i = 0; i < ctx.r().size(); i++) {
                List<Node> temp = (List<Node>) visitXQ(ctx.r(i), context);
                if (temp.size() != 1) {
                    System.out.println("Error!!!!");
                    return "";
                }
                nested.add(temp.get(0));
            }
            Node tuple = makeTag("tuple", nested);

            result.add(tuple);
            return result;

            // join case
        } else if (ctx.join() != null) {
        /* evaluate join using hash join algorithm */

            List<Node> first = (List<Node>) visitXQ(ctx.join().r(0), context); // compute the tuples of the first table in the join
            List<Node> second = (List<Node>) visitXQ(ctx.join().r(1), context); // compute the tuples of the second table in the join
            System.out.println("Reach Join");
            System.out.println("Join size:" + first.size());
            System.out.println("Join size:" + second.size());

            // number of attributes on which the tables are joined
            int numOfAtt = (ctx.join().list(0).getChildCount() - 1) / 2;

            List<Node> intermediate = new ArrayList<>(); // list storing tuples of an intermediate table

            //Cartesian product
            if (numOfAtt == 0) {
                System.out.println("Cartesian product!");
                for (Node fn : first) {
                    for (Node sn : second) {
                        List<Node> combined = new ArrayList<>();

                        for (int k = 0; k < fn.getChildNodes().getLength(); k++) {
                            combined.add(fn.getChildNodes().item(k));
                        }
                        for (int k = 0; k < sn.getChildNodes().getLength(); k++) {
                            combined.add(sn.getChildNodes().item(k));
                        }
                        Node joinedNode = makeTag("tuple", combined);
                        intermediate.add(joinedNode);
                    }
                }
                System.out.println("finished join size:" +intermediate.size());
                return intermediate;
            }

            // build a hash map on each joining attribute in the second table and perform hash join
            for (int i = 0; i < numOfAtt; i++) {
                String att1 = ctx.join().list(0).getChild(1 + i * 2).getText(); // a joining attribute in the first table
                String att2 = ctx.join().list(1).getChild(1 + i * 2).getText(); // corresponding joining attribute in the second table
                System.out.println("Joining.." + att1 + "..." + att2);
                if (i == 0) {
                    HashMap<String, List<Node>> bucket = new HashMap<>(); // hash map on the joining attribute of the second table
                    // construct the hash table
                    System.out.println("Building hash bucket");
                    for (Node sn : second) {
                        NodeList children = sn.getChildNodes();
                        for (int j = 0; j < children.getLength(); j++) {
                            Node child = children.item(j);
                            if (child.getNodeName().equals(att2)) {
                                bucket.putIfAbsent(child.getFirstChild().getTextContent(), new ArrayList<>());
                                bucket.get(child.getFirstChild().getTextContent()).add(sn);
                            }
                        }
                    }

                    // for each tuple in the first table, prune the hash table and join
                    for (Node fn : first) {

                        NodeList children = fn.getChildNodes(); // all attributes of a tuple
                        // for each attribute, check if it's the joining attribute, check its value, prune the hash table, and join

                        for (int j = 0; j < children.getLength(); j++) {
                            Node child = children.item(j);
                            System.out.println("Matching:" + child.getNodeName());
                            // if it's a joining attribute
                            if (child.getNodeName().equals(att1)) {
                                String att_val = child.getTextContent();
                                // prune the hash table
                                List<Node> target = bucket.get(att_val);
                                if (target == null) {
                                    continue;
                                }

                                NodeList childOfSource = children;
                                // for every matched tuple, combine attributes from both tuples and add to list storing intermediate joins' results
                                for (Node matched : target) {
                                    NodeList childOfMatch = matched.getChildNodes();
                                    List<Node> combined = new ArrayList<>();

                                    for (int k = 0; k < childOfSource.getLength(); k++) {
                                        combined.add(childOfSource.item(k));
                                    }
                                    for (int k = 0; k < childOfMatch.getLength(); k++) {
                                        combined.add(childOfMatch.item(k));
                                    }
                                    System.out.println("source:" + childOfSource.getLength());
                                    System.out.println("target:" + childOfMatch.getLength());
                                    System.out.println("combined:" + combined.size());
                                    Node joinedNode = makeTag("tuple", combined);
                                    System.out.println("Inmediate:" + ParserTest.nodeToString(joinedNode));

                                    intermediate.add(joinedNode);
                                }
                            }
                        }
                    }
                } else {
                    List<Node> tmp = new ArrayList<>();
                    for (Node node : intermediate) {
                        NodeList attr_list = node.getChildNodes();
                        String att_var1 = "";
                        String att_var2 = "";
                        for (int l = 0; l < attr_list.getLength(); l++) {
                            if (attr_list.item(l).getNodeName().equals(att1)) {
                                att_var1 = attr_list.item(l).getTextContent();
                            } else if (attr_list.item(l).getNodeName().equals(att2)) {
                                att_var2 = attr_list.item(l).getTextContent();
                            }
                        }
                        if (att_var1.equals(att_var2)) {
                            tmp.add(node);
                        }
                    }
                    intermediate = tmp;
                }
            }
            System.out.println("final join:" + intermediate.size());
            for (Node n : intermediate) {
                System.out.println("Node:" + ParserTest.nodeToString(n));
            }
            return intermediate;
        } else {
            return "?";
        }
    }

    public void visitFor(XQueryParser.RContext rContext, Map<String, List<Node>> context,
                         int r_index, int var_index, List<Node> result) {
    /* executes a for clause */

        System.out.println("Visit For");
        XQueryParser.FcContext fcContext = rContext.fc();
        if (r_index < fcContext.r().size()) {
            String var_name = fcContext.FN().get(var_index).getText();
            List<Node> temp = (List<Node>) visitXQ(fcContext.r(r_index), context);

            for (Node n : temp) {
                List<Node> binding = new ArrayList<>();
                binding.add(n);
                context.put(var_name, binding);
                visitFor(rContext, context, r_index + 1, var_index + 1, result);
            }
        } else {
            if (rContext.lc() != null) {
                visitLet(rContext, context, result);
            } else if (rContext.wc() != null) {
                visitWhere(rContext, context, result);
            } else {
                List<Node> t = (List<Node>) visitXQ(rContext.rc().r(), context);
                System.out.println("Do return in for:" + t.size());
                for (Node n : t) {
                    System.out.println(ParserTest.nodeToString(n));
                }
                result.addAll(t);
            }
        }
    }

    public void visitLet(XQueryParser.RContext rContext, Map<String, List<Node>> context,
                         List<Node> result) {
    /* executes a let clause */

        System.out.println("Visit Let");
        XQueryParser.LcContext lcContext = rContext.lc();
        int var_index = 2;

        for (int r_index = 0; r_index < lcContext.r().size(); r_index++) {
            String var_name = lcContext.getChild(var_index).getText();
            List<Node> temp = (List<Node>) visitXQ(lcContext.r(r_index), context);
            context.put(var_name, temp);
            var_index += 5;
            System.out.println("LC result:" + temp.size());
        }
        if (rContext.wc() != null) {
            visitWhere(rContext, context, result);
        } else if (rContext.rc() != null) {
            List<Node> t = (List<Node>) visitXQ(rContext.rc().r(), context);
            System.out.println("Do return let" + t.size());
            result.addAll(t);
        }
    }

    public void visitWhere(XQueryParser.RContext rContext, Map<String, List<Node>> context, List<Node> result) {
    /* executes a where clause */
        boolean doReturn = visitC(rContext.wc().c(), context);

        if (doReturn) {
            List<Node> t = (List<Node>) visitXQ(rContext.rc().r(), context);
            System.out.println("do Return after where" + t.size());
            System.out.println(ParserTest.nodeToString(t.get(0)));
            result.addAll(t);
        }
    }

    public boolean visitC(XQueryParser.CContext ctx, Map<String, List<Node>> context) {
    /* evaluates a condition in the where clause */

        // (c) case
        if (ctx.getChildCount() == 3 && ctx.c().size() == 1
                && ctx.getChild(0).getText().equals("(")
                && ctx.getChild(2).getText().equals(")")
                ) {
            System.out.println("reach(C)");
            return visitC(ctx.c().get(0), context);

            // c1 and c2 case
        } else if (ctx.getChildCount() == 3 && ctx.c().size() == 2 && ctx.getChild(1).getText().equals("and")) {
            System.out.println("reach C and C");
            return visitC(ctx.c().get(0), context) && visitC(ctx.c().get(1), context);

            // c1 or c2 case
        } else if (ctx.getChildCount() == 3 && ctx.c().size() == 2 && ctx.getChild(1).getText().equals("or")) {
            System.out.println("reach C or C");
            return visitC(ctx.c().get(0), context) || visitC(ctx.c().get(1), context);

            // not c case
        } else if (ctx.getChildCount() == 2 && ctx.c().size() == 1 && ctx.getChild(0).getText().equals("not")) {
            System.out.println("reach not C");
            return !visitC(ctx.c().get(0), context);

            // c1 = c2 // c1 eq c2 cases
        } else if (ctx.getChildCount() == 3 && ctx.r().size() == 2
                && (ctx.getChild(1).getText().equals("=") || ctx.getChild(1).getText().equals("eq"))) {
            System.out.println("reach eq C");
            List<Node> list1 = (List<Node>) visitXQ(ctx.r(0), context);
            List<Node> list2 = (List<Node>) visitXQ(ctx.r(1), context);

            for (Node n1 : list1) {
                for (Node n2 : list2) {
                    if (n1.isEqualNode(n2)) {
                        return true;
                    }
                }
            }
            return false;

            // c1 == c2 // c1 is c2 cases
        } else if (ctx.getChildCount() == 3 && ctx.r().size() == 2
                && (ctx.getChild(1).getText().equals("==") || ctx.getChild(1).getText().equals("is"))) {
            System.out.println("reach is");
            List<Node> list1 = (List<Node>) visitXQ(ctx.r(0), context);
            List<Node> list2 = (List<Node>) visitXQ(ctx.r(1), context);

            for (Node n1 : list1) {
                for (Node n2 : list2) {
                    if (n1.isSameNode(n2)) {
                        return true;
                    }
                }
            }
            return false;

            // empty(c) case
        } else if (ctx.getChildCount() == 4 && ctx.r().size() == 1
                && (ctx.getChild(0).getText().equals("empty") && ctx.getChild(1).getText().equals("(")
                && ctx.getChild(3).getText().equals(")"))) {
            List<Node> list = (List<Node>) visitXQ(ctx.r(0), context);
            System.out.println("Reach Empty");
            System.out.println(list.size());
            return list.isEmpty();
        }

        // some... satisfies... case
        else if (ctx.getChild(0).getText().equals("some")) {
            System.out.println("Reach some...satisfy..");
            Map<String, List<Node>> copyContext = new HashMap<>(context);
            return visitSome(ctx, copyContext, 0);
        } else {
            System.out.println("Weird");
        }
        return true;
    }

    public boolean visitSome(XQueryParser.CContext cContext, Map<String, List<Node>> context,
                             int index) {
    /* evaluates some... satisfies... condition */
        System.out.println("Visit some");

        if (index == cContext.r().size()) {
            return visitC(cContext.c(0), context);
        }

        String var_name = cContext.FN(index).getText();
        List<Node> temp = (List<Node>) visitXQ(cContext.r(index), context);
        boolean result = false;

        for (Node n : temp) {
            List<Node> list = new ArrayList<>();
            list.add(n);
            context.put(var_name, list);
            result = result || visitSome(cContext, context, index + 1);
            if (result) {
                break;
            }
        }
        return result;
    }

    public Object visitAP(XQueryParser.ApContext ctx) {
    /* evaluates an absolute path */

        // retrieve the filename
        System.out.println(ctx.FN());
        String filename = (String) ctx.FN().getText();

        // open the xml file
        // retrieve the document's root
        System.out.println("Opening" + filename);
        Node actualroot = null;
        Node root = null;
        Document doc;
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            factory.setIgnoringElementContentWhitespace(true);
            DocumentBuilder builder = factory.newDocumentBuilder();

            doc = builder.parse(new File(filename));
            System.out.println("Path:" + new File(filename).getAbsolutePath());
            doc.getDocumentElement().normalize();
            // root of the XML tree
            root = doc.getDocumentElement();
            // root of the document
            actualroot = root.getParentNode();

            System.out.println("Root element :" + root.getNodeName());
            System.out.println("Root Doc element :" + actualroot.getNodeName());

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        List<Node> inputNodes = new ArrayList<>();

        // doc(fileName)/rp case
        if (ctx.getChild(3).getText().equals("/")) {
            inputNodes.add(actualroot);
            List<Node> list = (List) visitRp(ctx.rp(), inputNodes);
            for (Node n : list) {
                System.out.println("final result:" + n.getNodeName());
            }
            return list;

            // doc(fileName)//rp case
        } else {
            System.out.println("reach // at root");

            // add root node and all its descendants
            Queue<Node> queue = new LinkedList<>();
            queue.offer(actualroot);
            while (!queue.isEmpty()) {
                Node curr = queue.poll();
                inputNodes.add(curr);
                NodeList nodeList = curr.getChildNodes();
                for (int i = 0; i < nodeList.getLength(); i++) {
                    queue.offer(nodeList.item(i));
                }
            }

            // call visitRp
            // evaluate the relative path on the list of input nodes
            return visitRp(ctx.rp(), inputNodes);
        }
    }

    public Object visitRp(XQueryParser.RpContext ctx, List<Node> nodes) {
    /* evaluates a relative path */

        System.out.println(ctx.getText());

        // rp1/rp2 case
        if (ctx.getChildCount() == 3 && ctx.getChild(1).getText().equals("/")) {
            System.out.println("Reach /");
            List<Node> list1 = (List<Node>) visitRp(ctx.rp(0), nodes);
            return unique(visitRp(ctx.rp(1), list1));

            // rp1//rp2 case
        } else if (ctx.getChildCount() == 3 && ctx.getChild(1).getText().equals("//")) {
            System.out.println("Reach //");

            List<Node> list1 = (List<Node>) visitRp(ctx.rp(0), nodes);
            List<Node> all_dec = new ArrayList<>();

            for (Node n : list1) {
                all_dec.add(n);
            }
            Queue<Node> queue = new LinkedList<>();
            // get all descendants
            for (Node n : list1) {
                NodeList nodeList = n.getChildNodes();
                for (int i = 0; i < nodeList.getLength(); i++) {
                    queue.offer(nodeList.item(i));
                }
            }
            while (!queue.isEmpty()) {
                Node curr = queue.poll();
                all_dec.add(curr);
                NodeList nodeList1 = curr.getChildNodes();
                for (int j = 0; j < nodeList1.getLength(); j++) {
                    queue.offer(nodeList1.item(j));
                }
            }

            all_dec = unique(all_dec);
            return unique(visitRp(ctx.rp(1), all_dec));

            // (rp) case
        } else if (ctx.getChildCount() == 3 && ctx.getChild(0).getText().equals("(") && ctx.getChild(2).getText().equals(")")) {
            System.out.println("Reach ()");
            return visitRp(ctx.rp(0), nodes);

            // rp1,rp2 case
        } else if (ctx.getChildCount() == 3 && ctx.getChild(1).getText().equals(",")) {
            System.out.println("Reach ,");

            List<Node> list1 = (List<Node>) visitRp(ctx.rp(0), nodes);
            List<Node> list2 = (List<Node>) visitRp(ctx.rp(1), nodes);
            list1.addAll(list2);
            return list1;

            // . case
        } else if (ctx.getChildCount() == 1 && ctx.getChild(0).getText().equals(".")) {
            System.out.println("Reach .");
            return new ArrayList<>(nodes);

            // * case
        } else if (ctx.getChildCount() == 1 && ctx.getChild(0).getText().equals("*")) {
            System.out.println("Reach *");
            List<Node> children = new ArrayList<>();
            for (Node n : nodes) {
                NodeList nodeList = n.getChildNodes();
                System.out.println("c:" + nodeList.getLength());

                for (int i = 0; i < nodeList.getLength(); i++) {
                    //TODO double play tag get returned
                    if (nodeList.item(i).getNodeType() == Node.ATTRIBUTE_NODE) {
                        continue;
                    }
                    System.out.println("count:" + nodeList.item(i).getChildNodes().getLength());
                    children.add(nodeList.item(i));
                }
            }
            return children;

            // .. case
        } else if (ctx.getChildCount() == 1 && ctx.getChild(0).getText().equals("..")) {
            List<Node> parents = new ArrayList<>();
            for (Node n : nodes) {
                if (n.getParentNode() == null) {
                    continue;
                }
                parents.add(n.getParentNode());
            }
            return parents;

            // text() case
        } else if (ctx.getChildCount() == 1 && ctx.getChild(0).getText().equals("text()")) {
            System.out.println("Reach text()");
            List<Node> textNodes = new ArrayList<>();
            for (Node n : nodes) {
                NodeList nodeList = n.getChildNodes();
                for (int i = 0; i < nodeList.getLength(); i++) {
                    Node textNode = nodeList.item(i);
                    if (textNode.getNodeType() == Node.TEXT_NODE) {
                        textNodes.add(textNode);
                    }
                }
            }
            return textNodes;

            // rp[f] case
        } else if (ctx.getChildCount() == 4
                && ctx.rp().size() == 1 && ctx.f() != null
                && ctx.getChild(1).getText().equals("[")
                && ctx.getChild(3).getText().equals("]")) {
            System.out.println("Reach F");
            List<Node> ret = new ArrayList<>();
            List<Node> outPut = (List<Node>) visitRp(ctx.rp(0), nodes);
            List<Boolean> fOutput = (List<Boolean>) visitF(ctx.f(), outPut);

            for (int i = 0; i < fOutput.size(); i++) {
                if (fOutput.get(i)) {
                    ret.add(outPut.get(i));
                }
            }
            return ret;

            // @attName case
        } else if (ctx.getChildCount() == 2 && ctx.getChild(0).getText().equals("@")) {
            System.out.println("@att");
            String att_name = ctx.getChild(1).getText();
            List<Node> ret = new ArrayList<>();

            for (Node n : nodes) {
                if (n.getAttributes() != null && n.getAttributes().getNamedItem(att_name) != null) {
                    ret.add(n.getAttributes().getNamedItem(att_name));
                }
            }
            return ret;

            // tagName case
        } else if (ctx.getChildCount() == 1) {
            System.out.println("Reach TagName, try to find:" + ctx.FN().getText());
            List<Node> result = new ArrayList<>();

            for (Node n : nodes) {
                NodeList nodeList = n.getChildNodes();
                for (int i = 0; i < nodeList.getLength(); i++) {
                    Node child = nodeList.item(i);
                    if (child.getNodeName().equals(ctx.FN().getText())) {
                        result.add(child);
                    }
                }
            }
            return result;
        }

        // no node reached
        System.out.println("Unreachable");
        return new ArrayList<>();
    }

    public Object visitF(XQueryParser.FContext ctx, List<Node> nodes) {
    /* evaluates the path filter */
        System.out.println(ctx.getText());
        List<Boolean> ret = new ArrayList<>();

        // rp case
        if (ctx.getChildCount() == 1 && ctx.rp().size() == 1) {
            System.out.println("reach F");
            for (Node n : nodes) {
                List<Node> input = new ArrayList<>();
                input.add(n);
                List<Node> result = (List<Node>) visitRp(ctx.rp(0), input);
                ret.add(!result.isEmpty());
            }

            // (f) case
        } else if (ctx.getChildCount() == 3 && ctx.f().size() == 1
                && ctx.getChild(0).getText().equals("(")
                && ctx.getChild(2).getText().equals(")")
                ) {
            System.out.println("reach(F)");
            return visitF(ctx.f().get(0), nodes);

            // f1 and f2 case
        } else if (ctx.getChildCount() == 3 && ctx.f().size() == 2 && ctx.getChild(1).getText().equals("and")) {
            System.out.println("reach F and F");
            List<Boolean> list1 = (List<Boolean>) visitF(ctx.f().get(0), nodes);
            List<Boolean> list2 = (List<Boolean>) visitF(ctx.f().get(1), nodes);
            for (int i = 0; i < list1.size(); i++) {
                ret.add(list1.get(i) && list2.get(i));
            }

            // f1 or f2 case
        } else if (ctx.getChildCount() == 3 && ctx.f().size() == 2 && ctx.getChild(1).getText().equals("or")) {
            System.out.println("reach F or F");
            List<Boolean> list1 = (List<Boolean>) visitF(ctx.f().get(0), nodes);
            List<Boolean> list2 = (List<Boolean>) visitF(ctx.f().get(1), nodes);
            for (int i = 0; i < list1.size(); i++) {
                ret.add(list1.get(i) || list2.get(i));
            }

            // not f case
        } else if (ctx.getChildCount() == 2 && ctx.f().size() == 1 && ctx.getChild(0).getText().equals("not")) {
            System.out.println("reach not F");
            List<Boolean> list = (List<Boolean>) visitF(ctx.f().get(0), nodes);
            for (int i = 0; i < list.size(); i++) {
                ret.add(!list.get(i));
            }

            // rp1 = rp2 // rp1 eq rp2 cases
        } else if (ctx.getChildCount() == 3 && ctx.rp().size() == 2
                && (ctx.getChild(1).getText().equals("=") || ctx.getChild(1).getText().equals("eq"))) {
            System.out.println("reach eq");

            for (Node n : nodes) {
                List<Node> input = new ArrayList<>();
                input.add(n);
                List<Node> list1 = (List<Node>) visitRp(ctx.rp(0), input);
                List<Node> list2 = (List<Node>) visitRp(ctx.rp(1), input);
                boolean found = false;
                for (Node n1 : list1) {
                    for (Node n2 : list2) {
                        if (n1.isEqualNode(n2)) {
                            found = true;
                            break;
                        }
                    }
                    if (found) {
                        break;
                    }
                }
                ret.add(found);
            }

            // rp1 == rp2 // rp1 is rp2 cases
        } else if (ctx.getChildCount() == 3 && ctx.rp().size() == 2
                && (ctx.getChild(1).getText().equals("==") || ctx.getChild(1).getText().equals("is"))) {
            System.out.println("reach is");

            for (Node n : nodes) {
                List<Node> input = new ArrayList<>();
                input.add(n);
                List<Node> list1 = (List<Node>) visitRp(ctx.rp(0), input);
                List<Node> list2 = (List<Node>) visitRp(ctx.rp(1), input);
                boolean found = false;
                for (Node n1 : list1) {
                    for (Node n2 : list2) {
                        if (n1.isSameNode(n2)) {
                            found = true;
                            break;
                        }
                    }
                    if (found) {
                        break;
                    }
                }
                ret.add(found);
            }

            // no case for filter found, shouldn't happen
        } else {
            System.out.println("Weird");
        }
        return ret;
    }

    /* helper functions */

    public List<Node> unique(Object o) {
    /* remove duplicates in an input list */
        List<Node> list = (List<Node>) o;
        Set<Node> set = new HashSet<>();
        for (Node n : list) {
            set.add(n);
        }
        return new ArrayList<>(set);
    }

    public Node makeText(String s) {
    /* create a text node */
        Document xmlDoc = new DocumentImpl();
        return xmlDoc.createTextNode(s);
    }

    public Node makeTag(String s, List<Node> nodes) {
    /* create a node element with the given tag and list of children nodes */
        Document xmlDoc = new DocumentImpl();
        Node root = xmlDoc.createElement(s);
        for (Node n : nodes) {
//            System.out.println("!:" + ParserTest.nodeToString(n));
            root.appendChild(xmlDoc.importNode(n, true));
            System.out.println("Make tag clone:" + n.getNodeName());
        }
        return root;
    }

}
