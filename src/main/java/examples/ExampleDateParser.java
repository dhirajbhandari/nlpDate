package examples;

import org.eggs.ParseException;
import org.eggs.Parser;
import org.parboiled.common.StringUtils;

import java.util.Scanner;

public class ExampleDateParser {
    private Parser parser;

    public ExampleDateParser() {
        this.parser = new Parser();
    }

    public static void main(String[] args) {
        new ExampleDateParser().parse("24 mar 2015");
        new ExampleDateParser().run(args);
    }

    public void run(String[] args) {
        while (true) {
            System.out.print("Enter a date expression (single RETURN to exit)!\n");
            String input = new Scanner(System.in).nextLine();
            if (StringUtils.isEmpty(input)) break;

            parse(input);
        }
    }

    public void parse(String input) {
        try {
            System.out.printf("text: '%s'  parsed: '%s'\n", input, parser.parse(input));
        } catch (ParseException parseException) {
            System.out.printf("error on parsing of text: '%s', reason: %s\n", input, parseException.getMessage());
        }
    }
}