import java.util.*;

/**
 * This class represents a corpus of documents.
 * It creates an inverted index for these documents.
 */
public class Corpus {

    private ArrayList<IdfDocument> documents;
    private HashMap<String, Set<IdfDocument>> invertedIndex;

    public Corpus(ArrayList<IdfDocument> documents) {
        this.documents = documents;
        invertedIndex = new HashMap<>();
        createInvertedIndex();
    }

    private void createInvertedIndex() {
        System.out.println("Creating the inverted index");

        Comparator<IdfDocument> documentComparator = Comparator.naturalOrder();

        for (IdfDocument document : documents) {
            Set<String> terms = document.getTermList();

            for (String term : terms) {
                Set<IdfDocument> docSet = invertedIndex.computeIfAbsent(term, k -> new TreeSet<>(documentComparator));
                docSet.add(document);
            }
        }
    }

    /**
     * Returns the IDF for a given term.
     * @param term a term in a document
     * @return the IDF for the term
     */
    public double getInverseDocumentFrequency(String term) {
        if (invertedIndex.containsKey(term)) {
            double size = documents.size();
            Set<IdfDocument> list = invertedIndex.get(term);
            double documentFrequency = list.size();

            return Math.log10(size / documentFrequency);
        } else {
            return 0;
        }
    }

    /**
     * @return the documents
     */
    public ArrayList<IdfDocument> getDocuments() {
        return documents;
    }

    /**
     * @return the inverted index
     */
    public HashMap<String, Set<IdfDocument>> getInvertedIndex() {
        return invertedIndex;
    }
}