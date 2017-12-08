import java.util.*;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import org.junit.Test;

public class Ch12Searching{
  int firstLargerThanK(int[] arr, int K){
    int from = 0; int to = arr.length - 1;
    while(from <= to){
      int mid = from + (to - from) / 2;
      if(arr[mid] == K) from = mid + 1;
      else if(arr[mid] > K && ((mid - 1 >= 0 && arr[mid - 1] <= K) || mid == 0)) return mid;
      else if(arr[mid] > K) to = mid - 1;
      else from = mid + 1;
    }
    return -1;
  }
  @Test
  public void testFirstLargerThanK(){
    assertThat(firstLargerThanK(new int[]{-14,-10,2,108,108,243,245,245,245,401}, -13), is(1));
    assertThat(firstLargerThanK(new int[]{-14,-10,2,108,108,243,245,245,245,401}, 1), is(2));
    assertThat(firstLargerThanK(new int[]{-14,-10,2,108,108,243,245,245,245,401}, 100), is(3));
    assertThat(firstLargerThanK(new int[]{-14,-10,2,108,108,243,245,245,245,401}, 108), is(5));
    assertThat(firstLargerThanK(new int[]{-14,-10,2,108,108,243,245,245,245,401}, 245), is(9));
    assertThat(firstLargerThanK(new int[]{-14,-10,2,108,108,243,245,245,245,401}, -15), is(0));
    assertThat(firstLargerThanK(new int[]{-14,-10,2,108,108,243,245,245,245,401}, 500), is(-1));
    assertThat(firstLargerThanK(new int[]{-14,-10,2,108,108,243,245,245,245,401}, 401), is(-1));
  }

  int firstOccurrence(int[] arr, int key){
    int from = 0; int to = arr.length - 1;
    while(from <= to){
      int mid = from + (to - from) / 2;
      if(arr[mid] == key && ((mid - 1 >= 0 && arr[mid - 1] != key) || mid == 0)) return mid;
      if(arr[mid] >= key) to = mid - 1;
      else from = mid + 1;
    }
    return -1;
  }
  @Test
  public void testFirstOccurrence(){
    assertThat(firstOccurrence(new int[]{5}, 5), is(0));
    assertThat(firstOccurrence(new int[]{5,5}, 5), is(0));
    assertThat(firstOccurrence(new int[]{1,1,2,2,2}, 1), is(0));
    assertThat(firstOccurrence(new int[]{1,2,3,4,5}, 2), is(1));
    assertThat(firstOccurrence(new int[]{1,2,3,4,5}, 5), is(4));
    assertThat(firstOccurrence(new int[]{-14,-10,2,108,108,243,245,245,245,401}, 100), is(-1));
    assertThat(firstOccurrence(new int[]{-14,-10,2,108,108,243,245,245,245,401}, 108), is(3));
    assertThat(firstOccurrence(new int[]{-14,-10,2,108,108,243,245,245,245,401}, 245), is(6));
  }
}
