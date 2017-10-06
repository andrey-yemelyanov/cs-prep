import java.util.*;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import org.junit.Test;

public class Ch6Arrays{
  static int[] applyPermutation(int[] a, int[] p){
    for(int i = 0; i < a.length; i++){
      if(p[i] >= 0){
        int current = i;
        int val = a[current];
        do{
          int next = p[current];
          int nextVal = a[next];
          a[next] = val;
          p[current] -= p.length;
          current = next;
          val = nextVal;
        }while(current != i);
      }
    }
    
    // restore permutation array
    for(int i = 0; i < p.length; i++){
      p[i] += p.length;
    }
    
    return a;
  }
  @Test
  public void testApplyPermutation(){
    int[] a = new int[]{1,2,3,4,5};
    int[] p = new int[]{0,1,2,3,4};
    assertThat(applyPermutation(a, p), is(new int[]{1,2,3,4,5}));
    assertThat(p, is(new int[]{0,1,2,3,4}));
    
    a = new int[]{1,2,3,4,5};
    p = new int[]{4,3,2,1,0};
    assertThat(applyPermutation(a, p), is(new int[]{5,4,3,2,1}));
    assertThat(p, is(new int[]{4,3,2,1,0}));
    
    a = new int[]{1,2,3,4,5};
    p = new int[]{1,0,4,2,3};
    assertThat(applyPermutation(a, p), is(new int[]{2,1,4,5,3}));
    assertThat(p, is(new int[]{1,0,4,2,3}));
    
    a = new int[]{1,2,3,4,5};
    p = new int[]{0,1,3,2,4};
    assertThat(applyPermutation(a, p), is(new int[]{1,2,4,3,5}));
    assertThat(p, is(new int[]{0,1,3,2,4}));
  }
  
  static int[] nextPermutation(int[] a){
    // Find longest non-increasing suffix and identify pivot
    int p = a.length - 1;
    while(p > 0 && a[p] <= a[p - 1]) p--;
    if(p == 0) return null; // already last permutation, next not available
        
    int[] next = Arrays.copyOf(a, a.length);
    
    // Find rightmost successor to pivot in the suffix
    int k = p;
    while(k < next.length && next[k] >= next[p - 1]) k++;
    
    // swap with pivot
    swap(next, p - 1, k - 1);
    
    // reverse the suffix
    reverse(next, p, next.length - 1);
    
    return next;
  }
  @Test
  public void testNextPermutation(){
    assertThat(nextPermutation(new int[]{1}), is(nullValue()));
    assertThat(nextPermutation(new int[]{1,2,3,4,5}), is(new int[]{1,2,3,5,4}));
    assertThat(nextPermutation(new int[]{5,4,3,2,1}), is(nullValue()));
    assertThat(nextPermutation(new int[]{2,3,5,4,1}), is(new int[]{2,4,1,3,5}));
    assertThat(nextPermutation(new int[]{1,2,3,5,4}), is(new int[]{1,2,4,3,5}));
    assertThat(nextPermutation(new int[]{0,1,2,5,3,3,0}), is(new int[]{0,1,3,0,2,3,5}));
    assertThat(nextPermutation(new int[]{6,2,1,5,4,3,0}), is(new int[]{6,2,3,0,1,4,5}));
    assertThat(nextPermutation(new int[]{1,0,3,2}), is(new int[]{1,2,0,3}));
  }
  
  // removes duplicates from a sorted array
  static int distinct(int[] a){
    int k = 1;
    for(int i = 1; i < a.length; i++){
      if(a[i] != a[i - 1]) a[k++] = a[i];
    }
    for(int i = k; i < a.length; i++){
      a[i] = 0;
    }
    return k;
  }
  @Test
  public void testDistinct(){
    int[] a = new int[]{0};
    assertThat(distinct(a), is(1));
    assertThat(a, is(new int[]{0}));
    
    a = new int[]{2};
    assertThat(distinct(a), is(1));
    assertThat(a, is(new int[]{2}));
    
    a = new int[]{1,2,3};
    assertThat(distinct(a), is(3));
    assertThat(a, is(new int[]{1,2,3}));
    
    a = new int[]{2,2,2};
    assertThat(distinct(a), is(1));
    assertThat(a, is(new int[]{2,0,0}));
    
    a = new int[]{2,2,2,3,3,3};
    assertThat(distinct(a), is(2));
    assertThat(a, is(new int[]{2,3,0,0,0,0}));
    
    a = new int[]{2,3,5,5,7,11,11,11,13};
    assertThat(distinct(a), is(6));
    assertThat(a, is(new int[]{2,3,5,7,11,13,0,0,0}));
  }
  
  // deletes a key from array and returns number of items available after deletion
  static int delete(int[] a, int key){
    int k = 0;
    for(int i = 0; i < a.length; i++){
      if(a[i] != key){
        a[k] = a[i];
        k++;
      }
    }
    return k;
  }
  @Test
  public void testDelete(){
    int[] a = new int[]{1,2,3,4,5};
    assertThat(delete(a, 6), is(5));
    assertThat(a, is(new int[]{1,2,3,4,5}));
    
    a = new int[]{1,2,3,4,5};
    assertThat(delete(a, 1), is(4));
    assertThat(a, is(new int[]{2,3,4,5,5}));
    
    a = new int[]{1,2,3,4,5};
    assertThat(delete(a, 5), is(4));
    assertThat(a, is(new int[]{1,2,3,4,5}));
    
    a = new int[]{1,2,1,3,4,1};
    assertThat(delete(a, 1), is(3));
    assertThat(a, is(new int[]{2,3,4,3,4,1}));
  }
  
