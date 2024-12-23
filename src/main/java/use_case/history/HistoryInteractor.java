package use_case.history;

import use_case.service.UserService;

/**
 * Interactor for the History use case.
 */
public class HistoryInteractor implements HistoryInputBoundary {
    private final UserService userService;
    private final HistoryOutputBoundary historyPresenter;

    public HistoryInteractor(UserService userService, HistoryOutputBoundary historyPresenter) {
        this.userService = userService;
        this.historyPresenter = historyPresenter;
    }

    @Override
    public void execute(HistoryInputData historyInputData) {
        String name = historyInputData.getUsername();
        int win = userService.getUserWins(name);
        int loss = userService.getUserLosses(name);
        String state = userService.getStatus(name);
        final HistoryOutputData historyOutputData = new HistoryOutputData(name, win, loss, state, false);
        historyPresenter.prepareSuccessView(historyOutputData);
    }

    @Override
    public void updateStatus(HistoryInputData historyInputData) {

        String name = historyInputData.getUsername();
        int win = userService.getUserWins(name);
        int loss = userService.getUserLosses(name);
        String status = historyInputData.getStatus();

        userService.setStatus(name, status);

        final HistoryOutputData historyOutputData = new HistoryOutputData(name, win, loss, status, false);
        historyPresenter.updateStatus(historyOutputData);
    }
}
