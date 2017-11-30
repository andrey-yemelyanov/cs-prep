import java.util.*;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import org.junit.Test;

public class Ch10BinaryTrees{
  List<Node> computeExterior(Node tree){
    if(tree == null) return null;
    List<Node> exterior = new ArrayList<>();
    exterior.add(tree);
    traverseLeft(tree.left, exterior, true);
    traverseRight(tree.right, exterior, true);
    return exterior;
  }
  void traverseLeft(Node root, List<Node> nodes, boolean isBoundary){
    if(root == null) return;
    if(isBoundary) nodes.add(root);
    traverseLeft(root.left, nodes, isBoundary || isLeaf(root.left));
    traverseLeft(root.right, nodes, isLeaf(root.right) || (isBoundary && root.left == null));
  }
  void traverseRight(Node root, List<Node> nodes, boolean isBoundary){
    if(root == null) return;
    traverseRight(root.left, nodes, isLeaf(root.left) || (isBoundary && root.right == null));
    traverseRight(root.right, nodes, isBoundary || isLeaf(root.right));
    if(isBoundary) nodes.add(root);
  }
  boolean isLeaf(Node node){
    return node != null && node.left == null && node.right == null;
  }
  @Test
  public void testComputeExterior(){
    Node tree = new Node('A',
      new Node('B',
        new Node('C', 
          new Node('D', null, null),
          new Node('E', null, null)),
        new Node('F', 
          null, 
          new Node('G', 
            new Node('H'),
            null))),
      new Node('I',
        new Node('J',
          null,
          new Node('K', 
            new Node('L', 
              null, 
              new Node('M', null, null)),
            new Node('N', null, null))),
        new Node('O',
          null,
          new Node('P', null, null))));
    assertThat(toCharList(computeExterior(tree)), is(Arrays.asList('A', 'B', 'C', 'D', 'E', 'H', 'M', 'N', 'P', 'O', 'I')));
  }
  List<Character> toCharList(List<Node> nodes){
    List<Character> list = new ArrayList<>();
    for(Node node : nodes) list.add((char)node.data);
    return list;
  }
  
  List<Node> traverseInorder(Node tree){
    List<Node> traversal = new ArrayList<>();
    Node current = tree;
    if(tree == null) return traversal; 
    while(current.left != null) current = current.left;
    Node prev = null;
    while(current != null){
      if(current.left == prev) traversal.add(current);
      if(current.right == null || current.right == prev){
        prev = current;
        current = current.parent;
      }else{
        if(current.right != null){
          current = current.right;
          while(current.left != null) current = current.left;
          prev = null;
        }
      }
    }
    return traversal;
  }
  @Test
  public void testTraverseInorder(){
    Node tree = new Node(1, null, null, null);
    assertThat(toIntList(traverseInorder(tree)), is(Arrays.asList(1)));
    tree.right = new Node(2,null,null,tree);
    assertThat(toIntList(traverseInorder(tree)), is(Arrays.asList(1,2)));
    tree.right = null;
    tree.left = new Node(2,null,null,tree);
    assertThat(toIntList(traverseInorder(tree)), is(Arrays.asList(2,1)));
    
    Node n1 = new Node(1);
    Node n2 = new Node(2);
    Node n3 = new Node(3);
    n1.left = n2; n1.right = n3;
    n2.parent = n1; n3.parent = n1;
    assertThat(toIntList(traverseInorder(n1)), is(Arrays.asList(2,1,3)));
    
    Node n4 = new Node(4);
    Node n5 = new Node(5);
    Node n6 = new Node(6);
    Node n7 = new Node(7);
    n2.left = n4; n2.right = n5;
    n3.left = n6; n3.right = n7;
    n4.parent = n2; n5.parent = n2;
    n6.parent = n3; n7.parent = n3;
    assertThat(toIntList(traverseInorder(n1)), is(Arrays.asList(4,2,5,1,6,3,7)));
    
    Node n8 = new Node(8);
    Node n9 = new Node(9);
    Node n10 = new Node(10);
    n4.right = n9;
    n5.left = n8;
    n7.right = n10;
    n9.parent = n4; n8.parent = n5; n10.parent = n7;
    assertThat(toIntList(traverseInorder(n1)), is(Arrays.asList(4,9,2,8,5,1,6,3,7,10)));
  }
  List<Integer> toIntList(List<Node> nodes){
    List<Integer> list = new ArrayList<>();
    for(Node node : nodes) list.add(node.data);
    return list;
  }
  
  // find k-th node in inorder traversal
  Node findKthNode(Node root, int k){
    if(root == null) return null;
    int nLeft = root.left != null ? root.left.count : 0;
    int nRight = root.right != null ? root.right.count : 0;
    if(nLeft + 1 == k) return root;
    if(nLeft >= k) return findKthNode(root.left, k);
    return findKthNode(root.right, k - nLeft - 1);
  }
  @Test
  public void testFindKthNode(){
    Node tree = new Node('A', 
      new Node('B', 
        new Node('C', null, null, 1),
        new Node('D', null, null, 1),
        3),
      new Node('E',
        new Node('F', null, null, 1),
        new Node('G', null, null, 1),
        3),
      7);
    assertThat(findKthNode(tree, 10), is(nullValue()));
    assertThat((char)findKthNode(tree, 1).data, is('C'));
    assertThat((char)findKthNode(tree, 2).data, is('B'));
    assertThat((char)findKthNode(tree, 3).data, is('D'));
    assertThat((char)findKthNode(tree, 4).data, is('A'));
    assertThat((char)findKthNode(tree, 5).data, is('F'));
    assertThat((char)findKthNode(tree, 6).data, is('E'));
    assertThat((char)findKthNode(tree, 7).data, is('G'));
  }
  
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
    public int count;
    public Node parent;
    public Node(int data){
      this.data = data;
    }
    public Node(int data, Node left, Node right){
      this.data = data;
      this.left = left;
      this.right = right;
    }
    public Node(int data, Node left, Node right, Node parent){
      this.data = data;
      this.left = left;
      this.right = right;
      this.parent = parent;
    }
    public Node(int data, Node left, Node right, int count){
      this.data = data;
      this.left = left;
      this.right = right;
      this.count = count;
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
