import java.util.*;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import org.junit.Test;

public class Ch5Arrays{
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
