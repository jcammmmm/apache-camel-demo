package org.jcammm.demos.camel.config;

public enum RoutesId {
    POST("POST"),
    GETALL("GETALL"),
    GETONE("GETONE"),
    GETBYNAME("GETBYNAME"),
    PUT("PUT"),
    PATCH("PATCH"),
    DELETE("DELETE");

    private RoutesId(String name) {
        this.name = name;
    }

    private String name;

    public String getName() {
        return this.name;
    }
}
