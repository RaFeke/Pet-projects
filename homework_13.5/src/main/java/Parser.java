import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;


public class Parser {
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static Map<String, List<String>> stations = new LinkedHashMap<>();
    private static List<Line> lines = new ArrayList<>();
    private static Subway subway;
    private static List<List<Station>> connection = new ArrayList<>();

    public static void parseHtml(String urlPage) {
        try {
            Document doc = Jsoup.connect(urlPage).maxBodySize(0).get();
            Elements stationsElement = doc.select("div.js-metro-stations.t-metrostation-list-table");
            Elements lineElements = doc.select("span.js-metro-line.t-metrostation-list-header");
            lineElements.forEach(element -> {
                lines.add(Line.builder().name(element.text()).number(element.attr("data-line")).build());
            });
            stationsElement.forEach(element -> {
                stations.put(element.attr("data-line"), element.select("span.name").eachText());
            });
            createJsonFile();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static void createJsonFile() throws IOException {

        subway = Subway.builder().lines(lines).stations(stations).connection(connection).build();
        try (FileWriter file = new FileWriter("src/main/resources/map.json")) {
            file.write(GSON.toJson(subway));
        }
    }

    static String parseFile(String path) {
        StringBuilder sb = new StringBuilder();
        try {
            List<String> lines = Files.readAllLines(Paths.get(path));
            lines.forEach(line -> sb.append(line).append("\n"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }

    static void JsonParser() throws org.json.simple.parser.ParseException {
        JSONParser parser = new JSONParser();
        JSONObject jsonObject = (JSONObject) parser.parse(parseFile("src/main/resources/map.json"));
        Map<String, List<String>> stations = (Map<String, List<String>>) jsonObject.get("stations");
        for (String lineId : stations.keySet()) {
            JSONArray stationsArray = (JSONArray) stations.get(lineId);
            for (Line line : subway.getLines()) {
                if (line.getNumber().equals(lineId)) {
                    System.out.println("Линия " + lineId + " " + line.getName() + " -> количество станций: " + stationsArray.size());

                }
            }
        }
    }
}
