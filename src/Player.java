import java.awt.*;

public class Player extends Shape {
    private boolean moveLeft = false;
    private boolean moveRight = false;
    private boolean moveUp = false;
    private boolean moveDown = false;
    public Player(int x, int y, int w, int h, double vx, double vy) {
        super(x, y, w, h, vx, vy);
    }

    public void setMoveLeft(boolean moveLeft) {this.moveLeft = moveLeft;}
    public void setMoveRight(boolean moveRight) {this.moveRight = moveRight;}
    public void setMoveUp(boolean moveUp) {this.moveUp = moveUp;}
    public void setMoveDown(boolean moveDown) {this.moveDown = moveDown;}
    public void actualizarVelocidadTeclado() {
        double velocidadBase = 8.0; // Cambia este número para ir más rápido o más lento

        // Eje Horizontal
        if (moveLeft && !moveRight) {
            this.vx = -velocidadBase;
        } else if (moveRight && !moveLeft) {
            this.vx = velocidadBase;
        } else {
            this.vx = 0;
        }

        // Eje Vertical
        if (moveUp && !moveDown) {
            this.vy = -velocidadBase;
        } else if (moveDown && !moveUp) {
            this.vy = velocidadBase;
        } else {
            this.vy = 0;
        }
    }
    public void stop(){
        setVx(0);
        setVy(0);
    }
    @Override
    public void paint(Graphics g2) {
        g2.setColor(Color.RED);
        g2.fillRect(getX(), getY(), getW(), getH()); // Dibuja un cuadrado/rectángulo
    }
}
