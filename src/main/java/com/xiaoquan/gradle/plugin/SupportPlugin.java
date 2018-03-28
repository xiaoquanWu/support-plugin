package com.xiaoquan.gradle.plugin;

import org.gradle.api.Action;
import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.Task;
import org.gradle.api.artifacts.Configuration;
import org.gradle.api.file.FileCollection;
import org.gradle.api.internal.file.collections.SimpleFileCollection;
import org.gradle.api.plugins.JavaPluginConvention;
import org.gradle.api.plugins.WarPlugin;
import org.gradle.api.plugins.WarPluginConvention;
import org.gradle.api.tasks.Copy;
import org.gradle.api.tasks.SourceSet;
import org.gradle.api.tasks.SourceSetContainer;
import org.gradle.api.tasks.bundling.War;

import java.io.File;
import java.util.SortedSet;
import java.util.concurrent.Callable;

public class SupportPlugin implements Plugin<Project> {

    private String Task_Group_Nuke = "nuke";

    @Override
    public void apply(Project project) {
        createJavaDirTask(project);
        createWebProject(project);


        createQaWar(project);
        logVlue(project);

    }


    private void logVlue(Project project) {
    }


    private void createJavaDirTask(Project project) {
        Task createJavaDir = project.getTasks().create("createJavaDir");

        createJavaDir.setGroup(Task_Group_Nuke);
        createJavaDir.getActions().add(task -> {
            JavaPluginConvention javaConvention = project.getConvention().getPlugin(JavaPluginConvention.class);
            SourceSetContainer sourceSets = javaConvention.getSourceSets();
//            sourceSets.
            SortedSet<String> names = sourceSets.getNames();

            names.forEach(n -> {
                project.getLogger().info("names:" + n.toString());

            });
            System.out.println(names);
            System.out.println(sourceSets.getAsMap());
//            System.out.println(sourceSets.);
            System.out.println(sourceSets.getByName(SourceSet.MAIN_SOURCE_SET_NAME).getJava().getSrcDirs());
            System.out.println(sourceSets.getByName(SourceSet.MAIN_SOURCE_SET_NAME).getResources().getSrcDirs());
            System.out.println(sourceSets.getByName(SourceSet.TEST_SOURCE_SET_NAME).getJava().getSrcDirs());
            System.out.println(sourceSets.getByName(SourceSet.TEST_SOURCE_SET_NAME).getResources().getSrcDirs());
            FileCollection runtimeClasspath = project.getConvention().getPlugin(JavaPluginConvention.class).getSourceSets().getByName(
                    SourceSet.MAIN_SOURCE_SET_NAME).getRuntimeClasspath();

            System.out.println("============");
            runtimeClasspath.getFiles().forEach(file -> System.out.println(file.getAbsolutePath()));
            System.out.println(runtimeClasspath.getAsPath());
            sourceSets.getByName(SourceSet.MAIN_SOURCE_SET_NAME).getJava().getSrcDirs().forEach(this::createIfNotExist);
            sourceSets.getByName(SourceSet.MAIN_SOURCE_SET_NAME).getResources().getSrcDirs().forEach(this::createIfNotExist);
            sourceSets.getByName(SourceSet.TEST_SOURCE_SET_NAME).getJava().getSrcDirs().forEach(this::createIfNotExist);
            sourceSets.getByName(SourceSet.TEST_SOURCE_SET_NAME).getResources().getSrcDirs().forEach(this::createIfNotExist);

        });

    }

    private boolean createWebProject(Project project) {
//        Task createJavaDir = project.getTasks().getByName("createJavaDir");
        Task createWebDir = project.getTasks().create("createWebDir");
        createWebDir.dependsOn(":createJavaDir");
        createWebDir.setGroup(Task_Group_Nuke);
        WarPluginConvention warPluginConvention = new WarPluginConvention(project);
        File file = new File(project.getRootDir(), warPluginConvention.getWebAppDirName());
        return createIfNotExist(file);
    }

    private void createQaWar(Project project) {
        project.getPlugins().apply("war");
        War warTask = (War) project.getTasks().getByName(WarPlugin.WAR_TASK_NAME);
//

        Task task = project.getTasks().create("BuildQA");

//        Copy copy = project.getTasks().create("Copy", Copy.class);


        task.setGroup(Task_Group_Nuke);
        task.getActions().add(t ->{

//
//            System.out.println("xxxxx:===>"+copy);
//            copy.getActions().add((cp) -> {
//                cp.from("/home/xiaoquan/idea-workspace/support-plugin/.idea/gradle.xml");
//                cp.into("/home/xiaoquan/idea-workspace/support-plugin/build/classes/java/main");
//
//            });
//
//            warTask.getClasspath().forEach(file -> {System.out.println(file.getAbsolutePath());});
//            warTask.getExcludes().forEach(file -> {System.out.println(file);});
////            warTask.exclude()

//            warTask.getClasspath().add(new SimpleFileCollection(new File("/home/xiaoquan/idea-workspace/support-plugin/.idea")));

        });



//        warTask.getClasspath().forEach(file -> {
//            System.out.println(file.getAbsolutePath());
//        });

//        project.getTasks().withType(War.class, new Action<War>() {
//            @Override
//            public void execute(War task) {
////                new SimpleFileCollection(new File(""));
////
////                task.getClasspath().forEach(file -> {
////                    System.out.println("classpath:" + file.getAbsolutePath());
////                });
//            }
//        });


    }


    private boolean createIfNotExist(File file) {
        return file.exists() || file.mkdirs();
    }
}
