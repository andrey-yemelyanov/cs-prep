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
  
  static int parity(int x){
    int result = 0;
    while(x > 0){
      result ^= 1;
      x &= (x - 1); // drop the lowest set bit of x
    }
    return result;
  }
  
  static int[] precomputeParity(int wordLength){
    int[] cache = new int[(int)Math.pow(2, wordLength)];
    for(int word = 0; word < cache.length; word++){
      cache[word] = parity(word);
    }
    return cache;
  }
  
  static int parity(int x, int[] cache){
    /*
      The parity of a binary word is 1 if the number of 1s in the word is odd, otherwise it is 0.
      
      Here we compute the parity of a 32-bit word.
      Break the 32-bit word into 4 non-overlapping 8-bit words. Pre-compute and cache the parity of 
      all 8-bit words - 2^8=256 values. For each 32 bit word, break it into 4 8-bit words and get 
      each 8-bit word's parity from the cache (by indexing into the cache using the 8-bit word). 
      Then XOR the 4 results. E.g. 1, 0, 1, 1 gives parity 1; 0, 0, 1, 1 gives parity 0.
      Time complexity of determining parity of a 32-bit word is O(1).
      Time complexity of pre-computing the cache: 256 8-bit words * O(n) = O(n).
    */
    
    final int WORD_LEN = 8;
    final int BIT_MASK = 0xFF;
    // Note the logical shift right >>>! This is needed so that the sign bit is not propagated.
    return cache[x >>> (3 * WORD_LEN)] ^
           cache[(x >>> (2 * WORD_LEN)) & BIT_MASK] ^
           cache[(x >>> (WORD_LEN)) & BIT_MASK] ^
           cache[x & BIT_MASK];
  }
  
  static int swapBits(int x, int i, int j){
    /*
      If bits at pos i and j are the same, then do nothing - return the same int.
      If the bits differ, flip their values.
    */
    int iBit = (x >> i) & 1;
    int jBit = (x >> j) & 1;
    if(iBit == jBit) return x;
    return x ^ ((1 << i) | (1 << j));
  }
  
  @Test
  public void testSwapBits(){
    assertThat(swapBits(0b00101101, 0, 2), is(0b00101101));
    assertThat(swapBits(0b00101101, 0, 1), is(0b00101110));
    assertThat(swapBits(0b00101101, 0, 7), is(0b10101100));
  }
  
  @Test
  public void testParity(){
    int[] cache = precomputeParity(8);
    assertThat(parity(0, cache), is(0));
    assertThat(parity(1, cache), is(1));
    assertThat(parity(0b01011111, cache), is(0));
    assertThat(parity(0b11011111, cache), is(1));
    assertThat(parity(0b1101111111011111, cache), is(0));
    assertThat(parity(0b110111111101111111011111, cache), is(1));
    assertThat(parity(0b11011111110111111101111111011111, cache), is(0));
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
