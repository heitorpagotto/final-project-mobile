package com.unifaj.projeto_final.env;

public class APIEnv {
    public static APIEnv instance = new APIEnv();
    private String _URI = "http://192.168.0.109:8082/api/";

    public static APIEnv getInstance() {
        return instance;
    }

    public static void setInstance(APIEnv instance) {
        APIEnv.instance = instance;
    }

    public String getURI() {
        return _URI;
    }

    public void setURI(String uri) {
        if (!uri.contains("/api/")) {
            uri += "/api/";
        }
        _URI = uri;
    }
}
