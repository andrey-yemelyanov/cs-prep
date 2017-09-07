import java.util.*;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import org.junit.Test;

/*
An element in a sorted array can be found in O(log n) time via binary search. But suppose we rotate an ascending order sorted array at some pivot unknown to you beforehand. So for instance, 1 2 3 4 5 might become 3 4 5 1 2. Devise a way to find an element in the rotated array in O(log n) time.
http://www.geeksforgeeks.org/search-an-element-in-a-sorted-and-pivoted-array/
*/
public class Search{
  static int search(int[] arr, int key){
    return 0;
  }
  
  @Test
  public void test(){
    assertThat(search(new int[] {5, 6, 7, 8, 9, 10, 1, 2, 3}, 3), is(8));
    assertThat(search(new int[] {5, 6, 7, 8, 9, 10, 1, 2, 3}, 30), is(-1));
    assertThat(search(new int[] {30, 40, 50, 10, 20}, 10), is(3));
    assertThat(search(new int[] {20, 10}, 10), is(1));
    assertThat(search(new int[] {20, 10}, 20), is(0));
    assertThat(search(new int[] {20, 10}, 30), is(-1));
    assertThat(search(new int[] {20, 10}, 5), is(-1));
    assertThat(search(new int[] {20}, 20), is(0));
    assertThat(search(new int[] {20}, 10), is(-1));
    assertThat(search(new int[] {}, 10), is(-1));
    assertThat(search(new int[] {1,2,3,4,5}, 0), is(-1));
    assertThat(search(new int[] {1,2,4,6,8}, 3), is(-1));
    assertThat(search(new int[] {1,2,4,6,8}, 2), is(1));
    assertThat(search(new int[] {1,2,4,6,8}, 8), is(4));
    assertThat(search(new int[] {1,2,4,6,8}, 9), is(-1));
  }
}
