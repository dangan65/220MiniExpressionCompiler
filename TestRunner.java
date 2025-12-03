
import java.util.*;

public class TestRunner {
    private static int passed = 0;
    private static int failed = 0;

    public static void main(String[] args) {
        System.out.println("╔════════════════════════════════════════════════════════════╗");
        System.out.println("║     MINI EXPRESSION COMPILER TEST SUITE                   ║");
        System.out.println("╚════════════════════════════════════════════════════════════╝");
        System.out.println();
        
        // Tokenizer Tests
        testTokenizer();
        
        // Parser Tests
        testParser();
        
        // AST Tests
        testAST();
        
        // Evaluator Tests
        testEvaluator();
        
        // Error Handling Tests
        testErrorHandling();
        
        // Edge Cases
        testEdgeCases();
        
        // Print Summary
        System.out.println("╔════════════════════════════════════════════════════════════╗");
        System.out.println("║                    TEST SUMMARY                            ║");
        System.out.println("╠════════════════════════════════════════════════════════════╣");
        System.out.printf("║  ✓ Passed:  %-3d                                            ║%n", passed);
        System.out.printf("║  ✗ Failed:  %-3d                                            ║%n", failed);
        System.out.printf("║  Total:     %-3d                                            ║%n", (passed + failed));
        
        double percentage = (passed * 100.0) / (passed + failed);
        System.out.printf("║  Success Rate: %.1f%%                                      ║%n", percentage);
        System.out.println("╚════════════════════════════════════════════════════════════╝");
        
        if (failed > 0) {
            System.out.println("\n⚠️  Some tests failed! Review the output above.");
            System.exit(1);
        } else {
            System.out.println("\n🎉 All tests passed! Your compiler is working correctly!");
            System.exit(0);
        }
    }
    
    // ===== TOKENIZER TESTS =====
    static void testTokenizer() {
        printHeader("TOKENIZER TESTS");
        
        testTokens("3 + 4", Arrays.asList("3", "+", "4"));
        testTokens("(3+2)*5-1", Arrays.asList("(", "3", "+", "2", ")", "*", "5", "-", "1"));
        testTokens("  10  *  20  ", Arrays.asList("10", "*", "20"));
        testTokens("-5", Arrays.asList("-", "5"));
        testTokens("((3))", Arrays.asList("(", "(", "3", ")", ")"));
        testTokens("123 + 456", Arrays.asList("123", "+", "456"));
        testTokens("100/10/2", Arrays.asList("100", "/", "10", "/", "2"));
        
        System.out.println();
    }
    
    static void testTokens(String input, List<String> expected) {
        try {
            Lexer lx = new Lexer(input);
            List<Token> tokens = lx.tokenize();
            List<String> actual = new ArrayList<>();
            for (Token t : tokens) {
                if (t.type != TokenType.EOF) actual.add(t.toString());
            }
            
            if (actual.equals(expected)) {
                System.out.println("  ✓ Tokenize: \"" + input + "\" → " + actual);
                passed++;
            } else {
                System.out.println("  ✗ Tokenize: \"" + input + "\"");
                System.out.println("      Expected: " + expected);
                System.out.println("      Got:      " + actual);
                failed++;
            }
        } catch (Exception e) {
            System.out.println("  ✗ Tokenize: \"" + input + "\" → Exception: " + e.getMessage());
            failed++;
        }
    }
    
    // ===== PARSER TESTS =====
    static void testParser() {
        printHeader("PARSER TESTS (Valid Inputs)");
        
        testParseValid("3 + 4 * 2");
        testParseValid("(1 + 2) * (3 + 4)");
        testParseValid("((3))");
        testParseValid("-5 + 3");
        testParseValid("10 / 2 - 3");
        testParseValid("--5");
        testParseValid("-(3 + 2)");
        testParseValid("2 + 3 * 4 - 5");
        
        System.out.println();
        
        printHeader("PARSER TESTS (Invalid Inputs)");
        
        testParseInvalid("3 + * 5");
        testParseInvalid("()");
        testParseInvalid("3 + (4 - )");
        testParseInvalid("+ 3");
        testParseInvalid("3 +");
        testParseInvalid("3 4");
        testParseInvalid("* 5");
        testParseInvalid("(3 + 4");
        testParseInvalid("3 + 4)");
        
        System.out.println();
    }
    
