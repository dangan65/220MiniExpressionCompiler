import java.util.*;

/** Lexer
 * 
 * Purpose is to read the raw input string
 * and convert it into a list of tokens that the parser can understand.
 *
 * It recognizes:
 *   - Integer numbers
 *   - Operators: +, -, *, /
 *   - Parentheses: (, )
 *   - Whitespace )
 *
 * If an invalid character appears, the lexer throws a RuntimeException
 */



public class Lexer {
    private final String input;
    private final int length;
    private int pos = 0;

    public Lexer(String input) {
        this.input = input;
        this.length = input.length();
    }

     /**
     * Converts the input string into a list of tokens.
     */
    public List<Token> tokenize() {
        List<Token> tokens = new ArrayList<>();

        while (!isAtEnd()) {
            char c = peek();

            if (Character.isWhitespace(c)) {
                advance();
                continue;
            }

            int startPos = pos;

            // Numbers: one or more digits
            if (Character.isDigit(c)) {
                tokens.add(numberToken());
            
             // Operators and parentheses
            } else {
                switch (c) {
                    case '+': tokens.add(simpleToken(TokenType.PLUS, "+", startPos)); advance(); break;
                    case '-': tokens.add(simpleToken(TokenType.MINUS, "-", startPos)); advance(); break;
                    case '*': tokens.add(simpleToken(TokenType.STAR, "*", startPos)); advance(); break;
                    case '/': tokens.add(simpleToken(TokenType.SLASH, "/", startPos)); advance(); break;
                    case '(': tokens.add(simpleToken(TokenType.LPAREN, "(", startPos)); advance(); break;
                    case ')': tokens.add(simpleToken(TokenType.RPAREN, ")", startPos)); advance(); break;
                    default:
                        throw new RuntimeException("Unexpected character '" + c + "' at position " + pos);
                }
            }
        }

        // Add end-of-file marker token
        tokens.add(new Token(TokenType.EOF, "", null, pos));
        return tokens;
    }

    private boolean isAtEnd() { return pos >= length; }
    private char peek() { return input.charAt(pos); }
    private char advance() { return input.charAt(pos++); }

    // Creates a simple single-character token (operators, parentheses)
    private Token simpleToken(TokenType type, String lexeme, int pos) {
        return new Token(type, lexeme, null, pos);
    }

    // Reads an integer literal 
    private Token numberToken() {
        int start = pos;
        // Walk digits
        while (!isAtEnd() && Character.isDigit(peek())) advance();
        String lex = input.substring(start, pos);
        return new Token(TokenType.NUMBER, lex, Integer.parseInt(lex), start);
    }
}
