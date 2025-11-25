package sets;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Set;

public class DuplicateWords {

    // Keep only letters, lowercase the result. Empty strings are ignored.
    public static String cleanString(String s) {
        return s.replaceAll("[^A-Za-z]", "").toLowerCase();
    }

    public static void main(String[] args) throws IOException {
        // allow overriding the file from args; default to the project’s text file
        String path = (args != null && args.length > 0) ? args[0] : "little_women.txt";

        // words encountered at least once
        Set<String> seen = new HashSet<>();
        // words encountered 2+ times
        Set<String> repeats = new HashSet<>();

        try (BufferedReader br = Files.newBufferedReader(Paths.get(path), StandardCharsets.UTF_8)) {
            String line;
            while ((line = br.readLine()) != null) {
                // split on whitespace; clean each token
                String[] tokens = line.split("\\s+");
                for (String t : tokens) {
                    String w = cleanString(t);
                    if (w.isEmpty()) continue;

                    // if adding fails, we’ve seen it before → count as repeat
                    if (!seen.add(w)) {
                        repeats.add(w);
                    }
                }
            }
        }

        // singletons = words seen exactly once = seen - repeats
        Set<String> singles = new HashSet<>(seen);
        singles.removeAll(repeats);

        System.out.println("number of duplicate words: " + repeats.size());
        System.out.println("number of single words: " + singles.size());
    }
}
