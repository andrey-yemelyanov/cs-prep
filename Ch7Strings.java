import java.util.*;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import org.junit.Test;

public class Ch7Strings{
  static int stringToInt(String s){
    boolean isNeg = s.charAt(0) == '-';
    int n = 0;
    for(int i = isNeg ? 1 : 0; i < s.length(); i++){
      int digit = (int)(s.charAt(i) - '0');
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
