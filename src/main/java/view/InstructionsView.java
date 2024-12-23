package view;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.*;

import org.jetbrains.annotations.NotNull;

import entity.DifficultyState;
import interface_adapter.grid.GridState;
import interface_adapter.history.HistoryController;
import interface_adapter.instructions.InstructionsController;
import interface_adapter.instructions.InstructionsState;
import interface_adapter.instructions.InstructionsViewModel;
import interface_adapter.profile.ProfileState;
import interface_adapter.profile.ProfileViewModel;
import use_case.service.UserService;

/**
 * Instructions View.
 */
public class InstructionsView extends JPanel implements PropertyChangeListener {

    public static final String SERIF = "Serif";
    private static final String viewName = "instructions";
    private final InstructionsViewModel instructionsViewModel;
    private InstructionsController instructionsController;
    private HistoryController historyController;
    private DifficultyState difficultyState;
    private GridState gridState;
    private ProfileViewModel profileViewModel;
    private final UserService userService;
    private final ProfileView profileView;

    public InstructionsView(InstructionsViewModel instructionsViewModel,
                            ProfileViewModel profileViewModel,
                            DifficultyState difficultyState, GridState gridState, UserService userService) {
        this.instructionsViewModel = instructionsViewModel;
        this.instructionsViewModel.addPropertyChangeListener(this);
        // Set layout for the main panel
        setLayout(new BorderLayout());
        this.difficultyState = difficultyState;
        setupComponents();
        this.userService = userService;
        this.gridState = gridState;
        this.profileView = new ProfileView(new ProfileViewModel());
        this.profileViewModel = profileViewModel;
    }

    public void setInstructionsController(InstructionsController instructionsController) {
        this.instructionsController = instructionsController;
    }

    public static String getViewName() {
        return viewName;
    }

    private void setupComponents() {
        // Title label
        JLabel titleLabel = new JLabel("How To Play", SwingConstants.CENTER);
        titleLabel.setFont(new Font(SERIF, Font.BOLD, 24));

        // Top panel with title and profile button
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.add(titleLabel, BorderLayout.CENTER);

        // Profile button
        JButton profilebutton = new JButton("View Profile");
        profilebutton.setFont(new Font(SERIF, Font.BOLD, 16));
        topPanel.add(profilebutton, BorderLayout.SOUTH);

        profilebutton.addActionListener(e -> {
            final ProfileState currentState = profileViewModel.getState();
            historyController.execute(currentState.getUsername(), currentState.getStatus()); });

        add(topPanel, BorderLayout.NORTH);

        // Main instructions panel
        JPanel instructionsPanel = new JPanel();
        instructionsPanel.setLayout(new BoxLayout(instructionsPanel, BoxLayout.Y_AXIS));

        // General instructions
        JLabel generalInstructions = new JLabel("<html><center>Guess the Wordle in 6 tries.<br>"
                + "Each guess must be a valid 5-letter word.<br>"
                + "The color of the tiles will change to show<br>"
                + "how close your guess was to the word.</center></html>", SwingConstants.CENTER);
        generalInstructions.setFont(new Font(SERIF, Font.PLAIN, 14));
        generalInstructions.setAlignmentX(Component.CENTER_ALIGNMENT);
        instructionsPanel.add(generalInstructions);

        // Examples panel
        JPanel examplesPanel = new JPanel();
        examplesPanel.setLayout(new BoxLayout(examplesPanel, BoxLayout.Y_AXIS));
        examplesPanel.setBorder(BorderFactory.createTitledBorder("Examples"));

        // Example 1: Correct letter and position
        examplesPanel.add(createExample("WORDY", 0));

        // Example 2: Correct letter, wrong position
        examplesPanel.add(createExample("LIGHT", 1));

        // Example 3: Letter not in the word
        examplesPanel.add(createExample("ROGUE", 2));

        instructionsPanel.add(examplesPanel);

        // Add instructions panel to the main frame
        add(instructionsPanel, BorderLayout.CENTER);

        // Panel for play and dropdown
        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new BorderLayout());

