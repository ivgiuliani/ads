package interviewquestions.java;

/**
 * Percy has just received a new game called Penguin Tiles. The game is played
 * on a rectangular grid. Except for one square, each square of the grid contains
 * a tile with a part of an image of a penguin.
 * The one remaining square is empty, and it is called the open square.
 * The player is allowed to slide one of the tiles adjacent to the open square
 * onto the open square. After several moves the tile game is supposed to form a
 * picture with the bottom right corner containing the open square.
 * Percy's version of Penguin Tiles is a misprint. Instead of each tile containing
 * a different part of a penguin all tiles contain an image of the same penguin.
 * In other words each pair of tiles in Percy's Penguin Tiles is indistinguishable.
 * Percy has decided to play with the game anyway but instead of moving just one
 * tile at a time he has decided to move several tiles at once. In one move, Percy
 * can either move some consecutive vertical tiles one square vertically, or
 * some consecutive horizontal tiles one square horizontally. Of course, one of the
 * tiles has to be moved onto the open square. (In other words, instead of moving
 * several tiles one at a time, Percy may move them all at once, if they all lie
 * in the same row or in the same column.)
 * You are given a String[] tiles representing the game. The j-th character of the
 * i-th element of tiles is 'P' if the square at row i, column j contains a tile,
 * and it is '.' (a period) for the open square.
 * Return the minimum number of moves to complete Percy's game.
 *
 * SRM566/DIV2
 */
public class PenguinTiles {
  public int minMoves(String[] tiles) {
    /* this is kind of easy to solve once you notice that every game
     * can be solved with at most two moves: push the dot to the rightmost
     * column (if not on the rightmost column already) and push it to the
     * bottommost row (if not on the bottommost row already), which
     * essentialy comes down to finding the dot in the matrix.
     */
    int moves = 0;
    int emptyRow = 0;
    int emptyColumn = 0;

    boolean found = false;
    for (int row = 0; row < tiles.length && !found; row++) {
      for (int column = 0; column < tiles[0].length(); column++) {
        if (tiles[row].charAt(column) == '.') {
          emptyRow = row;
          emptyColumn = column;
          found = true;
          break;
        }
      }
    }

    if (emptyRow != (tiles.length - 1)) moves++;
    if (emptyColumn != (tiles[0].length() - 1)) moves++;

    return moves;
  }

  public static void main(String[] args) {
    PenguinTiles t = new PenguinTiles();

    test(t.minMoves(new String[] {
        "PPPPPPPP",
        ".PPPPPPP"}) == 1);
    test(t.minMoves(new String[] {
        "PPP",
        "P.P",
        "PPP"}) == 2);
    test(t.minMoves(new String[] {
        "P.",
        "PP",
        "PP",
        "PP"}) == 1);
    test(t.minMoves(new String[] {
        ".PPP",
        "PPPP",
        "PPPP",
        "PPPP"}) == 2);
  }

  public static void test(boolean condition) {
    // assertions are disabled by default in java, mimic their behaviour here
    if (!condition) {
      throw new AssertionError("invalid test");
    }
  }
}
