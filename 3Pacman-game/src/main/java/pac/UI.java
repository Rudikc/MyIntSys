// The MIT License (MIT)
// 
// Copyright (c) 2014 Fredy Wijaya
// 
// Permission is hereby granted, free of charge, to any person obtaining a copy of
// this software and associated documentation files (the "Software"), to deal in
// the Software without restriction, including without limitation the rights to
// use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of
// the Software, and to permit persons to whom the Software is furnished to do so,
// subject to the following conditions:
// 
// The above copyright notice and this permission notice shall be included in all
// copies or substantial portions of the Software.
// 
// THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
// IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS
// FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
// COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER
// IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN
// CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.

package pac;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;
import javax.swing.Timer;

public class UI extends JFrame implements Game {
    private static final long serialVersionUID = 1L;
    private static int FRAME_HEIGHT = 500;
    private static int FRAME_WIDTH = 400;
    private boolean gameOver = false;
    private boolean win = false;
    private PacManGame pacManGame;
    private MazeBuilder mazeBuilder;
    private Timer pacManTimer;
    private int pacManTimerDelay = 180;
    private Timer ghostTimer;
    private int ghostTimerDelay = 280;
    private Image buffer;

    private static long beforeUsedMem;
    private static long steps = 0;
    private static long startTime;

    @Override
    public void gameOver() {
        gameOver = true;
        ghostTimer.stop();
        pacManTimer.stop();

        System.out.println("RAM used: " + (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory() - beforeUsedMem) / 1024 + " KB");
        System.out.println("Steps made: " + steps);
        System.out.println("Time passed: " + ((double) (System.currentTimeMillis() - startTime) / 1000) + " seconds");
    }

    @Override
    public void win() {
        win = true;
        ghostTimer.stop();
        pacManTimer.stop();
    }

    class PacManActionListener implements ActionListener {
        private PacManGame pacManGame;

        public PacManActionListener(PacManGame pacManGame) {
            this.pacManGame = pacManGame;
        }

        @Override
        public void actionPerformed(ActionEvent evt) {
            pacManGame.movePacMan();
            repaint();
        }
    }

    class GhostActionListener implements ActionListener {
        private PacManGame pacManGame;

        public GhostActionListener(PacManGame pacManGame) {
            this.pacManGame = pacManGame;
        }

        @Override
        public void actionPerformed(ActionEvent evt) {
            pacManGame.moveGhost();
            steps++;
            repaint();
        }
    }

    public UI() {
        init();
    }


    public void init() {
        setTitle("Pac-Man");
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setSize(FRAME_WIDTH, FRAME_HEIGHT);
        setResizable(false);
        addKeyListener(new PacManKeyListener());
        buffer = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_RGB);
        pacManGame = new PacManGame(this);
        mazeBuilder = new MazeBuilder(getWidth(), getHeight(), pacManGame.getPacMan(),
                pacManGame.getGhosts());
        pacManTimer = new Timer(pacManTimerDelay, new PacManActionListener(pacManGame));
        ghostTimer = new Timer(ghostTimerDelay, new GhostActionListener(pacManGame));
        ghostTimer.start();
    }

    @Override
    public void paint(Graphics graphics) {
        Graphics g = buffer.getGraphics();
        drawScore(g);
        drawMaze(g);
        if (gameOver) {
            String ram = "RAM: " + (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory() - beforeUsedMem) / 1024 + " KB";
            String time = "Time: " + ((double) (System.currentTimeMillis() - startTime) / 1000) + " s.";
            String stepes = "Steps: " + steps;
            g.setColor(Color.YELLOW);
            g.drawString(ram, 10, 45);
            g.drawString(time, FRAME_WIDTH / 2 - (time.length() + time.length() * 2), 45);
            g.drawString(stepes, FRAME_WIDTH - (stepes.length() + stepes.length() * 7), 45);
            g.drawString("Completed", FRAME_WIDTH / 2 - ("Completed".length()) - 26, FRAME_HEIGHT / 2);
        } else {
            if (win) {
                g.setColor(Color.YELLOW);
                g.drawString("YOU WIN!", FRAME_WIDTH / 2 - ("WIN!".length()) - 24,
                        FRAME_HEIGHT / 2);
            }
        }
        graphics.drawImage(buffer, 0, 0, this);
    }

    private void drawScore(Graphics g) {
        g.setColor(Color.BLUE);
        g.fillRect(0, 0, getWidth(), getHeight());
        int totalScore = pacManGame.getTotalScore();
        g.setColor(Color.YELLOW);

    }

    private void drawMaze(Graphics g) {
        mazeBuilder.setGraphics(g);
        mazeBuilder.draw(pacManGame.getMaze());
    }

    @Override
    public void update(Graphics graphics) {
        super.update(graphics);
    }

    public class PacManKeyListener implements KeyListener {
        @Override
        public void keyTyped(KeyEvent e) {
        }

        @Override
        public void keyPressed(KeyEvent e) {
            if (!win && !gameOver) {
            }
        }

        @Override
        public void keyReleased(KeyEvent e) {
        }
    }


    public static void main(String[] args) {

        beforeUsedMem = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
        startTime = System.currentTimeMillis();
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                UI pacMan = new UI();
                pacMan.setVisible(true);
            }
        });
    }
}
