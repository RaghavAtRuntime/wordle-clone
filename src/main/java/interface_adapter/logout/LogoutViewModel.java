package interface_adapter.logout;

import interface_adapter.ViewModel;
import interface_adapter.login.LoginState;
import view.LogoutView;

public class LogoutViewModel extends ViewModel<LogoutState> {

    public LogoutViewModel() {
        super("log out");
        setState(new LogoutState());
    }
}
