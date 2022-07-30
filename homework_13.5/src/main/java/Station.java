import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Station {
    String name;
    String numberLine;
}
