import java.util.*;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import org.junit.Test;

public class Ch15BST{
  static boolean isBinaryTreeBst(Node tree){
    return areKeysInRange(tree, Integer.MIN_VALUE, Integer.MAX_VALUE);
  }
  static boolean areKeysInRange(Node tree, int min, int max){
    if(tree == null) return true;
    if(tree.data < min || tree.data > max) return false;
    return areKeysInRange(tree.left, min, tree.data) && areKeysInRange(tree.right, tree.data, max);
  }
  @Test
  public void test(){
    assertThat(isBinaryTreeBst(null), is(true));
    assertThat(isBinaryTreeBst(new Node(1)), is(true));
    assertThat(isBinaryTreeBst(new Node(1,null, new Node(2))), is(true));
    assertThat(isBinaryTreeBst(new Node(1, new Node(0), new Node(2))), is(true));
    assertThat(isBinaryTreeBst(new Node(1, new Node(2), new Node(0))), is(false));
  }
  
  class Node{
    public Node left;
    public Node right;
    public int data;
    public Node(int data){
      this.data = data;
    }
    public Node(int data, Node left, Node right){
      this.data = data;
      this.left = left;
      this.right = right;
    }
  }
}
