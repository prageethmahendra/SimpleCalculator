package it.prageeth.calc.operator;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * This is the handler class to execute dynmaic operation funcitons
 * Created by prageeth.g on 24/11/2015.
 */
public class DynamicOperatorHandler {

    /**
     * NOTE : Execution Limitations : all the parameter values must be defined in the method string separated by comas.
     * This method cannot handle any other operator methods in - between parameter values.
     */
    /**
     * This evaluate function execute the operate method in the operator class by dynamically invoking the
     * class using java reflection.
     * ideally these classes and methods should be annotated and/or hidden behind an interface.
     *
     * @param method = name of the method and parameters as a string. Eg. "Gauss(5,1,0,1)"
     * @return
     */
    public double evaluate(String method) {
        if (method == null || method.trim().isEmpty()) {
            System.out.println("Empty or Null calc method : " + method);
            return Double.NaN;
        } else {
            Double value = 0.0;
            int methodEnd = method.indexOf('(');
            if (methodEnd > 0 && method.endsWith(")")) {
                String methodName = method.substring(0, methodEnd);
                // it is better to specify a configuration file to provide the package name instead of hardcording here
                String className = "it.prageeth.calc.operator.custom." + methodName + "Operator";
                try {
                    Class operatorClass = this.getClass().getClassLoader().loadClass(className);
                    // operate method is hardcoded. better to identify this method using a custom annotation
                    Method operatorMethod = operatorClass.getMethod("operate", String.class);
                    value = (Double) operatorMethod.invoke(null, method.substring(methodEnd + 1, method.length() - 1));

                    return value;
                } catch (ReflectiveOperationException e) {
                    System.out.println("Unable to calculate due to invalid operation expression.");
                }
                System.out.println("Invalid calc method : " + method);
                return Double.NaN;
            } else {
                System.out.println("Invalid calc method : " + method);
                return Double.NaN;
            }
        }
    }
}
