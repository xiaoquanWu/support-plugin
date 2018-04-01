package com.xiaoquan.gradle.plugin;

import org.gradle.api.DefaultTask;
import org.gradle.api.Project;
import org.gradle.api.tasks.Input;
import org.gradle.api.tasks.TaskAction;
import org.jibx.binding.Compile;

import java.util.function.Supplier;

public class JibxTask extends DefaultTask {

    private Project project;

    private boolean load = true;

    private boolean verbose = true;


    private Supplier<String[]> classPathsSupplier;

    private Supplier<String[]> bindingsSupplier;


    @TaskAction
    public void compile() {
        try {
            String[] pathes = classPathsSupplier.get();
            String[] files = bindingsSupplier.get();

            project.getLogger().info("---------Jibx ClassPaths---");
            for (String path : pathes) {
                project.getLogger().info(path);
            }
            for (String file : files) {
                project.getLogger().info(file);
            }
            Compile compiler = new Compile();
            compiler.setLoad(this.load);
            compiler.setVerbose(this.verbose);
            compiler.compile(pathes, files);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Input
    public void setLoad(boolean load) {
        this.load = load;
    }

    @Input
    public void setVerbose(boolean verbose) {
        this.verbose = verbose;
    }

    public void setClassPathsSupplier(Supplier<String[]> classPathsSupplier) {
        this.classPathsSupplier = classPathsSupplier;
    }

    public void setBindingsSupplier(Supplier<String[]> bindingsSupplier) {
        this.bindingsSupplier = bindingsSupplier;
    }

    public void setProject(Project project) {
        this.project = project;
    }
}
