/** NumberExpr
 * 
 * Represents a number
 *
 * Examples:
 *     3
 *     42
 *     1000
 *
 * This is a leaf node
 */
public class NumberExpr extends Expr {
    public final int value;
    public NumberExpr(int value) { this.value = value; }
}
