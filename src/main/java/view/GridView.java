package view;

import entity.GameState;
import entity.GuessResult;
import interface_adapter.grid.GridController;
import interface_adapter.grid.GridViewModel;

import javax.swing.*;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

/**
 * The View for the Wordle Grid.
 */
public class GridView extends JPanel implements PropertyChangeListener {

    private static final String viewName = "grid";
    private final GridViewModel gridViewModel;
    private final JTextField[][] gridCells;
    private GridController gridController;
    private GameState gameState;

    public static String getViewName() {
        return viewName;
    }

    public GridView(GridViewModel gridViewModel, GameState gameState) {
        this.gridViewModel = gridViewModel;
        this.gridViewModel.addPropertyChangeListener(this);
        this.gameState = gameState;

        int rows = 6;
        int cols = 5;
        gridCells = new JTextField[rows][cols];

        setLayout(new BorderLayout());
        setBackground(Color.BLACK);

        JLabel titleLabel = new JLabel("WORDLE!!!", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setForeground(Color.WHITE);
        add(titleLabel, BorderLayout.NORTH);

        JPanel gridPanel = new JPanel(new GridLayout(rows, cols, 10, 10));
        gridPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        gridPanel.setBackground(Color.BLACK);

        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                JTextField cell = new JTextField(1);
                cell.setHorizontalAlignment(JTextField.CENTER);
                cell.setFont(new Font("Arial", Font.BOLD, 18));
                cell.setBackground(Color.BLACK);
                cell.setForeground(Color.WHITE);
                cell.setCaretColor(Color.WHITE);
                cell.setBorder(BorderFactory.createLineBorder(Color.GRAY));

                ((AbstractDocument) cell.getDocument()).setDocumentFilter(new UppercaseDocumentFilter(row, col));
                int finalRow = row;
                int finalRow1 = row;
                cell.addKeyListener(new KeyAdapter() {
                    @Override
                    public void keyPressed(KeyEvent e) {

                        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                            gridController.handleEnter(finalRow, getRowWord(finalRow1));
                        }
                    }
                });

                gridCells[row][col] = cell;
                gridPanel.add(cell);
            }
        }
        add(gridPanel, BorderLayout.CENTER);
    }

    private String getRowWord(int row) {
        StringBuilder word = new StringBuilder();
        for (JTextField cell : gridCells[row]) {
            word.append(cell.getText());
        }
        return word.toString();
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        // Handle grid updates based on GridViewModel
        updateGrid(gridViewModel);
    }

    private void updateGrid(GridViewModel gridViewModel) {
        for (int row = 0; row < gridCells.length; row++) {
            for (int col = 0; col < gridCells[row].length; col++) {
                String letter = gridViewModel.getCellContent(row, col);
                gridCells[row][col].setText(letter);
                updateCellColor(row, col);
            }
        }
    }

    private void updateCellColor(int row, int col) {
        if (gridViewModel.isCellCorrectPosition(row, col)) {
            gridCells[row][col].setBackground(Color.GREEN);
        } else if (gridViewModel.isCellCorrectLetter(row, col)) {
            gridCells[row][col].setBackground(Color.ORANGE);
        } else {
            gridCells[row][col].setBackground(Color.GRAY);
        }
    }

    public void setGridController(GridController gridController) {
        this.gridController = gridController;
    }

    private class UppercaseDocumentFilter extends DocumentFilter {
        private final int row;
        private final int col;

        public UppercaseDocumentFilter(int row, int col) {
            this.row = row;
            this.col = col;
        }

        @Override
        public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr) throws BadLocationException {
            if (string != null && string.matches("[A-Z]")) {
                super.insertString(fb, offset, string, attr);
            }
        }
    }
}
