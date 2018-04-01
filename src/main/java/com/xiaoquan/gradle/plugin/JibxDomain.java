package com.xiaoquan.gradle.plugin;

public class JibxDomain {

    private String name;

    private boolean load = true;

    private boolean verbose = true;

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

    public boolean isLoad() {
        return load;
    }

    public void setLoad(boolean load) {
        this.load = load;
    }

    public boolean isVerbose() {
        return verbose;
    }

    public void setVerbose(boolean verbose) {
        this.verbose = verbose;
    }
}
