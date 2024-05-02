import java.util.HashMap;
import java.util.Set;

/**
 * This class implements the Vector-Space model.
 * It creates tf-idf vectors for each document in a corpus.
 */
public class VectorSpaceModel {

    private Corpus corpus;
    private HashMap<IdfDocument, HashMap<String, Double>> tfIdfWeights;

    public VectorSpaceModel(Corpus corpus) {
        this.corpus = corpus;
        tfIdfWeights = new HashMap<>();
        createTfIdfWeights();
    }

    private void createTfIdfWeights() {
        Set<String> terms = corpus.getInvertedIndex().keySet();

        for (IdfDocument idfDocument : corpus.getDocuments()) {
            HashMap<String, Double> weights = new HashMap<>();

            for (String term : terms) {
                double tf = idfDocument.getTermFrequency(term);
                double idf = corpus.getInverseDocumentFrequency(term);
                double weight = tf * idf;

                weights.put(term, weight);
            }
            tfIdfWeights.put(idfDocument, weights);
        }
    }

    private double getMagnitude(IdfDocument idfDocument) {
        double magnitude = 0;
        HashMap<String, Double> weights = tfIdfWeights.get(idfDocument);

        if (weights != null) {
            for (double weight : weights.values()) {
                magnitude += weight * weight;
            }
        }

        return Math.sqrt(magnitude);
    }

    private double getDotProduct(IdfDocument d1, IdfDocument d2) {
        double product = 0;
        HashMap<String, Double> weights1 = tfIdfWeights.get(d1);
        HashMap<String, Double> weights2 = tfIdfWeights.get(d2);

        if (weights1 != null && weights2 != null) {
            for (String term : weights1.keySet()) {
                product += weights1.get(term) * weights2.getOrDefault((Object) term, (double) 0);
            }
        }

        return product;
    }

    public double cosineSimilarity(IdfDocument d1, IdfDocument d2) {
        double magnitude1 = getMagnitude(d1);
        double magnitude2 = getMagnitude(d2);

        if (magnitude1 == 0 || magnitude2 == 0) {
            return 0; // or handle in a way that suits your logic
        }

        return getDotProduct(d1, d2) / (magnitude1 * magnitude2);
    }
}