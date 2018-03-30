package com.xiaoquan.gradle.plugin;

import org.gradle.api.Action;
import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.Task;
import org.gradle.api.file.FileCollection;
import org.gradle.api.plugins.JavaPlugin;
import org.gradle.api.plugins.JavaPluginConvention;
import org.gradle.api.plugins.WarPluginConvention;
import org.gradle.api.tasks.Copy;
import org.gradle.api.tasks.SourceSet;
import org.gradle.api.tasks.SourceSetContainer;
import org.gradle.plugins.ide.idea.model.IdeaModel;

import java.io.File;
import java.util.SortedSet;

public class SupportPlugin implements Plugin<Project> {

    private String Task_Group_Nuke = "nuke";
    private String Nuke_Ext_Name = "nukeEnv";

    @Override
    public void apply(Project project) {
        project.getExtensions().create(Nuke_Ext_Name, NukeEnvExtension.class, new EnvDomain("envDomain"), new JibxDomain("jibxDomain"));
        createJavaDirTask(project);
        createWebProject(project);

        configEnv(project);

        createJibxBind(project);

        logVlue(project);

    }


    private void createJavaDirTask(Project project) {
        Task createJavaDir = project.getTasks().create("createJavaDir");
        createJavaDir.setGroup(Task_Group_Nuke);
        createJavaDir.getActions().add(task -> {
            JavaPluginConvention javaConvention = project.getConvention().getPlugin(JavaPluginConvention.class);
            SourceSetContainer sourceSets = javaConvention.getSourceSets();

            sourceSets.getByName(SourceSet.MAIN_SOURCE_SET_NAME).getJava().getSrcDirs().forEach(this::createIfNotExist);
            sourceSets.getByName(SourceSet.MAIN_SOURCE_SET_NAME).getResources().getSrcDirs().forEach(this::createIfNotExist);
            sourceSets.getByName(SourceSet.TEST_SOURCE_SET_NAME).getJava().getSrcDirs().forEach(this::createIfNotExist);
            sourceSets.getByName(SourceSet.TEST_SOURCE_SET_NAME).getResources().getSrcDirs().forEach(this::createIfNotExist);

        });

    }

    private void createWebProject(Project project) {
        Task createWebDir = project.getTasks().create("createWebDir");
        createWebDir.dependsOn(":createJavaDir");
        createWebDir.setGroup(Task_Group_Nuke);
        createWebDir.getActions().add(task -> {
            WarPluginConvention warPluginConvention = new WarPluginConvention(project);
            File file = new File(project.getRootDir(), warPluginConvention.getWebAppDirName());
            createIfNotExist(file);
        });

    }

    private void configEnv(Project project) {
        Task jar = project.getTasks().getByName("jar");
        Copy copy = project.getTasks().create("copy", Copy.class);
        copy.into(project.getBuildDir() + "/classes/java/main");
//        copy.from("/home/xiaoquan/idea-workspace/support-plugin/.idea/misc.xml");
//        copy.into("/home/xiaoquan/tmp/gradle_tmp");

        Action<Task> taskAction = (t) -> {
            NukeEnvExtension nukeEnvExtension = (NukeEnvExtension) project.getExtensions().getByName(Nuke_Ext_Name);
            EnvDomain qa = nukeEnvExtension.getQa();

            if (qa.getConfigDir() != null) {
                String configDir = qa.getConfigDir();
                copy.from(configDir);
            }
            copy.exclude(qa.getExclude());

            copy.execute();
        };
        jar.doFirst(taskAction);


    }


    private void createJibxBind(Project project) {
        JibxTask jibxBind = project.getTasks().create("jibxBind", JibxTask.class);
        jibxBind.setGroup(Task_Group_Nuke);
        jibxBind.setProject(project);
//        project.getTasks().getByName("jibxBind").dependsOn JavaPlugin.COMPILE_JAVA_TASK_NAME
        jibxBind.dependsOn(JavaPlugin.COMPILE_JAVA_TASK_NAME, JavaPlugin.COMPILE_TEST_JAVA_TASK_NAME);
        jibxBind.setClassPathsSupplier(() -> {
            NukeEnvExtension nukeEnvExtension = (NukeEnvExtension) project.getExtensions().getByName(Nuke_Ext_Name);
            JibxDomain jibx = nukeEnvExtension.getJibx();
            return jibx.getClassPath();
        });

        jibxBind.setBindingsSupplier(() -> {
            NukeEnvExtension nukeEnvExtension = (NukeEnvExtension) project.getExtensions().getByName(Nuke_Ext_Name);
            JibxDomain jibx = nukeEnvExtension.getJibx();
            return jibx.getBindings();
        });


//        jibxBind.mustRunAfter(JavaPlugin.CLASSES_TASK_NAME);
//        project.getTasks().getByName(JavaPlugin.CLASSES_TASK_NAME).mustRunAfter(":jibxBind");

//        project.getTasks().getByName("compileJava").finalizedBy("jibxBind");

    }


    private boolean createIfNotExist(File file) {
        return file.exists() || file.mkdirs();
    }


    //------------------------------------------------------------------------------------------------------------------
    private void logVlue(Project project) {

        Task logVal = project.getTasks().create("logVal");

        logVal.setGroup(Task_Group_Nuke);
        logVal.getActions().add((t) -> {
            project.getPluginManager().apply("war");
            System.out.println("plugin java:" + project.getPluginManager().findPlugin("java"));
            System.out.println("plugin war:" + project.getPluginManager().findPlugin("war"));
            //            sourceSets.

            JavaPluginConvention javaConvention = project.getConvention().getPlugin(JavaPluginConvention.class);
            SourceSetContainer sourceSets = javaConvention.getSourceSets();
            SortedSet<String> names = sourceSets.getNames();

            names.forEach(n -> {
                project.getLogger().info("names:" + n.toString());

            });
            System.out.println(names);
            System.out.println(sourceSets.getAsMap());
            System.out.println("--sourceSets.getByName------");
            System.out.println(sourceSets.getByName(SourceSet.MAIN_SOURCE_SET_NAME).getJava().getSrcDirs());
            System.out.println(sourceSets.getByName(SourceSet.MAIN_SOURCE_SET_NAME).getJava().getOutputDir());
            System.out.println(sourceSets.getByName(SourceSet.MAIN_SOURCE_SET_NAME).getResources().getSrcDirs());
            System.out.println(sourceSets.getByName(SourceSet.TEST_SOURCE_SET_NAME).getJava().getSrcDirs());
            System.out.println(sourceSets.getByName(SourceSet.TEST_SOURCE_SET_NAME).getResources().getSrcDirs());


            FileCollection compileClasspath = sourceSets.getByName(SourceSet.TEST_SOURCE_SET_NAME).getCompileClasspath();

            compileClasspath.forEach(file -> System.out.println("compilePath:" + file.getAbsolutePath()));
            FileCollection runtimeClasspath = project.getConvention().getPlugin(JavaPluginConvention.class).getSourceSets().getByName(
                    SourceSet.MAIN_SOURCE_SET_NAME).getRuntimeClasspath();

            System.out.println("============");
            runtimeClasspath.getFiles().forEach(file -> System.out.println(file.getAbsolutePath()));
            System.out.println(runtimeClasspath.getAsPath());

            System.out.println("----------------------------");


            System.out.println("------------------------------------Idea plugin---------------------------------------");


            IdeaModel ideaModel = (IdeaModel) project.getExtensions().getByName("idea");

//            File outputDir = ideaModel.getModule().getOutputDir();


//            System.out.println("ideaModel:" + outputDir.getAbsolutePath());

        });


    }

}
