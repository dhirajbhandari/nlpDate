# nlpDate
natural date parser library for Java written using parboiled

#Using

```
String input = System.console().readLine("> ");
DateTime date = new Parser().parseDate(input);

if (date!= null) {
   System.out.printf("input: %s output: %s\n", input, date);
} else {
   System.out.printf("FAILED to parse input: %s\n", input);
}
```

#Building

##Requirements
JDK 1.7 or above
