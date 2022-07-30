import java.io.FileWriter;
import java.io.IOException;

public class Writer
{
    private static final String siteMap = "src/main/resources/map.txt";

    public void getWriting(String s){
        try {
            FileWriter fileWriter = new FileWriter(Writer.siteMap, true);
            fileWriter.write(s);
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
