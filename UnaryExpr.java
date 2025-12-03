/**
 * UnaryExpr
 * ---------
 * Represents a unary operation
 * Structure:
 *     operator  → the token for '-' (unary negation)
 *     right     → the expression the operator applies to
 *
 * This node is evaluated recursively by the Evaluator. Only used for minus.
 */

public class UnaryExpr extends Expr {
    public final Token operator;
    public final Expr right;

    //Constructs the node 
    public UnaryExpr(Token operator, Expr right) {
        this.operator = operator;
        this.right = right;
    }
}
