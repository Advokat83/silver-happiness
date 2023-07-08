import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public abstract class DataFile {

    private static final String HTML_FILE = "date/metro.html";//Путь получения страницы Московского метрополитена

    public static String DATA_FOLDERS = "date/BypassFolders";//Путь для поиска файлов (*json/*csv) и получения данных из них

    public static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();//Для записи первого json

    public static final Gson GSON_DATE = new GsonBuilder().setPrettyPrinting().create();//Для записи второго json

    public static String ParsePage() { //Получение страницы Московского метрополитена

        StringBuilder builder = new StringBuilder();

        try {
            List<String> lines = Files.readAllLines(Paths.get(HTML_FILE));
            lines.forEach(Line -> builder.append(Line + "\n"));
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
        return builder.toString();
    }
}


