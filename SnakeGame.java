import java.awt.*;
import java.awt.event.*;
import java.util.Random;
import javax.swing.*;

public class SnakeGame extends JPanel implements ActionListener, KeyListener {

    final int TILE_SIZE = 20;
    final int WIDTH = 600;
    final int HEIGHT = 600;

    int snakeLength = 3;
    int[] x = new int[900];
    int[] y = new int[900];

    int foodX, foodY;

    char direction = 'R';
    boolean running = true;

    Timer timer;
    Random random;

    public SnakeGame() {
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setBackground(Color.black);
        setFocusable(true);
        addKeyListener(this);

        random = new Random();
        spawnFood();

        for (int i = 0; i < snakeLength; i++) {
            x[i] = 100 - i * TILE_SIZE;
            y[i] = 100;
        }

        timer = new Timer(200, this);
        timer.start();
    }

    public void spawnFood() {
        foodX = random.nextInt(WIDTH / TILE_SIZE) * TILE_SIZE;
        foodY = random.nextInt(HEIGHT / TILE_SIZE) * TILE_SIZE;
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (running) {
            g.setColor(Color.red);
            g.fillOval(foodX, foodY, TILE_SIZE, TILE_SIZE);

            g.setColor(Color.green);
            for (int i = 0; i < snakeLength; i++) {
                g.fillRect(x[i], y[i], TILE_SIZE, TILE_SIZE);
            }
        } else {
            g.setColor(Color.white);
            g.drawString("Game Over!", WIDTH / 2 - 40, HEIGHT / 2);
        }
    }

    public void move() {
        for (int i = snakeLength; i > 0; i--) {
            x[i] = x[i - 1];
            y[i] = y[i - 1];
        }

        switch (direction) {
            case 'U': y[0] -= TILE_SIZE; break;
            case 'D': y[0] += TILE_SIZE; break;
            case 'L': x[0] -= TILE_SIZE; break;
            case 'R': x[0] += TILE_SIZE; break;
        }
    }

    public void checkFood() {
        if (x[0] == foodX && y[0] == foodY) {
            snakeLength++;
            spawnFood();
        }
    }

    public void checkCollision() {
        if (x[0] < 0 || x[0] >= WIDTH || y[0] < 0 || y[0] >= HEIGHT) {
            running = false;
        }

        for (int i = 1; i < snakeLength; i++) {
            if (x[0] == x[i] && y[0] == y[i]) {
                running = false;
            }
        }

        if (!running) {
            timer.stop();
        }
    }

    public void actionPerformed(ActionEvent e) {
        if (running) {
            move();
            checkFood();
            checkCollision();
        }
        repaint();
    }

    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_UP: if (direction != 'D') direction = 'U'; break;
            case KeyEvent.VK_DOWN: if (direction != 'U') direction = 'D'; break;
            case KeyEvent.VK_LEFT: if (direction != 'R') direction = 'L'; break;
            case KeyEvent.VK_RIGHT: if (direction != 'L') direction = 'R'; break;
        }
    }

    public void keyReleased(KeyEvent e) {}
    public void keyTyped(KeyEvent e) {}

    public static void main(String[] args) {
        JFrame frame = new JFrame("Snake Game");
        SnakeGame game = new SnakeGame();

        frame.add(game);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
