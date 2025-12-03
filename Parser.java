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

    private Expr parseFactor() throws ParseException {
        // Only allow MINUS as unary operator (not PLUS)
        if (match(TokenType.MINUS))
            return new UnaryExpr(previous(), parseFactor());

        if (match(TokenType.NUMBER))
            return new NumberExpr(previous().intValue);

        if (match(TokenType.LPAREN)) {
            Expr e = parseExpression();
            if (!match(TokenType.RPAREN)) 
                throw new ParseException("Missing ')' at position " + peek().position);
            return e;
        }

        throw new ParseException("Unexpected token '" + peek().lexeme + "' at position " + peek().position);
    }

    private boolean match(TokenType... types) {
        for (TokenType t : types) if (check(t)) { advance(); return true; }
        return false;
    }
    private boolean check(TokenType t) { return peek().type == t; }
    private Token advance() { pos++; return previous(); }
    private Token peek() { return tokens.get(pos); }
    private Token previous() { return tokens.get(pos-1); }
}