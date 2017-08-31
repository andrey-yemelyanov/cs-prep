import java.util.*;
import java.util.stream.*;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import org.junit.Test;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

public class LinearSearch{
  public static int search(int[] arr, int key){
    for(int i = 0; i < arr.length; i++){
      if(arr[i] == key) return i;
    }
    return -1;
  }
  @Test
  public void test(){
    int[] arr = new int[] {5, 3, 2, -1, 0, 100};
    assertThat(search(arr, 1), is(-1));
    assertThat(search(arr, 5), is(0));
    assertThat(search(arr, 100), is(5));
    assertThat(search(new int[] {}, 5), is(-1));
    assertThat(search(new int[] {1}, 1), is(0));
    assertThat(search(new int[] {1}, 0), is(-1));
  }
}
