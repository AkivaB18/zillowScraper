import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.LinkedList;

public class realtorsParser {
    private Document currentDoc;
    private String coreURL;

    public realtorsParser() {
        coreURL = "https://www.redfin.com/";
        try {
            currentDoc = Jsoup.connect("https://www.redfin.com/city/15502/PA/Philadelphia").get();
        } catch (IOException e) {
            System.out.println("Could not access link: " + e.getMessage());
        }
    }

    public LinkedList<house> getHousesfromPage() {
        Elements elements = this.currentDoc.select(".bp-Homecard__Content");
        LinkedList<house> redfinHouses = new LinkedList<house>();
        for (Element h : elements) {
            String cost = h.select(".bp-Homecard__Price--value").text();
            int cleaned_cost = Integer.parseInt(cost.replace("$", "").replace(",", ""));

            String s_beds = h.select("span:contains(bed)").first().text().split(" ")[0];
            int cleaned_beds = metricToInteger(s_beds);

            String s_baths = h.select("span:contains(bath)").first().text().split(" ")[0];
            float cleaned_baths = metricToFloat(s_baths);

            String sqft = h.select("span.bp-Homecard__LockedStat--value").first().text();
            int cleaned_sqft = Integer.parseInt(sqft.replace(",", ""));

            String addr = h.select("div.bp-Homecard__Address.flex.align-center.color-text-primary.font-body-xsmall-compact").first().text();
            String houseLink = h.parent().select("a").attr("href");
            redfinHouses.add(new house(cleaned_cost, cleaned_beds, cleaned_baths, cleaned_sqft, addr, this.coreURL + houseLink));
        }

        return redfinHouses;
    }

    public static int metricToInteger(String str) {
        try {
            return Integer.parseInt(str);
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    public static float metricToFloat(String str) {
        try {
            return Float.parseFloat(str);
        } catch (NumberFormatException e) {
            return -1;
        }
    }
}
