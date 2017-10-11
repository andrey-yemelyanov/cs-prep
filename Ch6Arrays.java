import java.util.*;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import org.junit.Test;

public class Ch6Arrays{
  static int[] pascalTriangle(int nRows){
    int[] a = new int[count(nRows - 1, nRows - 1)];
    for(int row = 0; row < nRows; row++){
      for(int col = 0; col < row + 1; col++){
        if(row == col || col == 0) a[count(row, col) - 1] = 1;
        else a[count(row, col) - 1] = a[count(row - 1, col - 1) - 1] + a[count(row - 1, col) - 1];
      }
    }
    return a;
  }
  // returns number of elements in Pascal triangle up until given row and col
  static int count(int row, int col){
    int sum = (row * (row + 1)) / 2;
    return sum + col + 1;
  }
  @Test
  public void testPascalTriangle(){
    assertThat(pascalTriangle(1), is(new int[]{1}));
    assertThat(pascalTriangle(2), is(new int[]{1,1,1}));
    assertThat(pascalTriangle(3), is(new int[]{1,1,1,1,2,1}));
    assertThat(pascalTriangle(4), is(new int[]{1,1,1,1,2,1,1,3,3,1}));
    assertThat(pascalTriangle(5), is(new int[]{1,1,1,1,2,1,1,3,3,1,1,4,6,4,1}));
  }
  
  static void rotate(int[][] m){
    for(int offset = 0; offset < m.length / 2; offset++){ // for each layer
      for(int i = 0; i < m.length - 2 * offset - 1; i++){ // rotate layer 'offset'
        int temp = m[offset][offset + i];
        m[offset][offset + i] = m[m.length - 1 - offset - i][offset];
        m[m.length - 1 - offset - i][offset] = m[m.length - 1 - offset][m.length - 1 - offset - i];
        m[m.length - 1 - offset][m.length - 1 - offset - i] = m[offset + i][m.length - 1 - offset];
        m[offset + i][m.length - 1 - offset] = temp;
      }
    }
  }
  @Test
  public void testRotate(){
    int[][] m = new int[][]{{1}};
    rotate(m);
    assertThat(m, is(new int[][]{{1}}));
    
    m = new int[][]{
      {1,2},
      {3,4}};
    rotate(m);
    assertThat(m, is(new int[][]{
      {3,1},
      {4,2}}));
      
    m = new int[][]{
      {1,  2,  3,  4},
      {5,  6,  7,  8},
      {9, 10, 11, 12},
      {13,14, 15, 16}
    };
    rotate(m);
    assertThat(m, is(new int[][]{
      {13,  9, 5, 1},
      {14, 10, 6, 2},
      {15, 11, 7, 3},
      {16, 12, 8, 4}
    }));
    
    m = new int[][]{
      {1,2,3},
      {4,5,6},
      {7,8,9}
    };
    rotate(m);
    assertThat(m, is(new int[][]{
      {7,4,1},
      {8,5,2},
      {9,6,3}
    }));
  }
  
