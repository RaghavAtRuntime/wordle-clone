package use_case.grid;


import entity.GuessResult;

public interface GridOutputBoundary {
    void switchToGameEndView();

    void presentGuessResult(GuessResult result);

    void presentLoss(GuessResult result);
    void presentWin(GuessResult result);
    void resetGridView();


}
