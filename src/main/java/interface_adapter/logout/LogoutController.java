package interface_adapter.logout;

import use_case.login.LoginInputBoundary;
import use_case.login.LoginInputData;
import use_case.login.LoginOutputBoundary;
import use_case.logout.LogoutInputBoundary;
import use_case.logout.LogoutInputData;

public class LogoutController {
    private final LogoutInputBoundary logoutUseCaseInteractor;

    public LogoutController(LogoutInputBoundary logoutUseCaseInteractor) {
        this.logoutUseCaseInteractor = logoutUseCaseInteractor;
    }

    public void execute(String username) {
        final LogoutInputData logoutInputData = new LogoutInputData(username);
        logoutUseCaseInteractor.execute(logoutInputData);
    }

    public void switchTogridView() {
        logoutUseCaseInteractor.switchTogridView();
    }
}