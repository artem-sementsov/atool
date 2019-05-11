package ru.croc.ecosmartdata.design.out.ddl;

import javafx.util.Pair;
import ru.croc.ecosmartdata.design.DataProvider;
import ru.croc.ecosmartdata.design.model.Namespace;
import ru.croc.ecosmartdata.design.out.Builder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class NamespaceDDLFileBuilder extends AbstractDDLFileBuilder implements Builder {
    public void build(DataProvider dataProvider) throws IOException {
        Map<String, Namespace> namespaces = dataProvider.getNamespaces();
        List<Pair<String, String>> results = new ArrayList<>();
        namespaces.forEach((namespace_s, namespace) -> {
            results.add(new Pair<>("ns@" + namespace_s + ".properties", scriptCreate(namespace)));
        });
        saveDDLScript(results);
    }

    String scriptCreate(Namespace namespace) {
        return "create_namespace '" + namespace.getNamespace() + "'\n";
    }
}
