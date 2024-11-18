package view;


import data_access.repository.UserRepositoryImpl;
import interface_adapter.logout.LogoutController;
import interface_adapter.logout.LogoutState;
import interface_adapter.logout.LogoutViewModel;
import interface_adapter.security.PasswordHasher;
import use_case.service.UserService;

import javax.swing.*;
import java.awt.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class LogoutView extends JPanel implements PropertyChangeListener {


    private static final String viewName = "game end";

    private final LogoutViewModel logoutViewModel;

    private final JButton logout;
    private final JButton playagain;
    private final UserService userService;



    private LogoutController logoutController;
//    private HistoryController historyController;


    public LogoutView(LogoutViewModel logoutViewModel, UserService userService) {

        this.logoutViewModel = logoutViewModel;
        this.userService = userService;
        this.logoutViewModel.addPropertyChangeListener(this);

        final JPanel stats = new JPanel();
        stats.setLayout(new GridLayout(4, 1));
        JLabel titleLabel = new JLabel("User's History", SwingConstants.CENTER);
        stats.add(titleLabel);
        int wins = userService.getUserWins(userService.getCurrentUsername());
        int losses = userService.getUserLosses(userService.getCurrentUsername());

//        int wins = logoutController.getuserstat().get("Win");
//        int losses = logoutController.getuserstat().get("Loss");

//        Calculate the winning rate
        double winRate = (wins + losses > 0) ? ((double) wins / (wins + losses)) * 100 : 0;

//         Create labels to display the stats
        JLabel winsLabel = new JLabel("Wins: " + wins,SwingConstants.CENTER);
        JLabel lossesLabel = new JLabel("Losses: " + losses, SwingConstants.CENTER);
        JLabel winRateLabel = new JLabel("Winning Rate: " + String.format("%.2f", winRate) + "%",
                SwingConstants.CENTER);

        // Set layout and add labels to the panel
        stats.add(winsLabel);
        stats.add(lossesLabel);
        stats.add(winRateLabel);



        final JPanel buttons = new JPanel();
        playagain = new JButton("Play Again?");
        buttons.add(playagain);
        logout = new JButton("Log Out");
        buttons.add(logout);

        playagain.addActionListener(
                new ActionListener() {
                    public void actionPerformed(ActionEvent evt) {
                        logoutController.switchToGridView();
                    }
                }
        );

        logout.addActionListener(
                new ActionListener() {
                    public void actionPerformed(ActionEvent evt) {
                        if (evt.getSource().equals(logout)) {
                            final LogoutState currentState = logoutViewModel.getState();

                            logoutController.execute(
                                    currentState.getUsername()
                            );
                        }
                    }
                }
        );


        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        this.add(stats);

        this.add(buttons);
    }

    public String getViewName() {
        return viewName;
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        // TODO: implement this
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
