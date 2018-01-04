import java.util.*;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import org.junit.Test;

public class Ch15BST{
  static Node lca(Node tree, int key1, int key2){
    if(tree == null) return null;
    if(tree.data >= Math.min(key1, key2) && tree.data <= Math.max(key1, key2)) return tree;
    if(tree.data < Math.min(key1, key2)) return lca(tree.right, key1, key2);
    return lca(tree.left, key1, key2);
  }
  @Test
  public void testLca(){
    Node tree = new Node(19,
      new Node(7,
        new Node(3,
          new Node(2),
          new Node(5)
        ),
        new Node(11,
          null,
          new Node(17,
            new Node(13),
            null
          )
        )
      ),
      new Node(43,
        new Node(23,
          null,
          new Node(37,
            new Node(29,
              null,
              new Node(31)
            ),
            new Node(41)
          )
        ),
        new Node(47,
          null,
          new Node(53)
        )
      )
    );
    assertThat(lca(tree, 2, 5).data, is(3));
    assertThat(lca(tree, 2, 17).data, is(7));
    assertThat(lca(tree, 2, 3).data, is(3));
    assertThat(lca(tree, 19, 43).data, is(19));
    assertThat(lca(tree, 2, 53).data, is(19));
    assertThat(lca(tree, 37, 47).data, is(43));
    assertThat(lca(tree, 31, 41).data, is(37));
    assertThat(lca(tree, 5, 13).data, is(7));
    assertThat(lca(tree, 47, 53).data, is(47));
    assertThat(lca(tree, 2, 19).data, is(19));
    assertThat(lca(tree, 19, 31).data, is(19));
    assertThat(lca(tree, 19, 19).data, is(19));
  }
  
  static List<Integer> getKLargestKeys(Node tree, int k){
    List<Integer> keys = new ArrayList<>();
    getKLargestKeys(tree, k, keys);
    return keys;
  }
  static void getKLargestKeys(Node tree, int k, List<Integer> keys){
    if(tree == null || keys.size() > k) return;
    getKLargestKeys(tree.right, k, keys);
    if(keys.size() < k){
      keys.add(tree.data);
      getKLargestKeys(tree.left, k, keys);
    }
  }
  @Test
  public void testGetKLargestKeys(){
    Node tree = new Node(19,
      new Node(7,
        new Node(3,
          new Node(2),
          new Node(5)
        ),
        new Node(11,
          null,
          new Node(17,
            new Node(13),
            null
          )
        )
      ),
      new Node(43,
        new Node(23,
          null,
          new Node(37,
            new Node(29,
              null,
              new Node(31)
            ),
            new Node(41)
          )
        ),
        new Node(47,
          null,
          new Node(53)
        )
      )
    );
    assertThat(getKLargestKeys(tree, 3), is(Arrays.asList(53,47,43)));
    assertThat(getKLargestKeys(tree, 1), is(Arrays.asList(53)));
    assertThat(getKLargestKeys(tree, 5), is(Arrays.asList(53,47,43,41,37)));
    assertThat(getKLargestKeys(tree, 8), is(Arrays.asList(53,47,43,41,37,31,29,23)));
    assertThat(getKLargestKeys(tree, 10), is(Arrays.asList(53,47,43,41,37,31,29,23,19,17)));
  }
  
  static Node searchKeyLargerThan(Node tree, int key){
    if(tree == null) return null;
    if(tree.data > key){
      Node node = searchKeyLargerThan(tree.left, key);
      if(node != null) return node;
      return tree;
    }
    return searchKeyLargerThan(tree.right, key);
  }
  @Test
  public void testSearchKeyLargerThan(){
    Node tree = new Node(19,
      new Node(7,
        new Node(3,
          new Node(2),
          new Node(5)
        ),
        new Node(11,
          null,
          new Node(17,
            new Node(13),
            null
          )
        )
      ),
      new Node(43,
        new Node(23,
          null,
          new Node(37,
            new Node(29,
              null,
              new Node(31)
            ),
            new Node(41)
          )
        ),
        new Node(47,
          null,
          new Node(53)
        )
      )
    );
    assertThat(searchKeyLargerThan(tree, 23).data, is(29));
    assertThat(searchKeyLargerThan(tree, 55), is(nullValue()));
    assertThat(searchKeyLargerThan(tree, 0).data, is(2));
    assertThat(searchKeyLargerThan(tree, 20).data, is(23));
    assertThat(searchKeyLargerThan(tree, 6).data, is(7));
    assertThat(searchKeyLargerThan(tree, 5).data, is(7));
    assertThat(searchKeyLargerThan(tree, 30).data, is(31));
    assertThat(searchKeyLargerThan(tree, 51).data, is(53));
    assertThat(searchKeyLargerThan(tree, 18).data, is(19));
  }
  
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
