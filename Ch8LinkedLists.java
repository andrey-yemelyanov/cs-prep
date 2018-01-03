import java.util.*;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import org.junit.Test;

public class Ch8LinkedLists{
  static Node sort(Node list){
    Node dummyHead = new Node();
    dummyHead.next = list;
    Node sortedTail = list;
    while(sortedTail != null && sortedTail.next != null){
      if(sortedTail.data.compareTo(sortedTail.next.data) <= 0){
        sortedTail = sortedTail.next;
      }else{
        Node prev = dummyHead;
        Node target = sortedTail.next;
        while(prev.next.data.compareTo(target.data) < 0) prev = prev.next;
        Node temp = prev.next;
        prev.next = target;
        sortedTail.next = target.next;
        target.next = temp;
      }
    }
    return dummyHead.next;
  }
  @Test
  public void testSort(){
    Node list = linkedList(1);
    assertThat(toString(sort(list)), is(toString(linkedList(1))));
    
    list = linkedList(1,2,3,4);
    assertThat(toString(sort(list)), is(toString(linkedList(1,2,3,4))));
    
    list = linkedList(5,4,3,2,1);
    assertThat(toString(sort(list)), is(toString(linkedList(1,2,3,4,5))));
    
    list = linkedList(1,9,2,0,7);
    assertThat(toString(sort(list)), is(toString(linkedList(0,1,2,7,9))));
  }
  
  static <T extends Number & Comparable<T>> Node<T> add(Node<T> list1, Node<T> list2){
    list1 = reverseList(list1);
    list2 = reverseList(list2);
    int carry = 0;
    Node result = null;
    while(list1 != null && list2 != null){
      int s = list1.data.intValue() + list2.data.intValue() + carry;
      carry = s / 10;
      result = insertFirst(result, s % 10);
      list1 = list1.next;
      list2 = list2.next;
    }
    while(list1 != null){
      int s = list1.data.intValue() + carry;
      carry = s / 10;
      result = insertFirst(result, s % 10);
      list1 = list1.next;
    }
    while(list2 != null){
      int s = list2.data.intValue() + carry;
      carry = s / 10;
      result = insertFirst(result, s % 10);
      list2 = list2.next;
    }
    if(carry != 0) result = insertFirst(result, carry);
    return result;
  }
  static <T extends Comparable<T>> Node insertFirst(Node list, T item){
    Node<T> node = new Node<T>();
    node.data = item;
    node.next = list;
    return node;
  }
  @Test
  public void testAdd(){
    Node list1 = linkedList(1);
    Node list2 = linkedList(1);
    assertThat(toString(add(list1, list2)), is(toString(linkedList(2))));
    
    list1 = linkedList(0);
    list2 = linkedList(0);
    assertThat(toString(add(list1, list2)), is(toString(linkedList(0))));
    
    list1 = linkedList(1,2);
    list2 = linkedList(1,2,3,4,5);
    assertThat(toString(add(list1, list2)), is(toString(linkedList(1,2,3,5,7))));
    
    list1 = linkedList(9,9);
    list2 = linkedList(9,9);
    assertThat(toString(add(list1, list2)), is(toString(linkedList(1,9,8))));
    
    list1 = linkedList(0);
    list2 = linkedList(0,0,0,1,2);
    assertThat(toString(add(list1, list2)), is(toString(linkedList(0,0,0,1,2))));
    
    list1 = linkedList(1);
    list2 = linkedList(9);
    assertThat(toString(add(list1, list2)), is(toString(linkedList(1,0))));
    
    list1 = linkedList(9);
    list2 = linkedList(9);
    assertThat(toString(add(list1, list2)), is(toString(linkedList(1,8))));
    
    list1 = linkedList(9);
    list2 = linkedList(9,9);
    assertThat(toString(add(list1, list2)), is(toString(linkedList(1,0,8))));
    
    list1 = linkedList(3,9,8);
    list2 = linkedList(9,4,7);
    assertThat(toString(add(list1, list2)), is(toString(linkedList(1,3,4,5))));
    
    list1 = linkedList(3,9,8);
    list2 = linkedList(9,4,7,9);
    assertThat(toString(add(list1, list2)), is(toString(linkedList(9,8,7,7))));
    
    list1 = linkedList(3,9,8);
    list2 = linkedList(9,9,7,9);
    assertThat(toString(add(list1, list2)), is(toString(linkedList(1,0,3,7,7))));
    
    list1 = linkedList(4,5,6,6,4,8,7,9,7,9,8,1,3,4,4,8,4,8);
    list2 = linkedList(9,9,8,9,7,7,8,4,9,4,5,4,6,4,6,4,8,8,7,4);
    assertThat(toString(add(list1, list2)), 
      is(toString(linkedList(1,0,0,3,5,4,4,3,3,7,4,3,4,4,5,9,9,3,7,2,2))));
  }
  
