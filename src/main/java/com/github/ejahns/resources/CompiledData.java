package com.github.ejahns.resources;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public final class CompiledData {

    static final int MAX_INDEX = 100;
    static final int MAX_COUNT = 100;

    public static final int TRUNCATION_PRECISION = 4;

    private static Map<Integer, List<Double>> ZEROS = null;
    private static boolean zerosTableExists = true;

    public static double lookupZero(int index, int number) {
        if (zerosTableExists && null == ZEROS) {
            populateZerosTable();
        }
        if (!zerosTableExists) return 0.0;
        if (index < MAX_INDEX && number < MAX_COUNT && null != ZEROS) {
            return ZEROS.get(index).get(number-1);
        }
        return 0.0;
    }

    private static void populateZerosTable() {
        ZEROS = new HashMap<>();
        File zerosFile = new File("src/main/java/com/github/ejahns/resources/zeros.txt");
        if (!zerosFile.exists()) {
            zerosTableExists = false;
            return;
        }
        List<String> zerosLines = null;
        try {
            zerosLines = FileUtils.readLines(zerosFile, "UTF-8");
            Iterator<String> iterator = zerosLines.iterator();
            int index = 0;
            while (iterator.hasNext()) {
                List<Double> zeros = Arrays.stream(iterator.next().split("\t")).map(Double::parseDouble).collect(Collectors.toList());
                ZEROS.put(index++, zeros);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
