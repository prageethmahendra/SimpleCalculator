package it.prageeth.calc.operator.custom;

/**
 * All the operator classes should be implemented in this package with a static operate funciton.
 * Operator classes should have a post fix "Operator"
 * All of the above conditions are hardcoded in the evaluation.
 *
 * in a commercial software these hardcoded values should be configurable at the runtime or compile time
 * ( using interfaces, custom annotations)
 */
/**
 * Created by prageeth.g on 24/11/2015.
 * this class calculate the gausian function value
 */
public class GaussOperator{

    /**
     * NOTE : for better extendability and code safety operator functions can be annotated with a
     * custom annotation so that it can be identified by the annotation instead of the hardcoded static method name
     */
    /**
     * this operate method parse the input parameter string and calculate the gausian function value
     * @param str four input parameters separated by comas
     * @return calculated gausian function value
     */
    public static Double operate(String str) {
        if (str != null && str.trim().length() > 0) {
            str = str.trim();
            String[] paramStrings = str.split(",");
            double a = 0, b = 0, c = 0, x = 0;
            if (paramStrings.length < 4) {
                return Double.NaN;
            }
            try {
                for (int i = 0; i < paramStrings.length; i++) {
                    double value = Double.parseDouble(paramStrings[i].trim());
                    switch (i) {
                        case 0:
                            a = value;
                            break;
                        case 1:
                            b = value;
                            break;
                        case 2:
                            c = value;
                            break;
                        case 3:
                            c = value;
                            break;
                    }
                }
            } catch (NumberFormatException e) {
                e.printStackTrace();
                return Double.NaN;
            }
            if(c > 0) {
                return a * Math.exp(Math.pow((x - b), 2) / 2 * Math.pow(c, 2));
            }
        }
        return Double.NaN;
    }
}
