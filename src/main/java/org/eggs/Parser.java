package org.eggs;


import org.eggs.nodes.Node;
import org.joda.time.DateTime;
import org.parboiled.Parboiled;
import org.parboiled.parserunners.RecoveringParseRunner;
import org.parboiled.support.ParsingResult;

import static org.parboiled.errors.ErrorUtils.printParseErrors;

public class Parser {

    private RecoveringParseRunner parseRunner;

    public Parser() {
        this(new ParsingContext());
    }

    public Parser(ParsingContext parsingContext) {
        DateParser parser = Parboiled.createParser(DateParser.class);
        parser.setParsingContext(parsingContext);
        this.parseRunner = new RecoveringParseRunner(parser.InputLine());
    }

    public DateTime parse(String input) throws ParseException {
        ParsingResult<Node> result = parseRunner.run(input);

        if (result.hasErrors()) {
            throw new ParseException("\nParse Errors:\n" + printParseErrors(result));
        } else {
            return result.resultValue.getDate();
        }
    }
}
