import java.util.*;

/** Parser
 * ------
 * A recursive parser that converts a list of tokens into an AST according to
 * grammar rules for  arithmetic expressions:
 * 
 *   - Standard arithmetic precedence (* and / ,  + and -)
 *   - Left to right associativity for all binary operators
 *   - Unary minus 
 *
 * If the input does not match a ParseException is thrown.
 */

public class Parser {
    private final List<Token> tokens;
    private int pos = 0;

    public Parser(List<Token> tokens) { this.tokens = tokens; }

    public Expr parse() throws ParseException {
        Expr expr = parseExpression();
        if (peek().type != TokenType.EOF)
            throw new ParseException("Unexpected token '" + peek().lexeme + "' at position " + peek().position);
        return expr;
    }

    private Expr parseExpression() throws ParseException {
        Expr e = parseTerm();
        while (match(TokenType.PLUS, TokenType.MINUS))
            e = new BinaryExpr(e, previous(), parseTerm());
        return e;
    }

    private Expr parseTerm() throws ParseException {
        Expr e = parseFactor();
        while (match(TokenType.STAR, TokenType.SLASH))
            e = new BinaryExpr(e, previous(), parseFactor());
        return e;
    }

    // Factor — handles unary operators, numbers, parentheses
    private Expr parseFactor() throws ParseException {

        if (check(TokenType.PLUS) || check(TokenType.MINUS)) {
            Token unary = peek();

            // Detect ambiguous no-space sequences like 9--2
            if (pos >= 1) {
                Token before = tokens.get(pos - 1);
                if (before.type == TokenType.NUMBER) {
                    throw new ParseException(
                        "Ambiguous operator sequence '" +
                        before.lexeme + unary.lexeme +
                        "' at position " + unary.position + ". Use parentheses or spaces for clarity."
                    );
                }
            }

            advance();

            // Detect double unary (--2, +-2)
            if (check(TokenType.PLUS) || check(TokenType.MINUS)) {
                Token second = peek();
                throw new ParseException(
                    "Unexpected unary operator sequence '" +
                    unary.lexeme + second.lexeme +
                    "' at position " + second.position + "."
                );
            }

            return new UnaryExpr(unary, parseFactor());
        }

        // Number literal
        if (match(TokenType.NUMBER))
            return new NumberExpr(previous().intValue);

        // Parenthesized expression
        if (match(TokenType.LPAREN)) {
            Expr inner = parseExpression();
            if (!match(TokenType.RPAREN))
                throw new ParseException("Missing ')' at position " + peek().position);
            return inner;
        }

        // Unrecognized token
        throw new ParseException(
            "Unexpected token '" + peek().lexeme + "' at position " + peek().position
        );
    }

    private boolean match(TokenType... types) {
        for (TokenType t : types) if (check(t)) { advance(); return true; }
        return false;
    }

    private boolean check(TokenType t) { return peek().type == t; }

    private Token advance() { pos++; return previous(); }

    private Token peek() { return tokens.get(pos); }

    private Token previous() { return tokens.get(pos - 1); }

}