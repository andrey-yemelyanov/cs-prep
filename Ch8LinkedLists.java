import java.util.*;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import org.junit.Test;

public class Ch8LinkedLists{
  static Node findCycle(Node head){
    Node fast = head; Node slow = head;
    while(fast != null && fast.next != null && fast.next.next != null){
      slow = slow.next;
      fast = fast.next.next;
      if(slow == fast){ // the two pointers meet - there is a cycle
        // find cycle length
        int cycleLen = 0;
        do{
          cycleLen++;
          fast = fast.next;
        }while(slow != fast);
        // Use two pointers that are 'cycleLen' apart. Advance pointers by one node 
        // until they meet. The node where they meet is the start node of the cycle.
        Node first = head; Node second = head;
        for(int i = 0; i < cycleLen; i++) second = second.next;
        while(first != second){
          first = first.next;
          second = second.next;
        }
        return first;
      }
    }
    return null;
  }
  @Test
  public void testFindCycle(){
    Node list = linkedList(); // empty list - no cycle
    assertNull(findCycle(list));
    
    list = linkedList(1); // no cycle
    assertNull(findCycle(list));
    
    list = linkedList(1); // cycle (self-loop) 1->1->...
    list.next = list;
    assertNotNull(findCycle(list));
    assertThat(findCycle(list).data.compareTo(1), is(0));
    
    list = linkedList(1,2,3,4); // no cycle
    assertNull(findCycle(list));
    
    list = linkedList(1,2,3,4,5,6,7,8,9,10); // no cycle
    assertNull(findCycle(list));
    
    list = linkedList(1,2,3,4); // cycle 1->2->3->4->2->...
    Node tail = list;
    while(tail.next != null) tail = tail.next;
    tail.next = list.next;
    assertNotNull(findCycle(list));
    assertThat(findCycle(list).data.compareTo(2), is(0));
    
    list = linkedList(1,2,3,4); // cycle 1->2->3->4->1->...
    tail = list;
    while(tail.next != null) tail = tail.next;
    tail.next = list;
    assertNotNull(findCycle(list));
    assertThat(findCycle(list).data.compareTo(1), is(0));
    
    list = linkedList(1,2); // cycle 1->2->1->...
    list.next.next = list;
    assertNotNull(findCycle(list));
    assertThat(findCycle(list).data.compareTo(1), is(0));
  }
  
  static Node reverseList(Node head, int from, int to){
    if(from == to) return head;
    Node predecessor = null;
    if(from > 1){
      predecessor = head;
      for(int i = 0; i < from - 2; i++) predecessor = predecessor.next;
    }
    Node reverseTail = predecessor == null ? head : predecessor.next;
    Node prev = null;
    Node current = reverseTail;
    for(int i = 0; i < to - from + 1; i++){
      Node next = current.next;
      current.next = prev;
      prev = current;
      current = next;
    }
    reverseTail.next = current;
    if(predecessor == null) return prev;
    predecessor.next = prev;
    return head;
  }
  @Test
  public void testReverseSubList(){
    Node list = linkedList();
    Node expected = linkedList();
    assertThat(toString(reverseList(list,1,1)), is(toString(expected)));
    
    list = linkedList(1);
    expected = linkedList(1);
    assertThat(toString(reverseList(list,1,1)), is(toString(expected)));
    
    list = linkedList(1,2);
    expected = linkedList(2,1);
    assertThat(toString(reverseList(list,1,2)), is(toString(expected)));
    
    list = linkedList(1,2);
    expected = linkedList(1,2);
    assertThat(toString(reverseList(list,1,1)), is(toString(expected)));
    
    list = linkedList(1,2,3,4,5);
    expected = linkedList(2,1,3,4,5);
    assertThat(toString(reverseList(list,1,2)), is(toString(expected)));
    
    list = linkedList(1,2,3,4,5);
    expected = linkedList(1,2,5,4,3);
    assertThat(toString(reverseList(list,3,5)), is(toString(expected)));
    
    list = linkedList(1,2,3,4,5,6,7,8);
    expected = linkedList(1,2,3,7,6,5,4,8);
    assertThat(toString(reverseList(list,4,7)), is(toString(expected)));
    
    list = linkedList(1,2,3,4,5,6,7,8);
    expected = linkedList(8,7,6,5,4,3,2,1);
    assertThat(toString(reverseList(list,1,8)), is(toString(expected)));
  }
  
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
    Node list = linkedList();
    Node expected = linkedList();
    assertThat(toString(reverseList(list)), is(toString(expected)));
    
    list = linkedList(1);
    expected = linkedList(1);
    assertThat(toString(reverseList(list)), is(toString(expected)));
    
    list = linkedList(1,2);
    expected = linkedList(2,1);
    assertThat(toString(reverseList(list)), is(toString(expected)));
    
    list = linkedList(1,2,3);
    expected = linkedList(3,2,1);
    assertThat(toString(reverseList(list)), is(toString(expected)));
    
    list = linkedList(1,2,3,4,5,6);
    expected = linkedList(6,5,4,3,2,1);
    assertThat(toString(reverseList(list)), is(toString(expected)));
  }
  
  static Node mergeLists(Node l1, Node l2){
    if(l1 == null && l2 == null) return null;
    if(l1 == null) return l2;
    if(l2 == null) return l1;
    
    Node head = null; Node tail = null;
    while(l1 != null && l2 != null){
      if(l1.data.compareTo(l2.data) < 0){
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
    Node l1 = linkedList(2,5,7);
    Node l2 = linkedList(3,11);
    Node expected = linkedList(2,3,5,7,11);
    assertThat(toString(mergeLists(l1, l2)), is(toString(expected)));
    
    l1 = linkedList(2,5,7);
    l2 = linkedList();
    expected = linkedList(2,5,7);
    assertThat(toString(mergeLists(l1, l2)), is(toString(expected)));
    
    l1 = linkedList();
    l2 = linkedList(3,11);
    expected = linkedList(3,11);
    assertThat(toString(mergeLists(l1, l2)), is(toString(expected)));
    
    l1 = linkedList(2,5,7);
    l2 = linkedList(0,1);
    expected = linkedList(0,1,2,5,7);
    assertThat(toString(mergeLists(l1, l2)), is(toString(expected)));
    
    l1 = linkedList(2,5,7);
    l2 = linkedList(10,11,12,13);
    expected = linkedList(2,5,7,10,11,12,13);
    assertThat(toString(mergeLists(l1, l2)), is(toString(expected)));
    
    l1 = linkedList(1,3,5,7);
    l2 = linkedList(2,4,6);
    expected = linkedList(1,2,3,4,5,6,7);
    assertThat(toString(mergeLists(l1, l2)), is(toString(expected)));
    
    l1 = null;
    l2 = linkedList(2,4,6);
    expected = linkedList(2,4,6);
    assertThat(toString(mergeLists(l1, l2)), is(toString(expected)));
    
    l1 = null;
    l2 = null;
    expected = null;
    assertThat(toString(mergeLists(l1, l2)), is(toString(expected)));
  }
  
  static class Node<T extends Comparable<T>>{
    public T data;
    public Node next;
  }
  static <T extends Comparable<T>> Node<T> linkedList(T... list){
    Node<T> head = null;
    Node<T> tail = null;
    for(T data : list){
      if(head == null){
        head = new Node<T>();
        head.data = data;
        tail = head;
      }else{
        tail.next = new Node<T>();
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
