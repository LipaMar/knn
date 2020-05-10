import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

public class KNN {
    private final ArrayList<IrisTestCase> learningCases;
    private final ArrayList<IrisTestCase> testingCases;
    private final ResultArray euResult;
    private final ResultArray cbResult;
    private final ResultArray m2Result;
    private final ArrayList<CaseWithDistance> euclideanDistance;
    private final ArrayList<CaseWithDistance> cityBlockDistance;
    private final ArrayList<CaseWithDistance> minkowskiDistance;

    private final boolean[][][] euclideanErrorArray;
    private final boolean[][][] cityBlockErrorArray;
    private final boolean[][][] minkowskiErrorArray;

    private final int maxNumberOfNeighbors;

    public KNN(ArrayList<IrisTestCase> learningCases, ArrayList<IrisTestCase> testingCases, int maxNumberOfNeighbors) {

        this.maxNumberOfNeighbors = maxNumberOfNeighbors;
        this.learningCases = learningCases;
        this.testingCases = testingCases;
        this.euResult = new ResultArray();
        this.cbResult = new ResultArray();
        this.m2Result = new ResultArray();
        testingCases.forEach(case1 -> euResult.addTestCaseDecision(case1.getDecision()));
        testingCases.forEach(case1 -> cbResult.addTestCaseDecision(case1.getDecision()));
        testingCases.forEach(case1 -> m2Result.addTestCaseDecision(case1.getDecision()));
        int votingMethods = 3;
        int xSize = testingCases.size();
        euclideanErrorArray = new boolean[votingMethods][xSize][maxNumberOfNeighbors];
        cityBlockErrorArray = new boolean[votingMethods][xSize][maxNumberOfNeighbors];
        minkowskiErrorArray = new boolean[votingMethods][xSize][maxNumberOfNeighbors];

        euclideanDistance = new ArrayList<>();
        cityBlockDistance = new ArrayList<>();
        minkowskiDistance = new ArrayList<>();
    }

    public void test() {
        for (IrisTestCase testingCase : testingCases) {
            calculateDistance(testingCase);
            sortDistances();
            topValuesCheck(euclideanDistance, testingCase, euclideanErrorArray,euResult);
            topValuesCheck(cityBlockDistance, testingCase, cityBlockErrorArray,cbResult);
            topValuesCheck(minkowskiDistance, testingCase, minkowskiErrorArray,m2Result);
            resetDistances();
        }
    }

    private void topValuesCheck(ArrayList<CaseWithDistance> distance, IrisTestCase testCase, boolean[][][] errorArray,ResultArray results) {
        for (int valuesInArray = 1; valuesInArray <= maxNumberOfNeighbors; valuesInArray++) {
            ArrayList<CaseWithDistance> arrayToCheck = minimizeArray(distance, valuesInArray);
            errorArray[0][testingCases.indexOf(testCase)][valuesInArray - 1] = conductNormalVoting(arrayToCheck, testCase,results);
            errorArray[1][testingCases.indexOf(testCase)][valuesInArray - 1] = conductSumOfTheInverseSquareVoting(arrayToCheck, testCase,results);
            errorArray[2][testingCases.indexOf(testCase)][valuesInArray - 1] = conductDistanceSumVoting(arrayToCheck, testCase,results);
        }
    }

    private boolean conductNormalVoting(ArrayList<CaseWithDistance> arrayToCheck, IrisTestCase testCase,ResultArray result) {
        String vote = normalVote(arrayToCheck);
        result.addNormalVoteCasesDecision(vote,arrayToCheck.size()-1);
        return vote.equals(testCase.getDecision());
    }

    private boolean conductSumOfTheInverseSquareVoting(ArrayList<CaseWithDistance> arrayToCheck, IrisTestCase testCase,ResultArray result) {
        String vote = sumOfTheInverseSquareVote(arrayToCheck);
        result.addInverseVoteMethodCasesDecision(vote,arrayToCheck.size()-1);
        return vote.equals(testCase.getDecision());
    }

    private boolean conductDistanceSumVoting(ArrayList<CaseWithDistance> arrayToCheck, IrisTestCase testCase,ResultArray result) {
        String vote = distanceSumVote(arrayToCheck);
        result.addSumVoteMethodCasesDecision(vote,arrayToCheck.size()-1);
        return vote.equals(testCase.getDecision());
    }

    private <E> ArrayList<E> minimizeArray(ArrayList<E> arrayList, int toSize) {
        return new ArrayList<E>(arrayList.subList(0, toSize));
    }

