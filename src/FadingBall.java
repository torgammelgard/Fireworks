import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

/**
 * Created by Tor on 2015-10-02.
 */
public class FadingBall extends JComponent implements ActionListener {

    private IBallListener listener;

    private Color color;
    private int x, y, w, h;
    private Timer timer;
    private static Random rand = new Random();

    public FadingBall(Container container, int red, int green, int blue){
        x = rand.nextInt(container.getWidth());
        y = rand.nextInt(container.getHeight());
        w = h = rand.nextInt(75) + 26;
        color = getRandomColor(red, green, blue);

        setBounds(x - w/2, y - h/2, w, h);
        timer = new Timer(1000/60, this);
        timer.start();
    }

    private Color getRandomColor(int r, int g, int b){
        int red = rand.nextInt(r+1);
        int green = rand.nextInt(g+1);
        int blue = rand.nextInt(b+1);

        return new Color(red, green, blue);
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D)g;

        g2.setColor(color);
        g2.fillArc(0,0, w, h, 0, 360);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        int red = color.getRed();
        int green = color.getGreen();
        int blue = color.getBlue();
        int alpha = color.getAlpha();

        if (red > 0)
            red--;
        if (green > 0)
            green--;
        if (blue > 0)
            blue--;
        if (alpha > 0)
            alpha -= 2;

        if (alpha <= 0) {
            timer.stop();
            fireFinishedOccurred();
            return;
        }
        color = new Color(red, green, blue, alpha);
        w++;
        h++;
        setBounds(x - w/2, y - h/2, w, h);
        repaint();
    }

    private void fireFinishedOccurred() {
        if (listener != null)
            listener.ballFadedOut(this);
    }

    public void setIsAliveListener(IBallListener listener){
        this.listener = listener;
    }
}
