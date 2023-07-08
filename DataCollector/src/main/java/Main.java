import java.io.IOException;

public class Main {
    public static void main(String[] args) throws Exception {

        startParsingWebPage(); //Парсинг страницы Московского метро
        startParsing(); //Парсинг Json и Csv файлов
        writeJson();//Запись Json файлов
    }

    private static void startParsingWebPage() {

        Webpage webpage = new Webpage();
        System.out.println("Получение страницы московского метро: ");
        System.out.println(DataFile.ParsePage());

        System.out.println("Получение номера и имени линии Московского метро: ");
        webpage.parseLine();

        System.out.println("\n" + "Получение номера линии и название станций линии: ");
        webpage.parseStations();

        System.out.println("\n" + "Получение пересадок со станций: ");
        webpage.parseConnections();
    }

    public static void startParsing() throws Exception { //Парсинг Json - Csv файлов

        ParsingFile parsingFile = new ParsingFile();
        String path = DataFile.DATA_FOLDERS;
        parsingFile.searchToFolder(path);
    }

    public static void writeJson() throws IOException {

        AddingData data = new AddingData();

        Webpage.mapWriteJson(DataFile.ParsePage());//Запись 1-го *json

        data.stationWriteJson();//Запись 2-го *json

    }

}
