import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class Main_game extends JPanel implements ActionListener {
    JFrame finestra;
    Timer timer;
    List<Shape> shapeList = new ArrayList<>();

    public Main_game() {
        setBackground(Color.WHITE);
        shapeList.add(new Shape(50, 200, 50, 50, 1, 1, 4,4));
        shapeList.add(new Shape(200, 50, 50, 50, 1, 1, 4,4));
        shapeList.add(new Shape(500, 50, 50, 50, -1, 1, 4,4));
        shapeList.add(new Shape(200, 400, 50, 50, 1, -1, 4,4));
        finestra = new JFrame();
        finestra.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        finestra.setSize(new Dimension(700, 500));
        finestra.add(this);
        finestra.setVisible(true);

        timer = new Timer(16, this);   // ~60 FPS
        timer.start();
    }

    public static void main(String[] args) {
        Main_game joc = new Main_game();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        for (Shape shape : shapeList){
            shape.mou();
            shape.comprovarXoc(getWidth(),getHeight());
        }
        for (int i = 0; i < shapeList.size(); i++) {
            for (int j = i + 1; j < shapeList.size(); j++) {
                shapeList.get(i).comporbarXocAmb(shapeList.get(j));
            }
        }
        repaint();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        for (Shape shape : shapeList){
            shape.paint(g2);
        }
    }
}
