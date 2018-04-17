package com.anomaly.android.kyanbas.Modal;

/**
 * Created by Harshil on 17-04-2018.
 */

public class Category {

    private Integer id;
    private String name;
    private Integer parentId;
    private String description;
    private String nicename;

    public Category(Integer id, String name, Integer parentId, String description, String nicename) {
        this.id = id;
        this.name = name;
        this.parentId = parentId;
        this.description = description;
        this.nicename = nicename;
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

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getNicename() {
        return nicename;
    }

    public void setNicename(String nicename) {
        this.nicename = nicename;
    }


}
