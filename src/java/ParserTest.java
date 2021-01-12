import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;
import org.w3c.dom.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

public class ParserTest {

    public static void main(String[] args) throws Exception {
        String query = args[0]; //input query provided in a txt file
        String flag = args[1];  //query evaluation using a -L left or -B bushy join plan
        System.out.println("flag:" + flag);

        //read the input query
        String content = readFile(System.getProperty("user.dir")+ "/" + query, Charset.defaultCharset());

        //parse the input query
        CharStream in = CharStreams.fromString(content);
        XQueryLexer lexer = new XQueryLexer(in);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        XQueryParser parser = new XQueryParser(tokens);
        ParseTree tree = parser.r();
        MyXQueryVisitor aVisitor = new MyXQueryVisitor();

        //perform rewriting to a bushy plan if indicated
        String rewrittenQuery = null;
        List<Node> list;
        try {
            aVisitor.rewriting = true;
            if(flag.equals("-B")){
                aVisitor.isL = false;
            }
            rewrittenQuery = (String) aVisitor.visit(tree);
            writeFile("rewrittenQuery.txt", rewrittenQuery);
            System.out.println("rewriting:" + rewrittenQuery);
        }catch (Exception e){
            e.printStackTrace();
            System.out.println("This query cannot be rewritten, execute it directly.");
        }

        //evaluate the rewritten query
        if(rewrittenQuery != null){
            in = CharStreams.fromString((String)rewrittenQuery);
            lexer = new XQueryLexer(in);
            tokens = new CommonTokenStream(lexer);
            parser = new XQueryParser(tokens);
            tree = parser.r();
            MyXQueryVisitor evaluator = new MyXQueryVisitor();
            evaluator.rewriting = false;
            list = (List<Node>)evaluator.visit(tree);
        //evaluate the original query without rewriting
        }else{
            aVisitor = new MyXQueryVisitor();
            aVisitor.rewriting = false;
            list = (List<Node>) aVisitor.visit(tree);
        }

        StringBuilder sb = new StringBuilder();

        for(Node n: list){
            if(n.getNodeType() == Node.TEXT_NODE){
                System.out.println("Output: " + n.getTextContent());
                sb.append(n.getTextContent());
                sb.append("\n");
            }
            else{
                System.out.println(nodeToString(n));
                sb.append(nodeToString(n));
                sb.append("\n");
            }
        }

        System.out.println("Output size: " + list.size());
        sb.append("Output size: " + list.size());
        //write the output of query evaluation to the result.txt file
        writeFile("result.txt", sb.toString());
    }

    // taken from https://stackoverflow.com/questions/6534182/java-getting-all-content-of-a-xml-node-as-string
    public static String nodeToString(Node node) {
        /* Prints a node in a clear format */
        StringWriter sw = new StringWriter();
        try {
            Transformer t = TransformerFactory.newInstance().newTransformer();
            t.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
            t.transform(new DOMSource(node), new StreamResult(sw));
        } catch (TransformerException te) {
            System.out.println("nodeToString Transformer Exception");
        }
        return sw.toString();
    }

    static String readFile(String path, Charset encoding)
            throws IOException
    {/* Reads from an input file */
        byte[] encoded = Files.readAllBytes(Paths.get(path));
        return new String(encoded, encoding);
    }

    static void writeFile(String fileName, String output){
        try {/* Writes to an output file */
            FileWriter myWriter = new FileWriter(fileName);
            myWriter.write(output);
            myWriter.close();
            System.out.println("Successfully wrote to the file.");
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

}

