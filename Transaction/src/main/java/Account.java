import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Account implements Comparable<Account> {

    private long money;

    private String accNumber;

    private boolean isBlocked;

    @Override
    public int compareTo(Account o) {
        return this.getAccNumber().compareTo(o.getAccNumber());
    }
}
