package interface_adapter.grid;

import entity.CellResult;
import entity.GuessResult;
import interface_adapter.ViewManagerModel;
import use_case.grid.GridOutputBoundary;
import interface_adapter.logout.GameEndViewModel;

import java.beans.PropertyChangeSupport;

/**
 * The Presenter for the Grid Use Case.
 */
public class GridPresenter implements GridOutputBoundary {

    private final GridViewModel gridViewModel;
    private final ViewManagerModel viewManagerModel;
    private final GameEndViewModel gameEndViewModel;
    private final PropertyChangeSupport support;

    public GridPresenter(ViewManagerModel viewManagerModel, GridViewModel gridViewModel, GameEndViewModel gameEndViewModel) {
        this.viewManagerModel = viewManagerModel;
        this.gridViewModel = gridViewModel;
        this.gameEndViewModel = gameEndViewModel;
        this.support = new PropertyChangeSupport(this);
    }

    @Override
    public void presentGuessResult(GuessResult result) {
        int currentRow = gridViewModel.getCurrentRow(); // Assumes GridViewModel tracks current row

        for (int col = 0; col < result.getCellResults().size(); col++) {
            CellResult cellResult = result.getCellResults().get(col);

            gridViewModel.setCellContent(currentRow, col, String.valueOf(cellResult.getLetter()));
            gridViewModel.setCellCorrectPosition(currentRow, col, cellResult.isCorrectPosition());
            gridViewModel.setCellCorrectLetter(currentRow, col, cellResult.isCorrectLetter());
        }

        support.firePropertyChange("gridUpdate", null, gridViewModel);
    }

    @Override
    public void presentWin(GuessResult result) {
        // present win, shows dialog box of win
    }

    @Override
    public void presentLoss(GuessResult result) {
        // present loss, shows dialog box of loss
    }

    @Override
    public void switchToGameEndView() {
        gameEndViewModel.firePropertyChange("viewDisplayed", false, true);
        viewManagerModel.setState(gameEndViewModel.getViewName());
        viewManagerModel.firePropertyChanged();
    }

    @Override
    public void resetGridView() {
        gridViewModel.resetGrid();
        // support.firePropertyChange("gridReset", null, gridViewModel);
    }
}
