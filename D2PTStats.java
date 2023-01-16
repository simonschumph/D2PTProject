import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.*;

public class D2PTStats {
    public static void main(String[] args){
        Scanner kb = new Scanner(System.in);

        System.out.print("Please enter hero name here: ");
        String heroName = kb.nextLine();

        System.out.print("Please enter item name here: ");
        String itemName = kb.nextLine();

        String d2ptURL = String.format("https://www.dota2protracker.com/hero/%s", heroName);
        Document d2ptPage = request(d2ptURL);

        Elements rows = d2ptPage.getElementsByAttributeValueContaining("items", itemName);
        ArrayList<Integer> timings = new ArrayList<Integer>();

        while (!rows.isEmpty()){
            Element row = rows.get(0);
            Elements item = row.getElementsByAttributeValue("title", itemName);
            int timing = Integer.parseInt(item.text().replace("m", ""));
            timings.add(timing);
            rows.remove(0);
        }

        double total = 0.0;
        for (int timing : timings){
            total += timing;
        }

        double avg = total/timings.size();
        System.out.printf("The average timing to purchase this item was %.2f minutes", avg);
    }

    private static Document request(String url){
        try {
            Connection con = Jsoup.connect(url);
            Document doc = con.get();
            return doc;
        }
        catch (IOException e){
            e.printStackTrace();
            return null;
        }
    }
}
