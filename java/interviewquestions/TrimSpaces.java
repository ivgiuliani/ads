package interviewquestions;

/**
 * Given a string with multiple spaces write a function to in-place trim all spaces
 * leaving a single space between words
 * e.g.
 * ___Hello___World___
 * Hello_World________
 */
public class TrimSpaces extends TestCase {
  public static char[] trim(char[] original) {
    int writeTo = 0;
    boolean space = true; // set to true initially to skip leading spaces

    for (int current = 0; current < original.length; current++) {
      if (original[current] != ' ') {
        original[writeTo] = original[current];
        writeTo++;
        space = false;
      } else {
        if (!space) {
          original[writeTo] = original[current];
          writeTo++;
          space = true;
        }
      }
    }

    // if you want to return the string *without* the trailing spaces
    // just return the substring from 0 to `writeTo` (included).
    while (writeTo < original.length) {
      original[writeTo++] = ' ';
    }

    return original;
  }

  public static void main(String[] args) {
    assertEquals(     "Hello World".toCharArray(),
                 trim("Hello World".toCharArray()));
    assertEquals(     "Hello World        ".toCharArray(),
                 trim("   Hello   World   ".toCharArray()));
    assertEquals(     "            ".toCharArray(),
                 trim("            ".toCharArray()));
    assertEquals(     "Hello World   ".toCharArray(),
                 trim("Hello World   ".toCharArray()));
    assertEquals(     "Hello World   ".toCharArray(),
                 trim("   Hello World".toCharArray()));
    assertEquals(     "Hello World   ".toCharArray(),
                 trim("Hello    World".toCharArray()));
    assertEquals(     "A B C A B C A B C A B C             ".toCharArray(),
                 trim(" A B C    A B C    A B C    A B C   ".toCharArray()));
  }
}
