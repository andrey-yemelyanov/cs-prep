import java.util.*;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import org.junit.Test;

public class BstUtil{
  static class Node{
    public int data;
    public Node left;
    public Node right;
    public Node next;
    @Override
    public String toString(){
      return Integer.toString(data);
    }
    public Node(int data, Node left, Node right){
      this.data = data; this.left = left; this.right = right;
    }
    public Node(int data){
      this.data = data;
    }
  }
  static Node head = null;
  static Node bstToLinkedList(Node node, Node tail){
    if(node == null) return tail;
    tail = bstToLinkedList(node.left, tail);
    if(tail == null){
      head = node; tail = node;
    }else{
      tail.next = node; tail = node;
    }
    tail = bstToLinkedList(node.right, tail);
    return tail;
  }
  static Node bstToLinkedList(Node bstRoot){
    head = null;
    bstToLinkedList(bstRoot, null);
    return head;
  }
  static String listToString(Node head){
    StringBuilder sb = new StringBuilder();
    while(head != null){
      sb.append(head  .toString());
      if(head.next != null) sb.append("->");
      head = head.next;
    }
    return sb.toString();
  }
  @Test
  public void testConvertBstToLinkedList(){
    Node bstRoot =
      new Node(7,
        new Node(4,
          new Node(3),
          new Node(5)),
        new Node(10,
          new Node(9),
          new Node(15)));
    assertThat(listToString(bstToLinkedList(bstRoot)), is("3->4->5->7->9->10->15"));

    bstRoot = null;
    assertThat(listToString(bstToLinkedList(bstRoot)), is(""));

    bstRoot = new Node(5);
    assertThat(listToString(bstToLinkedList(bstRoot)), is("5"));

    bstRoot =
      new Node(5,
        new Node(2),
        null);
    assertThat(listToString(bstToLinkedList(bstRoot)), is("2->5"));

    bstRoot =
      new Node(5,
        new Node(2),
        new Node(7));
    assertThat(listToString(bstToLinkedList(bstRoot)), is("2->5->7"));

    bstRoot =
      new Node(5,
        new Node(4,
          new Node(3,
            new Node(2,
              new Node(1),
              null),
            null),
          null),
        null);
    assertThat(listToString(bstToLinkedList(bstRoot)), is("1->2->3->4->5"));
  }
}
