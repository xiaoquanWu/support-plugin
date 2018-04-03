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

    private String[] classPaths;
    private String[] bindings;


    public JibxDomain(String name) {
        this.name = name;
    }


    public String[] getClassPaths() {
        return classPaths;
    }

    public String[] getBindings() {
        return bindings;
    }

    public void setBindings(String[] bindings) {
        this.bindings = bindings;
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


    public void setClassPaths(Object files) {
        if (files instanceof FileTree) {
            Set<File> fileSet = ((FileTree) files).getFiles();
            String[] classPaths = new String[fileSet.size()];
            List<String> collect = fileSet.stream().map(File::getAbsolutePath).collect(Collectors.toList());
            collect.toArray(classPaths);
            this.classPaths = classPaths;
        } else if (files instanceof List) {
            List<String> fileSet = (List<String>) files;
            String[] classPaths = new String[fileSet.size()];
            fileSet.toArray(classPaths);
            this.classPaths = classPaths;
        } else {
            throw new IllegalArgumentException("Invalid classPath parameters");
        }

    }


}
