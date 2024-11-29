package use_case.History;

import use_case.service.UserService;

public class HistoryInteractor implements HistoryInputBoundary {
    private final UserService userService;
    private final HistoryOutputBoundary historyPresenter;

    public HistoryInteractor(UserService userService, HistoryOutputBoundary historyPresenter) {
        this.userService = userService;
        this.historyPresenter = historyPresenter;
    }

    @Override
    public void execute(HistoryInputData historyInputData){

        String name = historyInputData.getUsername();
        int win = userService.getUserWins(name);
        int loss = userService.getUserLosses(name);
        final HistoryOutputData historyOutputData = new HistoryOutputData(name, win, loss, false) {
        };
        historyPresenter.prepareSuccessView(historyOutputData);
    }
}