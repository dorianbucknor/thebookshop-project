package thebookshop;

import javax.swing.*;
import java.awt.*;

public class BackgroundPanel extends JPanel {

    Image image = null;
    private int iWidth2;
    private int iHeight2;

    BackgroundPanel(Image image) {
        this.image = image;
        this.iWidth2 = image.getWidth(this) / 2;
        this.iHeight2 = image.getHeight(this) / 2;
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        if (image != null) {
            int x = this.getParent().getWidth() / 2 - iWidth2;
            int y = this.getParent().getHeight() / 2 - iHeight2;
            g.drawImage(image, x, y, this);
        }
    }
}
