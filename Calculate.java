package edu.tsystems.test.calc;

/* Класс Calculate получает строку, представляющую собой арифметическое
 * выражение, проверяет на наличие ошибок и вычисляет его.
 */

public class Calculate {

    private String expression;

    public Calculate(String str) {
        expression = str;
    }
    
    /* Метод res1 получает строку expression, проверяет её с помощью
     * функции isCorrect и возвращает ERROR, если выражение содержит ошибку.
     * Цикл while выделяет выражение в самых низкоуровневых скобках (выражение,
     * не имеющее скобок) и с помощью функции noBrackets вычислет его. Вычисленное
     * выражение (вместе со скобками) заменяется результатом (строкой содержащей число).
     * Цикл работает пока не будут заменены все выражения в скобках.
     * После цикла в последний раз применяется функция noBrackets и возвращается результат.
     */
    
    public String res1() {
        int i, j, t;
        String st, resStr;
        if (isCorrect(expression) == false) {
            return "ERROR";
        }
        while (expression.indexOf("(") > -1) {
            st = "";
            i = 0;
            t = 0;
            j = 0;
            i = expression.lastIndexOf("(");
            st = expression.substring(i + 1);
            t = st.indexOf(")");
            j = i + t + 1;
            st = expression.substring(i + 1, j);
            resStr = noBrackets(st);
            expression = expression.substring(0, i) + resStr + expression.substring(j + 1);
        }
        return noBrackets(expression);
    }
    
    /* Функция noBrackets вычисляет арифметические выражения, не содержащие
     * скобок. Сначало выполняются операции '*','/' (первый цикл while),
     * затем - '+','-' (второй цикл while). Функция выисляет положение в строке
     * определенной операции, её тип и некоторые другие параметры, и передает
     * эти данные функции elOperations.
     */

    private String noBrackets(String st) {
        int zn;
        int[] m1=new int[2], m2=new int[4];
        String resStr = st;
        boolean q = true;
        if (resStr.indexOf("--") == 0) {
            resStr = resStr.substring(2);
        }
        while (q) {
            m2[0] = resStr.indexOf("*-");
            m2[1] = resStr.indexOf("/-");
            m2[2] = resStr.indexOf("*");
            m2[3] = resStr.indexOf("/");
            m1=mini(m2);
            if (m2[0]+m2[1]+m2[2]+m2[3] == -4) {
                q = false;
            } else if (m1[1]==1) {
                resStr = elOperations(resStr, m1[0], 1, 1, 1);
            } else if (m1[1]==2) {
                resStr = elOperations(resStr, m1[0], 2, 1, 1);
            } else if (m1[1]==3) {
                resStr = elOperations(resStr, m1[0], 1, 0, 1);
            } else {
                resStr = elOperations(resStr, m1[0], 2, 0, 1);
            }
        }
        q = true;
        while (q) {
            zn = 1;
            if (resStr.charAt(0) == '-' && resStr.substring(1).indexOf("+") == -1 && resStr.substring(1).indexOf("-") == -1) {
                return resStr;
            } else if (resStr.charAt(0) == '-') {
                zn = -1;
                resStr = resStr.substring(1);
            }
            m2[0] = resStr.indexOf("+-");
            m2[1] = resStr.indexOf("--");
            m2[2] = resStr.indexOf("+");
            m2[3] = resStr.indexOf("-");
            m1 = mini(m2);
            if (m2[0] + m2[1] + m2[2] + m2[3] == -4) {
                q = false;
            } else if (m1[1] == 1) {
                resStr = elOperations(resStr, m1[0], 3, 1, zn);
            } else if (m1[1] == 2) {
                resStr = elOperations(resStr, m1[0], 4, 1, zn);
            } else if (m1[1] == 3) {
                resStr = elOperations(resStr, m1[0], 3, 0, zn);
            } else {
                resStr = elOperations(resStr, m1[0], 4, 0, zn);
            }
        }
        return resStr;
    }

    /* Функция elOperations, получив данные об операции, применяет её и заменяет
     * два числа и знак операции на результат.
     */
    
