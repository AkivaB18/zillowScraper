import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.IOException;
import java.util.*;

public class homesParser {
    private Document currentDoc;
    private String coreURL;

    public homesParser(String base) {
        coreURL = base;
        try {
            currentDoc = Jsoup.connect(coreURL).get();
        } catch (IOException e) {
            System.out.println("Could not access link: " + e.getMessage());
        }
    }

    public static HashMap<String, IdfDocument> createCityDescrp() {
        HashMap<String, IdfDocument> cityDescription = new HashMap<>();

        cityDescription.put("Philadelphia", new IdfDocument(homesParser.collectAllNeighborhood("https://www.neighborhoodscout.com/pa/philadelphia")));
        cityDescription.put("NYC", new IdfDocument(homesParser.collectAllNeighborhood("https://www.neighborhoodscout.com/ny/new-york")));
        cityDescription.put("Chicago", new IdfDocument(homesParser.collectAllNeighborhood("https://www.neighborhoodscout.com/il/chicago")));
        cityDescription.put("Los Angeles", new IdfDocument(homesParser.collectAllNeighborhood("https://www.neighborhoodscout.com/ca/los-angeles")));
        cityDescription.put("Washington DC", new IdfDocument(homesParser.collectAllNeighborhood("https://www.neighborhoodscout.com/dc/washington")));

        return cityDescription;
    }

    public static void compareToCities() {

        double maxCosine = -1;
        System.out.println("Describe your ideal city and we'll match" +
                "you to one of the five main cities supported in this program");

        Scanner option = new Scanner(System.in);
        String wants = option.nextLine();

            HashMap<String, IdfDocument> cities = createCityDescrp();
            ArrayList<IdfDocument> documents = new ArrayList<>();

            for (Map.Entry<String, IdfDocument> city : cities.entrySet()) {
                documents.add(city.getValue());
            }

            IdfDocument query = new IdfDocument(wants);
            Corpus corpus = new Corpus(documents);
            VectorSpaceModel vectorSpace = new VectorSpaceModel(corpus);

            for (IdfDocument doc : documents) {
                System.out.println("\nComparing to " + doc);
                double similarity = vectorSpace.cosineSimilarity(query, doc);

                if (Double.isNaN(similarity)) {
                    System.out.println("Similarity computation returned NaN");
                } else {
                    System.out.println(similarity);
                }
            }
        }


    private static String collectAllNeighborhood(String url) {
        try {
            Document currentDoc = Jsoup.connect(url).get();
            StringBuilder combinedText = new StringBuilder();

            Elements containerSections = currentDoc.select("section.container");

            for (Element section : containerSections) {
                Elements paragraphs = section.select("p");

                for (Element paragraph : paragraphs) {
                    combinedText.append(paragraph.text()).append(" ");
                }
            }

            return combinedText.toString().trim();
        } catch (Exception e) {
            System.out.println("Website couldn't be read");
            return "";
        }
    }
}