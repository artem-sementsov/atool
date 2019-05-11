package ru.croc.ecosmartdata.design.out.ddl;

import javafx.util.Pair;
import ru.croc.ecosmartdata.design.out.AbstractFileBuilder;

import java.io.IOException;
import java.util.List;

public class AbstractDDLFileBuilder extends AbstractFileBuilder {

    void saveDDLScript(List<Pair<String, String>> scripts) throws IOException {
        String filePath = "/hbase/ddl/";
        for (Pair<String, String> pair :scripts) {
            saveFile(filePath + pair.getKey(), pair.getValue());
        }
    }
}
