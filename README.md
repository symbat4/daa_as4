# Assignment 4. Patient Zero

Student ID: 254645
Name: Symbat Tungyshbay
Group: SE-2510

This file is the report. Fill it in as you go. How to start: `START_HERE.md`. The task: `docs/Assignment4.pdf`.

## Program output

The 9 lines your program prints on your data (`java -cp out Main`):

    Mutations: -1
    Shared core: -1
    Marker hits: 0
    First hit: -1
    Border length: 0 
    Shared words: -1
    Tests needed: 0
    Cheapest testing: 0
    Longest chain: -1

My outbreak report (a screenshot of `output/outbreak.html`):

![outbreak](output/outbreak.png)

## Questions

Answer in your own words, two or three sentences each.

Mission 1. What does the cell d[i][j] of your table mean, and how is it computed from other cells?
Each cell d[i][j] represents the minimum number of insertions, deletions, and replacements needed to transform the first i characters of A into the first j characters of B. If the current characters match, we take the diagonal value; otherwise, we add 1 to the minimum of the diagonal, upper, and left cells.

Mission 2. How is the longest common subsequence different from the longest common substring? My Shared core is bigger than the number of letters that are the same at the same place: why?
A subsequence can skip characters, while a substring must contain consecutive characters. My Shared core is bigger than the number of letters that are the same at the same positions because LCS finds matching letters in the same order, even when they appear at different positions.

Mission 3. What does the prefix function tell you, and why does the pointer in the text never go back in KMP? What is the Border length of my marker and what does it mean?
The prefix function tells us the length of the longest proper prefix that is also a suffix for each position in the marker. KMP never moves the text pointer backwards because it uses this information to avoid comparing characters that are already known to match. The Border length is the length of the longest proper prefix that is also a suffix of my marker.

Mission 4. Why must you compare the letters after the hashes are equal? What happens to the cost of Rabin-Karp if many windows have the same hash?
We must compare the actual letters because different strings can have the same hash due to hash collisions. If many windows have the same hash, Rabin-Karp needs more character comparisons, making its running time slower and potentially quadratic.

Mission 5. What are the two states of a person in your DP, and how do you get the answer for a person from its children? Why does a greedy "test the person with most links first" not always give the cheapest answer?
Each person has two states: the minimum cost when they are tested and the minimum cost when they are not tested. If a person is tested, their children can be either tested or not; if the person is not tested, all their children must be tested to cover every link. A greedy strategy does not always work because choosing the person with the most links may cost more than choosing a cheaper combination of other people.

## AI use

The syllabus requires you to say which AI tools you used and for what. Write the tool (ChatGPT, Copilot, other) and what you asked it: explain an idea, find an error, review code. Write "none" if you did not use any. If you took code or text from a book or a website, give the source here.
i used cloud to guide me through the process, to not miss important moments, also to explain me several concepts related to missions.

## Timing (Mission 7)

Milliseconds, average of 3 runs, from `java -cp out Bench`.

| test | size | time |
|---|---|---|
| naive search | 20 000 | 64.7 ms |
| naive search | 40 000 | 114.2 ms |
| naive search | 80 000 | 233.6 ms |
| KMP | 20 000 | 2.7 ms |
| KMP | 40 000 | 1.4 ms |
| KMP | 80 000 | 0.4 ms |
| edit distance | 500 | 5.9 ms |
| edit distance | 1000 | 11.1 ms |
| edit distance | 2000 | 31.9 ms |
| edit distance | 4000 | 140.0 ms |

What do the numbers say about how the time grows? When you double the size, by how much does each time grow? Two or three sentences.
When the text size doubles, naive search takes approximately twice as long, while KMP remains very fast, although the measured times fluctuate between runs. Edit distance becomes significantly slower as the input grows, approaching four times the time when the string length doubles because its time complexity is quadratic.

## Complexity

n is the length of the text (or of a strain), m the length of the pattern, k the length of the window, v the number of people.

| algorithm | time | space | assumptions |
|---|---|---|---|
| Edit distance | O(n*m) | O(n*m) | Full DP table |
| LCS | O(n*m) | O(n*m) | Full DP table |
| Prefix function and KMP | O(n+m) | O(m) | Prefix function uses O(m) space |
| Rabin-Karp (shared words) | Expected O(n+m) | O(1) | Fixed window length of 12; hash collisions are uncommon |
| Tree DP | O(v) | O(v) | Each person and infection link is processed once |

## Before you submit

- The four files in `data` and the screenshot `output/outbreak.png` are in the repository.
- `java tools/Check.java` shows all examples right.
- This README is filled in, the program output and the section AI use included.
- The work is pushed to main (at least one commit per mission) and the repository is public.
- The link is attached in Moodle.
