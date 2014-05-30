package graphs;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import interviewquestions.TestCase;

/**
 * Implementation of directed and weighted graph based on a "matrix" (array of array)
 */
public class MatrixGraph<T> extends TestCase {
  private Map<T, Integer> nodesToId = new HashMap<T, Integer>();
  private Map<Integer, T> idToNodes = new HashMap<Integer, T>();
  private int[][] graph;

  public MatrixGraph(int nodesCount) {
    graph = new int[nodesCount][nodesCount];
  }

  public void link(T n1, T n2, int weight) {
    int i1 = getId(n1);
    int i2 = getId(n2);

    graph[i1][i2] = weight;
  }

  public void unlink(T n1, T n2) {
    int i1 = getId(n1);
    int i2 = getId(n2);

    graph[i1][i2] = 0;
  }

  public int weight(T n1, T n2) {
    int i1 = getId(n1);
    int i2 = getId(n2);

    return graph[i1][i2];
  }

  public boolean isLinked(T n1, T n2) {
    return weight(n1, n2) > 0;
  }

  public Set<T> neighbours(T node) {
    int id = getId(node);
    int[] neighbours = graph[id];
    Set<T> list = new HashSet<T>();

    for (int nid = 0; nid < neighbours.length; nid++) {
      if (neighbours[nid] > 0) {
        list.add(idToNodes.get(nid));
      }
    }

    return list;
  }

  private int getId(T node) {
    if (!nodesToId.containsKey(node)) {
      if (nodesToId.size() > graph.length) {
        throw new ArrayIndexOutOfBoundsException(
            "number of nodes in the graph exceede maximum graph size"
        );
      }

      nodesToId.put(node, nodesToId.size());
    }

    int id = nodesToId.get(node);
    idToNodes.put(id, node);
    return id;
  }

  /**************************************
   * Specializations of the graph class *
   **************************************/

  public static class UndirectedMatrixGraph<T> extends MatrixGraph<T> {
    public UndirectedMatrixGraph(int nodesCount) {
      super(nodesCount);
    }

    @Override
    public void link(T n1, T n2, int weight) {
      super.link(n1, n2, weight);
      super.link(n2, n1, weight);
    }

    @Override
    public void unlink(T n1, T n2) {
      super.unlink(n1, n2);
      super.unlink(n2, n1);
    }
  }

  public static class UnweightedMatrixGraph<T> extends MatrixGraph<T> {
    public UnweightedMatrixGraph(int nodesCount) {
      super(nodesCount);
    }

    public void link(T n1, T n2) {
      super.link(n1, n2, 1);
      super.link(n2, n1, 1);
    }
  }

  public static class UndirectedUnweightedMatrixGraph<T> extends UndirectedMatrixGraph<T> {
    public UndirectedUnweightedMatrixGraph(int nodesCount) {
      super(nodesCount);
    }

    public void link(T n1, T n2) {
      super.link(n1, n2, 1);
      super.link(n2, n1, 1);
    }
  }


  public static void main(String[] args) {
    MatrixGraph<String> graph = new MatrixGraph<String>(10);

    //  node1 -> node2 -> node5 -> node7
    //        -> node3 -> node6
    //        -> node4

    graph.link("node1", "node2", 1);
    graph.link("node1", "node3", 2);
    graph.link("node1", "node4", 3);
    graph.link("node2", "node5", 4);
    graph.link("node3", "node6", 3);
    graph.link("node5", "node7", 2);

    assertTrue(graph.isLinked("node1", "node2"));
    assertTrue(graph.isLinked("node1", "node3"));
    assertTrue(graph.isLinked("node1", "node4"));
    assertTrue(graph.isLinked("node2", "node5"));
    assertTrue(graph.isLinked("node3", "node6"));
    assertTrue(graph.isLinked("node5", "node7"));
    assertFalse(graph.isLinked("node2", "node1"));
    assertFalse(graph.isLinked("node1", "node7"));
    assertFalse(graph.isLinked("node1", "node6"));
    assertFalse(graph.isLinked("node2", "node7"));
    assertFalse(graph.isLinked("node2", "node6"));
    assertFalse(graph.isLinked("node4", "node5"));

    assertEquals(1, graph.weight("node1", "node2"));
    assertEquals(2, graph.weight("node1", "node3"));
    assertEquals(3, graph.weight("node1", "node4"));
    assertEquals(4, graph.weight("node2", "node5"));
    assertEquals(3, graph.weight("node3", "node6"));
    assertEquals(2, graph.weight("node5", "node7"));
    assertEquals(0, graph.weight("node1", "node7"));

    assertEquals(new HashSet<String>(Arrays.asList(
        "node2", "node3", "node4"
    )), graph.neighbours("node1"));


    UndirectedMatrixGraph<String> undGraph = new UndirectedMatrixGraph<String>(10);
    undGraph.link("node1", "node2", 1);
    undGraph.link("node1", "node3", 2);
    undGraph.link("node1", "node4", 3);
    undGraph.link("node2", "node5", 4);
    undGraph.link("node3", "node6", 3);
    undGraph.link("node5", "node7", 2);

    assertTrue(undGraph.isLinked("node1", "node2"));
    assertTrue(undGraph.isLinked("node1", "node3"));
    assertTrue(undGraph.isLinked("node1", "node4"));
    assertTrue(undGraph.isLinked("node2", "node5"));
    assertTrue(undGraph.isLinked("node3", "node6"));
    assertTrue(undGraph.isLinked("node5", "node7"));
    assertTrue(undGraph.isLinked("node2", "node1"));
    assertTrue(undGraph.isLinked("node3", "node1"));
    assertTrue(undGraph.isLinked("node4", "node1"));
    assertTrue(undGraph.isLinked("node5", "node2"));
    assertTrue(undGraph.isLinked("node6", "node3"));
    assertTrue(undGraph.isLinked("node7", "node5"));

    UnweightedMatrixGraph<String> unwGraph = new UnweightedMatrixGraph<String>(10);
    unwGraph.link("node1", "node2");
    unwGraph.link("node1", "node3");
    unwGraph.link("node1", "node4");
    unwGraph.link("node2", "node5");
    unwGraph.link("node3", "node6");
    unwGraph.link("node5", "node7");

    assertEquals(1, unwGraph.weight("node1", "node2"));
    assertEquals(1, unwGraph.weight("node1", "node3"));
    assertEquals(1, unwGraph.weight("node1", "node4"));
    assertEquals(1, unwGraph.weight("node2", "node5"));
    assertEquals(1, unwGraph.weight("node3", "node6"));
    assertEquals(1, unwGraph.weight("node5", "node7"));
  }
}
