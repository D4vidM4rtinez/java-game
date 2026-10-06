import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;

public class Main_game extends JPanel implements ActionListener, KeyListener {
    JFrame finestra;
    Timer timer;
    List<Shape> shapeList = new ArrayList<>();
    Player player = new Player(50, 150, 20, 80, 0, 0);
    Player player2 = new Player(650, 150, 20, 80, 0, 0);
    PhysicsEngine physicsEngine = new PhysicsEngine();


    public Main_game() {
        setBackground(Color.WHITE);
        // Ahora puedes instanciar Círculos (y Rectángulos si quieres) dentro de la misma lista
        shapeList.add(new Circulo(50, 200, 50, 50, 2, 6));
        shapeList.add(new Circulo(200, 50, 50, 50, 5, 1));
        shapeList.add(player);
        shapeList.add(player2);

        finestra = new JFrame();
        finestra.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        finestra.setSize(new Dimension(700, 500));
        finestra.add(this);
        finestra.setVisible(true);

        timer = new Timer(16, this);   // ~60 FPS
        timer.start();

        setFocusable(true);
        addKeyListener(this);
        requestFocusInWindow();
    }

    public static void main(String[] args) {
        Main_game joc = new Main_game();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        player.actualizarVelocidadTeclado();
        player2.actualizarVelocidadTeclado();
        // 1. Mover figuras y comprobar colisión con paredes de forma genérica
        for (Shape shape : shapeList){
            shape.mou();
            comprovarXocParet(shape);
        }

        // 2. Comprobar colisiones entre ellas sin importar qué figura sean
        for (int i = 0; i < shapeList.size(); i++) {
            for (int j = i + 1; j < shapeList.size(); j++) {
                Shape s1 = shapeList.get(i);
                Shape s2 = shapeList.get(j);

                // Si sus cajas delimitadoras chocan, ejecutamos la física
                if (s1.getBounds().intersects(s2.getBounds())) {
                    resolverColision(s1, s2);
                }
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

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();

        if (key == KeyEvent.VK_UP ){
            player2.setMoveUp(true);
        }
        if (key == KeyEvent.VK_DOWN){
            player2.setMoveDown(true);
        }
        if (key == KeyEvent.VK_W){
            player.setMoveUp(true);
        }
        if (key == KeyEvent.VK_S){
            player.setMoveDown(true);
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();
        if (key == KeyEvent.VK_UP ){
            player2.setMoveUp(false);
        }
        if (key == KeyEvent.VK_DOWN){
            player2.setMoveDown(false);
        }
        if (key == KeyEvent.VK_W){
            player.setMoveUp(false);
        }
        if (key == KeyEvent.VK_S){
            player.setMoveDown(false);
        }
    }
    private void comprovarXocParet(Shape shape) {
        int ampladaPanell = getWidth();
        int alcadaPanell = getHeight();

        if (shape.getX() <= 0) {
            shape.setX(0);
            if (!(shape instanceof Player)){
                shape.setVx(Math.abs(shape.getVx()));
            }
        } else if (shape.getX() + shape.getW() >= ampladaPanell) {
            shape.setX(ampladaPanell - shape.getW());
            shape.setVx(-Math.abs(shape.getVx()));
        }

        if (shape.getY() <= 0) {
            shape.setY(0);
            shape.setVy(Math.abs(shape.getVy()));
        } else if (shape.getY() + shape.getH() >= alcadaPanell) {
            shape.setY(alcadaPanell - shape.getH());
            shape.setVy(-Math.abs(shape.getVy()));
        }
    }

    private void resolverColision(Shape s1, Shape s2) {
        if (s1 instanceof Circulo && s2 instanceof Circulo) {
            Circulo c1 = (Circulo) s1;
            Circulo c2 = (Circulo) s2;
            physicsEngine.bounceCircles(c1,c2);

        } else if (s1 instanceof Player||s2 instanceof Player) {
            Player player1;
            Shape shape;
            if (s1 instanceof Player){
                player1 = (Player) s1;
                shape = s2;
            } else {
                player1 = (Player) s2;
                shape = s1;
            }
            physicsEngine.playerBounce(player1 ,shape);

        } else {
            physicsEngine.basicBounce(s1,s2);
        }
    }
}
