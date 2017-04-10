package factory;

import javax.swing.border.AbstractBorder;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;

/**
 * Created by okori on 31-Mar-17.
 */
public class RoundedBorder extends AbstractBorder {

    private int gap;

    public RoundedBorder(int gap) {
        this.gap = gap;
    }

    @Override
    public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
        super.paintBorder(c, g, x, y, width, height);

        if (g instanceof Graphics2D) {
            ((Graphics2D) g).draw(new RoundRectangle2D.Double(x, y, width - 1, height - 1, gap, gap));
        }
    }
}
