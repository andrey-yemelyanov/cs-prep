import java.util.*;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import org.junit.Test;

public class Ch10BinaryTrees{
  BalanceCheck isBalanced(Node tree){
    if(tree == null) return new BalanceCheck(true, -1);
    BalanceCheck left = isBalanced(tree.left);
    if(!left.isBalanced) return new BalanceCheck(false, 0);
    BalanceCheck right = isBalanced(tree.right);
    if(!right.isBalanced) return new BalanceCheck(false, 0);
    boolean balanced = Math.abs(left.height - right.height) <= 1;
    int height = 1 + Math.max(left.height, right.height);
    return new BalanceCheck(balanced, height);
  }
  class BalanceCheck{
    public boolean isBalanced;
    public int height;
    public BalanceCheck(boolean isBalanced, int height){
      this.isBalanced = isBalanced;
      this.height = height;
    }
  }
  class Node{
    public int data;
    public Node left;
    public Node right;
    public Node(int data, Node left, Node right){
      this.data = data;
      this.left = left;
      this.right = right;
    }
    public Node(Node left, Node right){
      this.left = left;
      this.right = right;
    }
  }
  
  @Test
  public void testIsBalanced(){
    Node tree = null;
    assertThat(isBalanced(tree).isBalanced, is(true));
    tree = new Node(1,null,null);
    assertThat(isBalanced(tree).isBalanced, is(true));
    tree = new Node(1,
      new Node(2, 
        new Node(4,null,null), 
        new Node(5,null,null)),
      new Node(3, 
        new Node(6,null,null), 
        new Node(7,null,null)));
    assertThat(isBalanced(tree).isBalanced, is(true));
    tree = new Node(1, new Node(2,new Node(3,new Node(4,null,null),null),null),null);
    assertThat(isBalanced(tree).isBalanced, is(false));
  }
}
