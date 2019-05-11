package ru.croc.ecosmartdata.design.in.xls;

import javafx.util.Pair;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import ru.croc.ecosmartdata.design.in.Reader;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ReaderXls implements Reader {

    private static final Map cols = new HashMap<String, Integer>() {
    };
    XSSFWorkbook workbook;

    public ReaderXls(String filePath) throws IOException {
        fullFillColumnsMap();
        File file = new File(filePath);
        FileInputStream fip = new FileInputStream(file);
        workbook = new XSSFWorkbook(fip);
        if (file.isFile() && file.exists()) {
            System.out.println(filePath + " opened");
        } else {
            throw new RuntimeException(filePath + " not found");
        }
    }

    @Override
    public List<String> readNamespaces() {
        List<String> namespaces = new ArrayList<>();
        for (Row row : workbook.getSheet("Сущности и cf")) {
            if (row.getRowNum() == 0) continue;
            String namespace_s = getStringValueByColumn(row, "C");
            if (namespace_s != null && namespace_s != "") {
                namespaces.add(namespace_s);
            }
        }
        return namespaces;
    }

    @Override
    public List<Pair<String, String>> readEntities() {
        List<Pair<String, String>> entities = new ArrayList<>();
        for (Row row : workbook.getSheet("Сущности и cf")) {
            if (row.getRowNum() == 0) continue;
            String entity = getStringValueByColumn(row, "E");
            String namespace = getStringValueByColumn(row, "F");
            entities.add(new Pair<>(entity, namespace));
        }
        return entities;
    }

    @Override
    public List<Pair<String, String>> readColumnFamilies() {
        List<Pair<String, String>> columnFamilies = new ArrayList<>();
        for (Row row : workbook.getSheet("Сущности и cf")) {
            if (row.getRowNum() == 0) continue;
            String columnFamily = getStringValueByColumn(row, "S");
            String entity = getStringValueByColumn(row, "T");
            columnFamilies.add(new Pair<>(columnFamily, entity));
        }
        return columnFamilies;
    }

    @Override
    public List<Map<String, String>> readAttributes() {
        List<Map<String, String>> attributes = new ArrayList<>();
        for (Row row : workbook.getSheet("Атрибуты Профиля")) {
            if (row.getRowNum() == 0) continue;
            Map<String, String> attribute = new HashMap<>();
            attribute.put("namespace", getStringValueByColumn(row, "F"));
            attribute.put("entity", getStringValueByColumn(row, "G"));
            attribute.put("cf", getStringValueByColumn(row, "I"));
            attribute.put("q", getStringValueByColumn(row, "A"));
            attribute.put("profile", getStringValueByColumn(row, "E"));
            attribute.put("attribute2", getStringValueByColumn(row, "A"));
            attribute.put("datatype", getStringValueByColumn(row, "J"));
            attribute.put("type", getStringValueByColumn(row, "K"));
            attribute.put("kind_type", getStringValueByColumn(row, "L"));
            attribute.put("topic", getStringValueByColumn(row, "M"));
            attribute.put("actuality_default_state", getStringValueByColumn(row, "O"));
            attribute.put("class", getStringValueByColumn(row, "P"));
            attribute.put("quality", getStringValueByColumn(row, "Q"));
            attribute.put("int_ext", getStringValueByColumn(row, "S"));
            attribute.put("payment_condition", getStringValueByColumn(row, "T"));
            attribute.put("is_state_agency", getStringValueByColumn(row, "U"));
            attribute.put("predictors", getStringValueByColumn(row, "AK"));
            attribute.put("identifier", Integer.toString(row.getRowNum()));
            attributes.add(attribute);
        }
        return attributes;
    }

    private static void fullFillColumnsMap() {
        String k;
        Integer j = 0;
        for (char c = 'A'; c <= 'Z'; c++) {
            cols.put(Character.toString(c), j);
            j++;
        }
        for (char c2 = 'A'; c2 <= 'Z'; c2++) {
            for (char ch = 'A'; ch <= 'Z'; ch++) {
                k = Character.toString(c2) + Character.toString(ch);
                cols.put(k, j);
                j++;
            }
        }
    }

    private static String getStringValueByColumn(Row row, String name) {
        Cell cell = row.getCell((Integer) cols.get(name));
        if (cell == null) {
            return null;
        } else {
            cell.setCellType(Cell.CELL_TYPE_STRING);
            return cell.getRichStringCellValue().toString();
        }
    }
}
