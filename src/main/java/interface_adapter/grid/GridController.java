package interface_adapter.grid;

import entity.GuessResult;
import use_case.grid.GridInputBoundary;
import use_case.grid.GridInputData;

/**
 * The controller for the Grid Use Case.
 */
public class GridController {

    private final GridInputBoundary gridInteractor;
    private final GridState gridState; // Reference to GridState

    public GridController(GridInputBoundary gridInteractor, GridState gridState) {
        this.gridInteractor = gridInteractor;
        this.gridState = gridState;
    }

    /**
     * Executes the Grid Use Case.
     * @param row the row index in the grid
     * @param col the column index in the grid
     * @param letter the letter entered in the grid
     */
    public void execute(int row, int col, String letter) {
        gridState.setCellContent(row, col, letter);
        final GridInputData gridInputData = new GridInputData(row, col, letter);
        gridInteractor.execute(gridInputData);
    }

    /**
     * Requests a transition to signout view when implemented.
     */
    public void switchToGameEndView() {
        gridInteractor.switchToGameEndView();
    }

    /**
     * Sets the target word for the game by delegating to GridState.
     * @param targetWord the target word to be guessed
     */
    public void setTargetWord(String targetWord) {
        gridState.setTargetWord(targetWord);
    }

    /**
     * Checks if the guessed word matches the target word by delegating to GridState.
     * @param guessedWord the word guessed by the player
     * @return the GuessResult object containing feedback
     */
    public GuessResult checkWord(String guessedWord) {
        return gridInteractor.checkGuess(guessedWord);
    }

    /**
     * Records win/loss for the user
     * @param userWon whether the user won or not
     */
    public void recordGameResult(boolean userWon){
        gridInteractor.recordGameResult(userWon);
    }

    public void handleEnter(int row, String letter) {
        gridInteractor.handleEnter(row, letter);
    }
}
