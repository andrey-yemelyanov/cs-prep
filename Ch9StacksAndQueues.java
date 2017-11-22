import java.util.*;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import org.junit.Test;

public class Ch9StacksAndQueues{
  class StackBasedQueue{
    private int size = 0;
    private java.util.Stack<Integer> s1 = new java.util.Stack<>();
    private java.util.Stack<Integer> s2 = new java.util.Stack<>();
    public int dequeue(){
      if(size == 0) throw new RuntimeException("Queue is empty.");
      if(s2.isEmpty()){
        while(!s1.isEmpty()) s2.push(s1.pop());
      }
      int item = s2.pop();
      size--;
      return item;
    }
    public void enqueue(int item){
      s1.push(item);
      size++;
    }
    public int size(){
      return size;
    }
  }
  @Test
  public void testStackBasedQueue(){
    StackBasedQueue q = new StackBasedQueue();
    assertThat(q.size(), is(0));
    q.enqueue(1);
    assertThat(q.size(), is(1));
    q.enqueue(2);
    q.enqueue(3);
    q.enqueue(4);
    q.enqueue(5);
    q.enqueue(6);
    assertThat(q.size(), is(6));
    assertThat(q.dequeue(), is(1));
    assertThat(q.dequeue(), is(2));
    assertThat(q.dequeue(), is(3));
    assertThat(q.dequeue(), is(4));
    assertThat(q.dequeue(), is(5));
    assertThat(q.dequeue(), is(6));
    assertThat(q.size(), is(0));
    q.enqueue(7);
    q.enqueue(8);
    assertThat(q.dequeue(), is(7));
    q.enqueue(9);
    q.enqueue(10);
    assertThat(q.dequeue(), is(8));
    assertThat(q.dequeue(), is(9));
    assertThat(q.size(), is(1));
  }

  class CircularQueue<T>{
    private T[] q;
    private int head;
    private int tail;
    private int size;
    public CircularQueue(int capacity){
      q = (T[]) new Object[capacity];
    }
    public int size(){
      return size;
    }
    public T dequeue(){
      if(size == 0) throw new RuntimeException("Queue is empty.");
      T item = q[head];
      head = (head + 1) % q.length;
      size--;
      return item;
    }
    public void enqueue(T item){
      if(size == q.length) resize();
      q[tail] = item;
      tail = (tail + 1) % q.length;
      size++;
    }
    private void resize(){
      T[] new_q = (T[]) new Object[q.length * 2];
      for(int i = 0; i < q.length; i++){
        new_q[i] = q[(head + i) % q.length];
      }
      head = 0;
      tail = size;
      q = new_q;
    }
  }
  @Test
  public void testQueue(){
    CircularQueue<Integer> q = new CircularQueue<>(3);
    assertThat(q.size(), is(0));
    q.enqueue(1);
    assertThat(q.size(), is(1));
    q.enqueue(2);
    q.enqueue(3);
    q.enqueue(4);
    q.enqueue(5);
    q.enqueue(6);
    assertThat(q.size(), is(6));
    assertThat(q.dequeue(), is(1));
    assertThat(q.dequeue(), is(2));
    assertThat(q.dequeue(), is(3));
    assertThat(q.dequeue(), is(4));
    assertThat(q.dequeue(), is(5));
    assertThat(q.dequeue(), is(6));
    assertThat(q.size(), is(0));
  }

  // returns average of keys at each level
  List<Double> averageOnEachLevel(Node tree){
    Queue<Node> q = new LinkedList<>();
    q.add(tree);
    int nNodesAtCurrentLevel = 1;
    int sum = 0;
    int nNodes = nNodesAtCurrentLevel;
    List<Double> avg = new ArrayList<>();
    while(!q.isEmpty()){
      Node node = q.remove();
      sum += node.data;
      nNodesAtCurrentLevel--;
      if(node.left != null) q.add(node.left);
      if(node.right != null) q.add(node.right);
      if(nNodesAtCurrentLevel == 0){
        avg.add((double) sum / nNodes);
        nNodes = nNodesAtCurrentLevel = q.size();
        sum = 0;
      }
    }
    return avg;
  }
  @Test
  public void testAverageOnEachLevel(){
    Node tree = new Node(314,
      new Node(6,
        new Node(271,
          new Node(28),
          new Node(0)),
        new Node(561,
          null,
          new Node(3,
            new Node(17),
            null))),
      new Node(7,
        new Node(2,
          null,
          new Node(1,
            new Node(401,
              null,
              new Node(641)),
            new Node(257))),
        new Node(271,
          null,
          new Node(28))));
    List<Double> expected = Arrays.asList(314.0, 6.5, 276.25, 12.0, 225.0, 641.0);
    assertThat(averageOnEachLevel(tree), is(expected));
  }

