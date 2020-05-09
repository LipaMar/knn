import java.util.ArrayList;

public class IrisTestCase extends TestCase {
    private static int nextCase = 1;
    private static String[] atributeNames;
    private double sepalLength;
    private double sepalWidth;
    private double petalLength;
    private double petalWidth;
    private int caseNr;

    public IrisTestCase(String[] data, String decision) {
        super(data, decision);
        loadAtributes();
        caseNr = nextCase++;
    }

    public static void setAtributeNames(String[] names) {
        atributeNames = names;
    }

    public static ArrayList<IrisTestCase> convertTestCaseArrayList(ArrayList<TestCase> testCasesArrayList) {
        ArrayList<IrisTestCase> result = new ArrayList<>();
        for (TestCase testCase : testCasesArrayList) {
            result.add(new IrisTestCase(testCase.getData(), testCase.getDecision()));
        }
        return result;
    }

    private void loadAtributes() {
        String[] data = getData();
        try {
            sepalLength = Double.parseDouble(data[0]);
            sepalWidth = Double.parseDouble(data[1]);
            petalLength = Double.parseDouble(data[2]);
            petalWidth = Double.parseDouble(data[3]);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        } catch (IndexOutOfBoundsException e) {
            e.printStackTrace();
        }
    }

    public static String[] getAtributeNames() {
        return atributeNames;
    }

    public double getSepalLength() {
        return sepalLength;
    }

    public double getSepalWidth() {
        return sepalWidth;
    }

    public double getPetalLength() {
        return petalLength;
    }

    public double getPetalWidth() {
        return petalWidth;
    }

    public double[] getDataDouble() {
        return new double[]{sepalLength, sepalWidth, petalLength, petalWidth};
    }

    public int getCaseNr() {
        return caseNr;
    }

    @Override
    public String toString() {
        return "IrisTestCase{" +
                "caseNr=" + getCaseNr() +
                ", sepalLength=" + sepalLength +
                ", sepalWidth=" + sepalWidth +
                ", petalLength=" + petalLength +
                ", petalWidth=" + petalWidth +
                ", DECISION=" + getDecision() +
                "\n}";
    }
}
