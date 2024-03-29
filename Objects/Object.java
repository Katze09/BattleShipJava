package Objects;

import Windows.Main;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

/**
 *
 * @author Katze
 */
public abstract class Object implements GameObject
{

    protected BufferedImage img;
    protected int X1;
    protected int X2;
    protected int Y1;
    protected int Y2;
    protected int WIDTH;
    protected int HEIGHT;

    public Object(BufferedImage img, int X1, int Y1)
    {
        this.img = img;
        this.X1 = X1;
        this.Y1 = Y1;
        this.WIDTH = img.getWidth();
        this.HEIGHT = img.getHeight();
        this.X2 = X1 + WIDTH;
        this.Y2 = Y1 + HEIGHT;
    }

    public int getWIDTH()
    {
        return WIDTH;
    }

    public int getHEIGHT()
    {
        return HEIGHT;
    }

    public int getX1()
    {
        return X1;
    }

    public void setX(int X)
    {
        if (X < 0)
            X = 0;
        if ((X + WIDTH) > Main.WIDTH)
            X = Main.WIDTH - WIDTH;
        this.X1 = X;
        this.X2 = X + WIDTH;
    }

    public int getX2()
    {
        return X2;
    }

    public int getY1()
    {
        return Y1;
    }

    public void setY(int Y)
    {
        if (Y < 0)
            Y = 0;
        if ((Y + HEIGHT) > Main.HEIGHT)
            Y = Main.HEIGHT - HEIGHT;
        this.Y1 = Y;
        this.Y2 = Y + HEIGHT;
    }

    public int getY2()
    {
        return Y2;
    }

    @Override
    public abstract void draw(Graphics g);
}
