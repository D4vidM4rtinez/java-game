import java.awt.*;
import java.util.ArrayList;

public class Shape {

    private final int r;
    int centerx;
    int centery;
    private int velx;
    private int vely;
    private int x;
    private int dirx;
    private int y;
    private int diry;
    private int w;
    private int h;

    public Shape(int x, int y, int w, int h, int dirx, int diry, int velx, int vely) {
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
        this.dirx = dirx;
        this.diry = diry;
        setVelx(velx);
        setVely(vely);
        this.r = w/2;
        centerx = this.x + r;
        centery = this.y + r;
    }

    public int getVelx() {
        return velx;
    }

    public void setVelx(int velx) {
        this.velx = Math.max(velx, 0);
    }

    public int getVely() {
        return vely;
    }

    public void setVely(int vely) {
        this.vely = Math.max(vely, 0);
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getDirx() {
        return dirx;
    }

    public void setDirx(int dirx) {
        this.dirx = dirx;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getDiry() {
        return diry;
    }

    public void setDiry(int diry) {
        this.diry = diry;
    }

    public int getW() {
        return w;
    }

    public void setW(int w) {
        this.w = w;
    }

    public int getH() {
        return h;
    }

    public void setH(int h) {
        this.h = h;
    }

    public void paint(Graphics g2) {
        g2.setColor(Color.BLUE);
        g2.fillOval(x, y, w, h);
    }

    public void mou() {
        x = x + dirx * velx;
        y = y + diry * vely;
        centerx = x + r;
        centery = y + r;
    }

    private void rebotarX() {
        dirx = -dirx;
    }

    public void rebotarY() {
        diry = -diry;
    }

    public void comporbarXocAmb(Shape other){
        int distancex = this.centerx - other.centerx;
        int distancey = this.centery - other.centery;
        double distancia2 = (distancex * distancex) + (distancey * distancey);

        int sumaRad = this.r + other.r;
        double sumaRad2 = sumaRad * sumaRad;
        if (distancia2 <= sumaRad2 && distancia2 > 0) {
            // 1. Swap movement between the two shapes
            int tempDirx = this.dirx;
            int tempDiry = this.diry;
            int tempVelx = this.velx;
            int tempVely = this.vely;

            this.dirx = other.dirx;
            this.diry = other.diry;
            this.velx = other.velx;
            this.vely = other.vely;

            other.dirx = tempDirx;
            other.diry = tempDiry;
            other.velx = tempVelx;
            other.vely = tempVely;

            // 2. Push them apart so they don't stay overlapping
            separar(other, Math.sqrt(distancia2), sumaRad);
        }
    }
    private void separar(Shape other, double distancia, int sumaRad) {
        if (distancia == 0) distancia = 0.01; // avoid divide-by-zero if centers coincide

        double overlap = (sumaRad - distancia) / 2.0;
        double dx = (this.centerx - other.centerx) / distancia;
        double dy = (this.centery - other.centery) / distancia;

        this.x += (int)(dx * overlap);
        this.y += (int)(dy * overlap);
        other.x -= (int)(dx * overlap);
        other.y -= (int)(dy * overlap);

        this.centerx = this.x + this.r;
        this.centery = this.y + this.r;
        other.centerx = other.x + other.r;
        other.centery = other.y + other.r;
    }

    public void comprovarXoc(int ampladaPanell, int alcadaPanell) {
        // Left wall
        if (x <= 0) {
            x = 0;
            rebotarX();
        }
        // Right wall
        else if (x + w >= ampladaPanell) {
            x = ampladaPanell - w;
            rebotarX();
        }

        // Top wall
        if (y <= 0) {
            y = 0;
            rebotarY();
        }
        // Bottom wall
        else if (y + h >= alcadaPanell) {
            y = alcadaPanell - h;
            rebotarY();
        }
    }
}