    private void calculateDistance(IrisTestCase testingCase) {
        for (IrisTestCase learningCase : learningCases) {
            euclideanDistance.add(new CaseWithDistance(learningCase, Distance.euclideanDistance(learningCase.getDataDouble(), testingCase.getDataDouble())));
            cityBlockDistance.add(new CaseWithDistance(learningCase, Distance.cityBlockDistance(learningCase.getDataDouble(), testingCase.getDataDouble())));
            minkowskiDistance.add(new CaseWithDistance(learningCase, Distance.minkowskiDistance(learningCase.getDataDouble(), testingCase.getDataDouble(), 3)));
        }

    }

    private void resetDistances() {
        euclideanDistance.clear();
        cityBlockDistance.clear();
        minkowskiDistance.clear();
    }

    private void sortDistances() {
        Collections.sort(euclideanDistance);
        Collections.sort(cityBlockDistance);
        Collections.sort(minkowskiDistance);
    }

    private String normalVote(ArrayList<CaseWithDistance> candidates) {
        CaseWithDistance best = null;
        int max = 0;
        for (CaseWithDistance candidate : candidates) {
            int frequency = frequency(candidates, candidate);
            if (max < frequency) {
                max = frequency;
                best = candidate;
            }
        }
        return best.getIrisCase().getDecision();
    }

    public String distanceSumVote(ArrayList<CaseWithDistance> candidates) {
        Map<String, Double> map = new LinkedHashMap<>();
        for (CaseWithDistance candidate : candidates) {
            String decisionAsKey = candidate.getIrisCase().getDecision();
            Double addition = candidate.getDistance();

            if (map.get(decisionAsKey) == null) map.put(decisionAsKey, addition);
            else {
                Double value = map.get(decisionAsKey);
                map.put(decisionAsKey, value + addition);
            }
        }
        String best = null;
        double min = Double.MAX_VALUE;
        for (String key : map.keySet()) {
            ArrayList<String> decisions = new ArrayList<>();
            candidates.forEach(caseWithDistance -> decisions.add(caseWithDistance.getIrisCase().getDecision()));
            double value = map.get(key) / Collections.frequency(decisions, key);
            if (Double.compare(value, min) < 0) {
                min = value;
                best = key;
            }
        }
        return best;
    }

    public String sumOfTheInverseSquareVote(ArrayList<CaseWithDistance> candidates) {
        Map<String, Double> map = new LinkedHashMap<>();
        for (CaseWithDistance candidate : candidates) {
            String decisionAsKey = candidate.getIrisCase().getDecision();
            Double addition = 1d / Math.pow(candidate.getDistance(), 2);

            if (map.get(decisionAsKey) == null) map.put(decisionAsKey, addition);
            else {
                Double value = map.get(decisionAsKey);
                map.put(decisionAsKey, value + addition);
            }
        }
        String best = null;
        double min = Double.MAX_VALUE;
        for (String key : map.keySet()) {
            double value = map.get(key);
            if (Double.compare(value, min) < 0) {
                min = value;
                best = key;
            }
        }
        return best;
    }

    private int frequency(ArrayList<CaseWithDistance> collection, CaseWithDistance object) {
        int frequency = 0;
        if (object == null) {
            for (Object e : collection)
                if (e == null)
                    frequency++;
        } else {
            for (CaseWithDistance e : collection)
                if (object.getIrisCase().getDecision().equals(e.getIrisCase().getDecision()))
                    frequency++;
        }
        return frequency;
    }

    private static class CaseWithDistance implements Comparable<CaseWithDistance> {
        private final IrisTestCase irisCase;
        private final double distance;

        public CaseWithDistance(IrisTestCase irisCase, double distance) {
            this.irisCase = irisCase;
            this.distance = distance;
        }

        public IrisTestCase getIrisCase() {
            return this.irisCase;
        }

        public double getDistance() {
            return this.distance;
        }

        @Override
        public int compareTo(CaseWithDistance case1) {
            return Double.compare(this.distance, case1.distance);
        }

    }

    public boolean[][][] getEuclideanErrorArray() {
        return euclideanErrorArray;
    }

    public boolean[][][] getCityBlockErrorArray() {
        return cityBlockErrorArray;
    }

    public boolean[][][] getMinkowskiErrorArray() {
        return minkowskiErrorArray;
    }

    public ResultArray getEuResult() {
        return euResult;
    }

    public ResultArray getCbResult() {
        return cbResult;
    }

    public ResultArray getM2Result() {
        return m2Result;
    }
}
