package graphs;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import interviewquestions.TestCase;


/**
 * DFS between two nodes
 */
public class DFS extends TestCase {
  static class QPath<T> {
    T node;
    LinkedList<T> path = new LinkedList<T>();

    public QPath(T node) {
      this.node = node;
      this.path.add(node);
    }

    public QPath(T node, LinkedList<T> prev) {
      for (T p : prev) {
        path.add(p);
      }
      path.add(node);
      this.node = node;
    }

    public void addToPath(T node) {
      path.add(node);
    }
  }

  public static <T> List<T> dfs(Graph<T> graph, T start, T end) {
    Set<T> visited = new HashSet<T>();
    LinkedList<QPath<T>> stack = new LinkedList<QPath<T>>();

    visited.add(start);
    stack.add(new QPath<T>(start));

    while (!stack.isEmpty()) {
      QPath<T> node = stack.pop();
      visited.add(node.node);

      if (node.node.equals(end)) {
        return node.path;
      }

      for (T neighbour : graph.neighbours(node.node)) {
        if (!visited.contains(neighbour)) {
          stack.add(new QPath<T>(neighbour, node.path));
        }
      }
    }

    return new ArrayList<T>();
  }

  public static <T> List<T> dfs_recursive(Graph<T> graph, T start, T end) {
    Set<T> visited = new HashSet<T>();
    return dfs_recursive(graph, new QPath<T>(start), end, visited);
  }

  private static <T> List<T> dfs_recursive(Graph<T> graph, QPath<T> start, T end, Set<T> visited) {
    visited.add(start.node);

    if (start.node.equals(end)) {
      return start.path;
    }

    for (T neighbour : graph.neighbours(start.node)) {
      List<T> res = dfs_recursive(graph, new QPath<T>(neighbour, start.path), end, visited);
      if (!res.isEmpty()) {
        return res;
      }
    }
    return new ArrayList<T>();
  }

  // The DFS implementation below will compile only on java8 because of lambdas and stream methods
  public static <T> List<T> dfs8(Graph<T> graph, T start, Predicate<T> isEndNode) {
    Set<T> visited = new HashSet<>();
    LinkedList<QPath<T>> stack = new LinkedList<>();

    visited.add(start);
    stack.add(new QPath<T>(start));

    while (!stack.isEmpty()) {
      QPath<T> node = stack.pop();
      visited.add(node.node);

      if (isEndNode.test(node.node)) {
        return node.path;
      }

      stack.addAll(
          graph.neighbours(node.node).stream()
          .filter(neighbour -> !visited.contains(neighbour))
          .map(neighbour -> new QPath<>(neighbour, node.path))
          .collect(Collectors.toList())
      );
    }

    return new ArrayList<>();
  }

  public static void main(String[] args) {
    Graph<String> graph = new MatrixGraph<String>(10);
    List<String> path;

    //  node1 -> node2 -> node5 -> node7
    //        -> node3 -> node6
    //        -> node4

    graph.link("node1", "node2", 1);
    graph.link("node1", "node3", 2);
    graph.link("node1", "node4", 3);
    graph.link("node2", "node5", 4);
    graph.link("node3", "node6", 3);
    graph.link("node5", "node7", 2);

    path = dfs(graph, "node1", "node7");
    assertEquals(4, path.size());
    assertEquals("node1", path.get(0));
    assertEquals("node2", path.get(1));
    assertEquals("node5", path.get(2));
    assertEquals("node7", path.get(3));

    path = dfs(graph, "node1", "node6");
    assertEquals(3, path.size());
    assertEquals("node1", path.get(0));
    assertEquals("node3", path.get(1));
    assertEquals("node6", path.get(2));

    path = dfs8(graph, "node1", n -> n.equals("node7"));
    assertEquals(4, path.size());
    assertEquals("node1", path.get(0));
    assertEquals("node2", path.get(1));
    assertEquals("node5", path.get(2));
    assertEquals("node7", path.get(3));

    path = dfs8(graph, "node1", n -> n.equals("node6"));
    assertEquals(3, path.size());
    assertEquals("node1", path.get(0));
    assertEquals("node3", path.get(1));
    assertEquals("node6", path.get(2));

    path = dfs_recursive(graph, "node1", "node7");
    assertEquals(4, path.size());
    assertEquals("node1", path.get(0));
    assertEquals("node2", path.get(1));
    assertEquals("node5", path.get(2));
    assertEquals("node7", path.get(3));

    path = dfs_recursive(graph, "node1", "node6");
    assertEquals(3, path.size());
    assertEquals("node1", path.get(0));
    assertEquals("node3", path.get(1));
    assertEquals("node6", path.get(2));
  }
}
