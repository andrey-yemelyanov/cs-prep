import java.util.*;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import org.junit.Test;

public class Ch7Strings{
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
