import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Scanner;
import java.util.Set;
import java.util.HashMap;
import java.util.Set;

/**
 * This class represents one document.
 * It will keep track of the term frequencies.
 * @author swapneel
 *
 */
public class IdfDocument implements Comparable<IdfDocument> {

    /**
     * A hashmap for term frequencies.
     * Maps a term to the number of times this terms appears in this document.
     */
    private HashMap<String, Integer> termFrequency;

    /**
     * The name of the file to read.
     */
    private String text;

    /**
     * The constructor.
     * It takes in the name of a file to read.
     * It will read the file and pre-process it.
     * @param text the name of the file
     */
    public IdfDocument(String text) {
        this.text = text;
        termFrequency = new HashMap<String, Integer>();

        readFileAndPreProcess();
    }

    /**
     * This method will read in the file and do some pre-processing.
     * The following things are done in pre-processing:
     * Every word is converted to lower case.
     * Every character that is not a letter or a digit is removed.
     * We don't do any stemming.
     * Once the pre-processing is done, we create and update the
     */
    private void readFileAndPreProcess() {
            for (String word : this.text.split(" ")) {

                String filteredWord = word.replaceAll("[^A-Za-z0-9]", "").toLowerCase();

                if (!(filteredWord.equalsIgnoreCase(""))) {
                    if (termFrequency.containsKey(filteredWord)) {
                        int oldCount = termFrequency.get(filteredWord);
                        termFrequency.put(filteredWord, ++oldCount);
                    } else {
                        termFrequency.put(filteredWord, 1);
                    }
                }
            }
    }

    /**
     * This method will return the term frequency for a given word.
     * If this document doesn't contain the word, it will return 0
     * @param word The word to look for
     * @return the term frequency for this word in this document
     */
    public double getTermFrequency(String word) {
        return termFrequency.getOrDefault(word, 0);
    }

    /**
     * This method will return a set of all the terms which occur in this document.
     * @return a set of all terms in this document
     */
    public Set<String> getTermList() {
        return termFrequency.keySet();
    }

    @Override
    /**
     * The overriden method from the Comparable interface.
     */
    public int compareTo(IdfDocument other) {
        return 0;
    }

    /**
     * @return the filename
     */
    private String getFileName() {
        return "";
    }

    /**
     * This method is used for pretty-printing a Document object.
     * @return the filename
     */
    public String toString() {
        return this.text;
    }
}