import java.util.*;
/** MiniExpressionCompiler
 * 
 * Purpose is to serve as the main entry point for the mini expression compiler.
 *
 *   1. Read the raw input from the user
 *   2. Tokenize the input using the Lexer
 *   3. Parse the tokens into an Abstract Syntax Tree (AST)
 *   4. Print the AST in a readable tree format
 *   5. Evaluate the AST to produce a final numeric result
 *
 * Any errors that occur during this process (lexical, parsing, runtime)
 * are caught and displayed with readable messages.
 */

public class MiniExpressionCompiler {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        System.out.print("Enter expression: ");

        // Trim leading/trailing whitespace from user input
        String input = sc.nextLine().trim();

        // Handle empty input
        if (input.isEmpty()) {
            System.out.println("Error: Empty input provided");
            return;
        }

        try {
            // 1. Tokenization
            Lexer lx = new Lexer(input);
            List<Token> tokens = lx.tokenize();
            System.out.println("Tokens: " + tokens);

            // 2. Build AST
            Parser p = new Parser(tokens);
            Expr expr = p.parse();
            System.out.println("AST:"); 
            new AstPrinter().print(expr);

            // 3. Evaluation
            int result = new Evaluator().evaluate(expr);

            // 4. Output the final
            System.out.println("Result: " + result);

        // Catching all predictable error types   
        } catch (ParseException e) {
            System.out.println("Parse Error: " + e.getMessage());
        } catch (ArithmeticException e) {
            System.out.println("Arithmetic Error: " + e.getMessage());
        } catch (RuntimeException e) {
            System.out.println("Error: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Unexpected Error: " + e.getMessage());
        }
    }
}