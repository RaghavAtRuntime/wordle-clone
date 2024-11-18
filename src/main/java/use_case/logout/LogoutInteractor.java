package use_case.logout;

import use_case.service.UserService;

import java.util.HashMap;
import java.util.Map;

/**
 * The Logout Interactor.
 */
public class LogoutInteractor implements LogoutInputBoundary {
    private UserService userService;
    private LogoutOutputBoundary logoutPresenter;

    public LogoutInteractor(UserService userService,
                            LogoutOutputBoundary logoutOutputBoundary) {
        this.userService = userService;
        this.logoutPresenter = logoutOutputBoundary;
    }

    @Override
    public void execute(LogoutInputData logoutInputData) {

        final String name = logoutInputData.getUsername();
        userService.setCurrentUsername(null);
        final LogoutOutputData logoutOutputData = new LogoutOutputData(name, false);
        logoutPresenter.prepareSuccessView(logoutOutputData);
    }

    @Override
    public void switchToGridView() { logoutPresenter.switchTogridView();}
}