  static Node pivot(Node head, int k){
    Node lessThanHead = null; Node lessThanTail = null;
    Node equalHead = null; Node equalTail = null;
    Node greaterThanHead = null; Node greaterThanTail = null;
    Node current = head;
    while(current != null){
      int c = current.data.compareTo(k);
      if(c < 0){
        if(lessThanHead == null){
          lessThanHead = current;
          lessThanTail = current;
        }else{
          lessThanTail.next = current;
          lessThanTail = lessThanTail.next;
        }
      }else if(c == 0){
        if(equalHead == null){
          equalHead = current;
          equalTail = current;
        }else{
          equalTail.next = current;
          equalTail = equalTail.next;
        }
      }else{
        if(greaterThanHead == null){
          greaterThanHead = current;
          greaterThanTail = current;
        }else{
          greaterThanTail.next = current;
          greaterThanTail = greaterThanTail.next;
        }
      }
      current = current.next;
    }
    
    if(lessThanTail != null) lessThanTail.next = null;
    if(equalTail != null) equalTail.next = null;
    if(greaterThanTail != null) greaterThanTail.next = null;
    
    Node newHead = greaterThanHead;
    if(equalHead != null){
      equalTail.next = newHead;
      newHead = equalHead;
    }
    if(lessThanHead != null){
      lessThanTail.next = newHead;
      newHead = lessThanHead;
    }
    return newHead;
  }
  @Test
  public void testPivot(){
    Node list = linkedList();
    assertThat(toString(pivot(list, 1)), is(toString(linkedList())));
    
    list = linkedList(1);
    assertThat(toString(pivot(list, 1)), is(toString(linkedList(1))));
    
    list = linkedList(1);
    assertThat(toString(pivot(list, 0)), is(toString(linkedList(1))));
    
    list = linkedList(1);
    assertThat(toString(pivot(list, 2)), is(toString(linkedList(1))));
    
    list = linkedList(7,5,0,0,4,2);
    assertThat(toString(pivot(list, 3)), is(toString(linkedList(0,0,2,7,5,4))));
    
    list = linkedList(7,5,0,0,4,2);
    assertThat(toString(pivot(list, 4)), is(toString(linkedList(0,0,2,4,7,5))));
    
    list = linkedList(7,5,0,0,4,2);
    assertThat(toString(pivot(list, 10)), is(toString(linkedList(7,5,0,0,4,2))));
    
    list = linkedList(7,5,0,0,4,2);
    assertThat(toString(pivot(list, -1)), is(toString(linkedList(7,5,0,0,4,2))));
    
    list = linkedList(1,2,3,4,5,6,7,8,9);
    assertThat(toString(pivot(list, 5)), is(toString(linkedList(1,2,3,4,5,6,7,8,9))));
    
    list = linkedList(9,8,7,6,5,4,3,2,1);
    assertThat(toString(pivot(list, 5)), is(toString(linkedList(4,3,2,1,5,9,8,7,6))));
  }
  
