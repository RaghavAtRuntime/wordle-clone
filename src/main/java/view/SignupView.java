package view;

import interface_adapter.signup.SignupState;
import interface_adapter.signup.SignupViewModel;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class SignupView extends JPanel{
    private final String viewName = "Sign Up";

    private final SignupViewModel signupViewModel;
    private final JTextField usernameInputField = new JTextField(15);
    private final JPasswordField passwordInputField = new JPasswordField(15);
    private final JPasswordField repeatPasswordInputField = new JPasswordField(15);
    //private SignupController signupController;

    private final JButton signUp;

    public SignupView(SignupViewModel signupViewModel) {
        this.signupViewModel = signupViewModel;
        //signupViewModel.addPropertyChangeListener(this);

        final JLabel title = new JLabel(SignupViewModel.TITLE_LABEL);
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        final JLabel usernameLabel = new JLabel(SignupViewModel.USERNAME_LABEL);
        JPanel panel1 = new JPanel();
        panel1.add(usernameLabel);
        panel1.add(usernameInputField);

        final JLabel passwordLabel = new JLabel(SignupViewModel.PASSWORD_LABEL);
        JPanel panel2 = new JPanel();
        panel2.add(passwordLabel);
        panel2.add(passwordInputField);

        final JLabel repeatPasswordLabel = new JLabel(SignupViewModel.REPEAT_PASSWORD_LABEL);
        JPanel panel3 = new JPanel();
        panel3.add(repeatPasswordLabel);
        panel3.add(repeatPasswordInputField);


        final JPanel buttons = new JPanel();
        signUp = new JButton(SignupViewModel.SIGNUP_BUTTON_LABEL);
        buttons.add(signUp);


        JButton cancel = new JButton(SignupViewModel.CANCEL_BUTTON_LABEL);
        buttons.add(cancel);
//
//        signUp.addActionListener(
//                // This creates an anonymous subclass of ActionListener and instantiates it.
//                new ActionListener() {
//                    public void actionPerformed(ActionEvent evt) {
//                        if (evt.getSource().equals(signUp)) {
//                            final SignupState currentState = signupViewModel.getState();
//
//                            signupController.execute(
//                                    currentState.getUsername(),
//                                    currentState.getPassword(),
//                                    currentState.getRepeatPassword()
//                            );
//                        }
//                    }
//                }
//        );

//        cancel.addActionListener(this);

//        addUsernameListener();
//        addPasswordListener();
//        addRepeatPasswordListener();

        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        this.add(title);
        this.add(panel1);
        this.add(panel2);
        this.add(panel3);
        this.add(buttons);
    }

    public String getViewName() {
        return viewName;
    }

    public static void main(String[] args) {
        SignupViewModel model = new SignupViewModel();
        SignupView view = new SignupView(model);
        JFrame frame = new JFrame(view.getViewName());
        frame.setSize(500, 500);
        frame.setContentPane(view);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

    }
}

//    private void addUsernameListener() {
//        usernameInputField.getDocument().addDocumentListener(new DocumentListener() {
//
//            private void documentListenerHelper() {
//                final SignupState currentState = signupViewModel.getState();
//                currentState.setUsername(usernameInputField.getText());
//                signupViewModel.setState(currentState);
//            }
//
//            @Override
//            public void insertUpdate(DocumentEvent e) {
//                documentListenerHelper();
//            }
//
//            @Override
//            public void removeUpdate(DocumentEvent e) {
//                documentListenerHelper();
//            }
//
//            @Override
//            public void changedUpdate(DocumentEvent e) {
//                documentListenerHelper();
//            }
//        });
//    }
//
//    private void addPasswordListener() {
//        passwordInputField.getDocument().addDocumentListener(new DocumentListener() {
//
//            private void documentListenerHelper() {
//                final SignupState currentState = signupViewModel.getState();
//                currentState.setPassword(new String(passwordInputField.getPassword()));
//                signupViewModel.setState(currentState);
//            }
//
//            @Override
//            public void insertUpdate(DocumentEvent e) {
//                documentListenerHelper();
//            }
//
//            @Override
//            public void removeUpdate(DocumentEvent e) {
//                documentListenerHelper();
//            }
//
//            @Override
//            public void changedUpdate(DocumentEvent e) {
//                documentListenerHelper();
//            }
//        });
//    }
//
//    private void addRepeatPasswordListener() {
//        repeatPasswordInputField.getDocument().addDocumentListener(new DocumentListener() {
//
//            private void documentListenerHelper() {
//                final SignupState currentState = signupViewModel.getState();
//                currentState.setRepeatPassword(new String(repeatPasswordInputField.getPassword()));
//                signupViewModel.setState(currentState);
//            }
//
//            @Override
//            public void insertUpdate(DocumentEvent e) {
//                documentListenerHelper();
//            }
//
//            @Override
//            public void removeUpdate(DocumentEvent e) {
//                documentListenerHelper();
//            }
//
//            @Override
//            public void changedUpdate(DocumentEvent e) {
//                documentListenerHelper();
//            }
//        });
//    }
//
//    @Override
//    public void actionPerformed(ActionEvent evt) {
//        JOptionPane.showMessageDialog(this, "Cancel not implemented yet.");
//    }
//
//    @Override
//    public void propertyChange(PropertyChangeEvent evt) {
//        final SignupState state = (SignupState) evt.getNewValue();
//        if (state.getUsernameError() != null) {
//            JOptionPane.showMessageDialog(this, state.getUsernameError());
//        }
//    }
//

//
//    public void setSignupController(SignupController controller) {
//        this.signupController = controller;
//    }
//}