import com.skillbox.airport.Airport;
import com.skillbox.airport.Flight;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {


    }

    public static List<Flight> findPlanesLeavingInTheNextTwoHours(Airport airport) {
        LocalDateTime dateNow = LocalDateTime.now();
        return airport.getTerminals().stream()
                .flatMap(terminal -> terminal.getFlights().stream())
                .filter(flight -> flight.getType().equals(Flight.Type.DEPARTURE))
                .filter(flight -> modifiedDate(flight)
                .isAfter(dateNow) && modifiedDate(flight)
                        .isBefore(dateNow.plusHours(2)))
                .collect(Collectors.toList());
    }
    public static LocalDateTime modifiedDate(Flight date) {
        return LocalDateTime
                .ofInstant(date.getDate().toInstant(), ZoneId.systemDefault());
    }

}