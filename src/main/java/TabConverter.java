import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class TabConverter {
    public static ArrayList<TestCase> getTestCasesArrayList(InputStream stream, boolean hasHeaders) {
        ArrayList<TestCase> result = new ArrayList<>();
        Scanner in = null;
        in = new Scanner(stream);

        if (hasHeaders) {
            in.nextLine();
        }

        while (in.hasNextLine()) {
            String line = in.nextLine().trim();
            ArrayList<String> data = new ArrayList<String>(Arrays.asList(line.split(" ")));
            String decision = data.get(data.size() - 1);
            data.remove(decision);
            TestCase testCase = new TestCase(data.toArray(new String[0]), decision);
            result.add(testCase);
        }

        in.close();
        return result;
    }

    public static String[] getHeaders(String path) {
        String[] result = null;
        try {
            Scanner in = new Scanner(new File(path));
            if (in.hasNextLine()) {
                result = in.nextLine().trim().split(" ");
            }
            in.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return result;
    }

}