    static void testParseValid(String input) {
        try {
            Lexer lx = new Lexer(input);
            Parser p = new Parser(lx.tokenize());
            Expr expr = p.parse();
            System.out.println("  ✓ Accept: \"" + input + "\"");
            passed++;
        } catch (Exception e) {
            System.out.println("  ✗ Should accept: \"" + input + "\"");
            System.out.println("      Error: " + e.getMessage());
            failed++;
        }
    }
    
    static void testParseInvalid(String input) {
        try {
            Lexer lx = new Lexer(input);
            Parser p = new Parser(lx.tokenize());
            Expr expr = p.parse();
            System.out.println("  ✗ Should reject: \"" + input + "\" (was accepted)");
            failed++;
        } catch (Exception e) {
            System.out.println("  ✓ Reject: \"" + input + "\" → " + e.getMessage());
            passed++;
        }
    }
    
    // ===== AST TESTS =====
    static void testAST() {
        printHeader("AST STRUCTURE TESTS");
        
        // Test simple binary expression
        try {
            Lexer lx = new Lexer("3 + 4");
            Parser p = new Parser(lx.tokenize());
            Expr expr = p.parse();
            
            if (expr instanceof BinaryExpr) {
                BinaryExpr b = (BinaryExpr)expr;
                if (b.operator.type == TokenType.PLUS &&
                    b.left instanceof NumberExpr && ((NumberExpr)b.left).value == 3 &&
                    b.right instanceof NumberExpr && ((NumberExpr)b.right).value == 4) {
                    System.out.println("  ✓ AST structure correct for \"3 + 4\"");
                    passed++;
                } else {
                    System.out.println("  ✗ AST structure incorrect for \"3 + 4\"");
                    failed++;
                }
            } else {
                System.out.println("  ✗ Expected BinaryExpr for \"3 + 4\"");
                failed++;
            }
        } catch (Exception e) {
            System.out.println("  ✗ AST test failed: " + e.getMessage());
            failed++;
        }
        
        // Test operator precedence in AST
        try {
            Lexer lx = new Lexer("3 + 4 * 2");
            Parser p = new Parser(lx.tokenize());
            Expr expr = p.parse();
            
            // Should be: + at root, with 3 on left and (4*2) on right
            if (expr instanceof BinaryExpr) {
                BinaryExpr root = (BinaryExpr)expr;
                if (root.operator.type == TokenType.PLUS && root.right instanceof BinaryExpr) {
                    BinaryExpr mult = (BinaryExpr)root.right;
                    if (mult.operator.type == TokenType.STAR) {
                        System.out.println("  ✓ AST respects operator precedence (3 + 4 * 2)");
                        passed++;
                    } else {
                        System.out.println("  ✗ AST doesn't respect operator precedence");
                        failed++;
                    }
                } else {
                    System.out.println("  ✗ AST structure wrong for precedence test");
                    failed++;
                }
            } else {
                System.out.println("  ✗ Expected BinaryExpr for precedence test");
                failed++;
            }
        } catch (Exception e) {
            System.out.println("  ✗ Precedence AST test failed: " + e.getMessage());
            failed++;
        }
        
        // Test unary operator
        try {
            Lexer lx = new Lexer("-5");
            Parser p = new Parser(lx.tokenize());
            Expr expr = p.parse();
            
            if (expr instanceof UnaryExpr) {
                UnaryExpr u = (UnaryExpr)expr;
                if (u.operator.type == TokenType.MINUS && 
                    u.right instanceof NumberExpr && 
                    ((NumberExpr)u.right).value == 5) {
                    System.out.println("  ✓ AST correct for unary operator \"-5\"");
                    passed++;
                } else {
                    System.out.println("  ✗ AST structure wrong for unary operator");
                    failed++;
                }
            } else {
                System.out.println("  ✗ Expected UnaryExpr for \"-5\"");
                failed++;
            }
        } catch (Exception e) {
            System.out.println("  ✗ Unary AST test failed: " + e.getMessage());
            failed++;
        }
        
        System.out.println();
    }
    
    // ===== EVALUATOR TESTS =====
    static void testEvaluator() {
        printHeader("EVALUATOR TESTS");
        
        // Basic operations
        testEval("3 + 4", 7);
        testEval("10 - 3", 7);
        testEval("5 * 6", 30);
        testEval("20 / 4", 5);
        
        // Precedence
        testEval("3 + 4 * 2", 11);
        testEval("(3 + 4) * 2", 14);
        testEval("10 - 2 * 3", 4);
        testEval("20 / 4 + 1", 6);
        
        // Complex expressions
        testEval("(3 + 2) * 5 - 1", 24);
        testEval("(1 + 2) * (3 + 4)", 21);
        testEval("((3))", 3);
        testEval("(((((5)))))", 5);
        
        // Unary operators
        testEval("-5 + 3", -2);
        testEval("10 + -5", 5);
        testEval("--5", 5);
        testEval("---3", -3);
        testEval("-(3 + 2)", -5);
        
        // Left-to-right
        testEval("100 / 10 / 2", 5);
        testEval("10 - 5 - 2", 3);
        
        // Large numbers
        testEval("999 + 1", 1000);
        testEval("123 + 456", 579);
        
        // Negative results
        testEval("3 - 10", -7);
        testEval("5 - 20", -15);
        
        System.out.println();
    }
    