  static int[] spiralOrdering(int[][] m){
    int[] ordering = new int[m.length * m.length];
    int i = 0;
    for(int offset = 0; offset < (int) Math.ceil(m.length / 2.0); offset++){
      i = matrixLayer(m, offset, i, ordering);
    }
    return ordering;
  }
  static int matrixLayer(int[][] m, int offset, int i, int[] ordering){
    if(offset == m.length / 2){
      ordering[i++] = m[offset][offset];
      return i;
    }
    // add the first n-1 el. of the 1st row
    for(int col = offset; col < m.length - 1 - offset; col++){
      ordering[i++] = m[offset][col];
    }
    // add the first n-1 el. of the last col
    for(int row = offset; row < m.length - 1 - offset; row++){
      ordering[i++] = m[row][m.length - 1 - offset];
    }
    // add the last n-1 el. of the last row in reverse order
    for(int col = m.length - 1 - offset; col > offset; col--){
      ordering[i++] = m[m.length - 1 - offset][col];
    }
    // add the last n-1 el. of the first col in reverse order
    for(int row = m.length - 1 - offset; row > offset; row--){
      ordering[i++] = m[row][offset];
    }
    return i;
  }
  @Test
  public void testSpiralOrdering(){
    int[][] m = new int[][]{
      {1,2,3},
      {4,5,6},
      {7,8,9}
    };
    assertThat(spiralOrdering(m), is(new int[]{1,2,3,6,9,8,7,4,5}));
    m = new int[][]{
      { 1,  2,  3,  4},
      { 5,  6,  7,  8},
      { 9, 10, 11, 12},
      {13, 14, 15, 16}
    };
    assertThat(spiralOrdering(m), is(new int[]{1,2,3,4,8,12,16,15,14,13,9,5,6,7,11,10}));
    m = new int[][]{{1}};
    assertThat(spiralOrdering(m), is(new int[]{1}));
    m = new int[][]{
      {1,2},
      {3,4}
    };
    assertThat(spiralOrdering(m), is(new int[]{1,2,4,3}));
  }
  
  static int nonuniform(int[] population, double[] probability){
    // build a sorted array of prefix sums
    double[] prefixSums = new double[probability.length];
    prefixSums[0] = probability[0];
    for(int i = 1; i < probability.length; i++){
      prefixSums[i] = prefixSums[i - 1] + probability[i];
    }
    // generate a uniformly random number r between [0.0,1.0]
    Random random = new Random();
    double r = random.nextDouble();
    // using upperBound, find interval where r falls - i
    // return population[i]
    return population[upperBound(r, prefixSums)];
  }
  static int upperBound(double d, double[] arr){
    int from = 0; int to = arr.length - 1;
    while(from <= to){
      int mid = from + (to - from) / 2;
      if(arr[mid] == d) return mid;
      if(arr[mid] > d){
        if(mid - 1 < 0 || arr[mid - 1] < d) return mid;
        else to = mid - 1;
      }else from = mid + 1;
    }
    return -1;
  }
  @Test
  public void testNonuniform(){
    int[] population = new int[]{3,5,7,11};
    System.out.println("Non-uniform from population " + Arrays.toString(population));
    //double[] probability = new double[]{9/18.0,6/18.0,2/18.0,1/18.0};
    double[] probability = new double[]{0.25,0.25,0.25,0.25};
    final int N_RUNS = 1000000;
    Map<Integer, Integer> map = new HashMap<>();
    for(int i = 0; i < N_RUNS; i++){
      int n = nonuniform(population, probability);
      map.putIfAbsent(n, 0);
      map.put(n, map.get(n) + 1);
    }
    System.out.println(map);
  }
  
  static int[] randomSample(int[] population, int sampleSize){
    int[] sample = new int[sampleSize];
    Random random = new Random();
    for(int i = 0; i < sample.length; i++){
      // generate random number r between i and population.length - 1
      int r = random.nextInt(population.length - i) + i;
      // swap population[r] and population[i]
      swap(population, r, i);
      // add random population member to sample[i]
      sample[i] = population[i];
    }
    return sample;
  }
  @Test
  public void testRandomSample(){
    System.out.println("Random sample...");
    int[] population = new int[]{1,2,3,4,5,6,7,8,9,10,11,12,13,14,15};
    final int SAMPLE_SIZE = 15;
    final int N_RUNS = 10;
    for(int i = 0; i < N_RUNS; i++){
      int[] sample = randomSample(population, SAMPLE_SIZE);
      assertThat(sample.length, is(SAMPLE_SIZE));
      assertTrue(allDistinct(sample));
      System.out.println(Arrays.toString(sample));
    }
  }
  private boolean allDistinct(int[] a){
    HashSet<Integer> s = new HashSet<>();
    for(int i : a) s.add(i);
    return s.size() == a.length;
  }
  
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
