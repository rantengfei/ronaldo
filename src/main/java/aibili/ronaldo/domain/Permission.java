package aibili.ronaldo.domain;

/**
 * Created by rtf on  2018/1/28.
 */
public class Permission {
    private Integer id;
    private String name;
    private String description;
    private String url;
    private String method;

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    @Override
    public String toString() {
        return "Permission{" +
                "id=" + id +
                ", name=" + name +
                ", url=" + url +
                ", method=" + method +
                ", description=" + description +
                '}';
    }
}
