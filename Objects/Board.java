package Objects;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

/**
 *
 * @author Katze
 */
public class Board implements GameObject
{

    private final BufferedImage img;
    private final int X;
    private final int Y;
    public int boxBoard[][] = new int[100][4];

    public Board(BufferedImage img, int X, int Y)
    {
        this.img = img;
        this.X = X;
        this.Y = Y;
        setBoxBoard();
    }
    
    private void setBoxBoard()
    {
        int x = 50;
        int y = 50;
        int cont = 0;
        for (int i = 0; i < 100; i++)
        {
            boxBoard[i][0] = X + x;
            boxBoard[i][1] = X + x + 50;
            boxBoard[i][2] = Y + y;
            boxBoard[i][3] = Y + y + 50;
            x += 50;
            cont++;
            if (cont > 9)
            {
                x = 50;
                y += 50;
                cont = 0;
            }
        }
    }

    @Override
    public void draw(Graphics g)
    {
        g.drawImage(img, X, Y, null);
    }
}
