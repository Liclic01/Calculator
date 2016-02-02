/* Данная программа позволяет вычислять арифметические выражения, состоящие
 * из символов '0','1','2','3','4','5','6','7','8','9','.','*','/','+','-','(',')'.
 * Как числа в выражениях, так и результаты - вещественные числа с двойной точностью (тип double).
 * Пример: -(-(20/3.5*-12*(3+4))*2)
 * Ответ: -960.0
 * Также программа проверяет арифметические выражения на предмет ошибок.
 */
package edu.tsystems.test.calc;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.BufferedReader;
import java.io.PrintWriter;
import java.io.IOException;

public class CalculatorStarter {

    public static void main(String[] args) throws IOException {
        BufferedReader in = null;
        PrintWriter out = null;
        String expression;
        try {
            in = new BufferedReader(new FileReader(args[0]));
            out = new PrintWriter(new FileWriter(args[1]));
            while ((expression = in.readLine()) != null) {
                Calculate result = new Calculate(expression);
                out.println(result.res1());
            }
        } finally {
            if (in != null) {
                in.close();
            }
            if (out != null) {
                out.close();
            }
        }
    }
}