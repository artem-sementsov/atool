package ru.croc.ecosmartdata.design.in;

import javafx.util.Pair;

import java.util.List;
import java.util.Map;

public interface Reader {

    public List<String> readNamespaces();

    public List<Pair<String, String>> readEntities();

    public List<Pair<String, String>> readColumnFamilies();

    public List<Map<String, String>> readAttributes();

}