    private String elOperations(String resStr, int k, int oper, int sost, int znak) {
        Double a, b;
        int i = k + 1 + sost, j = k - 1;
        String st = "";
        while (resStr.charAt(i) != '*' && resStr.charAt(i) != '/' && resStr.charAt(i) != '+' && resStr.charAt(i) != '-' && resStr.length() != i + 1) {
            i++;
        }
        if (resStr.length() != i + 1) {
            i--;
        }
        b = Double.parseDouble(resStr.substring(k + 1, i + 1));
        while (resStr.charAt(j) != '*' && resStr.charAt(j) != '/' && resStr.charAt(j) != '+' && resStr.charAt(j) != '-' && j != 0) {
            j--;
        }
        if (j != 0) {
            j++;
        }
        a = Double.parseDouble(resStr.substring(j, k)) * znak;
        switch (oper) {
            case 1:
                st = String.valueOf(a * b);
                break;
            case 2:
                st = String.valueOf(a / b);
                break;
            case 3:
                st = String.valueOf(a + b);
                break;
            case 4:
                st = String.valueOf(a - b);
                break;
        }
        resStr = resStr.substring(0, j) + st + resStr.substring(i + 1);
        return resStr;
    }
    
    /* Функция mini - это вспомогательная функция для функции noBrackets,
     * вычисляющая правильный порядок действий в арифметических выражениях
     * без скобок.
     */

    private int[] mini(int[] m2) {
        int i, max, k;
        int[] m1 = new int[2];
        max=-1;
        for (i = 0; i < m2.length; i++) {
            if (m2[i] > max) {
                max = m2[i];
                m1[1]=i+1;
            }
        }
        m1[0] = max;
        for (i = 0; i < m2.length; i++) {
            if (m1[0] > m2[i] && m2[i] != -1) {
                m1[0] = m2[i];
                m1[1]=i+1;
            }
        }
        return m1;
    }
    
    /* Функция isCorrect проверяет данное арифметическое выражение на наличие
     * ошибок. Первый цикл while проверяет правильность расстановки скобок.
     * Цикл for проверяет "соседей" каждого символа.
     */

    private boolean isCorrect(String str) {
        int k = 0, i = 0;
        char ch1, ch2, ch3;
        while (k >= 0 && i != str.length()) {
            if (str.charAt(i) == '(') {
                k++;
            } else if (str.charAt(i) == ')') {
                k--;
            }
            i++;
        }
        if (k != 0) {
            return false;
        }
        ch1=str.charAt(0);
        if (ch1 == '*' || ch1 == '/' || ch1 == '+') {
            return false;
        }
        ch1=str.charAt(str.length()-1);
        if (isSymb(ch1) && ch1!=')') {
            return false;
        }
        for (i = 1; i < str.length() - 1; i++) {
            ch1 = str.charAt(i - 1);
            ch2 = str.charAt(i);
            ch3 = str.charAt(i + 1);
            if (ch2 == '-') {
                if ((isSymb(ch1) && isSymb(ch3) && !(ch1 == ')' && ch3 == '(') && !(ch1 == '(' && ch3 == '(')) || (isSymb(ch3) && ch3 != '(')) {
                    return false;
                }
            }
            if (ch2 == '+' || ch2 == '*' || ch2 == '/') {
                if ((isSymb(ch1) && ch1 != ')') || ((isSymb(ch3)) && ch3!='-' && ch3!='(')) {
                    return false;
                }
            }
            if (ch2 == '(') {
                if (!isSymb(ch1) || ch1==')' || (isSymb(ch3) && ch3!='(' && ch3!='-')) {
                    return false;
                }
            }
            if (ch2==')') {
                if (!isSymb(ch3) || ch3=='(' || (isSymb(ch1) && ch1!=')')) {
                    return false;
                }
            }
        }
        return true;
        
    }
    
    /* Функция isSymb - это вспомогательная функция для функции isCorrect,
     * определяющая является ли данный символ '*' или '/' или '+' или '-' или '(' или ')'.
     */

    private boolean isSymb(char ch) {
        if (ch=='*' || ch=='/' || ch=='+' || ch=='-' || ch=='(' || ch==')') {
            return true;
        } else {
            return false;
        }
    }
}