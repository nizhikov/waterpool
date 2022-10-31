package ru.nizhikov;

import java.util.Stack;

public class WaterCalculator {
    public static final int MIN_HEIGHT = 0;

    public static final int MAX_HEIGHT = 32_000;

    public static final int MAX_LENGTH = 32_000;

    public static long calculateWaterAmount(int[] landscape) {
        checkInput(landscape);

        if (landscape.length < 3)
            return 0;

        Stack<Integer> stack = new Stack<>();

        int i = 1;

        while (i < landscape.length && landscape[i - 1] <= landscape[i])
            i++;

        if (i == landscape.length)
            return 0;

        stack.push(i - 1);
        stack.push(i);

        i++;

        while (i < landscape.length) {
            // Skip all decreasing.
            while (i < landscape.length && landscape[i] <= landscape[i - 1])
                i++;

            // Skip all increasing.
            while (i < landscape.length && landscape[i] >= landscape[i - 1])
                i++;

            while (stack.size() >= 2) {
                int one = landscape[stack.get(stack.size() - 2)];
                int two = landscape[stack.get(stack.size() - 1)];
                int three = landscape[i - 1];

                if (one > two && three > two) {
                    stack.pop();
                }
                else
                    break;
            }

            stack.push(i - 1);
        }

        long res = 0;

        for (i = 1; i < stack.size(); i++) {
            Integer from = stack.get(i - 1);
            Integer to = stack.get(i);
            int bound = Math.min(landscape[from], landscape[to]);

            for (int j = from + 1; j < to; j++)
                res += Math.max(0, bound - landscape[j]);
        }

        return res;
    }

    private static void checkInput(int[] landscape) {
        if (landscape == null)
            throw new IllegalArgumentException("Landscape is null");

        if (landscape.length == 0)
            throw new IllegalArgumentException("Landscape is empty");

        if (landscape.length > MAX_LENGTH)
            throw new IllegalArgumentException("Landscape is too long");

        for (int i=0; i<landscape.length; i++) {
            if (landscape[i] < MIN_HEIGHT || landscape[i] > MAX_HEIGHT) {
                throw new IllegalArgumentException(
                    "Wrong " + i + " height. Must be between " + MIN_HEIGHT + " and " + MAX_HEIGHT
                );
            }
        }
    }
}
