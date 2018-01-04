import java.util.*;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import org.junit.Test;

public class Ch15BST{
  static Node searchForFirstOccurrence(Node tree, int key){
    if(tree == null) return null;
    if(tree.data > key) return searchForFirstOccurrence(tree.left, key);
    if(tree.data < key) return searchForFirstOccurrence(tree.right, key);
    Node nodeInLeftSubtree = searchForFirstOccurrence(tree.left, key);
    if(nodeInLeftSubtree != null) return nodeInLeftSubtree;
    return tree;
  }
  @Test
  public void testSearchForFirstOccurrence(){
    Node tree = new Node(108,
      new Node(108,
        new Node(-10,
          new Node(-14),
          new Node(2)
        ),
        new Node(108)
      ),
      new Node(285,
        new Node(243),
        new Node(285,
          null,
          new Node(401)
        )
      )
    );
    assertThat(searchForFirstOccurrence(tree, 108), is(tree.left));
    assertThat(searchForFirstOccurrence(tree, 285), is(tree.right));
    assertThat(searchForFirstOccurrence(tree, 243), is(tree.right.left));
    assertThat(searchForFirstOccurrence(tree, 200), is(nullValue()));
    assertThat(searchForFirstOccurrence(tree, -14), is(tree.left.left.left));
  }
  
  static boolean isBinaryTreeBst(Node tree){
    return areKeysInRange(tree, Integer.MIN_VALUE, Integer.MAX_VALUE);
  }
  static boolean areKeysInRange(Node tree, int min, int max){
    if(tree == null) return true;
    if(tree.data < min || tree.data > max) return false;
    return areKeysInRange(tree.left, min, tree.data) && areKeysInRange(tree.right, tree.data, max);
  }
  @Test
  public void testIsBinaryTreeBst(){
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
