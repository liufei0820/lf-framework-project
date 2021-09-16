package com.lf.rpc.client.entity;

import java.io.Serializable;

/**
 * @Classname User
 * @Date 2021/9/16 下午1:37
 * @Created by fei.liu
 */
public class User implements Serializable {

    private long uid;
    private short age;
    private short sex;

    public long getUid() {
        return uid;
    }

    public void setUid(long uid) {
        this.uid = uid;
    }

    public short getAge() {
        return age;
    }

    public void setAge(short age) {
        this.age = age;
    }

    public short getSex() {
        return sex;
    }

    public void setSex(short sex) {
        this.sex = sex;
    }
}
