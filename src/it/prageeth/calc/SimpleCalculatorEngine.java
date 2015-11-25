package it.prageeth.calc;

import it.prageeth.calc.operator.DynamicOperatorHandler;
import it.prageeth.calc.operator.PremitiveOperator;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.*;

/**
 * NOTE : Follow actions should be taken in a commercial product.
 * 01. For a better clarity and extendability it is better to breack this class into multiple sub classes and assign their
 * respective evaluation responsibility.
 * <p>
 * 02. To maintain the high quality and reliability unit tests should be whiten for each method with code coverage of 100%
 * <p>
 * This is the calculator controler class
 * Created by prageeth.g on 24/11/2015.
 */
public class SimpleCalculatorEngine {
    private static final String brackets = "()";
    private static DynamicOperatorHandler dynamicOperatorHandler = new DynamicOperatorHandler();

    public static void main(String[] args) {
        String str = null;
        Reader reader = new InputStreamReader(System.in);
        BufferedReader bufferedReader = new BufferedReader(reader);
        while (true) {
            System.out.println("Please select an option\n");
            System.out.println("1. Calculate an expression");
            System.out.println("2. Exit\n");

            try {
                str = bufferedReader.readLine();
                if (str != null && !str.trim().isEmpty()) {
                    if (str.trim().equals("1")) {
                        System.out.println("Please enter the calculator expression.\n");
                        str = bufferedReader.readLine();
                        if (str == null || str.trim().isEmpty()) {
                            System.out.println("Invalid expression...!");
                            continue;
                        }
                    } else if (str.trim().equals("2")) {
                        return;
                    } else {
                        continue;
                    }
                }
                System.out.println(str);
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (str != null && !str.trim().isEmpty()) {
                calculateExpression(str.trim());
            }
        }
    }

    /**
     * This method is the main calculator expression method.
     * for a better extendability and customizability this method can be moved to a separate CONTROLLER class
     *
     * @param expression
     */
    public static double calculateExpression(String expression) {

        List<String> tockenList = ExpressionTockenizer.getExpressionTockenList(expression);

        if (tockenList == null) {
            return Double.NaN;
        }
        Stack<String> infixStack = new Stack<>();
        Stack<String> opStack = new Stack<>();
        // to identify Unary operands
        boolean isUnaryOperatorFound = false;
        // to keep track on last added numerical infix
        boolean isLastOperandNumeric = false;
        for (int i = 0; i < tockenList.size(); i++) {
            String tocken = tockenList.get(i).trim();
            if (!ExpressionTockenizer.isValidOperator(tocken)) {
                if (isUnaryOperatorFound) {
                    // add a unary operator infrot of the numerical value
                    infixStack.push(opStack.pop() + tocken);
                } else {
                    infixStack.push(tocken);
                }
                isUnaryOperatorFound = false;
                isLastOperandNumeric = true;
            } else {
                if (!opStack.isEmpty()) {
                    String previousOperator = opStack.pop();
                    boolean discardBrackets = false;
                    while (previousOperator != null && !PremitiveOperator.isOrderCorrect(tocken, previousOperator)) {
                        if (previousOperator.equals("(") && tocken.equals(")")) {
                            // discard opening brackets of custom operators
                            discardBrackets = true;
                            break;
                        }
                        // add all the previous operators up-to the closing opening bracket and discard the closing bracket
                        infixStack.push(previousOperator);
                        previousOperator = opStack.isEmpty() ? null : opStack.pop();
                    }
                    if (previousOperator != null) {
                        // discard opening brackets of custom operators
                        if (!(previousOperator.equals("(") && discardBrackets)) {
                            opStack.push(previousOperator);
                        }
                    }
                    // always discard closing brackets
                    if (!tocken.equals(")")) {
                        // if a primitive operator existing without operands
                        if (!isLastOperandNumeric && ExpressionTockenizer.isUnary(tocken)) {
                            isUnaryOperatorFound = true;
                        }
                        opStack.push(tocken);
                        isLastOperandNumeric = false;
                    }
                } else {
                    opStack.push(tocken);
                    isUnaryOperatorFound = false;
                }
            }
        }
        while (!opStack.isEmpty()) {
            String ops = opStack.pop();
            infixStack.push(ops);
        }
        // better to use Java queue instead of arraylist in reverse order
        ArrayList<String> stringQueue = new ArrayList<>();
        while (!infixStack.isEmpty()) {
            stringQueue.add(infixStack.pop());
        }

        for (String s : stringQueue) {
            System.out.println("s = " + s);
        }
        // evaluate teh postfix queue
        return evaluatePostfixExpression(stringQueue);
    }

    /**
     * this method evaluate the postfix expression
     *
     * @param stringQueue
     */
    private static double evaluatePostfixExpression(ArrayList<String> stringQueue) {
        Stack<Double> validStack = new Stack<>();
        while (!stringQueue.isEmpty()) {
            String infixValue = stringQueue.get(stringQueue.size() - 1);
            if (ExpressionTockenizer.isValidOperator(infixValue)) {
                double value2 = 0;
                double value1 = 0;
                try {
                    value2 = validStack.pop();
                    if (validStack.isEmpty() && ExpressionTockenizer.isUnary(infixValue)) {
                        validStack.push(Double.parseDouble(infixValue + value2));
                    } else {
                        value1 = validStack.pop();
                        validStack.push(PremitiveOperator.operate(value1, value2, infixValue));
                    }
                } catch (Exception e) {
                    System.out.println("Invalid Expression");
                    return Double.NaN;
                }

            } else {
                if (ExpressionTockenizer.isDecisDecimal(infixValue)) {
                    validStack.push(Double.parseDouble(infixValue));
                } else {
                    validStack.push(evaluateFunction(infixValue));
                }
            }
            stringQueue.remove(stringQueue.size() - 1);
        }
        if (!validStack.isEmpty()) {
            double result = validStack.pop();
            System.out.println("Calculated Value = " + result);
            return result;
        }
        return Double.NaN;
    }

    /**
     * evaluate the operator functions such as Gauss()
     *
     * @param functionName
     * @return the evaluated function output
     */
    private static Double evaluateFunction(String functionName) {
        return dynamicOperatorHandler.evaluate(functionName);
    }
}