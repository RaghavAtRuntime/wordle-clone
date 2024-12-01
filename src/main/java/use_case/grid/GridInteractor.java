package use_case.grid;

import entity.GameState;
import entity.GuessResult;
import use_case.service.UserService;

public class GridInteractor implements GridInputBoundary {
    private final GridOutputBoundary gridPresenter;
    private final GameRepository gameRepository;
    private final UserService userService;

    public GridInteractor(GridOutputBoundary outputBoundary, GameRepository gameRepository, UserService userService) {
        this.gridPresenter = outputBoundary;
        this.gameRepository = gameRepository;
        this.userService = userService;
    }

    @Override
    public void execute(GridInputData gridInputData) {
        GuessResult result = checkGuess(gridInputData.getGuess());
        gridPresenter.presentGuessResult(result);
    }

    @Override
    public void switchToGameEndView() {
        gridPresenter.switchToGameEndView();
    }

    @Override
    public GuessResult checkGuess(String guess) {
        GameState gameState = gameRepository.getGameState();
        gameState.setRemainingAttempts(gameState.getRemainingAttempts() - 1);
        GuessResult result = gameState.checkGuess(guess);
        gameRepository.saveGameState(gameState);
        return result;
    }

    @Override
    public void recordGameResult(boolean userWon) {

    }

    @Override
    public void handleEnter(int row, String guessedWord) {
        GuessResult result = checkGuess(guessedWord);
        if (result.isCorrect()) {
            gridPresenter.presentWin(result);
        } else {
            gridPresenter.presentGuessResult(result);
        }
    }
}
