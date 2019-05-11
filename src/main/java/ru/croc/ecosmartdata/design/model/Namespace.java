package ru.croc.ecosmartdata.design.model;

import java.util.Set;
import java.util.TreeSet;

public class Namespace implements Comparable{
    String namespace;
    Set<Entity> entities = new TreeSet<>();

    public Namespace(String namespace) {
        this.namespace = namespace;
    }


    public String getNamespace() {
        return namespace;
    }

    public Namespace setNamespace(String namespace) {
        this.namespace = namespace;
        return this;
    }

    public void addEntity(Entity entity) {
        entities.add(entity);
    }

    @Override
    public boolean equals(Object obj) {
        Namespace that = (Namespace) obj;
        if (that.getNamespace() == this.getNamespace()) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public int compareTo(Object o) {
        Namespace that = (Namespace) o;
        return this.getNamespace().compareTo(that.getNamespace());
    }

    @Override
    public String toString() {
        return "Namespace{" +
                "namespace='" + namespace + '\'' +
                "}";
    }
}
