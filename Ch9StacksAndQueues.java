import java.util.*;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import org.junit.Test;

public class Ch9StacksAndQueues{
  static class Node{
    public int data;
    public Node left;
    public Node right;
    public Node(int data, Node left, Node right){
      this.data = data;
      this.left = left;
      this.right = right;
    }
    public Node(int data){
      this.data = data;
    }
  }
  static List<Integer> traverseInorder(Node root){
    if(root == null) return null;
    java.util.Stack<Node> s = new java.util.Stack<>();
    List<Integer> traversal = new ArrayList<>();
    s.push(root);
    while(s.peek().left != null) s.push(s.peek().left);
    while(!s.isEmpty()){
      Node node = s.pop();
      traversal.add(node.data);
      if(node.right != null){
        s.push(node.right);
        while(s.peek().left != null) s.push(s.peek().left);
      }
    }
    return traversal;
  }
  @Test
  public void testTraverseInorder(){
    Node tree = null;
    assertThat(traverseInorder(tree), is(nullValue()));
    
    tree = new Node(1, null, null);
    assertThat(traverseInorder(tree), is(Arrays.asList(1)));
    
    tree = new Node(3, new Node(1), new Node(4));
    assertThat(traverseInorder(tree), is(Arrays.asList(1,3,4)));
    
    tree = new Node(3, new Node(2, new Node(1), null), null);
    assertThat(traverseInorder(tree), is(Arrays.asList(1,2,3)));
    
    tree = new Node(1, null, new Node(2, null, new Node(3)));
    assertThat(traverseInorder(tree), is(Arrays.asList(1,2,3)));
    
    tree = new Node(
      19,
      new Node(
        17,
        new Node(
          8,
          null,
          new Node(9)),
        null),
      new Node(
        21,
        new Node(20),
        new Node(
          60,
          new Node(30),
          null))
    );
    assertThat(traverseInorder(tree), is(Arrays.asList(8,9,17,19,20,21,30,60)));
  }
  
  static boolean isWellFormed(String str){
    java.util.Stack<Character> s = new java.util.Stack<>();
    for(int i = 0; i < str.length(); i++){
      char c = str.charAt(i);
      switch(c){
        case '(':
        case '{':
        case '[':
          s.push(c);
          break;
        case ']':
          if(s.isEmpty() || s.peek() != '[') return false;
          s.pop();
          break;
        case '}':
          if(s.isEmpty() || s.peek() != '{') return false;
          s.pop();
          break;
        case ')':
          if(s.isEmpty() || s.peek() != '(') return false;
          s.pop();
          break;
      }
    }
    return s.isEmpty();
  }
  @Test
  public void testIsWellFormed(){
    assertTrue(isWellFormed("([]){()}"));
    assertTrue(isWellFormed("[()[]{()()}]"));
    assertFalse(isWellFormed("{)"));
    assertFalse(isWellFormed("[()[]{()()"));
  }
  
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
