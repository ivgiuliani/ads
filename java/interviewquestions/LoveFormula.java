package interviewquestions;

import java.util.ArrayList;
import java.util.Collections;

/**
 * A girl would like to go out with one of her favorite boys, but she does
 * not know which one to choose. Fortunately, she has a Love Calculator
 * which can calculate the probability of love between two people. Love Calculator
 * takes two people's names and uses the following algorithm to calculate the
 * probability of love between them:
 *   L = the total number of occurrences of the letter 'L' in both names
 *   O = the total number of occurrences of the letter 'O' in both names
 *   V = the total number of occurrences of the letter 'V' in both names
 *   E = the total number of occurrences of the letter 'E' in both names
 *
 * The percent probability of love is equal to
 * ((L+O)*(L+V)*(L+E)*(O+V)*(O+E)*(V+E))%100, where % is the modulo operator.
 * You are given a String girl containing the girl's name, and a String[]
 * boys containing her favorite boys' names.
 * Return the name of the boy with which the girl has the highest probability
 * of love. If there is more than one such boy, return the one among them
 * that comes earliest alphabetically.
 *
 * Assume all the names are uppercase and that at least one name (for the
 * boys array) will be given.
 */
public class LoveFormula extends TestCase {
  public String findBoy(String girl, String[] boys) {
    int[] girlchars = extract(girl);

    int max = 0;
    int love;
    ArrayList<String> names = new ArrayList<String>();
    for (String boy : boys) {
      love = formula(girlchars, extract(boy));

      // we could store only the last name if it comes before in alphabetic
      // ordering (yielding a O(1) space complexity), but this implementation
      // is cleaner
      if (love > max) {
        names.clear();
        names.add(boy);
        max = love;
      } else if (love == max) {
        names.add(boy);
      }
    }

    Collections.sort(names);
    return names.get(0);
  }

  private int formula(int[] girl, int[] boy) {
    int L = girl['L' - 'A'] + boy['L' - 'A'];
    int O = girl['O' - 'A'] + boy['O' - 'A'];
    int V = girl['V' - 'A'] + boy['V' - 'A'];
    int E = girl['E' - 'A'] + boy['E' - 'A'];
    return ((L+O)*(L+V)*(L+E)*(O+V)*(O+E)*(V+E)) % 100;
  }

  private int[] extract(String string) {
    int[] chars = new int[26];
    for (int i = 0; i < string.length(); i++)
      chars[string.charAt(i) - 'A'] += 1;
    return chars;
  }

  public static void main(String[] args) {
    LoveFormula love = new LoveFormula();
    assertEquals("FRANK", love.findBoy("LOVE", new String[] {"JACOB", "FRANK", "DANO"}));
    assertEquals("INDY", love.findBoy("JANE", new String[] {"THOMAS", "MICHAEL", "INDY", "LIU"}));
    assertEquals("PIERRE", love.findBoy("LILLY", new String[] {"PIERRE"}));
    assertEquals("DAVE", love.findBoy("MERYLOV", new String[] {"JOHN", "DAVE", "STEVE", "JOHN", "DAVE"}));
    assertEquals("AVERON", love.findBoy("LLOL", new String[] {"BVERON", "CVERON", "AVERON", "DVERON"}));
    assertEquals("VLOLUVCBLLQVESWHEEKC", love.findBoy("VELYLEOCEVE", new String[] {
        "YVXHOVE", "LCOKO", "OGWSJVEVEDLE", "WGFVSJEL", "VLOLUVCBLLQVESWHEEKC"
    }));
  }
}
