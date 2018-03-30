package com.xiaoquan.gradle.plugin;

public class JibxDomain {

    private String name;

    private String[] classPath;
    private String[] bindings;


    public JibxDomain(String name) {
        this.name = name;
    }


    public String[] getClassPath() {
        return classPath;
    }

    public String[] getBindings() {
        return bindings;
    }

    public void setBindings(String[] bindings) {
        this.bindings = bindings;
    }

    public void setClassPath(String[] classPath) {
        this.classPath = classPath;
    }
}
