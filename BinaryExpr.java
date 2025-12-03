/** Binary Expr
 * 
 * Represents a binary operation in the AST
 * 
 * Examples:
 *    3 + 4
 *    10 - 2
 *    (3 + 2) * 5
 */

public class BinaryExpr extends Expr {
    public final Expr left;
    public final Token operator;
    public final Expr right;
    public BinaryExpr(Expr left, Token operator, Expr right) {
        this.left = left;
        this.operator = operator;
        this.right = right;
    }
}
