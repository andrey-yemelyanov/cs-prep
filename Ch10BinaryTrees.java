import java.util.*;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import org.junit.Test;

public class Ch10BinaryTrees{
  Node findLca(Node root, int node1, int node2){
    return findLcaRec(root, node1, node2).lca;
  }
  LcaResult findLcaRec(Node root, int node1, int node2){
    if(root == null) return new LcaResult();
    LcaResult left = findLcaRec(root.left, node1, node2);
    if(left.lca != null) return left;
    LcaResult right = findLcaRec(root.right, node1, node2);
    if(right.lca != null) return right;
    int nNodesFound = left.nNodes + right.nNodes 
      + ((root.data == node1 || root.data == node2) ? 1 : 0);
    return new LcaResult(nNodesFound, nNodesFound == 2 ? root : null);
  }
  class LcaResult{
    public int nNodes;
    public Node lca;
    public LcaResult(){}
    public LcaResult(int nNodes, Node lca){
      this.nNodes = nNodes;
      this.lca = lca;
    }
  }
  @Test
  public void testFindLca(){
    Node tree = new Node(1, new Node(2, null, null), new Node(3, null, null));
    assertThat(findLca(tree, 2, 3).data, is(1));
    assertThat(findLca(tree, 2, 1).data, is(1));
    
    tree = new Node(1, 
            new Node(2, 
              new Node(4, null, null), 
              new Node(5, null, null)), 
            new Node(3, 
              new Node(6, null, null), 
              new Node(7, null, null)));
    assertThat(findLca(tree, 2, 7).data, is(1));
    assertThat(findLca(tree, 4, 5).data, is(2));
    assertThat(findLca(tree, 3, 6).data, is(3));
  }
  
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
