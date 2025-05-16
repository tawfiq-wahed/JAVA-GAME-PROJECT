import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Main extends JPanel implements ActionListener, KeyListener {
    private Timer timer;
    private int X=80,Y=50;
    private int D=35;
    private int SpeedX=3,SpeedY=2;

    private int pX=300;
    private final int pY=500;
    private final int pWidth=110;
    private final int pHeight=15;
    private final int Speed=12;

    public Main() {
        setBackground(Color.WHITE);
        setFocusable(true);
        addKeyListener(this);
        timer = new Timer(10, this);
        timer.start();
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.RED);
        g.fillOval(X,Y,D,D);
        g.setColor(Color.BLUE);
        g.fillRect(pX,pY,pWidth,pHeight);
    }

    public void actionPerformed(ActionEvent e) {
        X+= SpeedX;
        Y+= SpeedY;
        if (X<=0||X+D>=getWidth()) {
            SpeedX=-SpeedX;
        }
        if (Y<=0) {
            SpeedY=-SpeedY;
        }
        if (Y+D>=pY && X+D>=pX && X<=pX+pWidth) {
           SpeedY=-SpeedY;
           Y=pY-D;
        }
        if (Y+D>=getHeight()) {
            X=110;
            Y=120;
            SpeedY=-SpeedY;
        }
        repaint();
    }

    public void keyPressed(KeyEvent f) {
        int block=f.getKeyCode();
        if (block==KeyEvent.VK_LEFT && pX>0) {
            pX-=Speed;
        }

        if (block==KeyEvent.VK_RIGHT && pX+pWidth<getWidth()) {
            pX+=Speed;
        }
    }
    public void keyReleased(KeyEvent e) {

    }
    public void keyTyped(KeyEvent e) {

    }
}


 class GameWindow extends JFrame {
    public GameWindow() {
        setTitle("Ball Game");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(true);
        Main gamePanel = new Main();
        add(gamePanel);
        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(GameWindow::new);
    }
}
