import java.util.*;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import org.junit.Test;

public class Ch12Searching{
  int squareRoot(int k){
    int from = 0; int to = k; int root = 0;
    while(from <= to){
      int mid = from + (to - from) / 2;
      long square = (long) mid * (long) mid;
      if(square == k) return mid;
      if(square < k){
        from = mid + 1;
        root = mid;
      }else to = mid - 1;
    }
    return root;
  }
  @Test
  public void testSquareRoot(){
    assertThat(squareRoot(0), is(0));
    assertThat(squareRoot(1), is(1));
    assertThat(squareRoot(2), is(1));
    assertThat(squareRoot(3), is(1));
    assertThat(squareRoot(4), is(2));
    assertThat(squareRoot(5), is(2));
    assertThat(squareRoot(8), is(2));
    assertThat(squareRoot(9), is(3));
    assertThat(squareRoot(100), is(10));
    assertThat(squareRoot(99), is(9));
    assertThat(squareRoot(60762025), is(7795));
  }
  
  int findSmallestElement(int[] arr){
    if(arr[0] < arr[arr.length - 1]) return 0;
    int from = 0; int to = arr.length - 1;
    while(from <= to){
      int mid = from + (to - from) / 2;
      if(mid == 0 || arr[mid - 1] > arr[mid]) return mid;
      if(arr[from] > arr[mid]) to = mid - 1;
      else from = mid + 1;
    }
    return -1;
  }
  @Test
  public void testFindSmallestElement(){
    assertThat(findSmallestElement(new int[]{1,2,3,4,5}), is(0));
    assertThat(findSmallestElement(new int[]{5}), is(0));
    assertThat(findSmallestElement(new int[]{378,478,550,631,103,203,220,234,279,368}), is(4));
    assertThat(findSmallestElement(new int[]{550,631,103,203,220,234,279,368,378,478}), is(2));
    assertThat(findSmallestElement(new int[]{203,220,234,279,368,378,478,550,631,103}), is(9));
    assertThat(findSmallestElement(new int[]{631,103,203,220,234,279,368,378,478,550}), is(1));
  }
  
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
