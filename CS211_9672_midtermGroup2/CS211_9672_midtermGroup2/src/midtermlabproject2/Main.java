package midtermlabproject2;

import java.util.Scanner;

/** Menu-based console application: infix -> postfix conversion and postfix evaluation. */
public class Main {

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        boolean running = true;
        while (running) {
            System.out.println();
            System.out.println("==== Infix / Postfix Expression Tool ====");
            System.out.println("[1] Convert an infix expression to postfix");
            System.out.println("[2] Evaluate a postfix expression");
            System.out.println("[3] Exit");
            System.out.print("Choose an option: ");
            if (!in.hasNextLine()) break;
            String choice = in.nextLine().trim();
            switch (choice) {
                case "1": convertInfix(in); break;
                case "2": evaluatePostfix(in); break;
                case "3": running = false; System.out.println("Goodbye!"); break;
                default:  System.out.println("Invalid option. Please enter 1, 2, or 3.");
            }
        }
    }

    private static void convertInfix(Scanner in) {
        System.out.println("Operators: + - * / ^ (^ = exponentiation) and parentheses. No spaces. Example: ((A-(B+C))*D)^(E+F)");
        System.out.print("Infix expression: ");
        String infix = in.hasNextLine() ? in.nextLine().trim() : "";
        InfixToPostfix converter = new InfixToPostfix();
        try {
            String postfix = converter.convert(infix);
            System.out.println();
            System.out.println("Infix -> " + infix);
            TablePrinter.print(InfixToPostfix.HEADERS, converter.getRows());
            System.out.println("Postfix expression: " + postfix);
        } catch (InvalidExpressionException e) {
            System.out.println("Error: cannot convert the expression. " + e.getMessage());
        }
    }

    private static void evaluatePostfix(Scanner in) {
        System.out.println("Separate operands and operators with spaces. Use ^ for exponentiation. Example: 6 2 3 + - 3 8 2 / + * 2 ^ 3 +");
        System.out.print("Postfix expression: ");
        String postfix = in.hasNextLine() ? in.nextLine() : "";
        PostfixEvaluator evaluator = new PostfixEvaluator();
        try {
            double result = evaluator.evaluate(postfix);
            System.out.println();
            System.out.println("Postfix String -> " + postfix.trim());
            TablePrinter.print(PostfixEvaluator.HEADERS, evaluator.getRows());
            System.out.println("Result: " + PostfixEvaluator.format(result));
        } catch (InvalidExpressionException e) {
            System.out.println("Error: cannot evaluate the expression. " + e.getMessage());
        }
    }
}
