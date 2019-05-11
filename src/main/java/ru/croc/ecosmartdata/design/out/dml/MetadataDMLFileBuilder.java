package ru.croc.ecosmartdata.design.out.dml;

import javafx.util.Pair;
import ru.croc.ecosmartdata.design.DataProvider;
import ru.croc.ecosmartdata.design.model.Attribute;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MetadataDMLFileBuilder extends AbstractDMLFileBuilder {
    private static final String METADATA_TABLE = "attribute_metadata";
    private static final String METADATA_BASIC_METADATA_CF = "basic_metadata";
    private static final String METADATA_CHARACTERISTIC_CF = "characteristic";

    @Override
    public void build(DataProvider dataProvider) throws IOException {
        Map<String, Attribute> attributes = dataProvider.getAttributes();
        List<Pair<String, String>> results = new ArrayList<>();
        attributes.forEach((attribute_s, attribute) -> {
            results.add(new Pair<>(METADATA_TABLE + "_" + attribute_s + ".properties", scriptCreate(attribute)));
        });
        saveDMLScript(results);
    }

    String scriptCreate(Attribute attribute) {
        String prefix = "put '" + METADATA_TABLE + "', '" + attribute.getAttribute() + "', '";
        return prefix + METADATA_BASIC_METADATA_CF + ":profile', '" + attribute.getProfile() + "'\n" +
                prefix + METADATA_BASIC_METADATA_CF + ":entity', '" + attribute.getColumnFamily().getEntity().getEntity() + "'\n" +
                prefix + METADATA_BASIC_METADATA_CF + ":column_family', '" + attribute.getColumnFamily().getColumnFamily() + "'\n" +
                prefix + METADATA_BASIC_METADATA_CF + ":attribute2', '" + attribute.getAttribute() + "'\n" +
                prefix + METADATA_BASIC_METADATA_CF + ":datatype', '" + attribute.getDatatype() + "'\n" +
                prefix + METADATA_BASIC_METADATA_CF + ":type3', '" + attribute.getType() + "'\n" +
                prefix + METADATA_BASIC_METADATA_CF + ":kind_type', '" + attribute.getKind_type() + "'\n" +
                prefix + METADATA_BASIC_METADATA_CF + ":topic', '" + attribute.getTopic() + "'\n" +
                prefix + METADATA_BASIC_METADATA_CF + ":actuality_default_state', '" + attribute.getActuality_default_state() + "'\n" +
                prefix + METADATA_BASIC_METADATA_CF + ":class', '" + attribute.getClazz() + "'\n" +
                prefix + METADATA_BASIC_METADATA_CF + ":quality', '" + attribute.getQuality() + "'\n" +
                prefix + METADATA_CHARACTERISTIC_CF + ":int_ext', '" + attribute.getInt_ext() + "'\n" +
                prefix + METADATA_CHARACTERISTIC_CF + ":payment_condition', '" + attribute.getPayment_condition() + "'\n" +
                prefix + METADATA_CHARACTERISTIC_CF + ":is_state_agency', '" + attribute.getIs_state_agency() + "'\n" +
                "exit";
    }
}
