import org.gradle.testkit.runner.GradleRunner
import org.junit.Test

class App {


    @Test
    public void test1() {
        def result = GradleRunner.create().withProjectDir(new File("/home/xiaoquan/idea-workspace/support-plugin"))
                .withDebug(true)
                .withArguments("build", "--info").build()


        println result


    }


}
