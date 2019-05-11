package ru.croc.ecosmartdata.design.out.dml;

import javafx.util.Pair;
import ru.croc.ecosmartdata.design.DataProvider;
import ru.croc.ecosmartdata.design.model.Attribute;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class AliasDMLFileBuilder extends AbstractDMLFileBuilder {
    private static final String ALIAS_TABLE = "alias";
    private static final String ALIAS_CF = "basic_alias";
    private static final String ALIAS_Q = "value";

    @Override
    public void build(DataProvider dataProvider) throws IOException {
        Map<String, Attribute> attributes = dataProvider.getAttributes();
        List<Pair<String, String>> results = new ArrayList<>();
        attributes.forEach((attribute_s, attribute) -> {
            results.add(new Pair<>(ALIAS_TABLE + "_" + attribute_s + ".properties", scriptCreate(attribute)));
        });
        saveDMLScript(results);
    }

    String scriptCreate(Attribute attribute) {
        return "put '" + ALIAS_TABLE + "', '" + attribute.getAttribute() + "', '" + ALIAS_CF + ":" + ALIAS_Q + "', '" +
                attribute.getColumnFamily().getEntity().getNamespace().getNamespace() + ":" +
                attribute.getColumnFamily().getEntity().getEntity() + "." +
                attribute.getColumnFamily().getColumnFamily() + "." +
                attribute.getAttribute() + "'\n" +
                "exit";

    }
}
