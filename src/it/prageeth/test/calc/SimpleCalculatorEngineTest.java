package it.prageeth.test.calc;

import it.prageeth.calc.SimpleCalculatorEngine;
import org.junit.Assert;
import org.junit.Test;
import org.junit.Before;

import java.util.HashMap;

/**
 * Test cases for the Simple Calculator Engine calculate expression method
 * Created by prageeth.g on 25/11/2015.
 */
public class SimpleCalculatorEngineTest {

    HashMap<String,Double> expressionResultMap = new HashMap<>();

    @Before
    public void initialize()
    {
        expressionResultMap.put("1+2", 3.0);
        expressionResultMap.put("1-2", -1.0);
        expressionResultMap.put("2*2", 4.0);
        expressionResultMap.put("2/2", 1.0);
        expressionResultMap.put("(2)", 2.0);
        expressionResultMap.put("-2", -2.0);
        expressionResultMap.put("(-2)", -2.0);
        expressionResultMap.put("(-2+2)", 0.0);
        expressionResultMap.put("(-2) * (-2)", 4.0);
        expressionResultMap.put("Gauss(5,1,0,1)", 8.243606353500642);
        expressionResultMap.put("10/10/10/10/10", 10.0);
        expressionResultMap.put("10/10*10/10/10", 0.1);
        expressionResultMap.put("( 1 + 3) + 4 + Gauss(5,1,0,1) + 10 * 100 / 100", 26.243606353500642);
        expressionResultMap.put("(2) * 10 * 100 / 100", 20.0);
    }

    @Test
    public void calculateExpressionTest()
    {
        for (String expression : expressionResultMap.keySet()) {
            double value = SimpleCalculatorEngine.calculateExpression(expression);
            Assert.assertTrue(value == expressionResultMap.get(expression));
        }
    }
}

