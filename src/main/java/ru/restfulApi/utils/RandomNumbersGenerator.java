package ru.restfulApi.utils;

import org.apache.commons.lang3.RandomUtils;

public class RandomNumbersGenerator {
    public static int numberGeneratorInRange(int minValue, int maxValue) {
        return RandomUtils.nextInt(minValue, maxValue + 1);
    }

    public static double numberGeneratorInRange(double minValue, double maxValue) {
        return Math.round(RandomUtils.nextDouble(minValue, maxValue + 1) * 100.0) / 100.0;
    }
}
