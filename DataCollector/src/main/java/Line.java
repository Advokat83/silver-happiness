public class Line implements Comparable<Line> {

    private final String name;
    private final String number;

    public Line(String number, String name) {
        this.number = number.trim().charAt(0) == '0' ?
                number.substring(1).trim() : number.trim();
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String getNumber() {
        return number;
    }

    @Override
    public int compareTo(Line line) {

        return number.compareToIgnoreCase(line.getNumber());
    }

    @Override
    public boolean equals(Object obj) {
        return compareTo((Line) obj) == 0;
    }

}
