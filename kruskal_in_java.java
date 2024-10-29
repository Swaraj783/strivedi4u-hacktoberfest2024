import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

class Edge {
    int src, dest, weight;

    public Edge(int src, int dest, int weight) {
        this.src = src;
        this.dest = dest;
        this.weight = weight;
    }
}

class Subset {
    int parent, rank;
}

public class KruskalMST {
    int vertices;
    List<Edge> edges = new ArrayList<>();

    public KruskalMST(int vertices) {
        this.vertices = vertices;
    }

    public void addEdge(int src, int dest, int weight) {
        edges.add(new Edge(src, dest, weight));
    }

    public int find(Subset[] subsets, int i) {
        if (subsets[i].parent != i) {
            subsets[i].parent = find(subsets, subsets[i].parent);
        }
        return subsets[i].parent;
    }

    public void union(Subset[] subsets, int x, int y) {
        int rootX = find(subsets, x);
        int rootY = find(subsets, y);

        if (subsets[rootX].rank < subsets[rootY].rank) {
            subsets[rootX].parent = rootY;
        } else if (subsets[rootX].rank > subsets[rootY].rank) {
            subsets[rootY].parent = rootX;
        } else {
            subsets[rootY].parent = rootX;
            subsets[rootX].rank++;
        }
    }

    public void kruskalMST() {
        List<Edge> result = new ArrayList<>();
        Collections.sort(edges, Comparator.comparingInt(edge -> edge.weight));

        Subset[] subsets = new Subset[vertices];
        for (int i = 0; i < vertices; i++) {
            subsets[i] = new Subset();
            subsets[i].parent = i;
            subsets[i].rank = 0;
        }

        int e = 0;
        int i = 0;
        while (e < vertices - 1 && i < edges.size()) {
            Edge nextEdge = edges.get(i++);

            int x = find(subsets, nextEdge.src);
            int y = find(subsets, nextEdge.dest);

            if (x != y) {
                result.add(nextEdge);
                union(subsets, x, y);
                e++;
            }
        }

        for (Edge edge : result) {
            System.out.println("Edge: (" + edge.src + " - " + edge.dest + ") weight: " + edge.weight);
        }
    }

    public static void main(String[] args) {
        int vertices = 4;
        KruskalMST graph = new KruskalMST(vertices);

        graph.addEdge(0, 1, 10);
        graph.addEdge(0, 2, 6);
        graph.addEdge(0, 3, 5);
        graph.addEdge(1, 3, 15);
        graph.addEdge(2, 3, 4);

        graph.kruskalMST();
    }
}
