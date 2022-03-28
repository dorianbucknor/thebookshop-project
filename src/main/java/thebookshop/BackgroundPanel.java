package thebookshop;

import javax.swing.*;
import java.awt.*;

public class BackgroundPanel extends JPanel {

    ImageIcon image = null;
    private int iWidth2;
    private int iHeight2;

    public BackgroundPanel(ImageIcon image) {
        this.image = image;
        this.iWidth2 = image.getImage().getWidth(this) / 2;
        this.iHeight2 = image.getImage().getHeight(this) / 2;
        this.setPreferredSize(new Dimension(150, 1080));
        //this.setIcon(image);
    }
    @Override
    public void paint(Graphics g) {
        super.paint(g);
        if (image != null) {
            int x = this.getParent().getWidth() / 2 - iWidth2;
            int y = this.getParent().getHeight() / 2 - iHeight2;
            g.drawImage(image.getImage(), x, y, this);
        }
    }



}
