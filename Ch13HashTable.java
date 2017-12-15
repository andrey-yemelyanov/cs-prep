import java.util.*;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import org.junit.Test;

public class Ch13HashTable{
  class IsbnCache{
    class Node{
      public int isbn;
      public int price;
      public Node prev;
      public Node next;
      public Node(int isbn, int price){this.isbn = isbn; this.price = price;}
    }
    public static final int NOT_FOUND = -1;
    private int capacity = 100;
    private Node listHead; private Node listTail;
    private Map<Integer, Node> map = new HashMap<>();
    public IsbnCache(){}
    public IsbnCache(int capacity){this.capacity = capacity;}
    public int get(int isbn){
      if(!map.containsKey(isbn)) return NOT_FOUND;
      Node node = map.get(isbn);
      moveToFront(node);
      return node.price;
    }
    public void add(int isbn, int price){
      if(map.containsKey(isbn)){
        Node node = map.get(isbn);
        moveToFront(node);
        node.price = price;
        return;
      }
      if(map.size() == capacity){
        map.remove(listTail.isbn);
        eraseNode(listTail);
      }
      Node node = new Node(isbn, price);
      map.put(isbn, node);
      addToFront(node);
    }
    public boolean erase(int isbn){
      if(!map.containsKey(isbn)) return false;
      Node node = map.get(isbn);
      map.remove(isbn);
      eraseNode(node);
      return true;
    }
    private void moveToFront(Node node){
      if(node != listHead){
        eraseNode(node);
        addToFront(node);
      }
    }
    private void eraseNode(Node node){
      if(node == listHead) {
        if(listHead.next != null){
          listHead.next.prev = null;
        }
        listHead = listHead.next;
      }else{
        node.prev.next = node.next;
        if(node.next != null){
          node.next.prev = node.prev;
        }
      }
      if(node == listTail) listTail = node.prev;
    }
    private void addToFront(Node node){
      if(listHead == null){
        listHead = node;
        listTail = node;
      }else{
        node.next = listHead;
        listHead.prev = node;
        listHead = node;
      }
    }
  }
  @Test
  public void testIsbnCache(){
    IsbnCache cache = new IsbnCache(2);
    int isbn1 = 111; int isbn2 = 222; int isbn3 = 333;
    int price1= 200; int price2= 300; int price3= 400;
    assertThat(cache.get(isbn1), is(IsbnCache.NOT_FOUND));
    cache.add(isbn1, price1);
    assertThat(cache.get(isbn1), is(price1));
    cache.add(isbn2, price2);
    assertThat(cache.get(isbn2), is(price2));
    cache.add(isbn3, price3);
    assertThat(cache.get(isbn3), is(price3));
    assertThat(cache.get(isbn1), is(IsbnCache.NOT_FOUND));
    cache.erase(isbn2);
    assertThat(cache.get(isbn2), is(IsbnCache.NOT_FOUND));
    cache.erase(isbn3);
    assertThat(cache.get(isbn3), is(IsbnCache.NOT_FOUND));
    cache.add(isbn1, price1);
    assertThat(cache.get(isbn1), is(price1));
    cache.add(isbn2, price2);
    assertThat(cache.get(isbn2), is(price2));
  }
}
