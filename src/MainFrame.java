import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tor on 2015-10-02.
 */
public class MainFrame extends JFrame implements IBallListener {

    private static final String TITLE = "Fireworks lab";
    private List<FadingBall> balls;

    private JPanel gfxPanel;
    private JPanel menuPanel;
    private JLabel mainLabel;

    private JSlider red_slider;
    private JSlider green_slider;
    private JSlider blue_slider;

    private int red, green, blue = 100;

    private int counter = 0;

    public MainFrame() {

        balls = new ArrayList<>(30);

        gfxPanel = new JPanel(true);
        gfxPanel.setBackground(Color.BLACK);

        menuPanel = new JPanel();
        mainLabel = new JLabel(TITLE);
        mainLabel.setFont(new Font("Serif", Font.BOLD, 32));
        mainLabel.setHorizontalAlignment(SwingConstants.CENTER);
        mainLabel.setBackground(Color.BLACK);
        mainLabel.setForeground(Color.WHITE);
        mainLabel.setOpaque(true);

        red_slider = new JSlider(SwingConstants.VERTICAL);
        red_slider.setBackground(Color.RED);
        red_slider.addChangeListener(e -> {
            JSlider slider = (JSlider) e.getSource();
            red = ((slider.getValue() << 8) - 1) / 100;             // value between 0 - 255
        });
        green_slider = new JSlider(SwingConstants.VERTICAL);
        green_slider.setBackground(Color.GREEN);
        green_slider.addChangeListener(e -> {
            JSlider slider = (JSlider) e.getSource();
            green = ((slider.getValue() << 8) - 1) / 100;           // value between 0 - 255
        });
        blue_slider = new JSlider(SwingConstants.VERTICAL);
        blue_slider.addChangeListener(e -> {
            JSlider slider = (JSlider) e.getSource();
            blue = ((slider.getValue() << 8) - 1) / 100;            // value between 0 - 255
        });
        blue_slider.setBackground(Color.BLUE);


        ///////// LAYOUT //////////////
        setLayout(new GridBagLayout());
        GridBagConstraints gc = new GridBagConstraints();

        gc.fill = GridBagConstraints.BOTH;
        gc.gridx = 0;
        gc.gridy = 0;
        gc.weightx = 0.9;
        gc.weighty = 1.0;
        add(gfxPanel, gc);

        gc.gridx = 1;
        gc.weightx = 0.1;
        add(menuPanel, gc);

        ////// Layout withing menuPanel
        menuPanel.setLayout(new GridBagLayout());
        gc.weightx = 1.0;
        gc.weighty = 0.1;
        gc.gridx = 0;
        gc.gridy = 0;
        gc.gridwidth = 3;

        menuPanel.add(mainLabel, gc);

        gc.weighty = 0.9;
        gc.gridx = 0;
        gc.gridy = 1;
        gc.gridwidth = 1;
        menuPanel.add(red_slider, gc);
        gc.gridx = 1;
        menuPanel.add(green_slider, gc);
        gc.gridx = 2;
        menuPanel.add(blue_slider, gc);


        setSize(800, 600);
        setTitle(TITLE);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void update() {
        if (balls.size() < 100 && counter++ % 4 == 0) {
            FadingBall ball = new FadingBall(gfxPanel, red, green, blue);
            ball.setIsAliveListener(this);
            balls.add(ball);
            gfxPanel.add(ball);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MainFrame frame = new MainFrame();
            new Timer(1000 / 60, e -> frame.update()).start();
        });
    }

    @Override
    public void ballFadedOut(FadingBall ball) {
        if (balls.contains(ball)) {
            gfxPanel.remove(ball);
            balls.remove(ball);
            repaint();
        }
    }
}
