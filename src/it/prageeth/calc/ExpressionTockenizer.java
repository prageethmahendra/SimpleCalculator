package it.prageeth.calc;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * This class parse the expression and identify operators and operands
 * Created by prageeth.g on 25/11/2015.
 */
public class ExpressionTockenizer {

    private static final String whiteSpaceCharacters = " \n\t\f\r";
    private static final String operatorTokenizerString = "\t\r\n\t */+-()";
    private static final String operandTokenizerString = "1234567890.abcdefghijklmnopqustuvwxyz,ABCDEFGHIJKLPMOPQURSTUVWXYZ_";
    private static final String decimalRegExpression = "-?\\d+(\\.\\d+)?";
    private static final String operators = "()*/+-";
    private static final String unary = "+-";

    /**
     * This method breack the expression into multiple calculatable tockens to generate the postfix calculator
     * expression
     *
     * @param expression
     * @return the tocken list of the given expression
     */
    public static List<String> getExpressionTockenList(String expression) {
        ArrayList<String> tockenList = new ArrayList<>();
//        expression = reformatExpression(expression);
        StringTokenizer decimalTokenizer = new StringTokenizer(expression, operatorTokenizerString);
        StringTokenizer operatorTokenizer = new StringTokenizer(expression, operandTokenizerString);
        // to remove open brackets in custom operator functions
        boolean removeOpenBracket = false;
        // to remove close brackets in custom operator functions
        boolean removeCloseBracket = false;
        // to handle the initial unary character
        String firstch = String.valueOf(expression.charAt(0));
        // to handle initial brackets
        boolean addOpToList = isValidOperator(firstch) ;
        String decString = null;
        while (decimalTokenizer.hasMoreTokens()) {
            if (!addOpToList) {
                decString = decimalTokenizer.nextToken().trim();
                if (!isDecisDecimal(decString)) {
                    // function name found ( Eg. Gauss)
                    removeOpenBracket = true;
                    String parameters = decimalTokenizer.hasMoreElements() ? decimalTokenizer.nextToken().trim() : null;
                    if (parameters != null) {
                        // identify parameters and its brackets
                        tockenList.add(decString + "(" + parameters + ")");
                    } else {
                        System.out.println("Invalid Operator Function : " + decString);
                        return null;
                    }
                } else {
                    tockenList.add(decString);
                }
                addOpToList = true;
            }
            if (addOpToList) {

                boolean operatorAdded = false;
                while (operatorTokenizer.hasMoreTokens() && !operatorAdded) {
                    // operator found
                    String operatorString = operatorTokenizer.nextToken().trim();
                    int chCount = 0;
                    String operator;
                    while (operatorString.length() > chCount) {
                        char ch = operatorString.charAt(chCount);
                        if (!isWhitespace(ch)) {
                            operator = String.valueOf(ch);
                            if (!operator.isEmpty()) {
                                if (removeOpenBracket && operator.equals("(")) {
                                    removeOpenBracket = false;
                                    removeCloseBracket = true;
                                } else if (removeCloseBracket && operator.equals(")")) {
                                    removeCloseBracket = false;
                                } else {
                                    tockenList.add(operator);
                                    operatorAdded = true;
                                }
                            }
                        }
                        chCount++;
                    }
                }
            }
            addOpToList = false;
        }
        return tockenList;
    }

    /**
     * This method remove the whitespaces from the given expression
     *
     * @param expression
     * @return given expression without any whitespaces
     */
    private static String reformatExpression(String expression) {
        expression = expression.trim();
        String formattedExpression = "";
        for (int i = 0; i < expression.length(); i++) {
            char ch = expression.charAt(i);
            if (!isWhitespace(ch)) {
                formattedExpression += ch;
            }
        }
        return formattedExpression;
    }

    private static boolean isWhitespace(char ch) {
        return whiteSpaceCharacters.contains(String.valueOf(ch));
    }

    /**
     * To idenfy operator symbols
     *
     * @param operatorCharacter
     * @return returns true if the string is a valid operator symbol
     */
    public static boolean isValidOperator(String operatorCharacter) {
        return operators.contains(operatorCharacter);
    }


    /**
     * to identify numerical strings
     *
     * @param str
     * @return
     */
    public static boolean isDecisDecimal(String str) {
        return str.matches(decimalRegExpression);
    }

    public static boolean isUnary(String stringCh) {
        return unary.contains(stringCh);
    }
}
