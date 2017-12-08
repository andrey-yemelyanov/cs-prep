import java.util.*;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import org.junit.Test;

public class Ch12Searching{
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
