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
import java.util.List;
import java.util.Map;

public class ReaderXlsTest implements Reader {

    XSSFWorkbook workbook;

    public ReaderXlsTest(String filePath) throws IOException {
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
        return new ArrayList<>();
    }

    @Override
    public List<Pair<String, String>> readEntities() {
        return new ArrayList<>();
    }

    @Override
    public List<Pair<String, String>> readColumnFamilies() {
        return new ArrayList<>();
    }

    @Override
    public List<Map<String, String>> readAttributes() {
        return new ArrayList<>();
    }

    public void printRow(Integer index) {
        List<String> namespaces = new ArrayList<>();
        for (Row row : workbook.getSheet("Атрибуты Профиля")) {
            if (row.getRowNum() != index) continue;
            for (Cell cell : row) {
                cell.setCellType(Cell.CELL_TYPE_STRING);
                System.out.println("index = " + cell.getColumnIndex() + "value = " +  cell.getRichStringCellValue().toString());
            }
        }
    }
}
