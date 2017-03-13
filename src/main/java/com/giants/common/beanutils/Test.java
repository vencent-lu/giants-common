package com.giants.common.beanutils;

import java.beans.IntrospectionException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

public class Test {

    public static void main(String[] args) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException, IllegalArgumentException, IntrospectionException {
        List<Bean> beans = new ArrayList<Bean>();
        
        long time = System.currentTimeMillis();
        for (int i=0; i<1000000; i++) {
            byte gender = i%2 == 0 ? (byte)0 : (byte)1;
            boolean adult =  i%2 == 0 ? true : false;
            beans.add(new Bean(i+1, "name"+i, "address"+i, gender, i%100, adult));
        }
        System.out.println("init:"+ (System.currentTimeMillis() - time));
        
        for (int i=0; i<10; i++) {
            time = System.currentTimeMillis();
            for (Bean bean : beans) {
                PropertyUtils.getProperty(bean, "id");
                PropertyUtils.getProperty(bean, "name");
                PropertyUtils.getProperty(bean, "address");
                PropertyUtils.getProperty(bean, "gender");
                PropertyUtils.getProperty(bean, "age");
                PropertyUtils.getProperty(bean, "adult");
            }
            System.out.println("giants beanutils:"+ (System.currentTimeMillis() - time));
            time = System.currentTimeMillis();
                    
            for (Bean bean : beans) {
                org.apache.commons.beanutils.PropertyUtils.getProperty(bean, "id");
                org.apache.commons.beanutils.PropertyUtils.getProperty(bean, "name");
                org.apache.commons.beanutils.PropertyUtils.getProperty(bean, "address");
                org.apache.commons.beanutils.PropertyUtils.getProperty(bean, "gender");
                org.apache.commons.beanutils.PropertyUtils.getProperty(bean, "age");
                org.apache.commons.beanutils.PropertyUtils.getProperty(bean, "adult");
            }
            System.out.println("commons beanutils:"+ (System.currentTimeMillis() - time));
        }
               
        
        
    }

}
