import com.google.gson.annotations.SerializedName;
import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
@Builder
public class Subway {
    @SerializedName("lines")
    private List<Line> lines;
    @SerializedName("stations")
    private Map<String, List<String>> stations;
    @SerializedName("connections")
    private List<List<Station>> connection;
}
