import core.Line;
import core.Station;
import junit.framework.TestCase;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class RouteCalculatorTest extends TestCase{
    private Station begovaya;

    private List<Station> route1;
    private List<Station> route2;
    private List<Station> route3;
    private List<Station> route4;
    private List<Station> route5;
    private List<Station> route6;
    private List<Station> route7;
    private List<Station> route8;

    private RouteCalculator calculator;

    private List<Station> expectedEmpty;

    @Override
    public void setUp() throws Exception {

        route1 = new ArrayList<>();
        route2 = new ArrayList<>();
        route3 = new ArrayList<>();
        route4 = new ArrayList<>();
        route5 = new ArrayList<>();
        route6 = new ArrayList<>();
        route7 = new ArrayList<>();
        route8 = new ArrayList<>();

        Line line1 = new Line(1, "Первая");
        Line line2 = new Line(2, "Вторая");
        Line line3 = new Line(2, "Третья");

        Line non_existent_line = new Line(4, "Несуществующая");

        StationIndex stationIndex = new StationIndex();

        begovaya = new Station("Беговая", line1);
        Station gostinyi_dvor = new Station("Гостиный двор", line1);
        Station gorkovskaya = new Station("Горьковская", line2);
        Station sportivnaya = new Station("Спортивная", line2);
        Station kupchino = new Station("Купчино", line2);
        Station levaya = new Station("Левая", line3);
        Station admiralteyskaya = new Station("Адмиралтейская", line3);
        Station sadovaya = new Station("Садовая", line3);
        Station non_existent = new Station("Не существующая", non_existent_line);

        Stream.of(begovaya, gostinyi_dvor).forEach(line1::addStation);
        Stream.of(gorkovskaya, sportivnaya, kupchino).forEach(line2::addStation);
        Stream.of(levaya, admiralteyskaya, sadovaya).forEach(line3::addStation);

        Stream.of(begovaya, gostinyi_dvor, gorkovskaya, sportivnaya, kupchino, levaya, admiralteyskaya,
                sadovaya).forEach(stationIndex::addStation);

        Stream.of(line1, line2, line3).forEach(stationIndex::addLine);

        stationIndex.addConnection(Stream.of(gostinyi_dvor, sportivnaya).collect(Collectors.toList()));
        stationIndex.addConnection(Stream.of(kupchino, sadovaya).collect(Collectors.toList()));

        calculator = new RouteCalculator(stationIndex);
        route1.add(begovaya);
        route2 = calculator.getShortestRoute(begovaya, gostinyi_dvor);
        route3 = calculator.getShortestRoute(begovaya, sportivnaya);
        route4 = calculator.getShortestRoute(begovaya, sadovaya);

        route5 = calculator.getShortestRoute(gostinyi_dvor, begovaya);
        route6 = calculator.getShortestRoute(sportivnaya, begovaya);
        route7 = calculator.getShortestRoute(sadovaya, begovaya);
        route8 = calculator.getShortestRoute(begovaya, non_existent);

    }

    @Test
    public void testCalculateDurationDirect()  {
        expectedEmpty = Stream.of(begovaya).collect(Collectors.toList());
        double actual = RouteCalculator.calculateDuration(route4);
        double expected = 11.0;
        Assert.assertEquals("не соответствует длительность времени для маршрута с двумя пересадками", expected, actual, 0);

        actual = RouteCalculator.calculateDuration(route1);
        expected = 0.0;
        Assert.assertEquals("не соответствует длительность времени для маршрута, состоящего из одной станции", expected, actual, 0);

        actual = RouteCalculator.calculateDuration(route2);
        expected = 2.5;
        Assert.assertEquals("не соответствует длительность времени для маршрута, состоящего их двух станций", expected, actual, 0);

        actual = RouteCalculator.calculateDuration(route3);
        expected = 6.0;
        Assert.assertEquals("не соответствует длительность времени для маршрута с одной пересадкой", expected, actual, 0);
    }

    @Test
    public void testCalculateDurationReverse() {
        double actual = RouteCalculator.calculateDuration(route7);
        double expected = 11.0;
        Assert.assertEquals("не соответствует длительность времени для маршрута с двумя пересадками", expected, actual, 0);

        actual = RouteCalculator.calculateDuration(route5);
        expected = 2.5;
        Assert.assertEquals("не соответствует длительность времени для маршрута, состоящего их двух станций", expected, actual, 0);

        actual = RouteCalculator.calculateDuration(route6);
        expected = 6.0;
        Assert.assertEquals("не соответствует длительность времени для маршрута с одной пересадкой", expected, actual, 0);
    }

    @Test
    public void testGetShortestRoute() {
        Line line1 = new Line(1, "Первая");
        Line line2 = new Line(2, "Вторая");
        Line line3 = new Line(2, "Третья");

        Station gostinyi_dvor = new Station("Гостиный двор", line1);
        Station sportivnaya = new Station("Спортивная", line2);
        Station kupchino = new Station("Купчино", line2);
        Station sadovaya = new Station("Садовая", line3);

        expectedEmpty = Stream.of(begovaya).collect(Collectors.toList());
        List<Station> expectedThirst = Stream.of(begovaya, gostinyi_dvor).collect(Collectors.toList());
        List<Station> expectedSecond = Stream.of(begovaya, gostinyi_dvor, sportivnaya).collect(Collectors.toList());
        List<Station> expectedThird = Stream.of(begovaya, gostinyi_dvor, sportivnaya, kupchino, sadovaya).collect(Collectors.toList());


        List<Station> actualEmpty = calculator.getShortestRoute(begovaya, begovaya);
        Assert.assertEquals("перечень станций в маршруте не соответствует для машрута, состоящего из одной станции", expectedEmpty, actualEmpty);

        List<Station> actualThirst = route2;
        Assert.assertEquals("перечень станций не соответствует для маршрута без пересадок", expectedThirst, actualThirst);

        List<Station> actualSecond = route3;
        Assert.assertEquals("перечень станций не соответствует для маршрута с одной пересадкой", expectedSecond, actualSecond);

        List<Station> actualThird = route4;
        Assert.assertEquals("перечень станций не соответствует для машрута с двумя пересадками", expectedThird, actualThird);
    }

    @Test
    public void testGetShortestRoute2() {
        Line line1 = new Line(1, "Первая");
        Line line2 = new Line(2, "Вторая");
        Line line3 = new Line(2, "Третья");

        Station gostinyi_dvor = new Station("Гостиный двор", line1);
        Station sportivnaya = new Station("Спортивная", line2);
        Station sadovaya = new Station("Садовая", line3);
        Station kupchino = new Station("Купчино", line2);

        List<Station> expectedThirstReversed = Stream.of(gostinyi_dvor, begovaya).collect(Collectors.toList());
        List<Station> expectedSecondReversed = Stream.of(sportivnaya, gostinyi_dvor, begovaya).collect(Collectors.toList());
        List<Station> expectedThirdReversed = Stream.of(sadovaya, kupchino, sportivnaya, gostinyi_dvor, begovaya).collect(Collectors.toList());

        List<Station> actualThirstReversed = route5;
        Assert.assertEquals("перечень станций не соответствует для маршрута без пересадок", expectedThirstReversed, actualThirstReversed);

        List<Station> actualSecondReversed = route6;
        Assert.assertEquals("перечень станций не соответствует для маршрута с одной пересадкой", expectedSecondReversed, actualSecondReversed);

        List<Station> actualThirdReversed = route7;
        Assert.assertEquals("перечень станций не соответствует для машрута с двумя пересадками", expectedThirdReversed, actualThirdReversed);
    }

    @Test
    public void testGetShortestRoute3() {
        List<Station> expectedThirstNone = new ArrayList<>();
        List<Station> actualThirstNone = route8;
        Assert.assertEquals("перечень станций не соответствует для маршрута с несуществующей станцией метро", expectedThirstNone, actualThirstNone);
    }
}

