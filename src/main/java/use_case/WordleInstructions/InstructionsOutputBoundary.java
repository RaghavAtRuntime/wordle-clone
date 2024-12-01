package use_case.WordleInstructions;



public interface InstructionsOutputBoundary {
    /**
     * Executes the WordleInstructions use case.
     */
    void present(InstructionsOutputData outputData);
    void prepareSuccessView(InstructionsOutputData outputData);
    void prepareFailView(String errorMessage);
    void switchToGridView();
    void switchToDiscussionBoardView();
    void switchToProfileView();
}
