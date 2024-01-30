package Windows;

import Input.Mouse;
import Input.MouseDragged;
import Input.MouseWheel;
import Objects.States;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.image.BufferStrategy;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 *
 * @author Katze
 */
public class Main extends JFrame implements Runnable
{

    public static final int WIDTH = 1200, HEIGHT = 700;
    private Canvas canvas;
    private Thread thread;
    private boolean running = false;

    private BufferStrategy bs;
    private Graphics g;

    private final int FPS = 30;
    private double targettime = 1000000000 / FPS;
    private double delta = 0;
    private int averageFPS = FPS;

    private JPanel jp = new JPanel();
    private States ships = new States();
    private MouseDragged mo = new MouseDragged(ships);
    private Mouse mou = new Mouse(ships);
    private MouseWheel mw = new MouseWheel();

    public Main()
    {
        this.setSize(1200, 800);
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setTitle("Battle Ship");
        canvas = new Canvas();
        canvas.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        canvas.setMaximumSize(new Dimension(WIDTH, HEIGHT));
        canvas.setMinimumSize(new Dimension(WIDTH, HEIGHT));
        canvas.setFocusable(true);
        canvas.addMouseMotionListener(mo);
        canvas.addMouseListener(mou);
        canvas.addMouseWheelListener(mw);
        components();
        this.add(canvas);
    }

    private void components()
    {
        jp.setBounds(0, 700, 1200, 100);
        jp.setBackground(Color.LIGHT_GRAY);
        jp.setLayout(null);
        JButton buttonPlay = new JButton("Start game");
        buttonPlay.setBounds(50, 20, 120, 30);
        buttonPlay.addActionListener((ActionEvent e) ->
        {
            if (ships.battleship.isBoard() && ships.cruise.isBoard() && ships.destroyer1.isBoard() && ships.destroyer2.isBoard() && ships.submarine.isBoard())
            {
                mo.canMove = false;
                ships.start = true;
                JOptionPane.showMessageDialog(null, "Your turn");
            } else
                JOptionPane.showMessageDialog(null, "Please put all ships in the board");
        });
        jp.add(buttonPlay);
        JButton buttonreset = new JButton("Reset ships");
        buttonreset.setBounds(250, 20, 120, 30);
        buttonreset.addActionListener((ActionEvent e) ->
        {
            if (mo.canMove)
                ships.resetShips();
        });
        jp.add(buttonreset);
        this.getContentPane().add(jp);
    }

    public static void main(String[] args)
    {
        new Main().start();
    }

    private void update()
    {
        ships.update();
    }

    private void draw()
    {
        bs = canvas.getBufferStrategy();

        if (bs == null)
        {
            canvas.createBufferStrategy(2);
            return;
        }
        g = bs.getDrawGraphics();
        //Empieza dibujo
        g.setColor(Color.LIGHT_GRAY);
        g.fillRect(0, 0, WIDTH, HEIGHT);
        ships.draw(g);
        g.dispose();
        bs.show();
    }

    @Override
    public void run()
    {
        long now = 0;
        long lastTime = System.nanoTime();
        int frames = 0;
        long time = 0;

        while (running)
        {
            now = System.nanoTime();
            delta += (now - lastTime) / targettime;
            time += (now - lastTime);
            lastTime = now;

            if (delta >= 1)
            {
                update();
                draw();
                this.setTitle("Battle Ship FPS:" + averageFPS);
                delta--;
                frames++;
            }
            if (time >= 1000000000)
            {
                averageFPS = frames;
                frames = 0;
                time = 0;
            }
        }
        this.stop();
    }

    private void start()
    {
        thread = new Thread(this);
        thread.start();
        running = true;
    }

    private void stop()
    {
        try
        {
            thread.join();
            running = false;
        } catch (InterruptedException ex)
        {
            ex.printStackTrace();
        }
    }
}
