import java.util.Arrays;

public class TestCase {
    private String decision;
    private String[] data;

    public TestCase(String[] data, String decision) {
        this.decision = decision;
        this.data = data;
    }

    public String getDecision() {
        return decision;
    }

    public void setDecision(String decision) {
        this.decision = decision;
    }

    public String[] getData() {
        return data;
    }

    public void setData(String[] data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "TestCase{" +
                "decision='" + decision + '\'' +
                ", data=" + Arrays.toString(data) +
                '}';
    }
}
