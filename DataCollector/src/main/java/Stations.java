public class Stations implements Comparable <Stations> {

    private String numberLine;
    private String name;
    private String line;
    private String date;
    private String depth;
    boolean hasConnect;

    public Stations (String name) {
        this.name = name;
    }

    public Stations(String name, String numberLine) {
        this.name = name;
        this.numberLine = numberLine;
    }

    public Stations (String name, String numberLine, String line) {
        this.name = name;
        this.numberLine = numberLine;
        this.line = line;
    }

    public String getNumberLine() {
        return numberLine;
    }

    public String getName() {
        return name;
    }

    public String getLine() {
        return line;
    }

    public String getDate() {
        return date;
    }

    public String getDepth() {
        return depth;
    }

    public boolean isHasConnect() {
        return hasConnect;
    }


    public void setName(String name) {
        this.name = name;
    }

    public void setLine(String line) {
        this.line = line;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setDepth(String depth) {
        this.depth = depth;
    }

    public void setHasConnect(boolean hasConnect) {
        this.hasConnect = hasConnect;
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public int compareTo(Stations station) {
        int lineComparison = numberLine.compareToIgnoreCase(station.getNumberLine());
        if(lineComparison != 0) {
            return lineComparison;
        }
        return name.compareToIgnoreCase(station.getName());
    }

    @Override
    public boolean equals(Object obj) {
        return compareTo((Stations) obj) == 0;
    }
}
