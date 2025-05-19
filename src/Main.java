import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class TawfiqTicTacGame {
    private char[][] grid;
    private char currentplayer;
    private boolean gameover;
    private char winner;
    private int[][] Line;


    public TawfiqTicTacGame() {
        grid = new char[3][3];
        currentplayer = 'X';
        gameover = false;
        winner = ' ';
        Line = new int[3][1];
        emptyboard();
    }

    public void emptyboard() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                grid[i][j] = ' ';
            }
        }

                Line = new int[3][1];

        gameover = false;
        currentplayer = 'X';
        winner = ' ';

    }


    public char getwinner() { return winner; }
    public int[][] getwinline() { return Line; }
    public char getcurrentplayer() { return currentplayer; }
    public boolean isgameOver() { return gameover; }


    public boolean Move(int row,int col) {
        if (grid[row][col] == ' ' && !gameover) {
            grid[row][col] = currentplayer;
            if (checkWin(row, col)) {
                gameover = true;
                winner = currentplayer;
            } else if (isBoardFull()) {
                gameover = true;
                winner = 'D';
            } else {
                if(currentplayer=='X')currentplayer='0';
                else currentplayer='X';
            }
            return true;
        }
        return false;
    }

    private boolean checkWin(int row,int col) {
        if (grid[0][col] == currentplayer && grid[1][col] == currentplayer && grid[2][col] == currentplayer) {
            for (int i = 0; i < 3; i++) Line[i] = new int[]{i, col};
            return true;
        }
        if (grid[row][0] == currentplayer && grid[row][1] == currentplayer && grid[row][2] == currentplayer) {
            for (int i = 0; i < 3; i++) Line[i] = new int[]{row, i};
            return true;
        }
        if (grid[0][0] == currentplayer && grid[1][1] == currentplayer && grid[2][2] == currentplayer) {
            for (int i = 0; i < 3; i++) Line[i] = new int[]{i, i};
            return true;
        }

        if (grid[0][2] == currentplayer && grid[1][1] == currentplayer && grid[2][0] == currentplayer) {
            for (int i = 0; i < 3; i++) Line[i] = new int[]{i, 2 - i};
            return true;
        }

        return false;
    }

    private boolean isBoardFull() {
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++)
                if (grid[i][j] == ' ') return false;
        return true;
    }
}

public class Main extends JFrame implements ActionListener {

    private JPanel panel = new JPanel(new GridLayout(3, 3));
    private JLabel label = new JLabel("Player X's turn", JLabel.CENTER);
    private JButton[][] slide = new JButton[3][3];
    private TawfiqTicTacGame game = new TawfiqTicTacGame();

    public Main() {
        setTitle("Tawfiq's Tic Tac Toe");
        setSize(600, 700);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);



        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                slide[i][j] = new JButton("");
                slide[i][j].setFont(new Font("Arial", Font.BOLD, 80));
                slide[i][j].setBackground(Color.BLACK);
                slide[i][j].addActionListener(this);
               panel.add(slide[i][j]);
            }
        }

        setLayout(new BorderLayout());
        setLayout(new BorderLayout());
        add(label, BorderLayout.NORTH);
        add(panel, BorderLayout.CENTER);
        JButton resetButton = new JButton("Restart the Game");
        resetButton.addActionListener(e -> resetGame());
        add(resetButton, BorderLayout.SOUTH);
        setVisible(true);
    }

  //here override will happen
    public void actionPerformed(ActionEvent e) {
        JButton pressed = (JButton) e.getSource();
        outer:
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (slide[i][j] == pressed) {
                    char playermoved = game.getcurrentplayer();
                    if (game.Move(i, j)) {
                        slide[i][j].setText(String.valueOf(playermoved));
                        slide[i][j].setEnabled(false);
                        break outer;
                    }
                }
            }
        }
     panel.repaint();
        if (game.isgameOver()) {
            char winner = game.getwinner();
            if (winner != 'D') WinLine();
            showGameOver();
        } else {
            label.setText("Player " + game.getcurrentplayer() + "'s turn");
        }
    }

     void WinLine() {

            int[][] line = game.getwinline();

            for (int[] index : line) {
                slide[index[0]][index[1]].setBackground(Color.RED);

        }

    }


    private void showGameOver() {
        char winner = game.getwinner();
        if (winner == 'D') {
            JOptionPane.showMessageDialog(this, "The game is a draw!", "Game Over", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, "Player " + winner + " wins!", "Game Over", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void resetGame() {
        game.emptyboard();
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++) {
                slide[i][j].setText("");
                slide[i][j].setEnabled(true);
                slide[i][j].setBackground(null);
            }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Main::new);
    }
}
