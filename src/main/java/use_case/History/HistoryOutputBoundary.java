package use_case.History;

public interface HistoryOutputBoundary {
    void prepareSuccessView(HistoryOutputData historyOutputData);

    void updatestatus(HistoryOutputData historyOutputData);
}
