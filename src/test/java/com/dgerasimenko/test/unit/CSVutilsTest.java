package com.dgerasimenko.test.unit;

import com.dgerasimenko.utils.CSVUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;


public class CSVutilsTest {
    @Test
    public void exportTest() {
        byte[] csvByteArray = CSVUtils.convertToCSVStream(() -> new String[]{"h1", "h2", "h3"},
                () -> {
                    List<List<String>> body = new ArrayList<>(1);
                    List<String> fields = new ArrayList<>(3);
                    fields.add("value1");
                    fields.add("value2");
                    fields.add("value3");
                    body.add(fields);
                    return body;
                });
        Assertions.assertNotNull(csvByteArray);
        Assertions.assertTrue(csvByteArray.length > 0);
    }
}
