import java.util.*;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import org.junit.Test;

public class Ch5Arrays{
  static void partition(int[] a){
    final int nGroups = 4;
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
    partition(a);
    assertThat(a, is(new int[]{0, 1, 2, 3}));
    
    a = new int[]{0, 1, 2, 3, 0, 1, 2, 3};
    partition(a);
    assertThat(a, is(new int[]{0, 0, 2, 2, 1, 1, 3, 3}));
  }
}
