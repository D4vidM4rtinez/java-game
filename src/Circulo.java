import java.awt.*;

public class Circulo extends Shape {
    private int r;

    public Circulo(int x, int y, int w, int h, double vx, double vy) {
        super(x, y, w, h, vx, vy);
        this.r = w / 2;
    }

    public int getR() {
        return r;
    }

    /**
     * Calcula la posición del centro en tiempo real utilizando 'x' e 'y' de la clase padre Shape
     * @return
     */
    public double getCenterX() {
        return this.x + r;
    }

    /**
     * Calcula la posición del centro en tiempo real utilizando 'x' e 'y' de la clase padre Shape
     * @return
     */
    public double getCenterY() {
        return this.y + r;
    }

    @Override
    public void paint(Graphics g2) {
        g2.setColor(Color.BLUE);
        g2.fillOval(getX(), getY(), getW(), getH());
    }

    @Override
    public void setVx(double vx){
        this.vx = vx;
        limitarVelocidadMaxima();
    }

    @Override
    public void setVy(double vy){
        this.vy = vy;
        limitarVelocidadMaxima();
    }

    /**
     * Limita la velocidad total a un máximo de 8.0
     * para evitar que el círculo acelere de forma descontrolada en las colisiones.
     */
    private void limitarVelocidadMaxima() {
        double maxVelocidad = 8.0;
        // Calculamos la velocidad total actual usando el teorema de Pitágoras
        double velocidadActual = Math.sqrt((this.vx * this.vx) + (this.vy * this.vy));

        // Si la velocidad supera el límite, escalamos el vector proporcionalmente
        if (velocidadActual > maxVelocidad) {
            this.vx = (this.vx / velocidadActual) * maxVelocidad;
            this.vy = (this.vy / velocidadActual) * maxVelocidad;
        }
    }
}
