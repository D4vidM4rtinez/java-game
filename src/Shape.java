import java.awt.*;

public class Shape {

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
    }

    public int getVelx() {
        return velx;
    }

    public void setVelx(int velx) {
        this.velx = Math.max(velx, 1);
    }

    public int getVely() {
        return vely;
    }

    public void setVely(int vely) {
        this.vely = Math.max(vely, 1);
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

    public void paint(Graphics g2){
        g2.setColor(Color.BLUE);
        g2.fillOval(x, y, w, h);
    }

    public void mou(){
        x = x + dirx * velx;
        y = y + diry * vely;
    }

    private void rebotarX(){
        dirx = -dirx;
    }
    public void rebotarY() {
        diry = -diry;
    }

    public void comprovarXoc(int ampladaPanell, int alcadaPanell) {
        // Left wall
        if (x <= 0) {
            x = 0;
            if (dirx < 0) rebotarX();
        }
        // Right wall
        else if (x + w >= ampladaPanell) {
            x = ampladaPanell - w;
            if (dirx > 0) rebotarX();
        }

        // Top wall
        if (y <= 0) {
            y = 0;
            if (diry < 0) rebotarY();
        }
        // Bottom wall
        else if (y + h >= alcadaPanell) {
            y = alcadaPanell - h;
            if (diry > 0) rebotarY();
        }
    }
}
