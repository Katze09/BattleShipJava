package Images;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 *
 * @author Katze
 */
public class Load
{

    public static BufferedImage LoadImage(String path)
    {
       BufferedImage img = null;
       String route = "Images/";
        File file = new File(route + path);
        try
        {
            img = ImageIO.read(file);
        } catch (IOException ex)
        {
            System.err.println(ex.getMessage());
        }
        return img;
    }
}
