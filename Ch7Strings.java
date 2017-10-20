import java.util.*;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import org.junit.Test;

public class Ch7Strings{
  static Map<Character, Integer> romanToInt = new HashMap<>();
  static NavigableMap<Integer, String> intToRoman = new TreeMap<>();
  static{
    romanToInt.put('I', 1);
    romanToInt.put('V', 5);
    romanToInt.put('X', 10);
    romanToInt.put('L', 50);
    romanToInt.put('C', 100);
    romanToInt.put('D', 500);
    romanToInt.put('M', 1000);
    
    intToRoman.put(1, "I");
    intToRoman.put(4, "IV");
    intToRoman.put(5, "V");
    intToRoman.put(9, "IX");
    intToRoman.put(10, "X");
    intToRoman.put(40, "XL");
    intToRoman.put(50, "L");
    intToRoman.put(90, "XC");
    intToRoman.put(100, "C");
    intToRoman.put(400, "CD");
    intToRoman.put(500, "D");
    intToRoman.put(900, "CM");
    intToRoman.put(1000, "M");
  }
  static String integerToRoman(int n){
    int floor = intToRoman.floorKey(n);
    if(floor == n) return intToRoman.get(n);
    return intToRoman.get(floor) + integerToRoman(n - floor);
  }
  static int romanToInteger(String roman){
    char[] c = roman.toCharArray();
    int s = romanToInt.get(c[c.length - 1]);
    for(int i = c.length - 2; i >= 0; i--){
      if(romanToInt.get(c[i]) < romanToInt.get(c[i + 1])) s -= romanToInt.get(c[i]);
      else s += romanToInt.get(c[i]);
    }
    return s;
  }
  @Test
  public void testIntToRomanAndBack(){
    for(int i = 1; i <= 5000; i++){
      assertThat(romanToInteger(integerToRoman(i)), is(i));
    }
  }
  @Test
  public void testRomanToInteger(){
    assertThat(romanToInteger("I"), is(1));
    assertThat(romanToInteger("LIX"), is(59));
    assertThat(romanToInteger("L"), is(50));
    assertThat(romanToInteger("II"), is(2));
    assertThat(romanToInteger("IV"), is(4));
    assertThat(romanToInteger("VI"), is(6));
    assertThat(romanToInteger("VII"), is(7));
    assertThat(romanToInteger("VIII"), is(8));
    assertThat(romanToInteger("IX"), is(9));
    assertThat(romanToInteger("XI"), is(11));
    assertThat(romanToInteger("MCM"), is(1900));
    assertThat(romanToInteger("LXXXIX"), is(89));
    assertThat(romanToInteger("DCCCXC"), is(890));
    assertThat(romanToInteger("MDCCC"), is(1800));
    assertThat(romanToInteger("XCIV"), is(94));
    assertThat(romanToInteger("XLIV"), is(44));
  }
  
  static String getSequence(int n){
    String s = "1";
    while(n-- > 1) s = nextSequence(s);
    return s;
  }
  static String nextSequence(String s){
    String next = "";
    int count = 1; char prev = s.charAt(0);
    for(int i = 1; i < s.length(); i++){
      char current = s.charAt(i);
      if(current == prev) count++;
      else{
        next += count + "" + prev;
        count = 1;
      }
      prev = current;
    }
    next += count + "" + prev;
    return next;
  }
  @Test
  public void testGetSequence(){
    assertThat(getSequence(1), is("1"));
    assertThat(getSequence(2), is("11"));
    assertThat(getSequence(3), is("21"));
    assertThat(getSequence(4), is("1211"));
    assertThat(getSequence(5), is("111221"));
    assertThat(getSequence(6), is("312211"));
    assertThat(getSequence(7), is("13112221"));
    assertThat(getSequence(8), is("1113213211"));
  }
  
  static final String[] keypad = new String[]{
    "0", "1", "ABC", "DEF", "GHI", "JKL", "MNO", "PQRS", "TUV", "WXYZ"
  };
  static void computeMnemonics(String phoneNum, int i, String mnemonics, List<String> mnemonicsList){
    if(i == phoneNum.length()) mnemonicsList.add(mnemonics);
    else{
      int digit = phoneNum.charAt(i) - '0';
      for(char letter : keypad[digit].toCharArray()){
        computeMnemonics(phoneNum, i + 1, mnemonics + letter, mnemonicsList);
      }
    }
  }
  static List<String> computeMnemonics(String phoneNum){
    List<String> mnemonicsList = new ArrayList<>();
    computeMnemonics(phoneNum, 0, "", mnemonicsList);
    return mnemonicsList;
  }
  @Test
  public void testComputeMnemonics(){
    System.out.println(computeMnemonics("123"));
    System.out.println(computeMnemonics("790"));
    System.out.println(computeMnemonics("550052"));
  }
  
  static String reverseWords(String s){
    char[] str = s.toCharArray();
    reverse(str, 0, str.length - 1);
    int i = 0; int j = 0;
    while(true){
      while(j < s.length() && str[j] != ' ') j++;
      reverse(str, i, j - 1);
      if(j == str.length) break;
      j++;
      i = j;
    }
    return new String(str);
  }
  static void reverse(char[] s, int from, int to){
    while(from < to) swapChars(s, from++, to--);
  }
  static void swapChars(char[] s, int i, int j){
    char temp = s[i];
    s[i] = s[j];
    s[j] = temp;
  }
  @Test
  public void testReverseWords(){
    assertThat(reverseWords("john"), is("john"));
    assertThat(reverseWords("a b c d"), is("d c b a"));
    assertThat(reverseWords(""), is(""));
    assertThat(reverseWords("I want ice cream"), is("cream ice want I"));
    assertThat(reverseWords("Ram Turbine Design"), is("Design Turbine Ram"));
    assertThat(reverseWords("   "), is("   "));
    assertThat(reverseWords("51 12 52"), is("52 12 51"));
  }
  
  static boolean isPalindrome(String s){
    int i = 0; int j = s.length() - 1;
    while(i < j){
      while(!isAlphanumeric(s.charAt(i)) && i < j) i++; // skip non-alphanumeric chars from left
      while(!isAlphanumeric(s.charAt(j)) && i < j) j--; // skip non-alphanumeric chars from right
      if(toLower(s.charAt(i)) != toLower(s.charAt(j))) return false;
      i++; j--;
    }
    return true;
  }
  static char toLower(char c){
    return Character.toLowerCase(c);
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
