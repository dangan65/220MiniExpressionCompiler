/** Evaluator
 * 
 * The evaluator walks the tree using recursion:
 *   - evaluate(left subtree)
 *   - evaluate(right subtree)
 *   - apply the operator
 * 
 * The evaluator computes:
 *      - evaluate(3 + 2) → 5
 *      - evaluate(5) → 5
 *      - multiply results → 25
 */
public class Evaluator {
    public int evaluate(Expr e) {

        // Case 1: base case
        if (e instanceof NumberExpr) return ((NumberExpr)e).value;

        // Case 2: Unary expression
        if (e instanceof UnaryExpr) {
            UnaryExpr u = (UnaryExpr)e;
            int r = evaluate(u.right);
            return u.operator.type == TokenType.MINUS ? -r : r;
        }
        // Case 3: Binary expression
        if (e instanceof BinaryExpr) {
            BinaryExpr b = (BinaryExpr)e;

            // Recursively evaluate both sides of the binary operation
            int l = evaluate(b.left);
            int r = evaluate(b.right);
            switch (b.operator.type) {
                case PLUS: return l+r;
                case MINUS: return l-r;
                case STAR: return l*r;
                case SLASH: return l/r;
            }
        }
        throw new RuntimeException("Bad expression");
    }
}       
