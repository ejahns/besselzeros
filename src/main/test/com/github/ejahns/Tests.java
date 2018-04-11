package com.github.ejahns;

import org.apache.commons.io.FileUtils;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class Tests {

    @Test
    public void findZeroTests() throws IOException {
        List<List<String>> testValues = new ArrayList<>();
        File testFile = new File("src/main/test/com/github/ejahns/testData.txt");
        List<String> lines = FileUtils.readLines(testFile, "UTF-8");
        for (String line : lines) {
            List<String> values = Arrays.stream(line.split("\t")).collect(Collectors.toList());
            Assert.assertEquals(values.get(3),Double.toString(BesselJ.findZero(Double.valueOf(values.get(0)),Integer.valueOf(values.get(1)),Integer.valueOf(values.get(2)))));
        }
    }
}
