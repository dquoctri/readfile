package com.company.utils;

import java.net.URL;

public class ResourceLoader {

    public static final String SUFFIX = "/";

    public static String getResourcePath(String path){
        ClassLoader classLoader = ResourceLoader.class.getClassLoader();
        URL resource = classLoader.getResource(path);
        if (resource == null) {
            return "";
        }
        return resource.getPath();
    }
}
