package com.cui.java.study.web.spring.aop.springaop.factorybean;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.util.StringUtils;

public class PersonFactoryBean implements FactoryBean {
    private String property;

    public String getProperty() {
        return property;
    }

    public void setProperty(String property) {
        this.property = property;
    }

    @Override
    public Object getObject() throws Exception {
        if (StringUtils.isEmpty(property)) {
            return null;
        }
        String[] properties = property.split(",");
        Person person = new Person();
        person.setId(Integer.parseInt(properties[0]));
        person.setName(properties[1]);
        return person;
    }

    @Override
    public Class<?> getObjectType() {
        return Person.class;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }
}
