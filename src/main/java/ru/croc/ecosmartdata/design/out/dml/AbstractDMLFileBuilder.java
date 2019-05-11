package ru.croc.ecosmartdata.design.out.dml;

import javafx.util.Pair;
import ru.croc.ecosmartdata.design.out.AbstractFileBuilder;

import java.io.IOException;
import java.util.List;

public class AbstractDMLFileBuilder extends AbstractFileBuilder {
    void saveDMLScript(List<Pair<String, String>> scripts) throws IOException {
        String filePath = "/hbase/dml/";
        for (Pair<String, String> pair :scripts) {
            saveFile(filePath + pair.getKey(), pair.getValue());
        }
    }
}