  // reverse second half of the linked list and compare the two halves
  static boolean isPalindrome(Node head){
    int len = len(head);
    if(len < 2) return true;
    int mid = (int)Math.ceil(len / 2.0);
    head = reverseList(head, mid + 1, len);
    Node firstHalf = head;
    Node secondHalf = head;
    for(int i = 0; i < mid; i++) secondHalf = secondHalf.next;
    while(secondHalf != null){
      if(firstHalf.data.compareTo(secondHalf.data) != 0) return false;
      secondHalf = secondHalf.next;
      firstHalf = firstHalf.next;
    }
    return true;
  }
  @Test
  public void testIsPalindrome(){
    assertTrue(isPalindrome(linkedList()));
    assertTrue(isPalindrome(linkedList(1)));
    assertTrue(isPalindrome(linkedList(1,1)));
    assertFalse(isPalindrome(linkedList(1,2)));
    assertFalse(isPalindrome(linkedList(1,2,3)));
    assertTrue(isPalindrome(linkedList(1,2,1)));
    assertTrue(isPalindrome(linkedList(1,2,2,1)));
    assertFalse(isPalindrome(linkedList(1,2,3,1)));
    assertTrue(isPalindrome(linkedList(1,2,3,3,2,1)));
    assertTrue(isPalindrome(linkedList(1,2,3,2,1)));
    assertFalse(isPalindrome(linkedList(1,4,3,3,2,1)));
    assertTrue(isPalindrome(linkedList('a','b','c','c','b','a')));
  }
  
  static Node evenOddMerge(Node head){
    if(head == null) return null;
    
    Node even = head; Node evenHead = even;
    Node odd = head.next; Node oddHead = head.next;
    
    // create even and odd lists
    while(even != null && odd != null){
      even.next = odd.next;
      even = even.next;
      if(even != null){
        odd.next = even.next;
        odd = odd.next;
      }
    }
    
    // find even list tail
    even = evenHead;
    while(even.next != null) even = even.next;
    
    // append odd list to even list
    even.next = oddHead;
    return head;
  }
  @Test
  public void testEvenOddMerge(){
    Node list = linkedList();
    assertThat(toString(evenOddMerge(list)), is(toString(linkedList())));
    
    list = linkedList(1);
    assertThat(toString(evenOddMerge(list)), is(toString(linkedList(1))));
    
    list = linkedList(1,2);
    assertThat(toString(evenOddMerge(list)), is(toString(linkedList(1,2))));
    
    list = linkedList(1,2,3);
    assertThat(toString(evenOddMerge(list)), is(toString(linkedList(1,3,2))));
    
    list = linkedList(1,2,3,4);
    assertThat(toString(evenOddMerge(list)), is(toString(linkedList(1,3,2,4))));
    
    list = linkedList(1,2,3,4,5);
    assertThat(toString(evenOddMerge(list)), is(toString(linkedList(1,3,5,2,4))));
    
    list = linkedList(1,2,3,4,5,6,7,8,9,10);
    assertThat(toString(evenOddMerge(list)), is(toString(linkedList(1,3,5,7,9,2,4,6,8,10))));
  }
  
