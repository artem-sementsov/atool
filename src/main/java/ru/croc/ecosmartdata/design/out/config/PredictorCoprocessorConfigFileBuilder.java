package ru.croc.ecosmartdata.design.out.config;

import org.apache.log4j.Logger;
import ru.croc.ecosmartdata.design.DataProvider;
import ru.croc.ecosmartdata.design.model.Attribute;
import ru.croc.ecosmartdata.design.out.AbstractFileBuilder;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class PredictorCoprocessorConfigFileBuilder extends AbstractFileBuilder {

    private static final Logger LOG = Logger.getLogger(PredictorCoprocessorConfigFileBuilder.class);

    @Override
    public void build(DataProvider dataProvider) throws IOException {
        Map<String, Attribute> attributes = dataProvider.getAttributes();
        saveConfig(createConfig(attributes));
        //showTablesWithPredictors(attributes);
    }

    void saveConfig(String content) throws IOException {
        String filePath = "/application/hdfs/predictors.properties";
        saveFile(filePath, content);
    }

    Map<String, Set<String>> transformMap(Map<String, Attribute> attributes) {
        // transform map
        // dependencies attribute -> list of topics
        Map<String, Set<String>> deps = new HashMap<>();
        for (String attribute_s : attributes.keySet()) {
            Attribute attribute = attributes.get(attribute_s);
            String topic = attribute.getTopic();
            String qList = attribute.getPredictors();
            if (qList == null) {
                LOG.warn("Cannot check dependencies between predictors and attributes because list of predictor is null or empty. Row = " + attribute.getIdentifier());
            } else {
                if (!qList.equals("не является предиктором с видом event") && topic.startsWith("predictor_")) {
                    List<String> qs = Arrays.asList(qList.split(";"));
                    for (String q : qs) {
                        Attribute foundAttribute = attributes.get(q);
                        if (foundAttribute == null) {
                            LOG.warn(String.format("Cannot create dependency attribute -> predictors because attribute '%s' in attributes list was not found. Row = %s",
                                    q, attribute.getIdentifier()));
                        } else {
                            if (q != null && !q.isEmpty()) {
                                Set<String> predictors = deps.get(q);
                                if (predictors == null) {
                                    predictors = new TreeSet<>();
                                    predictors.add(topic);
                                    deps.put(q, predictors);
                                } else {
                                    predictors.add(topic);
                                }
                            }
                        }
                    }
                }
            }
        }
        return deps;
    }

    void showTablesWithPredictors(Map<String, Attribute> attributes) {
        Map<String, Set<String>> deps = transformMap(attributes);
        Set<String> predictors = new TreeSet<>();
        for (String attribute : deps.keySet()) {
            for (String dep : deps.get(attribute)) {
                predictors.add(dep);
            }
        }

        Set<String> tables = new TreeSet<>();
        for (String predictor : predictors) {
            tables.add(attributes.get(predictor).getColumnFamily().getEntity().getEntity());
        }

        String tablesList = "";
        for (String table : tables) {
            tablesList = tablesList + table + "\n";
        }
        System.out.println(tablesList);
    }

    String createConfig(Map<String, Attribute> attributes) {
        Set<String> tables = new TreeSet<>();
        Map<String, Set<String>> deps = transformMap(attributes);
        List<String> rows = new ArrayList<>();
        String content = "# configuration for postPut coprocessor\n" +
                "# specify predictor recalculation event in format:\n" +
                "# ns:table.cf.qualifier = topic1,topic2,topic3\n";
        for (String q : deps.keySet()) {
            Attribute attribute = attributes.get(q);
            String predictors = "";
            // create list of predictors
            for (String predictor : deps.get(q)) {
                predictors = predictors + "," + predictor;
            }
            predictors = predictors.substring(1);
            String namespace_s = attribute.getColumnFamily().getEntity().getNamespace().getNamespace();
            String entity_s = attribute.getColumnFamily().getEntity().getEntity();
            String row = namespace_s + ":" +
                    entity_s + "." +
                    attribute.getColumnFamily().getColumnFamily() + "." +
                    q + " = " + predictors + "\n";
            rows.add(row);
            tables.add(namespace_s + ":" + entity_s);
        }
        System.out.println(tables);
        // sort by full name
        for (String row : rows.stream().sorted().collect(Collectors.toCollection(TreeSet::new))) {
            content = content + row;
        }
        return content;
    }
}
