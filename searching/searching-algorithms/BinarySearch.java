import java.util.*;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import org.junit.Test;

public class BinarySearch{
  static int binarySearch(int[] arr, int key, int low, int high){
    if(high < low) return -1;
    int mid = low + (high - low) / 2;
    if(arr[mid] == key) return mid;
    if(arr[mid] > key) return binarySearch(arr, key, low, mid - 1);
    return binarySearch(arr, key, mid + 1, high);
  }
  static int binarySearch(int[] arr, int key){
    return binarySearch(arr, key, 0, arr.length - 1);
  }
  
  @Test
  public void test(){
    assertThat(binarySearch(new int[] {}, 1), is(-1));
    assertThat(binarySearch(new int[] {2}, 1), is(-1));
    assertThat(binarySearch(new int[] {2}, 3), is(-1));
    assertThat(binarySearch(new int[] {2}, 2), is(0));
    
    int[] arr = new int[] {1, 2, 3, 4, 5};
    assertThat(binarySearch(arr, 1), is(0));
    assertThat(binarySearch(arr, 5), is(4));
    assertThat(binarySearch(arr, 6), is(-1));
    assertThat(binarySearch(arr, 0), is(-1));
    assertThat(binarySearch(arr, 4), is(3));
  }
}
