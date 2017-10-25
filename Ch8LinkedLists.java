import java.util.*;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import org.junit.Test;

public class Ch8LinkedLists{
  static Node reverseList(Node head){
    Node prev = null;
    Node current = head;
    while(current != null){
      Node next = current.next;
      current.next = prev;
      prev = current;
      current = next;
    }
    return prev;
  }
  @Test
  public void testReverseList(){
    Node list = toLinkedList();
    Node expected = toLinkedList();
    assertThat(toString(reverseList(list)), is(toString(expected)));
    
    list = toLinkedList(1);
    expected = toLinkedList(1);
    assertThat(toString(reverseList(list)), is(toString(expected)));
    
    list = toLinkedList(1,2);
    expected = toLinkedList(2,1);
    assertThat(toString(reverseList(list)), is(toString(expected)));
    
    list = toLinkedList(1,2,3);
    expected = toLinkedList(3,2,1);
    assertThat(toString(reverseList(list)), is(toString(expected)));
    
    list = toLinkedList(1,2,3,4,5,6);
    expected = toLinkedList(6,5,4,3,2,1);
    assertThat(toString(reverseList(list)), is(toString(expected)));
  }
  
  static Node mergeLists(Node l1, Node l2){
    if(l1 == null && l2 == null) return null;
    if(l1 == null) return l2;
    if(l2 == null) return l1;
    
    Node head = null; Node tail = null;
    while(l1 != null && l2 != null){
      if(l1.data < l2.data){
        if(head == null){
          head = l1;
          tail = head;
        }else{
          tail.next = l1;
          tail = tail.next;
        }
        l1 = l1.next;
      }else{
        if(head == null){
          head = l2;
          tail = head;
        }else{
          tail.next = l2;
          tail = tail.next;
        }
        l2 = l2.next;
      }
    }
    
    if(l1 != null) tail.next = l1;
    if(l2 != null) tail.next = l2;
    return head;
  }
  @Test
  public void testMergeLists(){
    Node l1 = toLinkedList(2,5,7);
    Node l2 = toLinkedList(3,11);
    Node expected = toLinkedList(2,3,5,7,11);
    assertThat(toString(mergeLists(l1, l2)), is(toString(expected)));
    
    l1 = toLinkedList(2,5,7);
    l2 = toLinkedList();
    expected = toLinkedList(2,5,7);
    assertThat(toString(mergeLists(l1, l2)), is(toString(expected)));
    
    l1 = toLinkedList();
    l2 = toLinkedList(3,11);
    expected = toLinkedList(3,11);
    assertThat(toString(mergeLists(l1, l2)), is(toString(expected)));
    
    l1 = toLinkedList(2,5,7);
    l2 = toLinkedList(0,1);
    expected = toLinkedList(0,1,2,5,7);
    assertThat(toString(mergeLists(l1, l2)), is(toString(expected)));
    
    l1 = toLinkedList(2,5,7);
    l2 = toLinkedList(10,11,12,13);
    expected = toLinkedList(2,5,7,10,11,12,13);
    assertThat(toString(mergeLists(l1, l2)), is(toString(expected)));
    
    l1 = toLinkedList(1,3,5,7);
    l2 = toLinkedList(2,4,6);
    expected = toLinkedList(1,2,3,4,5,6,7);
    assertThat(toString(mergeLists(l1, l2)), is(toString(expected)));
    
    l1 = null;
    l2 = toLinkedList(2,4,6);
    expected = toLinkedList(2,4,6);
    assertThat(toString(mergeLists(l1, l2)), is(toString(expected)));
    
    l1 = null;
    l2 = null;
    expected = null;
    assertThat(toString(mergeLists(l1, l2)), is(toString(expected)));
  }
  
  static class Node{
    public int data;
    public Node next;
  }
  static Node toLinkedList(int... list){
    Node head = null;
    Node tail = null;
    for(int data : list){
      if(head == null){
        head = new Node();
        head.data = data;
        tail = head;
      }else{
        tail.next = new Node();
        tail.next.data = data;
        tail = tail.next;
      }
    }
    return head;
  }
  static void printLinkedList(Node head){
    System.out.println(toString(head));
  }
  static String toString(Node head){
    Node current = head;
    StringBuilder sb = new StringBuilder();
    sb.append("[");
    while(current != null){
      sb.append(current.data);
      if(current.next != null) sb.append("->");
      current = current.next;
    }
    sb.append("]");
    return sb.toString();
  }
}
