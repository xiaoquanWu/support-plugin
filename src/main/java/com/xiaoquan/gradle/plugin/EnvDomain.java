package com.xiaoquan.gradle.plugin;

import java.util.ArrayList;
import java.util.List;

public class EnvDomain {


    private String name;
    private String configDir;
    private List<String> exclude = new ArrayList<>();

    public EnvDomain(String name) {
        this.name = name;
    }


    public String getConfigDir() {
        return configDir;
    }

    public void setConfigDir(String configDir) {
        this.configDir = configDir;
    }

    public List<String> getExclude() {
        return exclude;
    }

    public void setExclude(List<String> exclude) {
        this.exclude = exclude;
    }

    public String getName() {
        return name;
    }
}
