package com.jeecms.cms;

public class Employee{
    public static int RecordSize=368;// 总计8+30*2+4=72字节，算准总字节数对于连续读取很重要
    private long id;// long类型似占8字节
    private String name;
    private String password1;
    private String password2;
    private String password3;
    private String password4;
    private String updateTime;
    public static int NameSize=30;// 预计名称最多30个字符，占30*2=60字节

    
    public Employee(long id,String name,String password1,String time){
    	  this.id = id;
        this.name=name;
        this.password1=password1;
        this.password2="";
        this.password3="";
        this.password4="";
        this.updateTime=time;
    }
    
    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }
    public String toString(){
        return "id:"+id+" name:"+name+" password1:"+password1+" password2:"+password2+" password3:"+password3+" password4:"+password4+" update time:"+updateTime;
    }
    
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getUpdateTime() {
    	return this.updateTime;
    }
    public void setUpdateTime(String time) {
        this.updateTime = time;
    }
    public String getPasswrod1() {
    	return this.password1;
    }
    public void setPasswrod1(String password1) {
        this.password1 = password1;
    }
    public String getPasswrod2() {
    	return this.password2;
    }
    public void setPasswrod2(String Passwrod2) {
        this.password2 = Passwrod2;
    }
    public String getPasswrod3() {
    	return this.password3;
    }
    public void setPasswrod3(String Passwrod3) {
        this.password3 = Passwrod3;
    }
    public String getPasswrod4() {
    	return this.password4;
    }
    public void setPasswrod4(String Passwrod4) {
        this.password4 = Passwrod4;
    }
}
