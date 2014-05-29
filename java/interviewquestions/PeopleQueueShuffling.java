package interviewquestions;

import java.util.Arrays;
import java.util.Random;

/**
 * You have a queue of people. Each person in the queue knows only
 * his height and the number of taller persons ahead of him.
 *
 * At one point, the queue is shuffled. Rebuild the original queue.
 */
public class PeopleQueueShuffling extends TestCase {
  /*
   * If we don't allow duplicate heights, this solution gets simpler because
   * pretty much all the edge cases go away. Duplicates are hard to handle
   * correctly (within the timespan of a job interview at least) so don't
   * beat yourself too hard over it if you cannot solve it (with duplicates)
   * in a short time.
   */

  /**
   * A person instance. Implements {@link java.lang.Comparable} so that
   * we can easily sort persons by height.
   */
  static class Person implements Comparable<Person> {
    final int height;
    final int tallerPersons;

    public Person(int height, int tallerPersons) {
      this.height = height;
      this.tallerPersons = tallerPersons;
    }

    @Override
    public String toString() {
      return String.format("Person<%d:%d>", height, tallerPersons);
    }

    @Override
    public int compareTo(Person other) {
      // this is right, do not listen to intellij
      //noinspection SuspiciousNameCombination
      return Integer.compare(this.height, other.height);
    }
  }

  public static Person[] rebuildQueue(Person[] queue) {
    Person[] orig = new Person[queue.length];
    Person[] sorted = queue.clone();
    Arrays.sort(sorted);

    for (Person shorter : sorted) {
      int pos = queue.length - 1;
      int counted = 0;

      while (counted < shorter.tallerPersons || orig[pos] != null) {
        if (orig[pos] == null || orig[pos].height == shorter.height) {
          counted++;
        }
        pos--;
      }

      orig[pos] = shorter;
    }

    return orig;
  }

  /**
   * Helper method to build the original queue of persons, which includes
   * the number of taller people in front of them for each person in the queue.
   *
   * @param heights  an ordered list (as an array) of heights
   * @return the Person[] array
   */
  public static Person[] makeQueueFromHeights(int ... heights) {
    Person[] queue = new Person[heights.length];

    for (int i = 0; i < heights.length; i++) {
      int h = heights[i];
      int tallerPersons = 0;

      for (int j = i + 1; j < heights.length; j++) {
        // persons with the same height are still considered as "taller"
        // if in the front of us
        if (heights[j] >= h) {
          tallerPersons++;
        }
      }

      queue[i] = new Person(h, tallerPersons);
    }

    return queue;
  }

  /**
   * Creates a shuffled version of the original queue. This does NOT change the
   * original queue, but rather creates a new array.
   * @param queue  original queue
   * @return a shuffled copy of the given queue
   */
  public static Person[] shuffled(Person[] queue) {
    Random random = new Random();
    Person[] shuffled = queue.clone();
    int index;

    // fisher-yates shuffling
    for (int i = queue.length - 1; i > 0; i--) {
      index = random.nextInt(i + 1);
      if (index != i) {
        Person a = shuffled[index];
        shuffled[index] = shuffled[i];
        shuffled[i] = a;
      }
    }

    return shuffled;
  }

  public static void main(String[] args) {
    // our queue is not really a "queue" (in the computer science sense)
    // but rather a sorted array
    Person[] queue;

    queue = makeQueueFromHeights(160);
    assertEquals(queue, rebuildQueue(shuffled(queue)));

    queue = makeQueueFromHeights(160, 172, 150, 140, 154, 156, 164, 180, 155);
    assertEquals(queue, rebuildQueue(shuffled(queue)));

    queue = makeQueueFromHeights(170, 171, 172, 173, 174, 175, 176, 177, 178, 179, 180);
    assertEquals(queue, rebuildQueue(shuffled(queue)));

    queue = makeQueueFromHeights(180, 179, 178, 177, 176, 175, 174, 173, 172, 171, 170);
    assertEquals(queue, rebuildQueue(shuffled(queue)));

    queue = makeQueueFromHeights(170, 180, 171, 181, 172, 182, 173, 183, 174, 184, 175);
    assertEquals(queue, rebuildQueue(shuffled(queue)));

    queue = makeQueueFromHeights(175, 180, 174, 181, 173, 182, 172, 183, 171, 184, 170);
    assertEquals(queue, rebuildQueue(shuffled(queue)));

    queue = makeQueueFromHeights(150, 160, 170, 150, 160, 170);
    assertEquals(queue, rebuildQueue(shuffled(queue)));
  }
}
