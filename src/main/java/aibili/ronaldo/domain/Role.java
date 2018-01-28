package aibili.ronaldo.domain;

/**
 * Created by rtf on  2018/1/27.
 */
public class Role implements Comparable<Role>{
    private Integer id;
    private String en_name;
    private String cn_name;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEn_name() {
        return en_name;
    }

    public void setEn_name(String en_name) {
        this.en_name = en_name;
    }

    public String getCn_name() {
        return cn_name;
    }

    public void setCn_name(String cn_name) {
        this.cn_name = cn_name;
    }

    @Override
    public int compareTo(Role o) {
        if(id == o.getId()){
            return 0;
        }else if(id > o.getId()){
            return 1;
        }else{
            return -1;
        }
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof Role){
            if(this.id == ((Role)obj).getId()){
                return true;
            }
        }
        return false;
    }
    @Override
    public String toString() {
        return "Role{" +
                "id=" + id +
                ", en_name=" + en_name +
                ", cn_name=" + cn_name +
                '}';
    }
}
