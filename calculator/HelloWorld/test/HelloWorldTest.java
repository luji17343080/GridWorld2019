import static org.junit.Assert.*;
import org.junit.Test;
//import junit.framework.Assert;
//import junit.framework.TestCase;
public class HelloWorldTest{
  public HelloWorld H = new HelloWorld();
  @Test
  public void testHello() {
    H.hello();
    /*Assert.assertEquals("Hello World!", helloworld.getStr());*/
    assertEquals("Hello World!", H.getStr());
  }
}
