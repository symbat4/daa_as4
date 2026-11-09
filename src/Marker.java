import java.util.*;

/**
 * Missions 3 and 4. Find a word in a long text.
 */
public class Marker {

    /**
     * Mission 3a. The prefix function of p. pi[i] = the length of the longest proper prefix of p[0..i] that is
     * also a suffix of p[0..i] ("proper" means shorter than p[0..i] itself).
     * Example: p = "ACGAC" gives [0, 0, 0, 1, 2]. The last number is the "Border length" line.
     */
    public static int[] prefixFunction(String p) {
        // TODO Mission 3a
        int m = p.length();
        int[] pi = new int[m];
        for (int i = 1, k = 0; i < m; i++) {
            while (k > 0 && p.charAt(i) != p.charAt(k)) k = pi[k - 1];
            if (p.charAt(i) == p.charAt(k)) k++;
            pi[i] = k;
        }
        return pi;
    }

    /**
     * Mission 3b. KMP. All start positions (counting from 0, in increasing order) where p occurs in text.
     * Overlapping occurrences count: "ACG" occurs in "ACGACG" at 0 and 3, and "AA" in "AAA" at 0 and 1.
     * Use the prefix function: the text pointer never goes back. The number of positions is "Marker hits",
     * the first one is "First hit" (-1 when there is none).
     */
    public static int[] findAll(String text, String p) {
        // TODO Mission 3b
        int m = p.length();
        if (m == 0) return new int[0];
        int[] pi = prefixFunction(p);
        int[] tmp = new int[text.length()];
        int cnt = 0, k = 0;
        for (int i = 0; i < text.length(); i++) {
            while (k > 0 && text.charAt(i) != p.charAt(k)) k = pi[k - 1];
            if (text.charAt(i) == p.charAt(k)) k++;
            if (k == m) {
                tmp[cnt++] = i - m + 1;
                k = pi[k - 1];
            }
        }
        return java.util.Arrays.copyOf(tmp, cnt);
    }

    /**
     * Mission 4. Rabin-Karp. The number of positions i in a such that the word of k letters starting at i
     * (a[i..i+k-1]) also occurs somewhere in b. Count positions, not different words. This is the "Shared words" line.
     * The idea: give every window of k letters a number (a rolling hash, updated in O(1) when the window moves by one),
     * look the number up among the windows of b, and when it is found COMPARE THE LETTERS, because two different words
     * can have the same hash.
     * Example: a = "ACGTAC", b = "TTACGT", k = 4: the words of a are ACGT, CGTA, GTAC; only ACGT is in b, so the answer is 1.
     */
    public static int sharedWords(String a, String b, int k) {
        // TODO Mission 4
        int n = a.length(), m = b.length();
        if (n < k || m < k || k <= 0) return 0;
        final long MOD = 1_000_000_007L, BASE = 131;
        long pow = 1;                                   // BASE^(k-1) mod MOD
        for (int i = 1; i < k; i++) pow = pow * BASE % MOD;

        // hashes of all windows of b: hash -> list of start positions
        HashMap<Long, ArrayList<Integer>> map = new HashMap<>();
        long h = 0;
        for (int i = 0; i < k; i++) h = (h * BASE + b.charAt(i)) % MOD;
        for (int j = 0; ; j++) {
            map.computeIfAbsent(h, x -> new ArrayList<>()).add(j);
            if (j + k >= m) break;
            h = ((h - b.charAt(j) * pow % MOD + MOD) % MOD * BASE + b.charAt(j + k)) % MOD;
        }

        // rolling hash over a
        int count = 0;
        h = 0;
        for (int i = 0; i < k; i++) h = (h * BASE + a.charAt(i)) % MOD;
        for (int i = 0; ; i++) {
            ArrayList<Integer> list = map.get(h);
            if (list != null) {
                for (int j : list) {                    // equal hash: COMPARE THE LETTERS
                    boolean same = true;
                    for (int t = 0; t < k; t++)
                        if (a.charAt(i + t) != b.charAt(j + t)) { same = false; break; }
                    if (same) { count++; break; }       // count the position once
                }
            }
            if (i + k >= n) break;
            h = ((h - a.charAt(i) * pow % MOD + MOD) % MOD * BASE + a.charAt(i + k)) % MOD;
        }
        return count;
    }
}
