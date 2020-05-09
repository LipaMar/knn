public final class Distance {
    private Distance() {
    }

    public static double euclideanDistance(double[] learningData, double[] testingData) {
        return minkowskiDistance(learningData, testingData, 2);
    }

    public static double cityBlockDistance(double[] learningData, double[] testingData) {
        double sum = 0;
        for (int i = 0; i < learningData.length; i++) {
            sum += Math.abs(learningData[i] - testingData[i]);
        }
        return sum;
    }

    public static double minkowskiDistance(double[] learningData, double[] testingData, double p) {
        double sum = 0;
        for (int i = 0; i < learningData.length; i++) {
            sum += Math.abs(Math.pow(learningData[i] - testingData[i], p));
        }
        return Math.pow(sum, 1.0 / p);
    }
}
