package use_case.grid;


import entity.GuessResult;

public interface GridInputBoundary {

    /**
     * Executes the grid action use case.
     * @param gridInputData the input data
     */
    void execute(GridInputData gridInputData);
    void switchToGameEndView();

    GuessResult checkGuess(String guessedWord);

    void recordGameResult(boolean userWon);
    void handleEnter(int row, String guessedWord);
}


