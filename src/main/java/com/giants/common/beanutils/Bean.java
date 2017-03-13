package com.giants.common.beanutils;

public class Bean {
    
    private Integer id;
    private String name;
    private String address;
    private Byte gender;
    private Integer age;
    private boolean adult;
    
        
    public Bean(Integer id, String name, String address, Byte gender, Integer age, boolean adult) {
        super();
        this.id = id;
        this.name = name;
        this.address = address;
        this.gender = gender;
        this.age = age;
        this.adult = adult;
    }
    
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }
    public Byte getGender() {
        return gender;
    }
    public void setGender(Byte gender) {
        this.gender = gender;
    }
    public Integer getAge() {
        return age;
    }
    public void setAge(Integer age) {
        this.age = age;
    }
    public boolean isAdult() {
        return adult;
    }
    public void setAdult(boolean adult) {
        this.adult = adult;
    }
    
    

}