  static Node rightCyclicShift(Node head, int k){
    int len = len(head);
    k %= len;
    Node cutNode = head;
    for(int i = 0; i < len - k - 1; i++) cutNode = cutNode.next;
    if(cutNode.next == null) return head;
    Node newHead = cutNode.next;
    Node tail = newHead;
    while(tail.next != null) tail = tail.next;
    tail.next = head;
    cutNode.next = null;
    return newHead;
  }
  @Test
  public void testRightCyclicShift(){
    Node list = linkedList(1);
    assertThat(toString(rightCyclicShift(list, 0)), is(toString(linkedList(1))));
    
    list = linkedList(1);
    assertThat(toString(rightCyclicShift(list, 5)), is(toString(linkedList(1))));
    
    list = linkedList(1,2);
    assertThat(toString(rightCyclicShift(list, 1)), is(toString(linkedList(2,1))));
    
    list = linkedList(1,2);
    assertThat(toString(rightCyclicShift(list, 2)), is(toString(linkedList(1,2))));
    
    list = linkedList(1,2,3,4,5);
    assertThat(toString(rightCyclicShift(list, 0)), is(toString(linkedList(1,2,3,4,5))));
    
    list = linkedList(1,2,3,4,5);
    assertThat(toString(rightCyclicShift(list, 1)), is(toString(linkedList(5,1,2,3,4))));
    
    list = linkedList(1,2,3,4,5);
    assertThat(toString(rightCyclicShift(list, 3)), is(toString(linkedList(3,4,5,1,2))));
    
    list = linkedList(1,2,3,4,5);
    assertThat(toString(rightCyclicShift(list, 4)), is(toString(linkedList(2,3,4,5,1))));
    
    list = linkedList(1,2,3,4,5);
    assertThat(toString(rightCyclicShift(list, 5)), is(toString(linkedList(1,2,3,4,5))));
  }
  
  static Node removeDuplicates(Node head){
    Node current = head; Node prev = head;
    while(prev != null){
      // skip all successors with the same value
      while(current != null && 
            current.next != null && 
            current.next.data.compareTo(current.data) == 0){
        current = current.next;
      }
      prev.next = current.next;
      prev = current.next;
      current = prev;
    }
    return head;
  }
  @Test
  public void testRemoveDuplicates(){
    Node list = linkedList();
    assertNull(removeDuplicates(list));
    
    list = linkedList(1);
    assertThat(toString(removeDuplicates(list)), is(toString(linkedList(1))));
    
    list = linkedList(1,1);
    assertThat(toString(removeDuplicates(list)), is(toString(linkedList(1))));
    
    list = linkedList(1,1,1,1);
    assertThat(toString(removeDuplicates(list)), is(toString(linkedList(1))));
    
    list = linkedList(1,2,3,4,5);
    assertThat(toString(removeDuplicates(list)), is(toString(linkedList(1,2,3,4,5))));
    
    list = linkedList(1,1,1,2,2,2,2,3,3,4,5,5);
    assertThat(toString(removeDuplicates(list)), is(toString(linkedList(1,2,3,4,5))));
  }
  
  // Use two iterators. Advance the second iterator by k-steps.
  // Then advance both iterators in tandem by one node. When the second
  // iterator reached the tail node, the first iterator will point at (k+1)-th last node.
  static Node deleteKthLastNode(Node head, int k){
    Node first = head; Node second = head;
    for(int i = 0; i < k; i++) second = second.next;
    while(second != null && second.next != null){
      first = first.next;
      second = second.next;
    }
    if(first == head) return head.next;
    first.next = first.next.next;
    return head;
  }
  @Test
  public void testDeleteKthLastNode(){
    Node list = linkedList(1);
    list = deleteKthLastNode(list, 1);
    assertNull(list);
    
    list = linkedList(1,2,3,4,5,6);
    list = deleteKthLastNode(list, 1);
    assertThat(toString(list), is(toString(linkedList(1,2,3,4,5))));
    
    list = linkedList(1,2,3,4,5,6);
    list = deleteKthLastNode(list, 2);
    assertThat(toString(list), is(toString(linkedList(1,2,3,4,6))));
    
    list = linkedList(1,2,3,4,5,6);
    list = deleteKthLastNode(list, 3);
    assertThat(toString(list), is(toString(linkedList(1,2,3,5,6))));
    
    list = linkedList(1,2,3,4,5,6);
    list = deleteKthLastNode(list, 6);
    assertThat(toString(list), is(toString(linkedList(2,3,4,5,6))));
  }
  
