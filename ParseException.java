/** ParseException
 * 
 * A exception type used to signal syntactic errors
 *
 * The parser throws this exception when the input does not match expected:
 *     - Missing parentheses
 *     - Unexpected operator
 *     - Extra tokens after a complete expression
 *     - Incorrect expression structure
 */
public class ParseException extends Exception {
    public ParseException(String msg) { super(msg); }
}
