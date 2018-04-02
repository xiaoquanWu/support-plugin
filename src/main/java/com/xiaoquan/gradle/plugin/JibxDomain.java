package com.xiaoquan.gradle.plugin;

import org.gradle.api.file.FileTree;

import java.io.File;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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

//    public void setClassPath(String[] classPath) {
//        this.classPath = classPath;
//    }

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


    public void setClassPath(FileTree files) {
        Set<File> fileSet = files.getFiles();
        String[] classPath = new String[fileSet.size()];
        List<String> collect = fileSet.stream().map(File::getAbsolutePath).collect(Collectors.toList());
        collect.toArray(classPath);
        this.classPath = classPath;
    }


}
