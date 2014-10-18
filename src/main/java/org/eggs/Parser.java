package org.eggs;

import org.parboiled.Parboiled;
import org.parboiled.common.StringUtils;
import org.parboiled.parserunners.RecoveringParseRunner;
import org.parboiled.support.ParsingResult;
import org.parboiled.support.ToStringFormatter;
import org.parboiled.trees.GraphNode;

import java.util.Scanner;

import static org.parboiled.errors.ErrorUtils.printParseErrors;
import static org.parboiled.support.ParseTreeUtils.printNodeTree;
import static org.parboiled.trees.GraphUtils.printTree;

/**
 * Main Entry Point to the Parser
 */
public class Parser {
    public static void main(String[] args) {
        main(DateParser.class);
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
}
