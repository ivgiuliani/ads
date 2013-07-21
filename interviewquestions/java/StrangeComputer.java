package interviewquestions.java;

/**
 * A strange computer has its memory consisting of several bits, each initially
 * set to 0, and it can only perform the following operation:
 *   choose one of the bits in memory, and choose a value - 0 or 1, all the bits
 *   between the selected bit and the last bit in memory, inclusive, will be set
 *   to the chosen value.
 *
 * For example, if the memory is "0010", and you choose the second bit and a value
 * of 1, the memory will change to "0111".
 * You are given a String mem. The number of characters in mem is equal to the
 * number of bits in the computer's memory.
 * Return the minimum number of operations required to set the computer's memory
 * equal to mem.
 *
 * Solve this question using bitmasks.
 */
public class StrangeComputer {
  public int setMemory(String mem) {
    long memory = 0;
    int changes = 0;
    int bit, currBit, bitPos;
    int length = mem.length();
    long mask;

    for (int i = 0; i < length; i++) {
      bit = mem.charAt(i) == '1' ? 1 : 0;
      bitPos = length - i - 1;
      currBit = ((memory & (1 << bitPos)) == 0) ? 0 : 1;
      mask = (1 << bitPos) - 1;

      if (bit == 0 && currBit != 0) {
        changes++;
        mask = ~mask;
        memory &= mask;
      }
      else if (bit == 1 && currBit != 1) {
        changes++;
        memory |= mask;
      }
    }

    return changes;
  }

  /* Alternative implementation that doesn't use bitmasks */
  public int setMemory2(String mem) {
    char[] array = mem.toCharArray();
    int changes = array[0] == '1' ? 1 : 0;
    for (int i = 1; i < array.length; i++) {
      if (array[i] != array[i - 1]) changes++;
    }
    return changes;
  }

  public static void main(String[] args) {
    StrangeComputer cmp = new StrangeComputer();
    test(cmp.setMemory("0011") == 1);
    test(cmp.setMemory("000") == 0);
    test(cmp.setMemory("0100") == 2);
    test(cmp.setMemory("111000111") == 3);

    test(cmp.setMemory2("0011") == 1);
    test(cmp.setMemory2("000") == 0);
    test(cmp.setMemory2("0100") == 2);
    test(cmp.setMemory2("111000111") == 3);
  }

  public static void test(boolean condition) {
    // assertions are disabled by default in java, mimic their behaviour here
    if (!condition) {
      throw new AssertionError("invalid test");
    }
  }
}
