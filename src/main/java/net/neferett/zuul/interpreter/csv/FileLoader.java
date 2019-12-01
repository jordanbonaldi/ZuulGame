package net.neferett.zuul.interpreter.csv;

import lombok.Data;
import lombok.SneakyThrows;

import java.io.*;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class FileLoader {

    private final File file;

    private InputStream stream;
    private BufferedReader reader;

    @SneakyThrows
    /*
     * Opening file inputs
     */
    void open() {
        this.stream = new FileInputStream(this.file);
        this.reader = new BufferedReader(new InputStreamReader(this.stream));
    }

    /*
        Getting file content
     */
    List<String> getLines() {
        return this.reader.lines().collect(Collectors.toList());
    }

    /**
     * Closing files
     */
    @SneakyThrows
    void close() {
        this.reader.close();
        this.stream.close();
    }

}
