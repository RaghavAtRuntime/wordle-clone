package view;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        final AppBuilder appBuilder = new AppBuilder();
        final JFrame application = appBuilder
                .addLoginView()
                .addSignupView()
                .addGridView()
                .addLogoutView()
                .addSignupUseCase()
                .addLoginUseCase()
                .addLogoutUsecase()
                .addGridUseCase()
                .build();

        application.pack();
        application.setVisible(true);
    }
}
