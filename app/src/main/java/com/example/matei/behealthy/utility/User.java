package com.example.matei.behealthy.utility;

import java.io.Serializable;

public class User implements Serializable{
    private String name;
    private String pass;

    public User(String name, String password){
        this.name = name;
        this.pass = password;
    }

    public int getPassword() {
        return pass.hashCode();
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        if(name != null && name.length() > 4) {
            this.name = name;
        }
    }

    public void setPassword(String password){
        if(password != null && password.length() > 4){
            this.pass = password;
        }
    }
}
