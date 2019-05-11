package ru.croc.ecosmartdata.design;

import org.apache.commons.io.FilenameUtils;
import org.apache.log4j.Logger;
import ru.croc.ecosmartdata.design.in.xls.ReaderXls;
import ru.croc.ecosmartdata.design.out.Builder;
import ru.croc.ecosmartdata.design.out.config.PredictorCoprocessorConfigFileBuilder;
import ru.croc.ecosmartdata.design.out.ddl.EntitiesDDLFileBuilder;
import ru.croc.ecosmartdata.design.out.ddl.NamespaceDDLFileBuilder;
import ru.croc.ecosmartdata.design.out.dml.AliasDMLFileBuilder;
import ru.croc.ecosmartdata.design.out.dml.MetadataDMLFileBuilder;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class ATool {

    private static final Logger LOG = Logger.getLogger(ATool.class);
    private static final String FILE_NAME = "test.xlsx";
    private static List<Builder> builders = new ArrayList<>();

    static {
        builders.add(new NamespaceDDLFileBuilder());
        builders.add(new EntitiesDDLFileBuilder());
        builders.add(new AliasDMLFileBuilder());
        builders.add(new MetadataDMLFileBuilder());
        builders.add(new PredictorCoprocessorConfigFileBuilder());
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        DataProvider dataProvider = getDataProvider();

        //buildingWithoutManager(dataProvider);
        buildingWithManager(dataProvider);
    }

    public static DataProvider getDataProvider() throws IOException {
        return new DataProvider(new ReaderXls(getPath()));
    }

    public static void buildingWithManager(DataProvider dataProvider) {
        BuildManager buildManager = new BuildManager(2, dataProvider);
        builders.forEach(builder -> {
            buildManager.addBuilder(builder);
        });
        buildManager.finish();
    }

    public static void buildingWithoutManager(DataProvider dataProvider) {
        builders.forEach(builder -> {
            try {
                builder.build(dataProvider);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    private static String getPath() {
        String filePath = "/";
        LOG.debug("filePath = " + filePath);
        String windowsPath = FilenameUtils.separatorsToWindows(filePath);
        LOG.debug("windowsPath = " + windowsPath);
        Path rootPath = FileSystems.getDefault().getPath(".").toAbsolutePath().getParent();
        LOG.debug("rootPath = " + rootPath);
        String fullPath = rootPath.toString() + windowsPath+ FILE_NAME;
        LOG.debug("fullPath = " + fullPath);
        return fullPath;
    }
}
