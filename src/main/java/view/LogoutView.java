package view;

import data_access.repository.UserRepositoryImpl;
import interface_adapter.loggedin.LoggedInState;
import interface_adapter.login.LoginController;
import interface_adapter.login.LoginState;
import interface_adapter.login.LoginViewModel;
import interface_adapter.logout.LogoutController;
import interface_adapter.logout.LogoutState;
import interface_adapter.logout.LogoutViewModel;
import interface_adapter.security.PasswordHasher;
import use_case.service.UserService;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class LogoutView extends JPanel implements ActionListener, PropertyChangeListener {

    private final String viewName = "game end";
    private final LogoutViewModel logoutViewModel;

    private final JButton logout;
    private final JButton playagain;
    private final UserService userService;

//    private final LoginState loginState;


    private LogoutController logoutController;

    public LogoutView(LogoutViewModel logoutViewModel, UserService userService) {

        this.logoutViewModel = logoutViewModel;
        this.userService = userService;
//        this.loginState = loginState;
        this.logoutViewModel.addPropertyChangeListener(this);

        final JPanel stats = new JPanel();
        int wins = userService.getUserWins(userService.getCurrentUsername());
        int losses = userService.getUserLosses(userService.getCurrentUsername());

        //Calculate the winning rate
        double winRate = (wins + losses > 0) ? ((double) wins / (wins + losses)) * 100 : 0;

        // Create labels to display the stats
        JLabel winsLabel = new JLabel("Wins: " + wins);
        JLabel lossesLabel = new JLabel("Losses: " + losses);
        JLabel winRateLabel = new JLabel("Winning Rate: " + String.format("%.2f", winRate) + "%");

        // Set layout and add labels to the panel
        setLayout(new GridLayout(3, 1)); // Display stats in a vertical column
        stats.add(winsLabel);
        stats.add(lossesLabel);
        stats.add(winRateLabel);


        final JPanel buttons = new JPanel();
        playagain = new JButton("play again");
        buttons.add(playagain);
        logout = new JButton("log out");
        buttons.add(logout);

        playagain.addActionListener(
                new ActionListener() {
                    public void actionPerformed(ActionEvent evt) {
                        logoutController.switchTogridView();
                    }
                }
        );

//        logout.addActionListener(
//                new ActionListener() {
//                    public void actionPerformed(ActionEvent evt) {
//                        if (evt.getSource().equals(logout)) {
//                            final LogoutState currentState = logoutViewModel.getState();
//
//                            logoutController.execute(
//                                    currentState.getUsername()
//                            );
//                        }
//                    }
//                }
//        );


        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        this.add(stats);
        this.add(buttons);
    }

    public String getViewName() {
        return viewName;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {

    }
    public void setLogoutController(LogoutController logoutController) {
        this.logoutController = logoutController;
    }

        public static void main(String[] args) {
        LogoutViewModel model = new LogoutViewModel();
        final UserRepositoryImpl userRepository = new UserRepositoryImpl();
        final PasswordHasher passwordHasher = new PasswordHasher();
        final UserService userService = new UserService(userRepository, passwordHasher);
        LogoutView view = new LogoutView(model, userService);
        JFrame frame = new JFrame(view.getViewName());
        frame.setSize(500, 500);
        frame.setContentPane(view);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

    }

}