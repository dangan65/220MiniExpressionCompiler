/** AST Printer
 * 
 * This class walks through the Abstract Syntax Tree and prints it in a readable format.
 * Each level of indentation visually represents the structure of the parsed expression.
 */ 


public class AstPrinter {

    public void print(Expr e) {
        print(e, "", true);
    }

    private void print(Expr e, String prefix, boolean isLast) {
        String connector = isLast ? "└─ " : "├─ ";

        if (e instanceof NumberExpr) {
            NumberExpr n = (NumberExpr) e;
            System.out.println(prefix + connector + n.value);
            return;
        }

        if (e instanceof UnaryExpr) {
            UnaryExpr u = (UnaryExpr) e;
            System.out.println(prefix + connector + u.operator.lexeme);
            print(u.right, prefix + (isLast ? "   " : "│  "), true);
            return;
        }

        if (e instanceof BinaryExpr) {
            BinaryExpr b = (BinaryExpr) e;
            System.out.println(prefix + connector + b.operator.lexeme);

            print(b.left, prefix + (isLast ? "   " : "│  "), false);
            print(b.right, prefix + (isLast ? "   " : "│  "), true);
        }
    }
}