  List<List<Integer>> alternatingLevelOrderTraversal(Node tree){
    List<List<Integer>> levelOrderTraversal = levelOrderTraversal(tree);
    for(int i = 0; i < levelOrderTraversal.size(); i++){
      if(i % 2 != 0){
        Collections.reverse(levelOrderTraversal.get(i));
      }
    }
    return levelOrderTraversal;
  }
  List<List<Integer>> levelOrderTraversal(Node tree){
    Queue<Node> q = new LinkedList<>();
    q.add(tree);
    int nNodesAtCurrentLevel = 1;
    List<List<Integer>> traversal = new ArrayList<>();
    List<Integer> currentLevel = new ArrayList<>();
    while(!q.isEmpty()){
      Node node = q.remove();
      currentLevel.add(node.data);
      nNodesAtCurrentLevel--;
      if(node.left != null) q.add(node.left);
      if(node.right != null) q.add(node.right);
      if(nNodesAtCurrentLevel == 0){
        nNodesAtCurrentLevel = q.size();
        traversal.add(currentLevel);
        currentLevel = new ArrayList<>();
      }
    }
    return traversal;
  }
  @Test
  public void testAlternatingLevelOrderTraversal(){
    Node tree = new Node(314,
      new Node(6,
        new Node(271,
          new Node(28),
          new Node(0)),
        new Node(561,
          null,
          new Node(3,
            new Node(17),
            null))),
      new Node(7,
        new Node(2,
          null,
          new Node(1,
            new Node(401,
              null,
              new Node(641)),
            new Node(257))),
        new Node(271,
          null,
          new Node(28))));

    List<List<Integer>> expected = new ArrayList<>();
    expected.add(Arrays.asList(314));
    expected.add(Arrays.asList(7,6));
    expected.add(Arrays.asList(271,561,2,271));
    expected.add(Arrays.asList(28,1,3,0,28));
    expected.add(Arrays.asList(17,401,257));
    expected.add(Arrays.asList(641));
    assertThat(alternatingLevelOrderTraversal(tree), is(expected));
  }

  void sortStack(java.util.Stack<Integer> s){
    if(s.isEmpty()) return;
    int top = s.pop();
    sortStack(s);
    insertInSortedOrder(top, s);
  }
  void insertInSortedOrder(int item, java.util.Stack<Integer> s){
    if(s.isEmpty() || s.peek() <= item) s.push(item);
    else{
      int top = s.pop();
      insertInSortedOrder(item, s);
      s.push(top);
    }
  }
  @Test
  public void testSortStack(){
    java.util.Stack<Integer> s = new java.util.Stack<>();
    sortStack(s);
    assertTrue(s.isEmpty());

    s.push(5);
    sortStack(s);
    java.util.Stack<Integer> expected = new java.util.Stack<>();
    expected.push(5);
    assertThat(s, is(expected));

    s.push(4);
    s.push(3);
    s.push(2);
    s.push(1);
    sortStack(s);
    expected = new java.util.Stack<>();
    expected.push(1);
    expected.push(2);
    expected.push(3);
    expected.push(4);
    expected.push(5);
    assertThat(s, is(expected));

    s.clear();
    s.push(1);
    s.push(2);
    s.push(3);
    s.push(4);
    sortStack(s);
    expected = new java.util.Stack<>();
    expected.push(1);
    expected.push(2);
    expected.push(3);
    expected.push(4);
    assertThat(s, is(expected));

    s.clear();
    s.push(7);
    s.push(1);
    s.push(3);
    s.push(5);
    s.push(2);
    sortStack(s);
    expected = new java.util.Stack<>();
    expected.push(1);
    expected.push(2);
    expected.push(3);
    expected.push(5);
    expected.push(7);
    assertThat(s, is(expected));
  }

  List<Integer> buildingsWithSunsetView(int[] buildings){
    java.util.Stack<Integer> s = new java.util.Stack<>();
    for(int i = buildings.length - 1; i >= 0; i--){
      int buildingHeight = buildings[i];
      while(!s.isEmpty() && s.peek() <= buildingHeight) s.pop();
      s.push(buildingHeight);
    }
    List<Integer> withSunsetView = new ArrayList<>();
    while(!s.isEmpty()) withSunsetView.add(s.pop());
    return withSunsetView;
  }
  @Test
  public void testBuildingsWithSunsetView(){
    assertThat(buildingsWithSunsetView(new int[]{1}), is(Arrays.asList(1)));
    assertThat(buildingsWithSunsetView(new int[]{5,4,3,2,1}), is(Arrays.asList(5)));
    assertThat(buildingsWithSunsetView(new int[]{1,2,3,4,5}), is(Arrays.asList(1,2,3,4,5)));
    assertThat(buildingsWithSunsetView(new int[]{6,1,2,3,4,5}), is(Arrays.asList(6)));
  }

  List<Integer> computeJumpFirstOrder(ListNode head){
    java.util.Stack<ListNode> s = new java.util.Stack<>();
    int order = 1;
    s.push(head);
    while(!s.isEmpty()){
      ListNode node = s.pop();
      if(node != null && node.order == 0){
        node.order = order++;
        s.push(node.next);
        s.push(node.jump);
      }
    }
    List<Integer> jumpFirstOrder = new ArrayList<>();
    ListNode current = head;
    while(current != null){
      jumpFirstOrder.add(current.order);
      current = current.next;
    }
    return jumpFirstOrder;
  }
  static class ListNode{
    public int order;
    public ListNode jump;
    public ListNode next;
  }
  @Test
  public void testComputeJumpFirstOrder(){
    ListNode nodeD = new ListNode();
    ListNode nodeC = new ListNode();
    ListNode nodeB = new ListNode();
    ListNode nodeA = new ListNode();

    nodeA.next = nodeB;
    nodeA.jump = nodeC;

    nodeB.next = nodeC;
    nodeB.jump = nodeD;

    nodeC.next = nodeD;
    nodeC.jump = nodeB;

    nodeD.jump = nodeD;

    assertThat(computeJumpFirstOrder(nodeA), is(Arrays.asList(1,3,2,4)));
  }

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
