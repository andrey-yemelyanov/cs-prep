import java.util.*;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import org.junit.Test;

public class Ch9StacksAndQueues{
  static double evalRpn(String rpnExpr, char delimiter){
    java.util.Stack<Double> s = new java.util.Stack<>();
    int i = 0;
    while(i < rpnExpr.length()){
      String token = "";
      while(i < rpnExpr.length() && rpnExpr.charAt(i) != delimiter) {
        token += rpnExpr.charAt(i);
        i++;
      }
      i++; // advance past the delimiter to the next valid char
      switch(token){
        case "+":
          s.push(s.pop() + s.pop());
          break;
        case "-":
          double b = s.pop();
          double a = s.pop();
          s.push(a - b);
          break;
        case "*":
          s.push(s.pop() * s.pop());
          break;
        case "/":
          b = s.pop();
          a = s.pop();
          s.push(a/b);
          break;
        default:
          s.push(Double.parseDouble(token));
      }
    }
    return s.pop();
  }
  @Test
  public void testEvalRpn(){
    assertThat(evalRpn("0",','), is(0.0));
    assertThat(evalRpn("5",','), is(5.0));
    assertThat(evalRpn("-5",','), is(-5.0));
    assertThat(evalRpn("-5.112",','), is(-5.112));
    assertThat(evalRpn("3,-4,*",','), is(-12.0));
    assertThat(evalRpn("3,4,*",','), is(12.0));
    assertThat(evalRpn("31,475,*",','), is(14725.0));
    assertThat(evalRpn("-100,5,+,1,*",','), is(-95.0));
    assertThat(evalRpn("1,1,+,-2,*",','), is(-4.0));
    assertThat(evalRpn("3,-4,/,5,*",','), is(-3.75));
    assertThat(evalRpn("3.6,-2,/,5,*",','), is(-9.0));
  }
  
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
