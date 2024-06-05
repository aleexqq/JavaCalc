import java.io.IOException;
//import java.util.Scanner;


public class Main {
    public static void main(String[] args) {
        String[] testInputs = {
                "1 + 1", "10 - 2", "3 * 3", "8 / 2",  // корректные арабские
                "I + I", "X - II", "III * III", "VIII / II",  // корректные римские
                "1 +", "+ 1", "1 1", "1 + 1 + 1",  // неверное количество аргументов
                "I + 1", "10 - II",  // смешанные системы счисления
                "1 ^ 1", "I % I",  // неподдерживаемый оператор
                "2 + 1", "11 - 1", "I + XI",  // числа вне диапазона
                "a + b", "1 + a", "I + a",  // некорректные строки
                "1 + 2   ", "1  +  2", "1   +   2"  // лишние пробелы
        };
        for (String input : testInputs) {
            try {
                String result = calc(input);
                System.out.println("Input: " + input + " => Output: " + result);
            } catch (IOException e) {
                System.out.println("Input: " + input + " => Error: " + e.getMessage());
            }
        }
    }

    public static String calc(String input) throws IOException {
        String[] expression = input.split(" ");
        if (expression.length != 3) {
            throw new IOException("Некорректный ввод: Формат математической операции должен" +
                    " удовлетворять условиям - два операнда и один оператор (+, -, /, *)");
        }

        String firstStr = expression[0], operator = expression[1], secondStr = expression[2];

        boolean isRoman = isRoman(firstStr) && isRoman(secondStr);
        boolean isArabic = isArabic(firstStr) && isArabic(secondStr);

        if (!isRoman && !isArabic) {
            throw new IOException("Неверный ввод: Калькулятор принимает числа от 1 до 10, " +
                    "находящихся одновременно либо в римской, либо в арабской системе счисления");
        }

        int first = isRoman ? RomanSymbols.valueOf(firstStr).getTranslation() : Integer.parseInt(firstStr);
        int second = isRoman ? RomanSymbols.valueOf(secondStr).getTranslation() : Integer.parseInt(secondStr);

        if (first < 1 || second < 1 || first > 10 || second > 10) {
            throw new IOException("Неверный ввод: Калькулятор принимает числа от 1 до 10.");
        }

        int result = switch (operator) {
            case "+" -> first + second;
            case "-" -> first - second;
            case "*" -> first * second;
            case "/" -> first / second;
            default -> throw new IOException("Некорректный ввод: Формат математической операции должен" +
                    "удовлетворять условиям - два операнда и один оператор (+, -, /, *)");
        };

        if (isRoman) {
            if (result < 0) {
                throw new IOException("Некорректный ввод: Вычисляемый результат в римской системе счисления не может быть меньше единицы.");
            } else {
                return toRoman(result);
            }
        } else {
            return String.valueOf(result);
        }
    }

    static boolean isArabic(String numStr) {
        try {
            Integer.parseInt(numStr);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    static boolean isRoman(String numStr) {
        try {
            RomanSymbols.valueOf(numStr);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    static String toRoman(int result) {
        String[] romanTens = {"", "X", "XX", "XXX", "XL", "L", "LX", "LXX", "LXXX", "XC", "C"};
        String[] romanUnits = {"", "I", "II", "III", "IV", "V", "VI", "VII", "VIII", "IX"};

        return romanTens[result / 10] + romanUnits[result % 10];
    }
}