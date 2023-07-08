import com.google.gson.JsonArray;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class Webpage {

    private static StationIndex stationIndex;

    private static ArrayList<Stations> stationsNumberName = new ArrayList<>();

    private static List<Line> lineList;

    public static StationIndex getStationIndex() {
        return stationIndex;
    }

    public static void mapWriteJson (String path) throws IOException {

        try (FileWriter writer = new FileWriter("src/main/resources/map1.json")) {
            JSONObject map = new JSONObject();
            JSONObject object = new JSONObject();
            for (int i = 0; i < stationsNumberName.size(); i++) {
                JsonArray array = new JsonArray();
                String str = stationsNumberName.get(i).getName();
                String REGEX_FOR_STATIONS = "[\\s][\\s]";
                String[] fragments = str.split(REGEX_FOR_STATIONS);
                for (String fragment : fragments) {
                    array.add(fragment.trim());
                    object.put(stationsNumberName.get(i).getNumberLine(), array);
                }
            }
            JSONArray lineArray = new JSONArray();
            for (int i = 0; i < lineList.size(); i++) {
                JSONObject object1 = new JSONObject();
                object1.put("number", lineList.get(i).getNumber());
                object1.put("name", lineList.get(i).getName());
                lineArray.add(object1);
            }
            map.put("stations", object);
            map.put("lines", lineArray);
            writer.write(DataFile.GSON.toJson(map));
            writer.flush();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public List<Line> parseLine() { // Получение номера и имени линий Московского метро

        lineList = new ArrayList<>();
        Document document;
        document = Jsoup.parse(DataFile.ParsePage());
        Elements addLine = document.select(".js-metro-line");
        for (Element line : addLine) {
            Line lines = new Line(line.attr("data-line"), line.text());
            lineList.add(lines);
            System.out.println(line.attr("data-line") + "." + " " + line.text());

        }
        return lineList;
    }

    public ArrayList<Stations> parseStations()  {//Получение имени станции и номера линии Московского метро

        stationIndex = new StationIndex();
        String stationRegex = "[0-9]+\\.";
        Document document;

        document = Jsoup.parse(DataFile.ParsePage());
        for (Element addLines : document.select(".js-depend")) {
            String lineNumber = addLines.attr("data-depend-set").substring(6);
            String lineName = document.getElementsByAttributeValue("data-line",lineNumber).get(0).text();
            for (Element addStations : addLines.select(".js-metro-stations")) {
                String station = addStations.text().replaceAll(stationRegex, "");
                stationsNumberName.add(new Stations(station, lineNumber, lineName));
                stationIndex.addStation(new Stations(addStations.text(),
                        addLines.attr("data-depend-set").substring(6), lineName));
                System.out.println("Линия: " + lineNumber + "\n" + "Станции " +
                        addStations.text().replaceAll(stationRegex, "->"));
            }
        }
        return stationsNumberName;
    }

    public void parseConnections () { //Получение пересадок со станций

        Document document;
        try {
            document = Jsoup.parse(DataFile.ParsePage());
            Elements addConnection = document.getElementsByClass("js-metro-stations t-metrostation-list-table");
            for (Element link : addConnection) {
                Elements connectionsList = link.select("p:has(span[title])");
                for (Element connectionElement : connectionsList) {
                    String station = connectionElement.text();
                    int indexSpace = station.lastIndexOf(";");
                    String stationName = station.substring(indexSpace + 1).trim();

                    Connections connections = new Connections();
                    connections.addStation(new Stations(stationName, link.attr("data-line")));

                    Elements connectionsStationList = connectionElement.select("span[title]");
                    for (Element conSpan : connectionsStationList) {
                        String line = conSpan.attr("class");
                        int indexDash = line.lastIndexOf("-");
                        String numberLine = line.substring(indexDash + 1);

                        String transitions = conSpan.attr("title");
                        int firstQuotes = transitions.indexOf("«");
                        int secondQuotes = transitions.lastIndexOf("»");
                        String transitionStationName = transitions.substring(firstQuotes + 1, secondQuotes);
                        connections.addStation(new Stations(transitionStationName, numberLine));
                        System.out.println(transitions);

                    }
                    stationIndex.addConnections(connections);
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
