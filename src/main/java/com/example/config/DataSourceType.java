package com.example.config;

/***
 * 枚举数据库
 */
public enum DataSourceType {

    WRITE("write", "写库"),
    READ("read", "读库"),
    ;

    DataSourceType(String type, String name) {
        this.type = type;
        this.name = name;
    }

    private String type;
    private String name;

    public String getType() {
        return this.type;
    }

    public String getName() {
        return this.name;
    }

}
