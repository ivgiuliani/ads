package interviewquestions;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

/**
 * Calculates the least number of dice rolls required to go from the first to
 * the last cell in the snakes and ladders game. Assume you only have one die.
 */
public class SnakesAndLadders extends TestCase {
  static class Pair {
    public final int idx;
    public final int rolls;
    public Pair(int idx, int rolls) {
      this.idx = idx;
      this.rolls = rolls;
    }
  }

  public static int min(final int[] board) {
    Set<Integer> visited = new HashSet<Integer>();
    Queue<Pair> q = new LinkedList<Pair>();
    final int lastCell = board.length - 1;
    int next;

    q.add(new Pair(0, 0));
    while (!q.isEmpty()) {
      Pair curr = q.poll();
      if (curr.idx == lastCell) {
        return curr.rolls;
      }

      // roll the die
      for (int die = 1; die <= 6 && curr.idx + die <= lastCell; die++) {
        next = board[curr.idx + die];
        if (next < curr.idx) {
          // a snake's head, which will take us back in the board so either we visited
          // the cell already or if we didn't we're better off not visiting it anyway
          // (we're further than that already)
          continue;
        }

        if (!visited.contains(next)) {
          q.add(new Pair(next, curr.rolls + 1));
          visited.add(next);
        }
      }
    }

    // this should never happen
    return -1;
  }

  public static void main(String[] args) {
    /*
     * A board is an array of integers where each cell holds the index we should
     * move to if we happen to be on that cell:
     *
     * - a cell which holds its own index number is a "normal" cell and no further
     *   movements happen
     * - a cell which holds a number greater than its own index is a ladder
     *   (the number is the index of the end of the ladder)
     * - a cell which holds a number smaller than its own index is a snake
     *   (the number is the index of the tail of the snake)
     *
     * For example, if the cell 25 contains the index 30 then if we throw
     * a die and end up on that cell, we'll fast-forward to the cell 30.
     *
     * For simplicity, our board index starts from 0
     */

    int[] board;

    // edge case, a board with only three items (one dice roll and we win)
    assertEquals(1, min(new int[] { 0, 1, 2 }));

    // no snakes and no ladders with a 6 cells board: only one die roll required (5)
    assertEquals(1, min(new int[] { 0, 1, 2, 3, 4, 5 }));

    // no snakes and no ladders with a 8 cells board: two dice rolls required (6+1, 5+2, 4+3, 2+4)
    // (the first cell doesn't count as a roll)
    assertEquals(2, min(new int[] { 0, 1, 2, 3, 4, 5, 6, 7 }));

    // one ladder, which makes the optimal number of dice rolls 3 (6+1+1)
    board = new int[] {  0,  1,  2,  3,  4,  5, 19,  7,  8,  9,
                        10, 11, 12, 13, 14, 15, 16, 17, 18, 19,
                        20, 29, 22, 23, 24, 25, 26, 27, 28, 29, 30};
    assertEquals(3, min(board));

    // one ladder and one snake, which makes the optimal number of dice rolls 3 (6+1+1)
    board = new int[] {  0,  1,  2,  3,  4,  5,  19,  7,  8,  9,
                         10, 11, 12, 13, 14, 15, 16, 17, 18, 19,
                         5,  29, 22, 23, 24, 25, 26, 27, 28, 29, 30};
    assertEquals(3, min(board));

    // a real board with 7 ladders and 6 snakes
    // For reference:
    // http://4.bp.blogspot.com/-cMt9obwH94E/UP8TB4WWbbI/AAAAAAAAA1g/qdLjDUIbE9k/s1600/Snakes_and_Ladders.jpg
    board = new int[] {  0,  1,  2,  3, 24,  5,  6,  7,  8, 28,
                        10, 11, 12, 13, 14, 15, 16, 17, 18, 19,
                        20, 40, 22, 23, 24, 25, 26, 54, 28, 29,
                        13, 31, 32, 33, 34, 35, 16, 37, 38, 39,
                        40, 41, 42, 94, 44, 45, 46, 47, 48, 49,
                        50, 51, 52, 53, 54, 55, 56, 57, 58, 59,
                        60, 61, 62, 63, 64, 65, 66, 67, 68, 88,
                        70, 71, 52, 73, 74, 75, 76, 38, 80, 79,
                        80, 81, 82, 83, 84, 85, 86, 87, 88, 89,
                        90, 34, 92, 93, 94, 95, 96, 97,  6, 99 };
    assertEquals(6, min(board));
  }
}
