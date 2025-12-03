/** TokenType
 * 
 * Enumerates all possible types of tokens that the Lexer can produce.
 *
 * These token types are basic building blocks of arithmetic expressions.
 */

public enum TokenType {
    NUMBER,
    PLUS, MINUS, STAR, SLASH,
    LPAREN, RPAREN,
    EOF
}
