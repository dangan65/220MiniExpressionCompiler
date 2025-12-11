# Mini Expression Compiler

A Java-based arithmetic expression compiler that demonstrates the core stages of
compilation: **tokenization**, **parsing**, **AST construction**, and **evaluation**.

The project uses a **recursive descent parser** to enforce operator precedence and
build a structured **Abstract Syntax Tree (AST)**, which is then evaluated to
produce a final result.

---

## Overview

The compiler supports:
- Integer numbers
- Binary operators: `+`, `-`, `*`, `/`
- Parentheses
- Unary minus (e.g., `-3`)
- Error detection with clear, descriptive messages

The program outputs:
- A token list
- Parsing result
- The generated AST in text form
- The final evaluated result

---

## Project Structure

| File | Description |
|-----|-------------|
| `MiniExpressionCompiler.java` | Main driver |
| `Lexer.java` | Tokenizer |
| `Parser.java` | Recursive descent parser |
| `Expr.java` | Base class for expressions |
| `NumberExpr.java` | Numeric literal node |
| `UnaryExpr.java` | Unary operator node |
| `BinaryExpr.java` | Binary operator node |
| `Evaluator.java` | AST evaluator |
| `AstPrinter.java` | Tree printer |
| `Token.java` | Token definition |
| `TokenType.java` | Token types |
| `ParseException.java` | Error handling |

---

## Features

### Tokenization

The lexer converts the input string into tokens such as numbers, operators, and
parentheses.

**Example**

**Input**
(3 + 2) * 5 - 1

markdown
Copy code

**Output**
[(, 3, +, 2, ), *, 5, -, 1]



---

### Parsing

A recursive descent parser checks whether the input conforms to the following grammar:

```text
E → E + T | E - T | T
T → T * F | T / F | F
F → (E) | number | -F
Invalid expressions generate descriptive errors with precise token positions.

AST Construction
The parser builds a tree that reflects operator precedence and grouping.

AST for the example expression

text
Copy code
AST:
└─ *
   ├─ +
   │  ├─ 3
   │  └─ 2
   └─ -
      ├─ 4
      └─ 1
Evaluation
The evaluator computes the result by recursively evaluating the AST.

Result

Copy code
15
Error Handling
The compiler detects:

Unexpected tokens

Missing operands

Misplaced operators

Invalid parentheses

Example

Input

scss

3 + (4 - )
Error

arduino
Copy code
Unexpected token ')' at position 6
Running the Program
Compile
bash
Copy code
javac *.java
Run
bash

java MiniExpressionCompiler
Enter any arithmetic expression when prompted.

Example Input and Output
Input

scss

(3 + 2) * (4 - 1)
Output

makefile

Tokens: [(, 3, +, 2, ), *, (, 4, -, 1, ), ]

AST:
└─ *
   ├─ +
   │  ├─ 3
   │  └─ 2
   └─ -
      ├─ 4
      └─ 1

Result: 15
What This Project Demonstrates
Compiler pipeline fundamentals (lexing → parsing → AST → evaluation)

Recursive descent parsing

Operator precedence handling

Tree-based program representation

Robust error reporting with position tracking
