package edu.duke;

import java.io.*;
import java.nio.charset.StandardCharsets;
import org.apache.commons.csv.*;

public class FileResource {
    private String filename;

    public FileResource(String filename) {
        this.filename = filename;
    }

    public CSVParser getCSVParser() {
        return getCSVParser(true);
    }

    public CSVParser getCSVParser(boolean withHeader) {
        try {
            Reader in = new InputStreamReader(new FileInputStream(filename), StandardCharsets.UTF_8);
            if (withHeader) {
                return CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(in);
            } else {
                return CSVFormat.DEFAULT.parse(in);
            }
        } catch (Exception e) {
            throw new RuntimeException("Cannot parse CSV: " + filename, e);
        }
    }
}
