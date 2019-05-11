package ru.croc.ecosmartdata.design.out;

import ru.croc.ecosmartdata.design.DataProvider;

import java.io.IOException;

public interface Builder {
    public void build(DataProvider dataProvider) throws IOException;
}
