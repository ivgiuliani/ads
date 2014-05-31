package graphs;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import interviewquestions.TestCase;


/**
 * BFS between two nodes
 */
public class BFS extends TestCase {
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

  public static <T> List<T> bfs(Graph<T> graph, T start, T end) {
    Set<T> visited = new HashSet<T>();
    Queue<QPath<T>> queue = new LinkedList<QPath<T>>();

    visited.add(start);
    queue.add(new QPath<T>(start));

    while (!queue.isEmpty()) {
      QPath<T> node = queue.poll();
      visited.add(node.node);

      if (node.node.equals(end)) {
        return node.path;
      }

      for (T neighbour : graph.neighbours(node.node)) {
        if (!visited.contains(neighbour)) {
          queue.add(new QPath<T>(neighbour, node.path));
        }
      }
    }

    return new ArrayList<T>();
  }

  // The BFS implementation below will compile only on java8 because of lambdas and stream methods
  public static <T> List<T> bfs8(Graph<T> graph, T start, Predicate<T> isEndNode) {
    Set<T> visited = new HashSet<>();
    Queue<QPath<T>> queue = new LinkedList<>();

    visited.add(start);
    queue.add(new QPath<T>(start));

    while (!queue.isEmpty()) {
      QPath<T> node = queue.poll();
      visited.add(node.node);

      if (isEndNode.test(node.node)) {
        return node.path;
      }

      queue.addAll(
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

    path = bfs(graph, "node1", "node7");
    assertEquals(4, path.size());
    assertEquals("node1", path.get(0));
    assertEquals("node2", path.get(1));
    assertEquals("node5", path.get(2));
    assertEquals("node7", path.get(3));

    path = bfs(graph, "node1", "node6");
    assertEquals(3, path.size());
    assertEquals("node1", path.get(0));
    assertEquals("node3", path.get(1));
    assertEquals("node6", path.get(2));

    path = bfs8(graph, "node1", n -> n.equals("node7"));
    assertEquals(4, path.size());
    assertEquals("node1", path.get(0));
    assertEquals("node2", path.get(1));
    assertEquals("node5", path.get(2));
    assertEquals("node7", path.get(3));

    path = bfs8(graph, "node1", n -> n.equals("node6"));
    assertEquals(3, path.size());
    assertEquals("node1", path.get(0));
    assertEquals("node3", path.get(1));
    assertEquals("node6", path.get(2));
  }
}
