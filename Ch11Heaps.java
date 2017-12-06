import java.util.*;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import org.junit.Test;

public class Ch11Heaps{
  List<Integer> sortIncreasingDecreasingArray(int[] arr){
    List<List<Integer>> lists = new ArrayList<>();
    List<Integer> list = new ArrayList<>();
    if(arr.length == 0) return list;
    list.add(arr[0]);
    boolean increasing = true;
    for(int i = 1; i < arr.length; i++){
      if(increasing){
        if(arr[i] > arr[i - 1]) list.add(arr[i]);
        else{
          increasing = false;
          lists.add(list);
          list = new ArrayList<>();
          list.add(arr[i]);
        }
      }else{
        if(arr[i] < arr[i - 1]) list.add(arr[i]);
        else{
          increasing = true;
          Collections.reverse(list);
          lists.add(list);
          list = new ArrayList<>();
          list.add(arr[i]);
        }
      }
    }
    if(!increasing) Collections.reverse(list);
    lists.add(list);
    return mergeLists(lists.toArray(new List[0]));
  }
  @Test
  public void testSortKIncreasingDecreasingArray(){
    assertThat(
      sortIncreasingDecreasingArray(new int[]{57,131,493,294,221,339,418,452,442,190}),
      is(Arrays.asList(57,131,190,221,294,339,418,442,452,493))
    );
  }
  
  class Element{
    public int value;
    public int list;
    public int locationInList;
    public Element(int value, int list, int locationInList){
      this.value = value;
      this.list = list;
      this.locationInList = locationInList;
    }
  }
  List<Integer> mergeLists(List<Integer>... lists){
    Queue<Element> pq = new PriorityQueue<>(new Comparator<Element>(){
      @Override
      public int compare(Element e1, Element e2){
        return Integer.compare(e1.value, e2.value);
      }
    });
    for(int i = 0; i < lists.length; i++){
      if(lists[i].size() > 0) pq.add(new Element(lists[i].get(0), i, 0));
    }
    List<Integer> mergedList = new ArrayList<>();
    while(!pq.isEmpty()){
      Element e = pq.poll();
      mergedList.add(e.value);
      int locationInList = e.locationInList + 1;
      if(locationInList < lists[e.list].size()){
        pq.add(new Element(lists[e.list].get(locationInList), e.list, locationInList));
      }
    }
    return mergedList;
  }
  
  @Test
  public void testMergeLists(){
    assertThat(mergeLists(Arrays.asList(), Arrays.asList()), is(Arrays.asList()));
    assertThat(mergeLists(Arrays.asList(1,2,3), Arrays.asList(4,5,6)), is(Arrays.asList(1,2,3,4,5,6)));
    assertThat(mergeLists(Arrays.asList(1,3,5), Arrays.asList(2,4,6)), is(Arrays.asList(1,2,3,4,5,6)));
    assertThat(mergeLists(
      Arrays.asList(1,3,5), 
      Arrays.asList(2,4,6), 
      Arrays.asList(1,2,3)), 
      is(Arrays.asList(1,1,2,2,3,3,4,5,6)));
    assertThat(mergeLists(
      Arrays.asList(1,2,3), 
      Arrays.asList(4,5,6), 
      Arrays.asList(7,8,9,10)), 
      is(Arrays.asList(1,2,3,4,5,6,7,8,9,10)));
    assertThat(mergeLists(
      Arrays.asList(1,4,7), 
      Arrays.asList(2,5,8), 
      Arrays.asList(3,6,9,10)), 
      is(Arrays.asList(1,2,3,4,5,6,7,8,9,10)));
  }
}