        // Add difficulty dropdown
        String[] difficulties = { "easy", "medium", "hard" };
        JComboBox<String> difficultyDropdown = new JComboBox<>(difficulties);
        difficultyDropdown.setFont(new Font(SERIF, Font.PLAIN, 14));
        bottomPanel.add(difficultyDropdown, BorderLayout.NORTH);

        // Play button
        JButton playButton = new JButton("Play");
        playButton.setFont(new Font(SERIF, Font.BOLD, 16));
        bottomPanel.add(playButton, BorderLayout.WEST);

        // Discussion board button
        JButton discussionBoardButton = new JButton("Discussion Board");
        discussionBoardButton.setFont(new Font(SERIF, Font.BOLD, 16));
        bottomPanel.add(discussionBoardButton, BorderLayout.EAST);

        // Add the bottom panel to the main frame
        add(bottomPanel, BorderLayout.SOUTH);

        // Button ActionListener for play button
        playButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Retrieve the selected difficulty from DifficultyState
                String selectedDifficulty = difficultyState.getDifficulty();

                // Pass the difficulty to the controller to fetch the word and switch views
                instructionsController.switchToGridView(selectedDifficulty);
                System.out.println(gridState.getTargetWord());
            }
        });

        // Button ActionListener for discussion board button
        discussionBoardButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Navigate to the discussion board
                instructionsController.switchToDiscussionBoardView();
            }
        });

        difficultyDropdown.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JComboBox<String> comboBox = (JComboBox<String>) e.getSource();
                String selectedDifficulty = (String) comboBox.getSelectedItem();
                difficultyState.setDifficulty(selectedDifficulty);
                System.out.println("Selected Difficulty: " + selectedDifficulty);
            }
        });

        // Sets initial state to easy
        difficultyDropdown.setSelectedIndex(0);
        difficultyDropdown.getActionListeners()[0].actionPerformed(new ActionEvent(difficultyDropdown,
                ActionEvent.ACTION_PERFORMED, null));
    }

    // This creates the visual examples for the user.
    private JPanel createExample(String word, int highlightedIndex) {
        JPanel examplePanel = new JPanel();
        examplePanel.setLayout(new BorderLayout());

        // Add explanatory text above the word tiles
        String explanation = switch (highlightedIndex) {
            case 0 -> "W is in the word and in the correct spot.";
            case 1 -> "I is in the word but in the wrong spot.";
            default -> "U is not in the word in any spot.";
        };
        JLabel explanationLabel = new JLabel(explanation, SwingConstants.CENTER);
        // Add space below the text
        explanationLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 5, 0));
        examplePanel.add(explanationLabel, BorderLayout.NORTH);

        // Create word tiles
        JPanel wordPanel = new JPanel();
        // Add horizontal and vertical gaps
        wordPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

        for (int i = 0; i < word.length(); i++) {
            JLabel tileLabel = getjLabel(word, highlightedIndex, i);
            wordPanel.add(tileLabel);
        }

        examplePanel.add(wordPanel, BorderLayout.CENTER);

        return examplePanel;
    }

    @NotNull
    private static JLabel getjLabel(String word, int highlightedIndex, int i) {
        JLabel tileLabel = new JLabel(String.valueOf(word.charAt(i)), SwingConstants.CENTER);
        tileLabel.setPreferredSize(new Dimension(40, 40));
        tileLabel.setOpaque(true);
        tileLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        // Set background color based on the example
        if (i == highlightedIndex) {
            tileLabel.setBackground(highlightedIndex == 0 ? Color.GREEN : highlightedIndex == 1 ? Color.ORANGE : Color
                    .DARK_GRAY);
        }
        else {
            tileLabel.setBackground(Color.BLACK);
        }
        tileLabel.setForeground(Color.WHITE);
        return tileLabel;
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        final InstructionsState state = (InstructionsState) evt.getNewValue();
    }

    public void setHistoryController(HistoryController historyController) {
        this.historyController = historyController;
    }
}
