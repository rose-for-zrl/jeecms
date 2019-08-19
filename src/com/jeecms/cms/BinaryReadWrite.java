package com.jeecms.cms;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.RandomAccessFile;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import com.jeecms.cms.Employee;
import java.util.Date;

public class BinaryReadWrite {
	
	static String filename = "emp.dat";

	public void addBinaryData(Employee data) throws Exception
    {
    	 DataOutputStream out=new DataOutputStream(new FileOutputStream(filename,true));
    	 System.out.println(data.toString());
    	 writeEmp(out,data);
    	 out.close();
    }
	
	public boolean isPassword(long id,String password) throws Exception
    {
	  	RandomAccessFile in=new RandomAccessFile(filename,"rw");
   	 int len=(int)(in.length()/Employee.RecordSize);
   	 List<Employee> ls=new ArrayList<Employee>();
        for(long i=0;i<len;i++){
            in.seek(i*Employee.RecordSize);
            ls.add(readEmp(in));
        }
      in.close();
      for(Employee emp:ls){
         if(emp.getId() == id)
            {
            if(emp.getPasswrod1().equals(password))
            	return true;
            else if(emp.getPasswrod2().equals(password)) 
            	return true;
            else if(emp.getPasswrod3().equals(password))
            	return true;
            else if(emp.getPasswrod4().equals(password))
             	return true;
            else
             	return false;
            }
        }
     return false;
    }
	
	public boolean isDateOverdue(String user) throws Exception
	{
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	  	RandomAccessFile in=new RandomAccessFile(filename,"rw");
	   	 int len=(int)(in.length()/Employee.RecordSize);
	   	 List<Employee> ls=new ArrayList<Employee>();
	        for(long i=0;i<len;i++){
	            in.seek(i*Employee.RecordSize);
	            ls.add(readEmp(in));
	        }
	      in.close();
	      for(Employee emp:ls){
	         if(emp.getName().equals(user))
	            {
	        	 	Date d = sdf.parse(emp.getUpdateTime()); //字符串转时间
	        	 	Date curd = new Date();
	        	 	long l=curd.getTime()-d.getTime();     
	        	 	long day=l/(24*60*60*1000);   
	        	 	if(day > 90)
	        	 		return true;
	        	 	else
	        	 		return false;
	            }
	        }
		
		
		return false;
	}
	public void deleteBinaryData(long id) throws Exception
    {
    	RandomAccessFile in=new RandomAccessFile(filename,"rw");
    	 int len=(int)(in.length()/Employee.RecordSize);
    	 List<Employee> ls=new ArrayList<Employee>();
         for(long i=0;i<len;i++){
             in.seek(i*Employee.RecordSize);
             ls.add(readEmp(in));
         }
       for(Employee emp:ls){
          if(emp.getId() == id)
             {
              ls.remove(emp);
              break;
             }
         }
      in.close();
      
 	 	DataOutputStream out=new DataOutputStream(new FileOutputStream(filename,false));
 	 	for(Employee emp:ls){
 	 		writeEmp(out,emp);
 	 	}
 	 	out.close();
      	
    }
    
	public void modifyBinaryData(Employee data) throws Exception
    {
    	RandomAccessFile in=new RandomAccessFile(filename,"rw");
   	 int len=(int)(in.length()/Employee.RecordSize);
   	 List<Employee> ls=new ArrayList<Employee>();
        for(long i=0;i<len;i++){
            in.seek(i*Employee.RecordSize);
            ls.add(readEmp(in));
        }
      for(Employee emp:ls){
         if(emp.getId() == data.getId())
            {
             ls.remove(emp);
             data.setPasswrod2(emp.getPasswrod1());
             data.setPasswrod3(emp.getPasswrod2());
             data.setPasswrod4(emp.getPasswrod3());
             break;
            }
        }
     ls.add(data);
     in.close();
     
	 	DataOutputStream out=new DataOutputStream(new FileOutputStream(filename,false));
	 	for(Employee emp:ls){
	 		writeEmp(out,emp);
	 	}
	 	out.close();
    }
   
	public void writeEmp(DataOutput out,Employee emp) throws Exception{
    	  out.writeLong(emp.getId());
        writeString(emp.getName(),Employee.NameSize,out);
        writeString(emp.getPasswrod1(),Employee.NameSize,out);
        writeString(emp.getPasswrod2(),Employee.NameSize,out);
        writeString(emp.getPasswrod3(),Employee.NameSize,out);
        writeString(emp.getPasswrod4(),Employee.NameSize,out);
        writeString(emp.getUpdateTime(),Employee.NameSize,out);
        System.out.println(emp.toString());
    }
    
	public Employee readEmp(DataInput in)  throws Exception{
    	 long id=in.readLong();
        String name=readString(Employee.NameSize,in);
        String Passwrod1=readString(Employee.NameSize,in);
        String Passwrod2=readString(Employee.NameSize,in);
        String Passwrod3=readString(Employee.NameSize,in);
        String Passwrod4=readString(Employee.NameSize,in);
        String Time=readString(Employee.NameSize,in);
        Employee emp = new Employee(id,name,Passwrod1,Time);
        emp.setPasswrod2(Passwrod2);
        emp.setPasswrod3(Passwrod3);
        emp.setPasswrod4(Passwrod4);
        System.out.println(emp.toString());
        return emp;
    }
    
    // 写入最大长度为size的字符串，不足写0
	public void writeString(String str,int size,DataOutput out) throws Exception{
        for(int i=0;i<size;i++){
            char c=0;
            if(i<str.length()){
                c=str.charAt(i);
            }
            out.writeChar(c);
        }
    }
    
    // 读入最大长度为size的字符串
	public String readString(int size,DataInput in) throws Exception{
        StringBuilder sb=new StringBuilder();
        
        for(int i=0;i<size;i++){
            char c=in.readChar();
            
            if(c==0){
                continue;
            }else{
                sb.append(c);
            }
        }
        
        return sb.toString();
    }
}