  static int[] multiply(int[] x, int[] y){
    int[] z = new int[x.length + y.length];
    reverse(x); reverse(y); // reverse arrays so we can multiply left to right
    for(int j = 0; j < y.length; j++){
      int carry = 0;
      for(int i = 0; i < x.length; i++){
        int k = z.length - 1 - j - i;
        int partial = x[i] * y[j] + z[k] + carry;
        z[k] = partial % 10;
        carry = partial / 10;
        if(i == x.length - 1) z[k - 1] = carry;
      }
    }
    return z;
  }
  static void reverse(int[] arr){
    reverse(arr, 0, arr.length - 1);
  }
  static void reverse(int[] arr, int from, int to){
    for(int i = from; i <= from + (to - from) / 2; i++){
      swap(arr, i, to - i + from);
    }
  }
  @Test
  public void testMultiply(){
    assertThat(multiply(new int[]{0,0,1,1}, new int[]{0,0,1,1}), is(new int[]{0,0,0,0,0,1,2,1}));
    assertThat(multiply(new int[]{1,1}, new int[]{1,1}), is(new int[]{0,1,2,1}));
    assertThat(multiply(new int[]{0}, new int[]{0}), is(new int[]{0,0}));
    assertThat(multiply(new int[]{1}, new int[]{0}), is(new int[]{0,0}));
    assertThat(multiply(new int[]{1}, new int[]{1}), is(new int[]{0,1}));
    assertThat(multiply(new int[]{5}, new int[]{6}), is(new int[]{3,0}));
    assertThat(multiply(new int[]{5}, new int[]{5}), is(new int[]{2,5}));
    assertThat(multiply(new int[]{4}, new int[]{2}), is(new int[]{0,8}));
    assertThat(multiply(new int[]{5,6,5,4,7,5,4}, new int[]{1,2,6,5}), is(new int[]{0,7,1,5,3,2,6,3,8,1,0}));
    assertThat(multiply(new int[]{1,2,3}, new int[]{2}), is(new int[]{0,2,4,6}));
    assertThat(multiply(new int[]{6,7}, new int[]{8,3}), is(new int[]{5,5,6,1}));
    assertThat(multiply(new int[]{1,9,3,7,0,7,7,2,1}, new int[]{7,6,1,8,3,8,2,5,7,2,8,7}), is(new int[]{1,4,7,5,7,3,9,5,2,5,8,9,6,7,6,4,1,2,9,2,7}));
    assertThat(multiply(new int[]{1,9,3,7,0,7,7,2,1}, new int[]{0}), is(new int[]{0,0,0,0,0,0,0,0,0,0}));
  }
  
  static int[] increment(int[] x){
    int[] y = new int[x.length + 1];
    int carry = 0;
    for(int i = x.length - 1; i >= 0; i--){
      int add = 0;
      if(i == x.length - 1) add = 1;
      int sum = x[i] + add + carry;
      if(sum >= 10) y[i + 1] = sum % 10; 
      else y[i + 1] = sum;
      carry = sum / 10;
    }
    if(carry != 0) y[0] = carry;
    return y;
  }
  @Test
  public void testIncrement(){
    assertThat(increment(new int[]{1,2,9}), is(new int[]{0,1,3,0}));
    assertThat(increment(new int[]{9,9,9}), is(new int[]{1,0,0,0}));
    assertThat(increment(new int[]{1,2,3}), is(new int[]{0,1,2,4}));
    assertThat(increment(new int[]{9}), is(new int[]{1,0}));
    assertThat(increment(new int[]{1,9,2}), is(new int[]{0,1,9,3}));
    assertThat(increment(new int[]{1,9,9}), is(new int[]{0,2,0,0}));
    assertThat(increment(new int[]{0}), is(new int[]{0,1}));
    assertThat(increment(new int[]{0,0,0,0}), is(new int[]{0,0,0,0,1}));
    assertThat(increment(new int[]{0,0,0,9}), is(new int[]{0,0,0,1,0}));
  }
  
  static void partition(int[] a, int nGroups){
    int i = 0;
    for(int k = 0; k < nGroups; k++){
      int groupId = a[i++];
      for(int j = i; j < a.length; j++){
        if(a[j] == groupId){
          swap(a, i, j);
          i++;
        }
      }
    }
  }
  static void swap(int[] a, int i, int j){
    int temp = a[i];
    a[i] = a[j];
    a[j] = temp;
  }
  @Test
  public void testPartition(){
    int[] a = new int[]{0, 1, 2, 3};
    partition(a, 4);
    assertThat(a, is(new int[]{0, 1, 2, 3}));
    
    a = new int[]{0, 1, 2, 3, 0, 1, 2, 3};
    partition(a, 4);
    assertThat(a, is(new int[]{0, 0, 2, 2, 1, 1, 3, 3}));
    
    a = new int[]{0, 1, 0, 1, 1, 0, 0, 1};
    partition(a, 2);
    assertThat(a, is(new int[]{0, 0, 0, 0, 1, 1, 1, 1}));
    
    a = new int[]{0,1,2,2,2,0,1,0,0,1};
    partition(a, 3);
    assertThat(a, is(new int[]{0, 0, 0, 0, 2, 2, 2, 1, 1, 1}));
  }
}
