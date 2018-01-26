package aibili.ronaldo.domain;

import java.io.Serializable;

/**
 * Created by programmer on  2018/1/25.
 */

public class Users implements Serializable{
    private Integer id;
    private String name;
    private String password;
    private String gender;

    public Users() {
        super();
    }

    public Users(String name, String password, String gender) {
        super();
        this.name = name;
        this.password = password;
        this.gender = gender;
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

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
