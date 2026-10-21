CS 211 Midterm Project 2 - Infix to Postfix Conversion and Postfix Evaluation
Package: midtermlabproject2   |   Entry point: midtermlabproject2.Main (menu-based console)

Classes
  Stack, LinkedStack, Node, StackException   user-defined linked stack from Module 3 (no java.util stack classes used)
  InfixToPostfix      infix -> postfix with trace table (Symbol | postfixExpression | operatorStack)
  PostfixEvaluator    postfix evaluation with trace table (symbol | operand1 | operand2 | value | operandStack)
  TablePrinter, Row   console table output
  InvalidExpressionException   reported as an error message by Main

Rules
  Infix input: no spaces; operators + - * / ^ and parentheses; operands are letters/numbers (e.g. A, B, 12, 3.5).
  Postfix input: tokens separated by spaces; operands must be numbers.
  ^ is exponentiation (right-to-left); * / are above + -.
