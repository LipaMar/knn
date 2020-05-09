import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class Main {



    public static void main(String[] args) throws IOException {
        final  InputStream TESTING_TAB_PATH = Main.class.getResource("IRIS_T01.tab").openStream();
        final  InputStream LEARNING_TAB_PATH = Main.class.getResource("IRIS_L01.tab").openStream();

        ArrayList<IrisTestCase> learningCases = IrisTestCase.convertTestCaseArrayList(TabConverter.getTestCasesArrayList(LEARNING_TAB_PATH, true));
        ArrayList<IrisTestCase> testingCases = IrisTestCase.convertTestCaseArrayList(TabConverter.getTestCasesArrayList(TESTING_TAB_PATH, true));

        KNN knn = new KNN(learningCases, testingCases, 5);
        knn.startTesting();
        System.out.println("______METRYKA EUKLIDESA______");
        printTable(knn.getEuclideanErrorArray());
        System.out.println("______METRYKA CITY BLOCK______");
        printTable(knn.getCityBlockErrorArray());
        System.out.println("______METRYKA MINKOWSKIEGO______");
        printTable(knn.getMinkowskiErrorArray());
    }

    public static void printTable(boolean[][][] errorTable) {
        for (int i = 0; i < 3; i++) {
            switch (i) {
                case 0:
                    System.out.println("Zwykła metoda głosowania dla n sąsiadów");
                    break;
                case 1:
                    System.out.println("Metoda sumy odwrotności kwadratów odległości dla n sąsiadów");
                    break;
                case 2:
                    System.out.println("Metoda sumy odległości  dla n sąsiadów");
                    break;
            }
            System.out.printf(" n = %4d%6d%6d%n", 1, 3, 5);
            int k = 1;
            for (boolean[] b : errorTable[i]) {
                System.out.printf("%2d. ", (k++));
                for (int j = 0; j < b.length; j++) {
                    if (j % 2 == 1) continue;
                    System.out.printf(" %4d ", b[j] ? 1 : 0);
                }
                System.out.println();
            }
            calculateError(errorTable[i]);
            System.out.println();
        }
    }

    public static void calculateError(boolean[][] errorTable) {
        double size = errorTable.length;

        System.out.printf("Błąd:%n %3s", " ");
        for (int j = 0; j < errorTable[0].length; j++) {
            if (j % 2 == 1) continue;
            double sum = 0;
            for (int i = 0; i < size; i++) {
                sum += errorTable[i][j] ? 1d : 0d;
            }
            double result = (1d - (sum / size)) * 100;
            System.out.printf("%5.0f%%", result);
        }
        System.out.println();
    }
}
