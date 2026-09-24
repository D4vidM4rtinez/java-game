import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Main_game extends JPanel implements ActionListener {
    JFrame finestra;
    Timer timer;
    Shape cercle;

    public Main_game() {
        setBackground(Color.WHITE);

        cercle = new Shape(50, 200, 50, 50, 1, -1, 4, 4);

        finestra = new JFrame();
        finestra.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        finestra.setSize(new Dimension(700, 500));
        finestra.add(this);
        finestra.setVisible(true);

        timer = new Timer(16, this);   // ~60 FPS
        timer.start();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        cercle.mou();
        cercle.comprovarXoc(getWidth(),getHeight());
        repaint();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        cercle.paint(g2);
    }
    public static void main(String[] args){
    Main_game joc = new Main_game();
    }
}
