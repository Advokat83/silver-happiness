import java.util.*;

public class StationIndex {
    private final List<Stations> stations;
    private final TreeSet<Connections> connections;

    public StationIndex()
    {
        stations = new ArrayList<>();
        connections = new TreeSet<>();
    }

    public List<Stations> getStations() {
        return stations;
    }

    public TreeSet<Connections> getConnections() {
        return connections;
    }

    public void addStation(Stations station)
    {
        stations.add(station);
    }

    public void addConnections (Connections connectionStations) {
        if (!containsStation(connectionStations)) {
            connections.add(connectionStations);
        }
    }

    private boolean containsStation(Connections stationsCon) {
        for(Connections connection : connections) {
            for(Stations stations : connection.getConnectionStations()) {
                for(Stations stationInner : stationsCon.getConnectionStations()) {
                    if(stations.getName().equals(stationInner.getName()) &&
                            stations.getNumberLine().equals(stationInner.getNumberLine())) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
