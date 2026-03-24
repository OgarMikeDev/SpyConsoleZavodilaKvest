import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

public class Main {
    static List<String> listWords = new ArrayList<>();
    public static void main(String[] args) throws Exception {
        /*
        TODO
         Получить html код в файл pageWithWords.html(использовать jsoup).
         */
        Document document = Jsoup.connect("https://www.zavodila-kvest.com/product/domashniy-shpionskiy-kvest/?ysclid=mn472yi4f1214846025").get();
        FileWriter fileWriter = new FileWriter("src/main/resources/pageWithWords.html");
        fileWriter.write(document.toString());
        fileWriter.close();

        StringBuilder builderLocationsForHouse = new StringBuilder();
        String templateForStartAndEndCut = "<span style=\"color: #ff9900;\">";
        boolean rangeForCut = false;
        int numberLineForCut = 1;
        for (String line : document.toString().split("\n")) {
            boolean conditionForCut = line.contains(templateForStartAndEndCut) && line.contains("Локации");
            if (conditionForCut && numberLineForCut == 1) {
                rangeForCut = true;
                numberLineForCut = 2;
            } else if (conditionForCut && numberLineForCut == 2) {
                rangeForCut = false;
            }

            if (rangeForCut) {
                //TODO Добавлять в builderLocationsForHouse строки - локации для дома
                String curentWord = line.replaceAll("[^А-я]", "");
                if (curentWord.length() >= 1 && !curentWord.contains("Локации")) {
                    listWords.add("🌹" + curentWord + "🌹");
                }
            }
        }

        for (String word : listWords) {
            System.out.println(word);
        }
    }
}
