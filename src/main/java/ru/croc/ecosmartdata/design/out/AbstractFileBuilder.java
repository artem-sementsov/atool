package ru.croc.ecosmartdata.design.out;

import org.apache.commons.io.FilenameUtils;
import org.apache.log4j.Logger;
import ru.croc.ecosmartdata.design.DataProvider;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;

public class AbstractFileBuilder implements Builder {

    private static final Logger LOG = Logger.getLogger(AbstractFileBuilder.class);

    @Override
    public void build(DataProvider dataProvider) throws IOException {
    }

    public void saveFile(String filePath, String content) throws IOException {
        LOG.debug("filePath = " + filePath);
        String windowsPath = FilenameUtils.separatorsToWindows(filePath);
        LOG.debug("windowsPath = " + windowsPath);
        Path rootPath = FileSystems.getDefault().getPath(".").toAbsolutePath().getParent();
        LOG.debug("rootPath = " + rootPath);
        String fullPath = rootPath.toString() + windowsPath;
        LOG.debug("fullPath = " + fullPath);
        BufferedWriter writer = new BufferedWriter(new FileWriter(fullPath));
        writer.write(content);
        writer.close();
    }

}
