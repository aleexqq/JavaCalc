package calculator;

import java.util.Scanner;

public class Main {
    private static final int MIN_VALUE = 1;
    private static final int MAX_VALUE = 10;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String input = sc.nextLine();
        try {
            System.out.println(calc(input));
        } catch (Exception e) {
            System.out.println("Ошибка: " + e.getMessage());
        }
    }

    public static String calc(String input) throws IllegalArgumentException {
        String[] expression = input.trim().split(" ");

        if (expression.length != 3) {
            throw new IllegalArgumentException("Некорректный ввод: Формат математической операции должен" +
                    " удовлетворять условиям - два операнда и один обособленный пробелами оператор (+, -, /, *)");
        }

        String firstStr = expression[0], operator = expression[1], secondStr = expression[2];

        boolean isRoman = isRoman(firstStr) && isRoman(secondStr);
        boolean isArabic = isArabic(firstStr) && isArabic(secondStr);

        if (!isRoman && !isArabic) {
            throw new IllegalArgumentException("Неверный ввод: Калькулятор принимает числа от 1 до 10, " +
                    "находящихся одновременно либо в римской, либо в арабской системе счисления");
        }

        int first = isRoman ? RomanSymbols.valueOf(firstStr).getTranslation() : Integer.parseInt(firstStr);
        int second = isRoman ? RomanSymbols.valueOf(secondStr).getTranslation() : Integer.parseInt(secondStr);

        int result = switch (operator) {
            case "+" -> first + second;
            case "-" -> first - second;
            case "*" -> first * second;
            case "/" -> first / second;
            default -> throw new IllegalArgumentException("Некорректный ввод: Калькулятор принимает на вход" +
                    " только следующие операторы: [+, -, /, *]");
        };

        if (isRoman) {
            if (result < 0) {
                throw new IllegalArgumentException("Некорректный ввод: Вычисляемый результат в римской системе счисления не может быть меньше единицы.");
            } else {
                return toRoman(result);
            }
        } else {
            return String.valueOf(result);
        }
    }

    static boolean isArabic(String numStr) {
        try {
            int num = Integer.parseInt(numStr);
            return num <= MAX_VALUE && num >= MIN_VALUE;
        } catch (Exception e) {
            return false;
        }
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