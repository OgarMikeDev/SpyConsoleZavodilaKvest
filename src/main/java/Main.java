import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

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

        /*
        TODO
         Запросить ввод кол-ва пользователей и ограничить его от 3 до 10 человек.
         Запросить ввод кол-ва шпионову и ограничить их от 1 до кол-ва человек - 1.
         Если пользователь вводит некорректные значения, он должен вводить их заново.
         */
        System.out.println("Введите кол-во игроков:");
        int countPlayer = new Scanner(System.in).nextInt();
        boolean isCorrectCountPlayer = countPlayer >= 3 && countPlayer <= 10 ? true : false;
        while (!isCorrectCountPlayer) {
            System.out.println("Ввод некорректен, попробуйте заново ввести кол-во игроков:");
            countPlayer = new Scanner(System.in).nextInt();
            isCorrectCountPlayer = countPlayer >= 3 && countPlayer <= 10 ? true : false;
        }

        System.out.println("Введите кол-во шпионов:");
        int countSpy = new Scanner(System.in).nextInt();
        boolean isCorrectCountSpy = countSpy >= 1 && countSpy <= countPlayer - 1 ? true : false;
        while (!isCorrectCountSpy) {
            System.out.println("Ввод некорректен, попробуйте заново ввести кол-во шпионов:");
            countSpy = new Scanner(System.in).nextInt();
            isCorrectCountSpy =  countSpy >= 1 && countSpy <= countPlayer - 1 ? true : false;
        }

        List<Integer> listSpy = new ArrayList<>();
        while (listSpy.size() < countSpy) {
            int randomNumberSpy = 1 + (int) (Math.random() * countPlayer);
            if (randomNumberSpy >= 1 && randomNumberSpy <= countPlayer && !listSpy.contains(randomNumberSpy)) {
                listSpy.add(randomNumberSpy);
            }
        }

        System.out.println("Шпионы " + listSpy);

        String randomWord = listWords.get((int) (Math.random() * listWords.size()));
        for (int numberCurrentPlayer = 1; numberCurrentPlayer <= countPlayer; numberCurrentPlayer++) {
            System.out.println("Нажмите \"Enter\", чтоб открыть свою карточку");
            String enter = new Scanner(System.in).nextLine();
            if (listSpy.contains(numberCurrentPlayer)) {
                System.out.println("Эй, ты шпион, усы сбрей");
                System.out.println("Нажмите \"Enter\", чтоб закрыть свою карточку");
                enter = new Scanner(System.in).nextLine();
            } else {
                System.out.println(randomWord);
                System.out.println("Нажмите \"Enter\", чтоб закрыть свою карточку");
                enter = new Scanner(System.in).nextLine();
            }
        }
    }
}
