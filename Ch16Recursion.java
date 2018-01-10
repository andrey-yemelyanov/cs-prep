import java.util.*;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import org.junit.Test;

public class Ch16Recursion{
  static void uniquePermutations(int[] arr, int i, int[] p, List<List<Integer>> permutations){
    if(i == arr.length){
      if(isUniquePermutation(p, arr)){
        List<Integer> permutation = new ArrayList<>();
        for(int index : p) permutation.add(arr[index]);
        permutations.add(permutation);
        return;
      }
    }
    for(int j = i; j < arr.length; j++){
      swap(p, i, j);
      uniquePermutations(arr, i + 1, p, permutations);
      swap(p, i, j);
    }
  }
  static boolean isUniquePermutation(int[] p, int[] arr){
    for(int i = 0; i < p.length; i++){
      for(int j = i - 1; j >= 0; j--){
        if(arr[i] == arr[j] && p[i] < p[j]) return false;
      }
    }
    return true;
  }
  static void swap(int[] arr, int i, int j){
    int temp = arr[i];
    arr[i] = arr[j];
    arr[j] = temp;
  }
  static List<List<Integer>> uniquePermutations(int[] arr){
    List<List<Integer>> permutations = new ArrayList<>();
    int[] p = new int[arr.length];
    for(int i = 0; i < p.length; i++) p[i] = i;
    uniquePermutations(arr, 0, p, permutations);
    for(List<Integer> permutation : permutations) System.out.println(permutation);
    System.out.println();
    return permutations;
  }
  @Test
  public void testPermute(){
    assertThat(uniquePermutations(new int[]{2,3,2,3}).size(), is(6));
    assertThat(uniquePermutations(new int[]{2,3,0,2}).size(), is(12));
    assertThat(uniquePermutations(new int[]{1,2,3}).size(), is(6));
    assertThat(uniquePermutations(new int[]{2,2,2}).size(), is(1));
    assertThat(uniquePermutations(new int[]{2,3,2,2}).size(), is(4));
  }
}
