package ru.croc.ecosmartdata.design;

import org.apache.log4j.Logger;
import ru.croc.ecosmartdata.design.in.Reader;
import ru.croc.ecosmartdata.design.model.Attribute;
import ru.croc.ecosmartdata.design.model.ColumnFamily;
import ru.croc.ecosmartdata.design.model.Entity;
import ru.croc.ecosmartdata.design.model.Namespace;

import java.util.HashMap;
import java.util.Map;

public class DataProvider {

    private static final Logger LOG = Logger.getLogger(DataProvider.class);

    Reader reader;
    Map<String, Namespace> namespaces = new HashMap<>();
    Map<String, Entity> entities = new HashMap<>();
    Map<String, ColumnFamily> columnFamilies = new HashMap<>();
    Map<String, Attribute> attributes = new HashMap<>();

    public DataProvider(Reader reader) {
        this.reader = reader;

        // namespaces
        reader.readNamespaces().forEach(namespace_s -> {
            Namespace namespace = namespaces.get(namespace_s);
            if (namespace != null) {
                LOG.warn("Duplicate namespace " + namespace_s);
            } else {
                namespaces.put(namespace_s, new Namespace(namespace_s));
            }
        });

        // entities
        reader.readEntities().forEach(pair -> {
            String entity_s = pair.getKey();
            String namespace_s = pair.getValue();
            if (entity_s != null && namespace_s != null) {
                if (entity_s.isEmpty()) {
                    LOG.warn("Entity name is empty");
                } else {
                    if (!entity_s.contains("--//--")) {
                        Namespace namespace = namespaces.get(namespace_s);
                        if (namespace != null) {
                            Entity entity = entities.get(entity_s);
                            if (entity == null) {
                                entity = new Entity(entity_s, namespace);
                                entities.put(entity_s, entity);
                                namespace.addEntity(entity);
                            } else {
                                LOG.warn("Duplicate entity " + entity_s);
                            }
                        } else {
                            LOG.warn(String.format("No such namespace '%s'. Entity '%s'", namespace_s, entity_s));
                        }
                    }
                }
            }
        });

        // cfs
        reader.readColumnFamilies().forEach(pair -> {
            String cf_s = pair.getKey();
            String entity_s = pair.getValue();
            if (cf_s != null && entity_s != null) {
                Entity entity = entities.get(entity_s);
                if (entity != null) {
                    ColumnFamily columnFamily = columnFamilies.get(cf_s);
                    if (columnFamily == null) {
                        columnFamily = new ColumnFamily(cf_s, entity);
                        columnFamilies.put(cf_s, columnFamily);
                        entity.addColumnFamily(columnFamily);
                    } else {
                        LOG.warn("Duplicate column family " + cf_s);
                    }
                } else {
                    LOG.warn(String.format("No such entity '%s'. Column family '%s'", entity_s, cf_s));
                }
            }
        });

        // attributes
        reader.readAttributes().forEach(map -> {
            String ns_s = map.get("namespace");
            String entity_s = map.get("entity");
            String cf_s = map.get("cf");
            String q_s = map.get("q");
            if (ns_s != null && entity_s != null && cf_s != null && q_s != null) {
                if (q_s.isEmpty()) {
                    LOG.warn("Attribute name is empty. Row = " + map.get("identifier"));
                } else {
                    Attribute attribute = attributes.get(q_s);
                    if (attribute != null) {
                        LOG.warn("Duplicate attribute " + q_s + " rows " + map.get("identifier") + " and " + attribute.getIdentifier());
                        //LOG.debug(String.join(",", new String[]{map.get("namespace"), map.get("entity"), map.get("cf"), map.get("q")}));
                    } else {
                        ColumnFamily columnFamily = columnFamilies.get(cf_s);
                        if (columnFamily != null) {
                            Entity entity = columnFamily.getEntity();
                            if (entity.getEntity().equals(entity_s)) {
                                Namespace namespace = entity.getNamespace();
                                if (namespace.getNamespace().equals(ns_s)) {
                                    attribute = new Attribute(q_s, columnFamily);
                                    String profile = map.get("profile");
                                    if (profile == null || profile.contains("--//--")) profile = "";
                                    attribute.setProfile(profile);
                                    String datatype = map.get("datatype");
                                    if (datatype == null || datatype.contains("--//--")) datatype = "";
                                    attribute.setDatatype(datatype);
                                    String type = map.get("type");
                                    if (type == null || type.contains("--//--")) type = "";
                                    attribute.setType(type);
                                    String kind_type = map.get("kind_type");
                                    if (kind_type == null || kind_type.contains("--//--")) kind_type = "";
                                    attribute.setKind_type(kind_type);
                                    String topic = map.get("topic");
                                    if (topic == null || topic.contains("--//--")) topic = "";
                                    attribute.setTopic(topic);
                                    String actuality_default_state = map.get("actuality_default_state");
                                    if (actuality_default_state == null || actuality_default_state.contains("--//--")) actuality_default_state = "";
                                    attribute.setActuality_default_state(actuality_default_state);
                                    String clazz = map.get("class");
                                    if (clazz == null || clazz.contains("--//--")) clazz = "ru.croc.smartdata.SimpleAttribute";
                                    attribute.setClazz(clazz);
                                    String quality = map.get("quality");
                                    if (quality == null || quality.contains("--//--")) quality = "1";
                                    attribute.setQuality(quality);
                                    String int_ext = map.get("int_ext");
                                    if (int_ext == null || int_ext.contains("--//--")) int_ext = "";
                                    attribute.setInt_ext(int_ext);
                                    String payment_condition = map.get("payment_condition");
                                    if (payment_condition == null || payment_condition.contains("--//--")) payment_condition = "";
                                    attribute.setPayment_condition(payment_condition);
                                    String is_state_agency = map.get("is_state_agency");
                                    if (is_state_agency == null || is_state_agency.contains("--//--")) is_state_agency = "";
                                    attribute.setIs_state_agency(is_state_agency);
                                    String predictors = map.get("predictors");
                                    if (predictors == null || predictors.contains("--//--")) predictors = "";
                                    attribute.setPredictors(predictors);
                                    attribute.setIdentifier(map.get("identifier"));
                                    attributes.put(q_s, attribute);
                                } else {
                                    LOG.warn(String.format("Cannot create attribute '%s', because entity '%s' belongs to namespace '%s', not to namespace '%s'",
                                            q_s, entity_s, namespace.getNamespace(), ns_s));
                                }
                            } else {
                                LOG.warn(String.format("Cannot create attribute '%s', because column family '%s' belongs to entity '%s', not to entity '%s'",
                                        q_s, cf_s, entity.getEntity(), entity_s));
                            }
                        } else {
                            LOG.warn(String.format("No such column family '%s'. Attribute '%s'", cf_s, q_s));
                        }
                    }
                }
            } else {
                LOG.info("Cannot create attribute from row = " + map.get("identifier") + ". Attribute had been skipped");
            }
        });
    }

    public Map<String, Namespace> getNamespaces() {
        return namespaces;
    }

    public Map<String, Entity> getEntities() {
        return entities;
    }

    public Map<String, ColumnFamily> getColumnFamilies() {
        return columnFamilies;
    }

    public Map<String, Attribute> getAttributes() {
        return attributes;
    }
}
