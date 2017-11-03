import java.util.*;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import org.junit.Test;

public class Ch9StacksAndQueues{
  // Implements a stack with Max api. To ensure that pop, push and max have time complexity O(1)
  // for each entry in the stack, we store the max value at or below that entry. Space O(n).
  class Stack{
    private StackNode head = null;
    private int size = 0;
    public int max(){
      ensureStackHasElements();
      return head.max;
    }
    public int pop(){
      ensureStackHasElements();
      StackNode poppedNode = head;
      head = head.next;
      size--;
      return poppedNode.data;
    }
    public int peek(){
      ensureStackHasElements();
      return head.data;
    }
    public void push(int n){
      StackNode newNode = new StackNode();
      newNode.data = n;
      newNode.max = isEmpty() ? n : Math.max(head.max, n);
      newNode.next = head;
      head = newNode;
      size++;
    }
    public int size(){
      return size;
    }
    public boolean isEmpty(){
      return size() == 0;
    }
    private void ensureStackHasElements(){
      if(head == null) throw new RuntimeException("Stack is empty");
    }
  }
  class StackNode{
    public int data;
    public int max;
    public StackNode next;
  }
  @Test
  public void testStack(){
    Stack s = new Stack();
    assertTrue(s.isEmpty());
    s.push(1);
    assertThat(s.max(), is(1));
    s.push(10);
    assertThat(s.max(), is(10));
    s.push(3);
    assertThat(s.max(), is(10));
    s.push(9);
    assertThat(s.max(), is(10));
    s.pop();
    assertThat(s.max(), is(10));
    s.pop();
    assertThat(s.max(), is(10));
    s.pop();
    assertThat(s.max(), is(1));
    s.pop();
    assertTrue(s.isEmpty());
  }
}
