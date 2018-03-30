import org.gradle.api.AntBuilder
import org.gradle.api.Task
import org.gradle.api.internal.project.taskfactory.ITaskFactory
import org.gradle.api.plugins.JavaPluginConvention
import org.gradle.api.tasks.Copy
import org.gradle.api.tasks.SourceSetContainer
import org.gradle.plugins.ide.idea.IdeaPlugin
import org.gradle.plugins.ide.idea.model.IdeaModule
import org.gradle.testfixtures.ProjectBuilder
import org.gradle.testkit.runner.GradleRunner
import org.jibx.binding.Compile
import org.junit.Test

class App {


    def rootDir = new File("/home/xiaoquan/idea-workspace/support-plugin")

    @Test
    public void test1() {
        def result = GradleRunner.create().withProjectDir(rootDir)
                .withDebug(true)
                .withArguments("jibxBind", "--info").build()


        println result


    }


    @Test
    public void test2() {
        def project = ProjectBuilder
                .builder()
                .withProjectDir(rootDir)
                .build()

        project.pluginManager.apply 'java'


        project.task("bindTest", dependsOn: ['compileJava']) {
            HashSet<File> result = new HashSet<File>()
            result.addAll(project.sourceSets.main.compileClasspath.files)
            result.addAll(project.sourceSets.main.output.files)
            result.addAll(project.sourceSets.test.compileClasspath.files)
            result.addAll(project.sourceSets.test.output.files)


            def classPaths = result.toArray() as String[]
            classPaths = [
                    "/home/xiaoquan/idea-workspace/support-plugin/extra/jibx-bind.jar",
                    "/home/xiaoquan/idea-workspace/support-plugin/extra/jibx.jar",
                    "/home/xiaoquan/idea-workspace/support-plugin/extra/joda-time.jar",
                    "/home/xiaoquan/idea-workspace/support-plugin/build/classes/java/main",
            ] as String[]
            def bindings = ['/home/xiaoquan/idea-workspace/support-plugin/src/test/resources/binding.xml'] as String[]

//            classPaths = [
//                    "/home/xiaoquan/idea-workspace/support-plugin/extra/jibx-bind.jar",
//                    "/home/xiaoquan/idea-workspace/support-plugin/extra/jibx.jar",
//                    "/home/xiaoquan/idea-workspace/support-plugin/extra/joda-time.jar",
//                    "/home/xiaoquan/nuke-workspace/git-workspace/go-bookingpipe/build/classes/java/main"
//            ] as String[]
//
//            bindings = ['/home/xiaoquan/nuke-workspace/git-workspace/go-bookingpipe/src/main/java/jibx/message.xml'] as String[]

            Compile compiler = new Compile();


            compiler.setLoad(true);
//        compiler.setSkipValidate(!this.validate);
            compiler.setVerbose(true);
//        compiler.setVerify(this.verify);
            compiler.compile(classPaths, bindings);

        }
        project.compileJava.finalizedBy(project.bindTest)



//        project.getDependencies().artifactTypes.each { println it.fileNameExtensions}
//        project.bindTest.mustRunAfter("build")
    }


    @Test
    public void testCopy() {

        def project = ProjectBuilder
                .builder()
                .withProjectDir(rootDir)
                .build()
        def task = project.getServices().get(ITaskFactory.class).create("testTask", Copy) as Copy

        println task.getActions().size()

        task.from("/home/xiaoquan/idea-workspace/support-plugin/build/classes/java/main")
//        task.from("/home/xiaoquan/idea-workspace/support-plugin/build/docs/javadoc")
        task.from("/home/xiaoquan/idea-workspace/support-plugin/build/docs/javadoc/allclasses-frame.html")
//        task.include("**/*.html")
        task.into("/home/xiaoquan/tmp/gradle_tmp")



        task.execute()
        task.getIncludes().each { println it }


        println project.getPluginManager().findPlugin("maven")
        println project.getPluginManager().findPlugin("java")



        println project.getBuildDir().getAbsolutePath()


    }


}
