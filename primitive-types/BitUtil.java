import java.util.*;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import org.junit.Test;

public class BitUtil{
  static boolean isPowerOfTwo(int x){
    // find the rightmost set bit in x, the value must be equal to x
    if(x == 0) return false;
    return x == (x & ~(x - 1));
  }
  
  static int modPowOfTwo(int x, int powOfTwo){
    return x & (powOfTwo - 1);
  }
  
  static int propagateRightmostSetBit(int x){
    int rightmostBitSet = x & (~(x - 1));
    if(rightmostBitSet == 0) return x;
    return x | (rightmostBitSet - 1);
  }
  
  @Test
  public void testPropagateRightmostSetBit(){
    assertThat(propagateRightmostSetBit(0), is(0));
    assertThat(propagateRightmostSetBit(1), is(1));
    assertThat(propagateRightmostSetBit(2), is(3));
    assertThat(propagateRightmostSetBit(4), is(7));
    assertThat(propagateRightmostSetBit(0b01010000), is(0b01011111));
    assertThat(propagateRightmostSetBit(0b11110001), is(0b11110001));
  }
  
  @Test
  public void testModPowOfTwo(){
    assertThat(modPowOfTwo(77, 64), is(13));
    assertThat(modPowOfTwo(64, 64), is(0));
    assertThat(modPowOfTwo(63, 64), is(63));
    assertThat(modPowOfTwo(1, 64), is(1));
    assertThat(modPowOfTwo(0, 64), is(0));
    assertThat(modPowOfTwo(2, 1), is(0));
    assertThat(modPowOfTwo(45, 1), is(0));
    assertThat(modPowOfTwo(2, 2), is(0));
    assertThat(modPowOfTwo(4, 2), is(0));
    assertThat(modPowOfTwo(5, 2), is(1));
  }
  
  @Test
  public void testIsPowerOfTwo(){
    assertThat(isPowerOfTwo(1), is(true));
    assertThat(isPowerOfTwo(2), is(true));
    assertThat(isPowerOfTwo(4), is(true));
    assertThat(isPowerOfTwo(8), is(true));
    assertThat(isPowerOfTwo(16), is(true));
    assertThat(isPowerOfTwo(32), is(true));
    assertThat(isPowerOfTwo(64), is(true));
    assertThat(isPowerOfTwo(0), is(false));
    assertThat(isPowerOfTwo(9), is(false));
    assertThat(isPowerOfTwo(6), is(false));
    assertThat(isPowerOfTwo(11), is(false));
  }
}
