package it.prageeth.calc.operator;

import it.prageeth.calc.ExpressionTockenizer;

/**
 * This class handles and primitive operator specific operations
 * Created by prageeth.g on 25/11/2015.
 */
public class PremitiveOperator {
    /**
     * Execute the premitve operations
     *
     * @param value1
     * @param value2
     * @param operator
     * @return the calculated value
     */
    public static double operate(final double value1, final double value2, final String operator) {
        if (ExpressionTockenizer.isValidOperator(operator)) {
            switch (operator) {
                case "+":
                    return value1 + value2;
                case "-":
                    return value1 - value2;
                case "*":
                    return value1 * value2;
                case "/":
                    return value1 / value2;
            }
        }
        return Double.NaN;
    }

    /**
     * This function define the precedence of the operators
     * Please note that Special consideration is given for the brackets
     *
     * @param firstOperator
     * @param secondOperator
     * @return true if operator precedence is correct
     */
    public static boolean isOrderCorrect(String firstOperator, String secondOperator) {
        String opsOrder = "*/-+";
        String bracketOrder = ")(";
        String characterOrder = bracketOrder + opsOrder;
        if (firstOperator.equals(")")) {
            return false;
        }
        if (secondOperator.equals("(") && opsOrder.contains(firstOperator)) {
            return true;
        } else {
            return characterOrder.indexOf(firstOperator) <= characterOrder.indexOf(secondOperator);
        }
    }
}
