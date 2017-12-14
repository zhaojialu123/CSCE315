import java.awt.*;
import java.awt.image.BufferedImage;
import javax.swing.*;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class image_panel extends JPanel
{
    private BufferedImage buffer_i;
    private Graphics g;
    public image_panel(LayoutManager l)
    {
        super(l);
        //invoke immediate parent class constructor
        try
        {
            buffer_i = ImageIO.read(new File("woodboard.png"));
            g = buffer_i.getGraphics();
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
    }
    
    @Override
    protected void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        g.drawImage(buffer_i,0,0,this);
    }
}
