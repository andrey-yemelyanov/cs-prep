import java.util.*;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import org.junit.Test;

public class Ch12Searching{
  int quickselect(int[] arr, int k){
    int from = 0; int to = arr.length - 1;
    while(from <= to){
      int pivot = partition(arr, from, to);
      if(pivot == k - 1) return arr[pivot];
      if(pivot > k - 1) to = pivot - 1;
      else from = pivot + 1;
    }
    return -1;
  }
  int partition(int[] arr, int from, int to){
    int pivotIndex = new Random().nextInt(to - from + 1) + from;
    int pivot = arr[pivotIndex];
    swap(arr, pivotIndex, to);
    int j = from;
    for(int i = from; i < to; i++) if(arr[i] > pivot) swap(arr, j++, i);
    swap(arr, j, to);
    return j;
  }
  void swap(int[] arr, int i, int j){
    int temp = arr[i];
    arr[i] = arr[j];
    arr[j] = temp;
  }
  @Test
  public void testQuickselect(){
    assertThat(quickselect(new int[]{3}, 1), is(3));
    assertThat(quickselect(new int[]{3}, 2), is(-1));
    assertThat(quickselect(new int[]{3,2,1,5,4}, 1), is(5));
    assertThat(quickselect(new int[]{3,2,1,5,4}, 2), is(4));
    assertThat(quickselect(new int[]{3,2,1,5,4}, 3), is(3));
    assertThat(quickselect(new int[]{5,4,3,2,1}, 1), is(5));
    assertThat(quickselect(new int[]{5,4,3,2,1}, 2), is(4));
    assertThat(quickselect(new int[]{1,2,3,4,5}, 1), is(5));
    assertThat(quickselect(new int[]{1,2,3,4,5}, 2), is(4));
  }
  
  final int SMALLER = 1;
  final int EQUAL = 2;
  final int LARGER = 3;
  int compare(double d1, double d2){
    final double EPSILON = 0.00000000001;
    double diff = d1 - d2;
    if(diff < -EPSILON) return SMALLER;
    if(diff > EPSILON) return LARGER;
    return EQUAL;
  }
  double squareRoot(double k){
    double from = 0; double to = 0;
    if(k < 1.0){
      from = k; to = 1.0;
    }else{
      from = 1.0; to = k;
    }
    
    while(compare(from, to) == SMALLER){
      double mid = from + 0.5 * (to - from);
      double squared = mid * mid;
      if(compare(squared, k) == EQUAL) return mid;
      if(compare(squared, k) == LARGER) to = mid;
      else from = mid;
    }
    
    return from;
  }
  @Test
  public void testRealSquareRoot(){
    assertThat(squareRoot(1.0), is(1.0));
    assertThat(String.format(Locale.US, "%.3f", squareRoot(4.0)), is("2.000"));
    assertThat(String.format(Locale.US, "%.3f", squareRoot(0.5)), is("0.707"));
    assertThat(String.format(Locale.US, "%.3f", squareRoot(112.0)), is("10.583"));
    assertThat(String.format(Locale.US, "%.3f", squareRoot(1142332.0)), is("1068.799"));
    assertThat(String.format(Locale.US, "%.3f", squareRoot(100.0)), is("10.000"));
  }
  
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
