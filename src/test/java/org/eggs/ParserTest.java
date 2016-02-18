package org.eggs;

import org.eggs.nodes.Node;
import org.joda.time.DateTime;
import org.parboiled.Parboiled;
import org.parboiled.common.StringUtils;
import org.parboiled.parserunners.RecoveringParseRunner;
import org.parboiled.support.ParsingResult;
import org.parboiled.support.ToStringFormatter;
import org.parboiled.trees.GraphNode;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static org.parboiled.errors.ErrorUtils.printParseErrors;
import static org.parboiled.support.ParseTreeUtils.printNodeTree;
import static org.parboiled.trees.GraphUtils.printTree;

/**
 * Main Entry Point to the ParserTest
 */
public class ParserTest {
    private static final String WORKING_INPUT_FILE_NAME = "working_dates.txt";
    public static void main(String[] args) {
        println("Starting ParserTest");
        new ParserTest().run();
    }

    public void run() {
       Parser dateParser = new Parser();
       for(String input : readFromFile()) {
           ParsingResult<Node> result = dateParser.parse(input);
           if (result.resultValue != null) {
               System.out.printf("input: '%s' output: %s\n", input, result.resultValue.getDate());
           } else {
               System.out.printf("FAILED with input: '%s'\n", input);
           }
       }
    }

    private List<String> readFromFile() {
        try {
            InputStream is = this.getClass().getResourceAsStream(WORKING_INPUT_FILE_NAME);
            System.out.printf("xxxx: %s", is);
            BufferedReader reader = new BufferedReader(new InputStreamReader(this.getClass().getResourceAsStream(WORKING_INPUT_FILE_NAME)));
            List<String> inputs = new ArrayList<>();
            inputs.add(reader.readLine().replace("\n", ""));
            return inputs;
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    @SuppressWarnings({"unchecked"})
    public static <P extends DateParser> void main(Class<P> parserClass) {
        DateParser parser = Parboiled.createParser(parserClass);

        while (true) {
            System.out.print("Enter a date expression (single RETURN to exit)!\n");
            String input = new Scanner(System.in).nextLine();
            if (StringUtils.isEmpty(input)) break;

            ParsingResult<?> result = new RecoveringParseRunner(parser.InputLine()).run(input);

            if (result.hasErrors()) {
                System.out.println("\nParse Errors:\n" + printParseErrors(result));
            }

            Object value = result.parseTreeRoot.getValue();
            if (value != null) {
                String str = value.toString();
                int ix = str.indexOf('|');
                if (ix >= 0) str = str.substring(ix + 2); // extract value part of AST node toString()
                System.out.println(input + " = " + str + '\n');
            }
            if (value instanceof GraphNode) {
                System.out.println("\nAbstract Syntax Tree:\n" +
                        printTree((GraphNode) value, new ToStringFormatter(null)) + '\n');
            } else {
                System.out.println("\nParse Tree:\n" + printNodeTree(result) + '\n');
            }
        }
    }

    private static void println(String s) {
        System.out.println(s);
    }
}
