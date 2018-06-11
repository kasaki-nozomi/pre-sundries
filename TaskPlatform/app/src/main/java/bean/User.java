package bean;


import java.io.Serializable;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;

public class User extends BmobUser implements Serializable{

    // 父类中已经存在的属性
    // private String id;
    // private String username;
    // private String password;
    // private String email;
    // private String regTime;

    private String sex;  				// 性别
    private String profiles;            // 简介
    private String age;                 // 年龄
    private String phone; 				// 电话
    private String cademy; 				// 学院
    private String address;             // 所在地
    private String QQ;                  // QQ
    private BmobFile touxiang;          // 头像

    public BmobFile getTouxiang() {
        return touxiang;
    }

    public void setTouxiang(BmobFile touxiang) {
        this.touxiang = touxiang;
    }

    public String getAddress() {
        return this.address;
    }

    public void setAddress(String adress) {
        this.address = adress;
    }

    public String getAge() {
        return this.age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getProfiles() {
        return this.profiles;
    }

    public void setProfiles(String profiles) {
        this.profiles = profiles;
    }

    public String getQQ() {
        return this.QQ;
    }

    public void setQQ(String QQ) {
        this.QQ = QQ;
    }

    public String getCademy() {
        return this.cademy;
    }

    public void setCademy(String cademy) {
        this.cademy = cademy;
    }

    public String getPhone() {
        return this.phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getSex() {
        return this.sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

}
