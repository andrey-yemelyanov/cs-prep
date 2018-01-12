import java.util.*;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import org.junit.Test;

public class Ch16Recursion{
  static List<Node> binaryTrees(int nNodes){
    if(nNodes == 0){
      List<Node> empty = new ArrayList<>();
      empty.add(null);
      return empty;
    }
    List<Node> trees = new ArrayList<>();
    for(int i = 0; i < nNodes; i++){
      int leftTreeSize = i;
      int rightTreeSize = nNodes - i - 1;
      List<Node> leftTrees = binaryTrees(leftTreeSize);
      List<Node> rightTrees = binaryTrees(rightTreeSize);
      for(Node leftTree : leftTrees){
        for(Node rightTree : rightTrees){
          trees.add(new Node(leftTree, rightTree));
        }
      }
    }
    return trees;
  }
  static class Node{
    public Node left;
    public Node right;
    public Node(Node left, Node right){
      this.left = left;
      this.right = right;
    }
  }
  @Test
  public void testBinaryTrees(){
    assertThat(binaryTrees(1).size(), is(1));
    assertThat(binaryTrees(2).size(), is(2));
    assertThat(binaryTrees(3).size(), is(5));
  }
  
  static List<String> generateMatchedParens(int n){
    List<String> parens = new ArrayList<>();
    generateMatchedParens(n, 0, 0, new StringBuilder(), parens);
    return parens;
  }
  static void generateMatchedParens(int n, int nLeft, int nRight, StringBuilder current, List<String> parens){
    if(nLeft == nRight && nLeft == n){
      parens.add(current.toString());
      return;
    }
    if(nLeft < n){
      current.append("(");
      generateMatchedParens(n, nLeft + 1, nRight, current, parens);
      current.deleteCharAt(current.length() - 1);
    }
    if(nRight < nLeft){
      current.append(")");
      generateMatchedParens(n, nLeft, nRight + 1, current, parens);
      current.deleteCharAt(current.length() - 1);
    }
  }
  @Test
  public void testGenerateMatchedParens(){
    assertThat(generateMatchedParens(1), is(Arrays.asList("()")));
    assertThat(generateMatchedParens(2), is(Arrays.asList("(())", "()()")));
    assertThat(generateMatchedParens(3), is(Arrays.asList("((()))", "(()())", "(())()", "()(())", "()()()")));
  }
  
  static List<List<Integer>> subsets(int n, int k){
    List<List<Integer>> subsets = new ArrayList<>();
    subsets(n, k, 1, subsets, new ArrayList<>());
    for(List<Integer> s : subsets) System.out.println(s);
    System.out.println();
    return subsets;
  }
  static void subsets(int n, int k, int i, List<List<Integer>> subsets, List<Integer> subset){
    if(subset.size() == k){
      subsets.add(new ArrayList<>(subset));
      return;
    }
    for(int j = i; j <= n; j++){
      subset.add(j);
      subsets(n, k, j + 1, subsets, subset);
      subset.remove(subset.size() - 1);
    }
  }
  @Test
  public void testSubsets2(){
    assertThat(subsets(5, 3).size(), is(10));
  }
  
  static List<List<Integer>> subsets(List<Integer> set){
    List<List<Integer>> subsets = new ArrayList<>();
    for(long subset = 0; subset < (1 << set.size()); subset++){
      List<Integer> subsetList = new ArrayList<>();
      long s = subset;
      int i = set.size() - 1;
      while(s != 0){
        if((s & 1) == 1) subsetList.add(set.get(i));
        s >>= 1;
        i--;
      }
      Collections.reverse(subsetList);
      subsets.add(subsetList);
    }
    for(List<Integer> s : subsets) System.out.println(s);
    System.out.println();
    return subsets;
  }
  @Test
  public void testSubsets(){
    assertThat(subsets(Arrays.asList(1)).size(), is(2));
    assertThat(subsets(Arrays.asList(1,2)).size(), is(4));
    assertThat(subsets(Arrays.asList(1,2,3)).size(), is(8));
  }
  
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
    assertThat(uniquePermutations(new int[]{2,3,2,3,2,3}).size(), is(20));
    assertThat(uniquePermutations(new int[]{2,3,2,3}).size(), is(6));
    assertThat(uniquePermutations(new int[]{2,3,0,2}).size(), is(12));
    assertThat(uniquePermutations(new int[]{1,2,3}).size(), is(6));
    assertThat(uniquePermutations(new int[]{2,2,2}).size(), is(1));
    assertThat(uniquePermutations(new int[]{2,3,2,2}).size(), is(4));
  }
}
