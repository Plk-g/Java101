package maps;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.LinkedHashMap;
import java.util.Map;

public class UrlLog {

    private static final int RECENT_LIMIT = 5;

    public static void main(String[] args) {
        String path = "server_log.csv";

        // userid -> queue of most recent URLs (bounded)
        Map<String, Deque<String>> recentPerUser = new LinkedHashMap<>();
        // url -> total visits
        Map<String, Integer> urlCounts = new LinkedHashMap<>();

        try (BufferedReader br = Files.newBufferedReader(Paths.get(path), StandardCharsets.UTF_8)) {
            String line;
            while ((line = br.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty()) continue;
                // Expected CSV: timestamp,userid,url
                String[] parts = line.split("\\s*,\\s*");
                if (parts.length < 3) continue; // tolerate bad lines
                String user = parts[1];
                String url = parts[2];

                // bump count
                urlCounts.merge(url, 1, Integer::sum);

                // push into per-user bounded deque
                Deque<String> q = recentPerUser.computeIfAbsent(user, k -> new ArrayDeque<>(RECENT_LIMIT));
                if (q.size() == RECENT_LIMIT) {
                    q.removeFirst(); // drop oldest
                }
                q.addLast(url); // newest at the tail
            }
        } catch (IOException e) {
            System.err.println("Could not read " + path + ": " + e.getMessage());
            return;
        }

        // Output
        System.out.println("Recent URLs per user:");
        for (Map.Entry<String, Deque<String>> e : recentPerUser.entrySet()) {
            System.out.println(e.getKey() + " -> " + e.getValue());
        }

        System.out.println("\nURL Visit Counts:");
        for (Map.Entry<String, Integer> e : urlCounts.entrySet()) {
            System.out.printf("  %-10s : %d%n", e.getKey(), e.getValue());
        }
    }
}
