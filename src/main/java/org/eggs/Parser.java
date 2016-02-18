package org.eggs;


import org.eggs.nodes.Node;
import org.joda.time.DateTime;
import org.parboiled.Parboiled;
import org.parboiled.common.StringUtils;
import org.parboiled.parserunners.BasicParseRunner;
import org.parboiled.parserunners.ParseRunner;
import org.parboiled.parserunners.RecoveringParseRunner;
import org.parboiled.parserunners.TracingParseRunner;
import org.parboiled.support.ParsingResult;

import static org.parboiled.errors.ErrorUtils.printParseErrors;
import static org.parboiled.support.ParseTreeUtils.printNodeTree;

public class Parser {
    private Logger log = Logger.getLogger(Parser.class);

    private ParseRunner parseRunner;

    public Parser() {
        this(new ParsingContext());
    }

    public Parser(ParsingContext parsingContext) {
        DateParser parser = Parboiled.createParser(DateParser.class);
        parser.setParsingContext(parsingContext);
        this.parseRunner = new BasicParseRunner(parser.InputLine());
        //this.parseRunner = new TracingParseRunner(parser.InputLine());
        //this.parseRunner = new RecoveringParseRunner(parser.InputLine());
    }

    public ParsingResult<Node> parse(String input) throws ParseException {
        ParsingResult<Node> result = parseRunner.run(input);

        if (result.hasErrors()) {
            throw new ParseException("\nParse Errors:\n" + printParseErrors(result));
        } else {
            return result;
        }
    }

    public DateTime parseDate(String input) {
        ParsingResult<Node> result = parse(input);
        Node value = result.parseTreeRoot.getValue();
        return value != null ? value.getDate() : null;
    }

    public static void main(String[] args) {
        example();
        //Parser parser = new Parser();
//        parser.runCli();
    }

    private void runCli() {
        while (true) {
            //System.out.print("Enter a date expression (single RETURN to exit)!\n");
           // String input = new Scanner(System.in).nextLine();
            String input = System.console().readLine("\nEnter a date expression (single RETURN to exit)!:> ");
            if (StringUtils.isEmpty(input)) break;

            try {
                System.out.printf("\ninput: '%s'    \n", input);
                ParsingResult<Node> result = parse(input);
                Node value = result.parseTreeRoot.getValue();
                if (value != null) {
                    System.console().printf(input + " = %s\n", value.getDate());
                }
                log.debug("\nParse Tree:\n" + printNodeTree(result) + '\n');
            } catch (Exception ex) {
                log.error("Exception while parsing", ex);
            }
        }
    }

    public static void example() {
        String input = System.console().readLine("> ");
        DateTime date = new Parser().parseDate(input);

        if (date!= null) {
            System.out.printf("%s = %s\n", input, date);
        } else {
            System.out.printf("FAILED to parse input: %s\n", input);
        }
    }
}
