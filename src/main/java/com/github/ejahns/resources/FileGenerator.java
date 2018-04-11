package com.github.ejahns.resources;

import com.github.ejahns.BesselJ;
import org.apache.commons.io.FileUtils;
import org.apache.commons.math3.util.Precision;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.github.ejahns.resources.CompiledData.*;

public class FileGenerator {

    private static final String ENTRY_FORMAT = "%" + (TRUNCATION_PRECISION + 4) + "." + TRUNCATION_PRECISION + "f";

    public static void main(String[] args) throws IOException {
        List<String> zerosTable = new ArrayList<>();
        for (int i = 0; i <= MAX_INDEX; i++) {
            double lastZero = 0.0;
            List<Double> zeros = new ArrayList<>();
            for (int j = 1; j <= MAX_COUNT; j++) {
                lastZero = BesselJ.findZeroAfter(i, lastZero, TRUNCATION_PRECISION + 1);
                zeros.add(Precision.round(lastZero, TRUNCATION_PRECISION, BigDecimal.ROUND_DOWN));
            }
            zerosTable.add(zeros.stream().map(d -> String.format(ENTRY_FORMAT, d)).collect(Collectors.joining("\t")));
        }
        File zerosFile = new File("src/main/java/com/github/ejahns/resources/zeros.txt");
        FileUtils.writeLines(zerosFile, "UTF-8", zerosTable);
    }
}
