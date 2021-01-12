import org.w3c.dom.*;
import javax.xml.parsers.*;
import java.io.*;
import java.util.*;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;


public class NewVisitor extends XPathBaseVisitor<Object> {

    @Override public Object visitR(XPathParser.RContext ctx) {
        /* evaluates the absolute path */

        // retrieve the filename
        System.out.println(ctx.FN());
        String filename = (String) ctx.FN().getText();

        // open the xml file
        // retrieve the document's root
        System.out.println("Opening" + filename);
        //String prefix = "/Users/ryouyasachi/Downloads/XQueryReal/src/";
        Node actualroot = null;
        Node root = null;
        Document doc;
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            factory.setIgnoringElementContentWhitespace(true);
            DocumentBuilder builder = factory.newDocumentBuilder();

            //doc = builder.parse(new File(prefix+filename));
            doc = builder.parse(new File(filename));
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
        if(ctx.getChild(3).getText().equals("/")){
            inputNodes.add(actualroot);
            List<Node> list = (List)visitRp(ctx.rp(), inputNodes);
//            System.out.println(list.size());
            for(Node n : list){
                System.out.println("final result:" + n.getNodeName());
            }
            return list;

        // doc(fileName)//rp case 
        }else {
            System.out.println("reach // at root");

            // add root node and all its descendants
            Queue<Node> queue = new LinkedList<>();
            queue.offer(actualroot);
            while(!queue.isEmpty()){
                Node curr = queue.poll();
                if(!curr.getNodeName().equals("#text") && !curr.isSameNode(actualroot)){

                    inputNodes.add(curr);
                }
                NodeList nodeList = curr.getChildNodes();
                for(int i = 0; i < nodeList.getLength(); i ++){
                    queue.offer(nodeList.item(i));
                }
            }

        // call visitRp 
        // evaluate the relative path on the list of input nodes
        return visitRp(ctx.rp(), inputNodes);
        }
    }


