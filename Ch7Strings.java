import java.util.*;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import org.junit.Test;

public class Ch7Strings{
  static boolean isPalindrome(String s){
    int i = 0; int j = s.length() - 1;
    while(i < j){
      while(!isAlphanumeric(s.charAt(i)) && i < j) i++; // skip non-alphanumeric chars from left
      while(!isAlphanumeric(s.charAt(j)) && i < j) j--; // skip non-alphanumeric chars from right
      if(Character.toLowerCase(s.charAt(i)) != Character.toLowerCase(s.charAt(j))) return false;
      i++; j--;
    }
    return true;
  }
  static boolean isAlphanumeric(char c){
    return Character.isDigit(c) || Character.isLetter(c);
  }
  @Test
  public void testIsPalindrome(){
    assertTrue(isPalindrome("A man, a plan,  a canal, PanaMA!"));
    assertFalse(isPalindrome("canal"));
    assertFalse(isPalindrome("Ray a Ray"));
    assertFalse(isPalindrome("Some random sentence..."));
    assertTrue(isPalindrome("Able was I, ere I saw ELBA!!!"));
    assertTrue(isPalindrome(""));
    assertTrue(isPalindrome("a"));
    assertTrue(isPalindrome("a "));
    assertTrue(isPalindrome(", .. !@ \\||* *"));
  }
  
  static StringBuilder replaceRemove(StringBuilder s){
    // forward iteration: remove "b"s
    int insertAt = 0; int countA = 0;
    for(int i = 0; i < s.length(); i++){
      if(s.charAt(i) != 'b') s.setCharAt(insertAt++, s.charAt(i));
      if(s.charAt(i) == 'a') countA++;
    }
    
    // resize StringBuilder to fit the resulting string
    int len = insertAt + countA;
    if(len < s.length()){ // remove chars not in final string
      int from = insertAt; int to = s.length();
      s.delete(from, to);
    }
    else {
      int to = len - s.length();
      for(int i = 0; i < to; i++) s.append(' '); // append chars to fit the final string
    }
            
    // backward iteration: remove "a"s
    int nextChar = insertAt - 1; int writeAt = s.length() - 1;
    while(nextChar >= 0){
      if(s.charAt(nextChar) == 'a'){
        s.setCharAt(writeAt--, 'd');
        s.setCharAt(writeAt--, 'd');
      }else{
        s.setCharAt(writeAt--, s.charAt(nextChar));
      }
      nextChar--;
    }
    
    return s;
  }
  @Test
  public void testReplaceRemove(){
    assertThat(replaceRemove(new StringBuilder("bacaba")).toString(), is("ddcdddd")); 
    assertThat(replaceRemove(new StringBuilder("cddc")).toString(), is("cddc"));
    assertThat(replaceRemove(new StringBuilder("bcb")).toString(), is("c"));
    assertThat(replaceRemove(new StringBuilder("aca")).toString(), is("ddcdd"));
    assertThat(replaceRemove(new StringBuilder("a")).toString(), is("dd"));
    assertThat(replaceRemove(new StringBuilder("bbbbbb")).toString(), is(""));
    assertThat(replaceRemove(new StringBuilder("b")).toString(), is(""));
    assertThat(replaceRemove(new StringBuilder("c")).toString(), is("c"));
    assertThat(replaceRemove(new StringBuilder("ab")).toString(), is("dd"));
    assertThat(replaceRemove(new StringBuilder("abcdabcd")).toString(), is("ddcdddcd"));
  }
  
  static int columnId(String columnEncoding){
    int code = 0;
    for(char c : columnEncoding.toCharArray()){
      code = code * 26 + c - 'A' + 1;
    }
    return code;
  }
  @Test
  public void testColumnId(){
    assertThat(columnId("A"), is(1));
    assertThat(columnId("B"), is(2));
    assertThat(columnId("Z"), is(26));
    assertThat(columnId("AA"), is(27));
    assertThat(columnId("ZZ"), is(702));
    assertThat(columnId("AB"), is(28));
    assertThat(columnId("ZY"), is(701));
    assertThat(columnId("YZ"), is(676));
  }
  
  static String convert(String number, int base1, int base2){
    return toBase(toDecimal(number, base1), base2);
  }
  static int toDecimal(String s, int base){
    boolean isNeg = s.charAt(0) == '-';
    int n = 0;
    for(int i = isNeg ? 1 : 0; i < s.length(); i++){
      int digit = isDigit(s.charAt(i)) ? s.charAt(i) - '0' : s.charAt(i) - 'A' + 10;
      n = n * base + digit;
    }
    if(isNeg) n = -n;
    return n;
  }
  static String toBase(int n, int base){
    boolean isNeg = n < 0;
    if(isNeg) n = -n;
    String s = "";
    do{
      int digit = n % base;
      char c = digit > 9 ? (char)(digit - 10 + 'A') : (char)('0' + digit);
      s = c + s;
      n /= base;
    }while(n != 0);
    if(isNeg) s = "-" + s;
    return s;
  }
  static boolean isDigit(char c){
    return c >= '0' && c <= '9';
  }
  @Test
  public void testConvert(){
    assertThat(convert("0", 7, 13), is("0"));
    assertThat(convert("615", 7, 13), is("1A7"));
    assertThat(convert("1A7", 13, 7), is("615"));
    assertThat(convert("112", 10, 2), is("1110000"));
    assertThat(convert("1110000", 2, 10), is("112"));
    assertThat(convert("ABCDEF", 16, 10), is("11259375"));
    assertThat(convert("-ABCDEF", 16, 10), is("-11259375"));
    assertThat(convert("-1A7", 13, 7), is("-615"));
    assertThat(convert("ABCDEF", 16, 2), is("101010111100110111101111"));
  }
  
  static int stringToInt(String s){
    boolean isNeg = s.charAt(0) == '-';
    int n = 0;
    for(int i = isNeg ? 1 : 0; i < s.length(); i++){
      int digit = s.charAt(i) - '0';
      n = n * 10 + digit;
    }
    if(isNeg) n = -n;
    return n;
  }
  @Test
  public void testStringToInt(){
    assertThat(stringToInt("0"), is(0));
    assertThat(stringToInt("5"), is(5));
    assertThat(stringToInt("-5"), is(-5));
    assertThat(stringToInt("-20"), is(-20));
    assertThat(stringToInt("20"), is(20));
    assertThat(stringToInt("1000"), is(1000));
    assertThat(stringToInt("12345"), is(12345));
  }
  
  static String intToString(int n){
    boolean isNeg = n < 0;
    if(isNeg) n = -n;
    String s = "";
    do{
      int digit = n % 10;
      s = ((char)('0' + digit)) + s;
      n /= 10;
    }while(n != 0);
    if(isNeg) s = "-" + s;
    return s;
  }
  @Test
  public void testIntToString(){
    assertThat(intToString(0), is("0"));
    assertThat(intToString(5), is("5"));
    assertThat(intToString(-5), is("-5"));
    assertThat(intToString(-20), is("-20"));
    assertThat(intToString(20), is("20"));
    assertThat(intToString(1000), is("1000"));
    assertThat(intToString(12345), is("12345"));
  }
}
