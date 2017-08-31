import java.util.*;
import java.util.stream.*;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import org.junit.Test;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

public class ClassName{
  
  
  @Test
  public void test(){
    
  }
  
  public static void main(String... args){
    runTests();
  }
  static void runTests(){
    Result r = JUnitCore.runClasses(ClassName.class);
    if(!r.wasSuccessful()){
      System.out.println("TEST FAILED!");
      for (Failure failure : r.getFailures()) {
        System.out.println(failure.getTrace());
      }
    }else{
      System.out.println("SUCCESS!");
    }
  }
}
