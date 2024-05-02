import java.util.HashMap;
import java.util.Set;

public class IdfDocument implements Comparable<IdfDocument> {

    private HashMap<String, Integer> termFrequency;
    private String content;

    public IdfDocument(String content) {
        this.content = content;
        termFrequency = new HashMap<>();
        preprocess(content);
    }

    private void preprocess(String content) {
        String[] words = content.split("\\s+");

        for (String word : words) {
            String filteredWord = word.replaceAll("[^A-Za-z0-9]", "").toLowerCase();

            if (!filteredWord.equals("")) {
                termFrequency.put(filteredWord, termFrequency.getOrDefault(filteredWord, 0) + 1);
            }
        }
    }

    public double getTermFrequency(String word) {
        return termFrequency.getOrDefault(word, 0);
    }

    public Set<String> getTermList() {
        return termFrequency.keySet();
    }

    @Override
    public int compareTo(IdfDocument other) {
        return this.content.compareTo(other.content);
    }

    @Override
    public String toString() {
        return content;
    }
}