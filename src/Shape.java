import java.awt.*;

public abstract class Shape {
    // Atributos protegidos para que las clases hijas puedan usarlos
    protected double x;
    protected double y;
    protected int w;
    protected int h;
    protected double vx;
    protected double vy;

    public Shape(int x, int y, int w, int h, double vx, double vy) {
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
        this.vx = vx;
        this.vy = vy;
    }

    // Getters y Setters universales
    public int getX() { return (int) x; }

    public void setX(double x) { this.x = x; }

    public int getY() { return (int) y; }

    public void setY(double y) { this.y = y; }

    public int getW() { return w; }

    public int getH() { return h; }

    public double getVx() { return vx; }

    public void setVx(double vx) { this.vx = vx; }

    public double getVy() { return vy; }

    public void setVy(double vy) { this.vy = vy; }

    // Todas las figuras se mueven igual de forma básica
    public void mou() {
        x += vx;
        y += vy;
    }

    // Cada figura se dibuja de forma diferente
    public abstract void paint(Graphics g2);


    public Rectangle getBounds() {
        return new Rectangle((int) x, (int) y, w, h);
    }
}
