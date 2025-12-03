/** Token
 * 
 * A token captures:
 *   - its type       (PLUS, NUMBER, STAR, etc.)
 *   - its lexeme     (the exact characters from the input)
 *   - its value      (for NUMBER tokens only)
 *   - its position   (index in the original input string)
 *
 * Example:
 *   Input: "35 + 2"
 *
 *   Tokens produced:
 *     NUMBER "35" (value = 35) at position 0
 *     PLUS   "+"               at position 3
 *     NUMBER "2"  (value = 2)  at position 5
 *
 * The Parser uses these tokens to build the AST.
 */

public class Token {

    //The category
    public final TokenType type;
    // Characters
    public final String lexeme;
    // Parsed integer value
    public final Integer intValue;
    // The index of the token's first character
    public final int position;

    // Constructor
    public Token(TokenType type, String lexeme, Integer intValue, int position) {
        this.type = type;
        this.lexeme = lexeme;
        this.intValue = intValue;
        this.position = position;
    }

    // Representation of Complete token
    @Override
    public String toString() {
        if (type == TokenType.NUMBER) return intValue.toString();
        return lexeme;
    }
}
