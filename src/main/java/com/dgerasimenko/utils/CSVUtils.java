package com.dgerasimenko.utils;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class CSVUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger(CSVUtils.class);

    public static byte[] convertToCSVStream(HeadersSupplier headersProcessor, BodySupplier bodyProcessor)  {
        try (
                ByteArrayOutputStream out = new ByteArrayOutputStream();
                CSVPrinter csvPrinter = new CSVPrinter(
                        new PrintWriter(out),
                        CSVFormat.DEFAULT.withHeader(headersProcessor.getHeaders())
                )
        ) {
            for (List<String> record : bodyProcessor.getBody()) {
                csvPrinter.printRecord(record);
            }
            csvPrinter.flush();

            return out.toByteArray();
        } catch (IOException e) {
            LOGGER.error("Exception while convert to CSV", e);
            throw new RuntimeException(e.getMessage());
        }
    }

    public interface HeadersSupplier {
        String[] getHeaders();
    }

    public interface BodySupplier {
        List<List<String>> getBody();
    }
}
