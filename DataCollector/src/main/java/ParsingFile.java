import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.util.*;

public class ParsingFile {

    public static Map<String, Stations> stationsList = new HashMap<>();

    public static Map<String, Stations> getStationsList() {
        return stationsList;
    }

    public Map<String, Stations> searchToFolder (String path) throws IOException {
        File doc = new File(path);
            if (doc.isFile()) {
                DataFile.DATA_FOLDERS = doc.getAbsolutePath();
                if (doc.getName().endsWith(".json")) {
                    System.out.println(doc);
                    getParseJson(doc);
                }

                if (doc.getName().endsWith(".csv")) {
                    System.out.println(doc);
                    getParseFormatCsv(doc);
                }
            }
            else {
                File[] files = doc.listFiles();
                for (File file : files) {
                    searchToFolder(file.getAbsolutePath());
            }
        }
        return stationsList;
    }

    private void getParseJson(File doc) {

        JSONParser parser = new JSONParser();
        String filePath = doc.getAbsolutePath();

        try {
            FileReader reader = new FileReader(filePath);
            JSONArray jsonData = (JSONArray) parser.parse(reader);
            for (int i = 0; i < jsonData.size(); i++) {
                JSONObject object = (JSONObject) jsonData.get(i);
                String stationName = (String) object.get("station_name");
                if (!stationsList.containsKey(stationName)) {
                    stationsList.put(stationName, new Stations(stationName));

                }
                if (doc.getName().startsWith("dates")) {
                    String date = (String) object.get("dates");
                    stationsList.get(stationName).setDate(date);
                }
                else if (doc.getName().startsWith("depth")) {
                    String depth = (String) object.get("depth");
                    stationsList.get(stationName).setDepth(depth);
                    System.out.println(stationName + "\n" + depth);
                }
            }
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    private void getParseFormatCsv(File doc) throws FileNotFoundException {
        String filePath = doc.getAbsolutePath();
        BufferedReader reader = new BufferedReader(new FileReader(filePath));
        try {
            String splitBy = ",";
            String line = "";
            while ((line = reader.readLine()) != null) {
                String[] lines = line.split(splitBy);
                for (int i = 0; i < lines.length; i++) {
                    if (i % 2 == 0) {
                        String stationName = lines[i];
                        if (!stationsList.containsKey(stationName)) {
                            stationsList.put(stationName, new Stations(stationName));
                        }
                        if (doc.getName().startsWith("dates")) {
                            stationsList.get(stationName).setDate(lines[++i]);
                        } else if (doc.getName().startsWith("depth")) {
                            stationsList.get(stationName).setDepth(lines[++i]);
                        }
                        System.out.println(stationName + " " + lines[i]);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
