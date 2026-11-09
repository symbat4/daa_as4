import java.util.*;

/**
 * Mission 5. Dynamic programming on the tree of infections. Root the tree in person 0.
 * Input.Tree.neighbours() gives the neighbours of every person. The number of people is at most about 100, so a recursive DFS is fine here.
 */
public class Outbreak {

    /**
     * Choose people to test so that for every link (somebody infected somebody) at least one of the two is tested,
     * and the total cost of testing is as small as possible. cost[v] is the price of testing person v.
     * Returns tested[v] = true if person v is tested.
     *
     * State for every person v (looking only at v and everything below it): the smallest cost when v IS tested, and
     * the smallest cost when v is NOT tested. What must then be true about the children of v in each case?
     * Do not forget to rebuild the answer (who exactly is tested), not only the cost: the picture needs it.
     * Main calls this twice: with all costs equal to 1 (the line "Tests needed") and with the real costs ("Cheapest testing").
     */
    public static boolean[] cheapestTests(int n, int[][] nb, int[] cost) {
        // TODO Mission 5a
        int[] parent = new int[n];
        int[] order = new int[n];                       // DFS order, parents before children
        int cnt = 0;
        Arrays.fill(parent, -2);
        parent[0] = -1;
        int[] stack = new int[n];
        int top = 0;
        stack[top++] = 0;
        while (top > 0) {
            int v = stack[--top];
            order[cnt++] = v;
            for (int c : nb[v]) if (parent[c] == -2) { parent[c] = v; stack[top++] = c; }
        }
        long[] take = new long[n], skip = new long[n];
        for (int i = n - 1; i >= 0; i--) {              // children before parents
            int v = order[i];
            take[v] = cost[v];
            skip[v] = 0;
            for (int c : nb[v]) {
                if (c == parent[v]) continue;
                take[v] += Math.min(take[c], skip[c]);  // v tested: child is free to choose
                skip[v] += take[c];                     // v not tested: child MUST be tested
            }
        }
        boolean[] tested = new boolean[n];
        tested[0] = take[0] <= skip[0];
        for (int i = 0; i < n; i++) {                   // top-down: rebuild the choices
            int v = order[i];
            for (int c : nb[v]) {
                if (c == parent[v]) continue;
                if (!tested[v]) tested[c] = true;
                else tested[c] = take[c] <= skip[c];
            }
        }
        return tested;
    }

    /**
     * The number of links on the longest chain of infections, that is the longest path in the tree (counted in links).
     * A path goes up from one person and down to another; for every person you need the two longest ways down.
     * Example: a line 0-1-2-3 has chain 3; a star with centre 0 and leaves 1, 2, 3 has chain 2.
     */
    public static int longestChain(int n, int[][] nb) {
        // TODO Mission 5b
        int[] parent = new int[n];
        int[] order = new int[n];
        int cnt = 0;
        Arrays.fill(parent, -2);
        parent[0] = -1;
        int[] stack = new int[n];
        int top = 0;
        stack[top++] = 0;
        while (top > 0) {
            int v = stack[--top];
            order[cnt++] = v;
            for (int c : nb[v]) if (parent[c] == -2) { parent[c] = v; stack[top++] = c; }
        }
        int[] down = new int[n];                        // longest way down from v, in links
        int best = 0;
        for (int i = n - 1; i >= 0; i--) {
            int v = order[i];
            int d1 = 0, d2 = 0;                         // two longest ways down through different children
            for (int c : nb[v]) {
                if (c == parent[v]) continue;
                int len = down[c] + 1;
                if (len > d1) { d2 = d1; d1 = len; }
                else if (len > d2) d2 = len;
            }
            down[v] = d1;
            best = Math.max(best, d1 + d2);
        }
        return best;
    }
}
