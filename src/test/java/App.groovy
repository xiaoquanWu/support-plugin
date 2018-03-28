import org.gradle.api.AntBuilder
import org.gradle.api.Task
import org.gradle.api.internal.project.taskfactory.ITaskFactory
import org.gradle.api.tasks.Copy
import org.gradle.testfixtures.ProjectBuilder
import org.gradle.testkit.runner.GradleRunner
import org.junit.Test

class App {


    def rootDir = new File("/home/xiaoquan/idea-workspace/support-plugin")

    @Test
    public void test1() {
        def result = GradleRunner.create().withProjectDir(rootDir)
                .withDebug(true)
                .withArguments("build", "--info").build()


        println result




    }



    @Test
    public void test2() {
        def project = ProjectBuilder
                .builder()
                .withProjectDir(rootDir)
                .build()

        AntBuilder ant = project.getAnt();

        ant.echo(message: 'xxx')
        ant.taskdef()

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
        task.getIncludes().each { println it}


        println project.getPluginManager().findPlugin("maven")
        println project.getPluginManager().findPlugin("java")



        println project.getBuildDir().getAbsolutePath()




    }


}
