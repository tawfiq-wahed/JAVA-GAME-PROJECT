import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


class TawfiqTicTacGame {


    private final char[][] grid;
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
            if (checkwin(row, col)) {
                gameover = true;
                winner = currentplayer;
            } else if (isboardfull()) {
                gameover = true;
                winner = 'D';
            } else {
                if (currentplayer == 'X') {
                    currentplayer = '0';

                } else {
                    currentplayer = 'X';

                }

            }


            return true;
        }
        return false;
    }

    private boolean checkwin(int row,int col) {
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

    private boolean isboardfull() {
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++)
                if (grid[i][j] == ' ') return false;
        return true;
    }



}

public class Main extends JFrame implements ActionListener {

    private final JPanel panel = new JPanel(new GridLayout(3, 3));
    private final JLabel label = new JLabel("Player X's turn", JLabel.CENTER);
    private final JButton[][] slide = new JButton[3][3];
    private final TawfiqTicTacGame game = new TawfiqTicTacGame();

    public Main() {
        setTitle("Tawfiq's Tic Tac Toe");
        setSize(500, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);




        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                slide[i][j] = new JButton("");
                slide[i][j].setFont(new Font("Arial", Font.BOLD, 75));
                slide[i][j].setBackground(Color.BLACK);
                slide[i][j].addActionListener(this);
                panel.add(slide[i][j]);
            }
        }


        setLayout(new BorderLayout());
        add(label, BorderLayout.NORTH);
        add(panel, BorderLayout.CENTER);
        JButton resetbutton = new JButton("Restart the Game");
        resetbutton.addActionListener(e -> resetgame());
        add(resetbutton, BorderLayout.SOUTH);
        setVisible(true);
    }


    public void actionPerformed(ActionEvent e) {
        JButton pressed = (JButton) e.getSource();
        boolean k=false;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (slide[i][j] == pressed) {
                    char playermoved = game.getcurrentplayer();
                    if (game.Move(i, j)) {
                        slide[i][j].setText(String.valueOf(playermoved));
                        slide[i][j].setEnabled(false);
                        k=true;
                        break ;
                    }
                }
            }
            if(k)break;
        }
        panel.repaint();
        if (game.isgameOver()) {
            char winner = game.getwinner();
            if (winner != 'D') winline();
            showgameover();
        } else {
            label.setText("Player " + game.getcurrentplayer() + "'s turn");
        }
    }

    void winline() {

        int[][] line = game.getwinline();

        for (int[] index : line) {
            slide[index[0]][index[1]].setBackground(Color.RED);

        }

    }


    private void showgameover() {
        char winner = game.getwinner();
        if (winner == 'D') {

            JOptionPane.showMessageDialog(this, "The game is a draw!", "Game Over", JOptionPane.INFORMATION_MESSAGE);
        } else {

            JOptionPane.showMessageDialog(this, "Player " + winner + " wins!", "Game Over", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void resetgame() {
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