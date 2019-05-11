package ru.croc.ecosmartdata.design.model;

import java.util.Set;
import java.util.TreeSet;

public class ColumnFamily implements Comparable {
    String columnFamily;
    Entity entity;
    Set<Attribute> attributes = new TreeSet<>();

    public ColumnFamily(String columnFamily) {
        this.columnFamily = columnFamily;
    }

    public ColumnFamily(String columnFamily, Entity entity) {
        this.columnFamily = columnFamily;
        this.entity = entity;
    }

    public String getColumnFamily() {
        return columnFamily;
    }

    public ColumnFamily setColumnFamily(String columnFamily) {
        this.columnFamily = columnFamily;
        return this;
    }

    public Entity getEntity() {
        return entity;
    }

    public ColumnFamily setEntity(Entity entity) {
        this.entity = entity;
        return this;
    }

    public Set<Attribute> getAttributes() {
        return attributes;
    }

    public ColumnFamily setAttributes(Set<Attribute> attributes) {
        this.attributes = attributes;
        return this;
    }

    @Override
    public boolean equals(Object obj) {
        ColumnFamily that = (ColumnFamily) obj;
        if (that.getColumnFamily() == this.getColumnFamily()) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public int compareTo(Object o) {
        ColumnFamily that = (ColumnFamily) o;
        return this.getColumnFamily().compareTo(that.getColumnFamily());
    }

    @Override
    public String toString() {
        return "ColumnFamily{" +
                "columnFamily='" + columnFamily + '\'' +
                "}";
    }
}
