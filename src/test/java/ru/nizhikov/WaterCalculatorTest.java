package ru.nizhikov;

import java.io.InputStream;
import java.util.Arrays;
import java.util.Scanner;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static ru.nizhikov.WaterCalculator.MAX_HEIGHT;
import static ru.nizhikov.WaterCalculator.MAX_LENGTH;
import static ru.nizhikov.WaterCalculator.MIN_HEIGHT;

public class WaterCalculatorTest {
    @Test(expected = IllegalArgumentException.class)
    public void testNullInput() {
        WaterCalculator.calculateWaterAmount(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testEmptyInput() {
        WaterCalculator.calculateWaterAmount(new int[0]);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testTooLongInput() {
        WaterCalculator.calculateWaterAmount(new int[MAX_LENGTH + 1]);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNegativeInput() {
        WaterCalculator.calculateWaterAmount(new int[] { 10, 10, MIN_HEIGHT - 1 });
    }

    @Test(expected = IllegalArgumentException.class)
    public void testTooBigInput() {
        WaterCalculator.calculateWaterAmount(new int[] { 10, 10, MAX_HEIGHT + 1 });
    }

    @Test
    public void testFiles() {
        int i = 0;

        while (true) {
            String testName = "/water_calc_tests/" + i + ".txt";

            InputStream test = getClass().getResourceAsStream(testName);

            if (test == null)
                break;

            Scanner sc = new Scanner(test);

            int[] landscape = Arrays.stream(sc.nextLine().split(" ")).mapToInt(Integer::parseInt).toArray();
            long expRes = sc.nextLong();

            assertEquals(testName, expRes, WaterCalculator.calculateWaterAmount(landscape));

            i++;
        }

        assertEquals(9, i - 1);
    }
}
