package ru.croc.ecosmartdata.design.model;

import org.w3c.dom.Attr;

public class Attribute implements Comparable{
    String attribute;
    ColumnFamily columnFamily;

    String profile;
    String attribute2;
    String datatype;
    String type;
    String kind_type;
    String topic;
    String actuality_default_state;
    String clazz;
    String quality;
    String int_ext;
    String payment_condition;
    String is_state_agency;

    String predictors;

    String identifier;

    public Attribute(String attribute, ColumnFamily columnFamily) {
        this.attribute = attribute;
        this.columnFamily = columnFamily;
    }

    public String getAttribute() {
        return attribute;
    }

    public Attribute setAttribute(String attribute) {
        this.attribute = attribute;
        return this;
    }

    public ColumnFamily getColumnFamily() {
        return columnFamily;
    }

    public Attribute setColumnFamily(ColumnFamily columnFamily) {
        this.columnFamily = columnFamily;
        return this;
    }

    public String getProfile() {
        return profile;
    }

    public Attribute setProfile(String profile) {
        this.profile = profile;
        return this;
    }

    public String getAttribute2() {
        return attribute2;
    }

    public Attribute setAttribute2(String attribute2) {
        this.attribute2 = attribute2;
        return this;
    }

    public String getDatatype() {
        return datatype;
    }

    public Attribute setDatatype(String datatype) {
        this.datatype = datatype;
        return this;
    }

    public String getType() {
        return type;
    }

    public Attribute setType(String type) {
        this.type = type;
        return this;
    }

    public String getKind_type() {
        return kind_type;
    }

    public Attribute setKind_type(String kind_type) {
        this.kind_type = kind_type;
        return this;
    }

    public String getTopic() {
        return topic;
    }

    public Attribute setTopic(String topic) {
        this.topic = topic;
        return this;
    }

    public String getActuality_default_state() {
        return actuality_default_state;
    }

    public Attribute setActuality_default_state(String actuality_default_state) {
        this.actuality_default_state = actuality_default_state;
        return this;
    }

    public String getClazz() {
        return clazz;
    }

    public Attribute setClazz(String clazz) {
        this.clazz = clazz;
        return this;
    }

    public String getQuality() {
        return quality;
    }

    public Attribute setQuality(String quality) {
        this.quality = quality;
        return this;
    }

    public String getInt_ext() {
        return int_ext;
    }

    public Attribute setInt_ext(String int_ext) {
        this.int_ext = int_ext;
        return this;
    }

    public String getPayment_condition() {
        return payment_condition;
    }

    public Attribute setPayment_condition(String payment_condition) {
        this.payment_condition = payment_condition;
        return this;
    }

    public String getIs_state_agency() {
        return is_state_agency;
    }

    public Attribute setIs_state_agency(String is_state_agency) {
        this.is_state_agency = is_state_agency;
        return this;
    }

    public String getPredictors() {
        return predictors;
    }

    public Attribute setPredictors(String predictors) {
        this.predictors = predictors;
        return this;
    }

    public String getIdentifier() {
        return identifier;
    }

    public Attribute setIdentifier(String identifier) {
        this.identifier = identifier;
        return this;
    }

    @Override
    public boolean equals(Object obj) {
        Attribute that = (Attribute) obj;
        if (this.getAttribute() == that.getAttribute()) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public int compareTo(Object o) {
        Attribute that = (Attribute) o;
        return this.getAttribute().compareTo(that.getAttribute());
    }

    @Override
    public String toString() {
        return "Attribute{" +
                "profile='" + profile + '\'' +
                "}";
    }
}