  // deletes a non-tail node in a linked list in O(1) time by copying successor's data
  // and deleting the successor
  static Node deleteNode(Node list, Node nodeToDelete){
    if(nodeToDelete == list) return list.next; // deleting head
    nodeToDelete.data = nodeToDelete.next.data;
    nodeToDelete.next = nodeToDelete.next.next;
    return list;
  }
  @Test
  public void testDeleteNode(){
    // delete the head
    Node list = linkedList(1);
    list = deleteNode(list, list);
    assertNull(list);
    assertThat(len(list), is(0));
    
    // delete the head
    list = linkedList(1,2,3,4);
    list = deleteNode(list, list);
    assertNotNull(list);
    assertThat(toString(list), is(toString(linkedList(2,3,4))));
    
    // delete a node in the middle - 3
    list = linkedList(1,2,3,4);
    list = deleteNode(list, list.next.next);
    assertNotNull(list);
    assertThat(toString(list), is(toString(linkedList(1,2,4))));
  }
  
  static Node findFirstOverlappingNode(Node list1, Node list2){
    int len1 = len(list1);
    int len2 = len(list2);
    Node longerList = len1 == Math.max(len1, len2) ? list1 : list2;
    Node shorterList = list1 == longerList ? list2 : list1;
    // advance longer list by diff in list lengths
    for(int i = 0; i < Math.abs(len1 - len2); i++) longerList = longerList.next;
    // advance both lists in tandem by one node until a common node is found
    while(longerList != null && shorterList != null){
      if(longerList == shorterList) return longerList;
      longerList = longerList.next;
      shorterList = shorterList.next;
    }
    return null;
  }
  @Test
  public void testFindFirstOverlappingNode(){
    // empty lists
    Node list1 = linkedList();
    Node list2 = linkedList();
    assertNull(findFirstOverlappingNode(list1, list2));
    
    // non-overlapping lists
    list1 = linkedList(1,2,3);
    list2 = linkedList(4,5,6);
    assertNull(findFirstOverlappingNode(list1, list2));
    
    // overlapping lists - same length of 6, overlapping node is 5
    list1 = linkedList(1,2);
    list2 = linkedList(3,4,5,6,7,8);
    list1.next.next = list2.next.next;
    assertNotNull(findFirstOverlappingNode(list1, list2));
    assertThat(findFirstOverlappingNode(list1, list2).data.compareTo(5), is(0));
    
    // overlapping lists - diff lengths, overlapping node is 4
    list1 = linkedList(1,2,3);
    list2 = linkedList(4,5,6);
    list1.next.next.next = list2;
    assertNotNull(findFirstOverlappingNode(list1, list2));
    assertThat(findFirstOverlappingNode(list1, list2).data.compareTo(4), is(0));
    
    // overlapping lists - diff lengths, overlapping node is 6
    list1 = linkedList(1);
    list2 = linkedList(2,3,4,5,6);
    list1.next = list2.next.next.next.next;
    assertNotNull(findFirstOverlappingNode(list1, list2));
    assertThat(findFirstOverlappingNode(list1, list2).data.compareTo(6), is(0));
  }
  static int len(Node head){
    int len = 0;
    Node current = head;
    while(current != null){
      len++;
      current = current.next;
    }
    return len;
  }
  
  static Node findCycle(Node head){
    Node fast = head; Node slow = head;
    while(fast != null && fast.next != null && fast.next.next != null){
      slow = slow.next;
      fast = fast.next.next;
      if(slow == fast){ // the two pointers meet - there is a cycle!
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
    
    list = linkedList(1,2,3,4); // cycle 1->2->3->4->2->3->4->...
    Node tail = list;
    while(tail.next != null) tail = tail.next;
    tail.next = list.next;
    assertNotNull(findCycle(list));
    assertThat(findCycle(list).data.compareTo(2), is(0));
    
    list = linkedList(1,2,3,4); // cycle 1->2->3->4->1->2->3->4...
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
