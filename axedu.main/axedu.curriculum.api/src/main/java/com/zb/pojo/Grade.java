package com.zb.pojo;
import java.io.Serializable;

/***
*   
*/
public class Grade implements Serializable {
    //年级编号
    private Integer gradeid;
    //年级名称
    private String gradename;
    //级别（1.幼儿2.小学3.初中4.高中）
    private Integer level;
    //get set 方法
    public void setGradeid (Integer  gradeid){
        this.gradeid=gradeid;
    }
    public  Integer getGradeid(){
        return this.gradeid;
    }
    public void setGradename (String  gradename){
        this.gradename=gradename;
    }
    public  String getGradename(){
        return this.gradename;
    }
    public void setLevel (Integer  level){
        this.level=level;
    }
    public  Integer getLevel(){
        return this.level;
    }
}
