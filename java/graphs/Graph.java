package graphs;

import java.util.Set;

/**
 * Graph interface for different graph backends
 * @param <T>  type of the items in the graph
 */
public interface Graph<T> {
  public void link(T n1, T n2, int weight);
  public void unlink(T n1, T n2);
  public int weight(T n1, T n2);
  public boolean isLinked(T n1, T n2);
  public Set<T> neighbours(T node);
}
