package ru.croc.ecosmartdata.design.out.ddl;

import javafx.util.Pair;
import org.apache.log4j.Logger;
import ru.croc.ecosmartdata.design.DataProvider;
import ru.croc.ecosmartdata.design.model.ColumnFamily;
import ru.croc.ecosmartdata.design.model.Entity;
import ru.croc.ecosmartdata.design.out.Builder;

import java.io.IOException;
import java.util.*;

public class EntitiesDDLFileBuilder extends AbstractDDLFileBuilder implements Builder {

    private static final Logger LOG = Logger.getLogger(EntitiesDDLFileBuilder.class);

    public void build(DataProvider dataProvider) throws IOException {
        Map<String, Entity> entities = dataProvider.getEntities();
        List<Pair<String, String>> results = new ArrayList<>();
        entities.forEach((entity_s, entity) -> {
            Set<ColumnFamily> cfs = entity.getCfs();
            if (entity.getCfs() == null || entity.getCfs().isEmpty()) {
                if (!entity_s.startsWith("services.")) {
                    LOG.warn("No column families for entity '" + entity_s + "'. This entity was skipped");
                    return;
                } else {
                    cfs = new TreeSet<>();
                    cfs.add(new ColumnFamily("cf"));
                    entity.setCfs(cfs);
                }
            } else {
                results.add(new Pair<>(entity.getNamespace().getNamespace() + "@" + entity_s + ".properties", scriptRecreate(entity)));
            }
        });
        saveDDLScript(results);
    }

    String scriptCreate(Entity entity) {
        String result = "create '" + entity.getNamespace().getNamespace() + ":" + entity.getEntity() + "'";
        for (ColumnFamily cf : entity.getCfs()) {
            result = result + ", '" + cf.getColumnFamily() + "'";
        }
        result = result + "\n";
        return result;
    }

    String scriptRecreate(Entity entity) {
        return "disable '" + entity.getNamespace().getNamespace() + ":" + entity.getEntity() + "'\n" +
                "drop '" + entity.getNamespace().getNamespace() + ":" + entity.getEntity() + "'\n" +
                scriptCreate(entity);
    }
}
