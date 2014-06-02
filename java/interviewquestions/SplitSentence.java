package interviewquestions;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 * Given an input string and a dictionary of words, segment the input string
 * into a space-separated sequence of dictionary words if possible. For
 * example, if the input string is "applepie" and dictionary contains a
 * standard set of English words, then we would return the string "apple pie"
 * as output.
 *
 * If more than one split is possible (i.e.: "peanutbutter" can be split into
 * "peanut butter" and "pea nut butter"), return a list of all the possible
 * splits.
 */
public class SplitSentence extends TestCase {
  private static Set<String> dictionary = new HashSet<String>();
  static {
    dictionary.add("a");
    dictionary.add("i");
    dictionary.add("o");
    dictionary.add("pea");
    dictionary.add("nut");
    dictionary.add("butter");
    dictionary.add("peanut");
    dictionary.add("post");
    dictionary.add("man");
    dictionary.add("postman");
    dictionary.add("run");
    dictionary.add("runner");
    dictionary.add("am");
    dictionary.add("want");
    dictionary.add("doing");
    dictionary.add("well");
    dictionary.add("an");
    dictionary.add("iphone");
    dictionary.add("android");
    dictionary.add("phone");
    dictionary.add("big");
  }

  public static List<String> splitSentence(String sentence) {
    List<String> possibilities = new LinkedList<String>();
    String tmp;
    final int length = sentence.length();

    if (dictionary.contains(sentence)) {
      possibilities.add(sentence);
    }

    for (int i = 1; i < length; i++) {
      tmp = sentence.substring(0, i);

      if (dictionary.contains(tmp)) {
        for (String split : splitSentence(sentence.substring(i, length))) {
          possibilities.add(tmp + " " + split);
        }
      }
    }

    return possibilities;
  }

  public static void main(String[] args) {
    assertEquals(2, splitSentence("peanutbutter").size());
    assertTrue(splitSentence("peanutbutter").contains("pea nut butter"));
    assertTrue(splitSentence("peanutbutter").contains("peanut butter"));

    assertEquals(1, splitSentence("runner").size());
    assertTrue(splitSentence("runner").contains("runner"));

    assertEquals(2, splitSentence("postman").size());
    assertTrue(splitSentence("postman").contains("postman"));
    assertTrue(splitSentence("postman").contains("post man"));

    assertEquals(1, splitSentence("iamdoingwell").size());
    assertTrue(splitSentence("iamdoingwell").contains("i am doing well"));

    assertEquals(1, splitSentence("i").size());
    assertTrue(splitSentence("i").contains("i"));

    assertEquals(2, splitSentence("iphone").size());
    assertTrue(splitSentence("iphone").contains("iphone"));
    assertTrue(splitSentence("iphone").contains("i phone"));

    assertEquals(2, splitSentence("iwantaniphone").size());
    assertTrue(splitSentence("iwantaniphone").contains("i want an iphone"));
    assertTrue(splitSentence("iwantaniphone").contains("i want an i phone"));

    assertEquals(1, splitSentence("iwantanandroidphone").size());
    assertTrue(splitSentence("iwantanandroidphone").contains("i want an android phone"));

    assertEquals(1, splitSentence("bigo").size());
    // I know it's big *oh*, but I want to test what happens when the last item
    // is a letter (edge case)
    assertTrue(splitSentence("bigo").contains("big o"));
  }
}
