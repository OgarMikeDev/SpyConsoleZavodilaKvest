import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.FileWriter;

public class Main {
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
                builderLocationsForHouse.append(line.replaceAll("[^А-я]", "")).append("\n");
            }
        }

        System.out.println(builderLocationsForHouse);
    }
}
