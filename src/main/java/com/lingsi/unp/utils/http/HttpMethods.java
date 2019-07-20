package com.lingsi.unp.utils.http;

public enum HttpMethods {
    GET(0, "GET"),
    POST(1, "POST"),
    HEAD(2, "HEAD"),
    PUT(3, "PUT"),
    DELETE(4, "DELETE"),
    TRACE(5, "TRACE"),
    PATCH(6, "PATCH"),
    OPTIONS(7, "OPTIONS");

    private int code;
    private String name;

    private HttpMethods(int code, String name) {
        this.code = code;
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public int getCode() {
        return this.code;
    }
}
