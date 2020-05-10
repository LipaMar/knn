import java.util.ArrayList;

public class ResultArray {
    private ArrayList<String> testingCases;
    private ArrayList<ArrayList<String>> normalVoteCases;
    private ArrayList<ArrayList<String>> inverseVoteMethodCases;
    private ArrayList<ArrayList<String>> sumVoteMethodCases;

    public ResultArray() {
        this.testingCases = new ArrayList<>();
        this.normalVoteCases = new ArrayList<>();
        this.inverseVoteMethodCases = new ArrayList<>();
        this.sumVoteMethodCases = new ArrayList<>();
        for(int i=0;i<5;i++){
            this.normalVoteCases.add(new ArrayList<String>());
            this.inverseVoteMethodCases.add(new ArrayList<String>());
            this.sumVoteMethodCases.add(new ArrayList<String>());
        }
    }

    public void addTestCaseDecision(String decision) {
        testingCases.add(decision);
    }

    public void addNormalVoteCasesDecision(String decision,int nr) {
        normalVoteCases.get(nr).add(decision);
    }

    public void addInverseVoteMethodCasesDecision(String decision,int nr) {
        inverseVoteMethodCases.get(nr).add(decision);
    }

    public void addSumVoteMethodCasesDecision(String decision,int nr) {
        sumVoteMethodCases.get(nr).add(decision);
    }

    public ArrayList<String> getTestingCases() {
        return testingCases;
    }

    public ArrayList<String> getNormalVoteCases(int nr) {
        return normalVoteCases.get(nr);
    }

    public ArrayList<String> getInverseVoteMethodCases(int nr) {
        return inverseVoteMethodCases.get(nr);
    }

    public ArrayList<String> getSumVoteMethodCases(int nr) {
        return sumVoteMethodCases.get(nr);
    }
}