    public Object visitRp(XPathParser.RpContext ctx, List<Node> nodes) {
        /* evaluates the relative path */

        System.out.println(ctx.getText());

        // rp1/rp2 case
        if(ctx.getChildCount() == 3 && ctx.getChild(1).getText().equals("/")){
            System.out.println("Reach /");
            List<Node> list1 = (List<Node>) visitRp(ctx.rp(0), nodes);
            return unique(visitRp(ctx.rp(1),list1));

        // rp1//rp2 case    
        } else if(ctx.getChildCount() == 3 && ctx.getChild(1).getText().equals("//")){
            System.out.println("Reach //");
            List<Node> list1 = (List<Node>) visitRp(ctx.rp(0), nodes);
            List<Node> all_dec = new ArrayList<>();

            // get all descendants
            for(Node n : list1) {
                NodeList nodeList = n.getChildNodes();
                for (int i = 0; i < nodeList.getLength(); i++) {
                    Queue<Node> queue = new LinkedList<>();
                    queue.offer(nodeList.item(i));
                    while(!queue.isEmpty()){
                        Node curr = queue.poll();
                        if(!curr.getNodeName().equals("#text")){
                            all_dec.add(curr);
                        }
                        NodeList nodeList1 = curr.getChildNodes();
                        for(int j = 0; j < nodeList1.getLength(); j ++){
                            queue.offer(nodeList1.item(j));
                        }
                    }
                }
            }

            all_dec = unique(all_dec);
            return unique(visitRp(ctx.rp(1), all_dec));

        // (rp) case 
        } else if(ctx.getChildCount() == 3 && ctx.getChild(0).getText().equals("(") && ctx.getChild(2).getText().equals(")") ){
            System.out.println("Reach ()");
            return visitRp(ctx.rp(0), nodes);

        // rp1,rp2 case 
        } else if(ctx.getChildCount() == 3 && ctx.getChild(1).getText().equals(",") ){
            System.out.println("Reach ,");

            List<Node> list1 = (List<Node>) visitRp(ctx.rp(0), nodes);
            List<Node> list2 = (List<Node>) visitRp(ctx.rp(1), nodes);
            list1.addAll(list2);
            return list1;

        // . case
        } else if(ctx.getChildCount() == 1 && ctx.getChild(0).getText().equals(".")){
            System.out.println("Reach .");
            return new ArrayList<>(nodes);

        // * case
        } else if(ctx.getChildCount() == 1 && ctx.getChild(0).getText().equals("*")) {
            System.out.println("Reach *");
            List<Node> children = new ArrayList<>();
            for (Node n : nodes) {
                NodeList nodeList = n.getChildNodes();
                System.out.println("c:" + nodeList.getLength());

                for (int i = 0; i < nodeList.getLength(); i++) {
                    //TODO double play tag get returned
                    if(nodeList.item(i).getNodeType() == Node.TEXT_NODE || nodeList.item(i).getNodeType() == Node.ATTRIBUTE_NODE){
                        continue;
                    }
                    System.out.println("count:" + nodeList.item(i).getChildNodes().getLength());
                    children.add(nodeList.item(i));
                }
            }
            return children;

        // .. case
        } else if(ctx.getChildCount() == 1 && ctx.getChild(0).getText().equals("..")){
            List<Node> parents = new ArrayList<>();
            for (Node n : nodes) {
                if(n.getParentNode() == null){
                    continue;
                }
                parents.add(n.getParentNode());
            }
            return parents;

        // text() case
        } else if(ctx.getChildCount() == 1 && ctx.getChild(0).getText().equals("text()")){
            System.out.println("Reach text()");
            List<Node> textNodes = new ArrayList<>();
            for (Node n : nodes) {
                NodeList nodeList = n.getChildNodes();
                for (int i = 0; i < nodeList.getLength(); i++) {
                    Node textNode = nodeList.item(i);
                    if(textNode.getNodeType() == Node.TEXT_NODE){
                        textNodes.add(textNode);
                    }
                }
            }
            return textNodes;

        // rp[f] case
        }else if(ctx.getChildCount() == 4
                && ctx.rp().size() == 1 && ctx.f() != null
                && ctx.getChild(1).getText().equals("[")
                && ctx.getChild(3).getText().equals("]")){
            System.out.println("Reach F");
            List<Node> ret = new ArrayList<>();
            List<Node> outPut = (List<Node>) visitRp(ctx.rp(0), nodes);
            List<Boolean> fOutput = (List<Boolean>) visitF(ctx.f(), outPut);

            for(int i = 0; i < fOutput.size(); i++){
                if(fOutput.get(i)){
                    ret.add(outPut.get(i));
                }
            }
            return ret;

        // @attName case
        }else if(ctx.getChildCount() == 2 && ctx.getChild(0).getText().equals("@")){
            System.out.println("@att");
            String att_name = ctx.getChild(1).getText();
            List<Node> ret = new ArrayList<>();

            for(Node n : nodes){
                if(n.getAttributes() != null && n.getAttributes().getNamedItem(att_name) != null){
                    ret.add(n.getAttributes().getNamedItem(att_name));
                }
            }
            return ret;

        // tagName case
        }else if(ctx.getChildCount() == 1){
            System.out.println("Reach TagName");
            List<Node> result = new ArrayList<>();

            for (Node n : nodes){
                NodeList nodeList = n.getChildNodes();
                for(int i = 0; i < nodeList.getLength(); i ++){
                    Node child = nodeList.item(i);
                    if(child.getNodeName().equals(ctx.FN().getText())){
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

    public Object visitF(XPathParser.FContext ctx, List<Node> nodes) {
        /* evaluates the path filter */
        System.out.println(ctx.getText());
        List<Boolean> ret = new ArrayList<>();

        // rp case
        if(ctx.getChildCount() == 1 && ctx.rp().size() == 1){
            System.out.println("reach F");
            for(Node n : nodes){
                List<Node> input = new ArrayList<>();
                input.add(n);
                List<Node> result = (List<Node>) visitRp(ctx.rp(0), input);
                ret.add(!result.isEmpty());
            }

        // (f) case
        }else if(ctx.getChildCount() == 3 && ctx.f().size() == 1
                && ctx.getChild(0).getText().equals("(")
                && ctx.getChild(2).getText().equals(")")
                ){
            System.out.println("reach(F)");
            return visitF(ctx.f().get(0), nodes);

        // f1 and f2 case
        }else if(ctx.getChildCount() == 3 && ctx.f().size() == 2 && ctx.getChild(1).getText().equals("and")){
            System.out.println("reach F and F");
            List<Boolean> list1 = (List<Boolean>) visitF(ctx.f().get(0), nodes);
            List<Boolean> list2 = (List<Boolean>) visitF(ctx.f().get(1), nodes);
            for(int i = 0; i < list1.size(); i++){
                ret.add(list1.get(i) && list2.get(i));
            }

        // f1 or f2 case
        }else if(ctx.getChildCount() == 3 && ctx.f().size() == 2 && ctx.getChild(1).getText().equals("or")){
            System.out.println("reach F or F");
            List<Boolean> list1 = (List<Boolean>) visitF(ctx.f().get(0), nodes);
            List<Boolean> list2 = (List<Boolean>) visitF(ctx.f().get(1), nodes);
            for(int i = 0; i < list1.size(); i++){
                ret.add(list1.get(i) || list2.get(i));
            }

        // not f case
        }else if(ctx.getChildCount() == 2 && ctx.f().size() == 1 && ctx.getChild(0).getText().equals("not")) {
            System.out.println("reach not F");
            List<Boolean> list = (List<Boolean>) visitF(ctx.f().get(0), nodes);
            for (int i = 0; i < list.size(); i++) {
                ret.add(!list.get(i));
            }

        // rp1 = rp2 // rp1 eq rp2 cases
        }else if(ctx.getChildCount() == 3 && ctx.rp().size() == 2
                &&(ctx.getChild(1).getText().equals("=") || ctx.getChild(1).getText().equals("eq"))){
            System.out.println("reach eq");

            for(Node n : nodes){
                List<Node> input = new ArrayList<>();
                input.add(n);
                List<Node> list1 = (List<Node>) visitRp(ctx.rp(0), input);
                List<Node> list2 = (List<Node>) visitRp(ctx.rp(1), input);
                boolean found = false;
                for(Node n1 : list1){
                    for(Node n2: list2){
                        if(n1.isEqualNode(n2)){
                            found = true;
                            break;
                        }
                    }
                    if(found){
                        break;
                    }
                }
                ret.add(found);
            }

        // rp1 == rp2 // rp1 is rp2 cases
        }else if(ctx.getChildCount() == 3 && ctx.rp().size() == 2
                &&(ctx.getChild(1).getText().equals("==") ||ctx.getChild(1).getText().equals("is"))){
            System.out.println("reach is");

            for(Node n : nodes){
                List<Node> input = new ArrayList<>();
                input.add(n);
                List<Node> list1 = (List<Node>) visitRp(ctx.rp(0), input);
                List<Node> list2 = (List<Node>) visitRp(ctx.rp(1), input);
                boolean found = false;
                for(Node n1 : list1){
                    for(Node n2: list2){
                        if(n1.isSameNode(n2)){
                            found = true;
                            break;
                        }
                    }
                    if(found){
                        break;
                    }
                }
                ret.add(found);
            }

        // no case for filter found, shouldn't happen
        }
        else {
            System.out.println("Weird");
        }
        return ret;
    }

    /* helper functions */

    public List<Node> unique(Object o){
        /* remove duplicates in an input list */
        List<Node> list = (List<Node>) o;
        Set<Node> set = new HashSet<>();
        for(Node n : list){
            set.add(n);
        }
        return new ArrayList<>(set);
    }

}