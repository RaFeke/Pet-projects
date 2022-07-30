import org.json.simple.parser.ParseException;


public class Main {
    public static final String WEB_SITE = "https://www.moscowmap.ru/metro.html#lines";

    public static void main(String[] args) throws ParseException {
        Parser.parseHtml(WEB_SITE);
        Parser.JsonParser();

    }
}
