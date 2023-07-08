import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class AddingData {

    Webpage webpage = new Webpage();

    List<Stations> stations = webpage.getStationIndex().getStations();

    TreeSet<Connections> connections = webpage.getStationIndex().getConnections();

    Map<String, Stations> stationsList = ParsingFile.getStationsList();

    public AddingData() {
    }

    public void stationWriteJson() throws IOException {

        try (FileWriter writer = new FileWriter("src/main/resources/stations.json")) {

            setHasConnections(connections, stationsList);
            setNumberNameLine(stations, stationsList);
            JSONObject map = new JSONObject();
            JSONArray jsonArray = new JSONArray();
            for (Map.Entry<String, Stations> entry : stationsList.entrySet()) {
                JSONObject object = new JSONObject();
                if (entry.getValue().getName() != null) {
                    object.put("name", entry.getValue().getName());
                }
                if (entry.getValue().getLine() != null) {
                    object.put("line", entry.getValue().getLine());
                }
                if (entry.getValue().getDate() != null) {
                    object.put("date", entry.getValue().getDate());
                }
                if (entry.getValue().getDepth() != null) {
                    object.put("depth", entry.getValue().getDepth());
                }
                object.put("hasConnection", entry.getValue().isHasConnect());
                jsonArray.add(object);
            }
            map.put("stations", jsonArray);
            writer.write(DataFile.GSON_DATE.toJson(map));
            writer.flush();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private static void setHasConnections(TreeSet<Connections> connections, Map<String, Stations> stationsList) {
        stationsList.keySet().forEach(i -> {
            for (Connections connection : connections) {
                for (Stations stations : connection.getConnectionStations()) {
                    if (stations.getName().equals(i)) {
                        stationsList.get(i).setHasConnect(true);
                    }
                }
            }
        });
    }

    private static void setNumberNameLine(List<Stations> stations, Map<String, Stations> stationsList) {
        for (var entry : stationsList.entrySet()) {
            for (Stations station : stations) {
                if (station.getName().toLowerCase().contains(entry.getKey().toLowerCase())) {
                    entry.getValue().setLine(station.getLine());
                }
            }
        }
    }
}