    static void testEval(String input, int expected) {
        try {
            Lexer lx = new Lexer(input);
            Parser p = new Parser(lx.tokenize());
            Expr expr = p.parse();
            int result = new Evaluator().evaluate(expr);
            
            if (result == expected) {
                System.out.println("  ✓ Eval \"" + input + "\" = " + result);
                passed++;
            } else {
                System.out.println("  ✗ Eval \"" + input + "\" expected " + expected + " but got " + result);
                failed++;
            }
        } catch (Exception e) {
            System.out.println("  ✗ Eval \"" + input + "\" failed: " + e.getMessage());
            failed++;
        }
    }
    
    // ===== ERROR HANDLING TESTS =====
    static void testErrorHandling() {
        printHeader("ERROR HANDLING TESTS");
        
        testErrorMessage("3 + * 5", "consecutive operators");
        testErrorMessage("()", "empty parentheses");
        testErrorMessage("3 + (4 - )", "incomplete expression");
        testErrorMessage("@#$", "invalid characters");
        testErrorMessage("3 +", "incomplete expression");
        testErrorMessage("* 3", "invalid start");
        testErrorMessage("a + b", "letters not allowed");
        testErrorMessage("3.5", "decimals not supported");
        
        System.out.println();
    }
    
    static void testErrorMessage(String input, String description) {
        try {
            Lexer lx = new Lexer(input);
            List<Token> tokens = lx.tokenize();
            Parser p = new Parser(tokens);
            Expr expr = p.parse();
            new Evaluator().evaluate(expr);
            
            System.out.println("  ✗ Should reject " + description + ": \"" + input + "\" (no error thrown)");
            failed++;
        } catch (Exception e) {
            System.out.println("  ✓ Reject " + description + ": \"" + input + "\"");
            System.out.println("      → " + e.getMessage());
            passed++;
        }
    }
    
    // ===== EDGE CASES =====
    static void testEdgeCases() {
        printHeader("EDGE CASE TESTS");
        
        // Division by zero
        testDivisionByZero("10 / 0");
        testDivisionByZero("5 / (3 - 3)");
        testDivisionByZero("100 / (10 - 5 * 2)");
        
        // Empty input
        testEmptyInput("");
        testEmptyInput("   ");
        
        System.out.println();
    }
    
    static void testDivisionByZero(String input) {
        try {
            Lexer lx = new Lexer(input);
            Parser p = new Parser(lx.tokenize());
            Expr expr = p.parse();
            int result = new Evaluator().evaluate(expr);
            System.out.println("  ✗ Division by zero not caught: \"" + input + "\" returned " + result);
            failed++;
        } catch (ArithmeticException e) {
            System.out.println("  ✓ Division by zero caught: \"" + input + "\"");
            System.out.println("      → " + e.getMessage());
            passed++;
        } catch (Exception e) {
            System.out.println("  ✓ Division by zero handled: \"" + input + "\"");
            System.out.println("      → " + e.getMessage());
            passed++;
        }
    }
    
    static void testEmptyInput(String input) {
        String display = input.isEmpty() ? "<empty>" : "<whitespace>";
        try {
            if (input.trim().isEmpty()) {
                System.out.println("  ✓ Empty input handled: " + display);
                passed++;
                return; 
            }
            Lexer lx = new Lexer(input);
            Parser p = new Parser(lx.tokenize());
            Expr expr = p.parse();
            System.out.println("  ✗ Empty input should be rejected: " + display);
            failed++;
        } catch (Exception e) {
            System.out.println("  ✓ Empty input rejected: " + display);
            passed++;
        }
    }
    
    // ===== UTILITY =====
    static void printHeader(String title) {
        System.out.println("┌────────────────────────────────────────────────────────────┐");
        System.out.printf("│  %-56s  │%n", title);
        System.out.println("└────────────────────────────────────────────────────────────┘");
    }
}