package Objects;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

/**
 *
 * @author Katze
 */
public class Cruise extends Ships
{

    public Cruise(BufferedImage img, int life, int X1, int Y1)
    {
        super(img, life, X1, Y1);
    }

    @Override
    public void draw(Graphics g)
    {
        g.drawImage(img, X1, Y1, null);
    }
    
}
