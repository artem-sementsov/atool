package ru.croc.ecosmartdata.design.model;

import java.util.Objects;
import java.util.Set;
import java.util.TreeSet;

public class Entity implements Comparable {
    String entity;
    Namespace namespace;
    Set<ColumnFamily> cfs = new TreeSet<>();

    public Entity(String entity) {
        this.entity = entity;
    }

    public Entity(String entity, Namespace namespace) {
        this.entity = entity;
        this.namespace = namespace;
    }

    public Entity(String entity, Namespace namespace, Set<ColumnFamily> cfs) {
        this.entity = entity;
        this.namespace = namespace;
        this.cfs = cfs;
    }

    public void addColumnFamily(ColumnFamily columnFamily) {
        cfs.add(columnFamily);
    }

    public String getEntity() {
        return entity;
    }

    public Namespace getNamespace() {
        return namespace;
    }

    public Set<ColumnFamily> getCfs() {
        return cfs;
    }

    public Entity setEntity(String entity) {
        this.entity = entity;
        return this;
    }

    public Entity setNamespace(Namespace namespace) {
        this.namespace = namespace;
        return this;
    }

    public Entity setCfs(Set<ColumnFamily> cfs) {
        this.cfs = cfs;
        return this;
    }

    @Override
    public int hashCode() {
        return Objects.hash(entity);
    }

    @Override
    public boolean equals(Object obj) {
        Entity that = (Entity) obj;
        if (this.getEntity() == that.getEntity()) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public int compareTo(Object o) {
        Entity that = (Entity) o;
        return this.getEntity().compareTo(that.getEntity());
    }

    @Override
    public String toString() {
        return "Entity{" +
                "entity='" + entity + '\'' +
                ", namespace=" + namespace +
                ", cfs=" + cfs +
                "}";
    }
}
