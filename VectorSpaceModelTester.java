//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

import java.util.ArrayList;

public class VectorSpaceModelTester {
    public VectorSpaceModelTester() {
    }

    public static void main(String[] args) {

        IdfDocument query = new IdfDocument("query.txt");

        ArrayList<IdfDocument> idfDocuments = new ArrayList();
        idfDocuments.add(query);

        Corpus corpus = new Corpus(idfDocuments);
        VectorSpaceModel vectorSpace = new VectorSpaceModel(corpus);

        for(int i = 1; i < idfDocuments.size(); ++i) {
            IdfDocument doc = (IdfDocument) idfDocuments.get(i);
            System.out.println("\nComparing to " + doc);
            System.out.println(vectorSpace.cosineSimilarity(query, doc));
        }

    }
}
