import java.awt.*;

public class PhysicsEngine {

    public void playerBounce(Player player, Shape s1){
        Rectangle playerHitbox = player.getBounds();
        Rectangle r1 = s1.getBounds();

        // 1. Obtener la zona exacta de intersección donde se cruzan
        Rectangle interseccion = playerHitbox.intersection(r1);

        // Determinar el eje del impacto comparando el ancho y alto del cruce
        boolean esChoqueHorizontal = (interseccion.width < interseccion.height);

        if (esChoqueHorizontal) {
            // --- REBOTE LATERAL (Izquierda / Derecha) ---

            // Invertimos la velocidad en X de la pelota
            s1.setVx(-s1.getVx());

            // EFECTO: Transferimos el 50% de la velocidad vertical del jugador a la pelota
            s1.setVy(s1.getVy() + (player.getVy() * 0.5));

        } else {
            s1.setVy(-s1.getVy());

            // EFECTO: Si golpeas la pelota moviéndote de lado, le transmites
            // el 50% de tu velocidad horizontal. ¡Esto te permite dirigir la pelota!
            s1.setVx(s1.getVx() + (player.getVx() * 0.5));
        }
        aplicarSeparacionGenerica(player, s1);
    }
    /**
     * uses the Rectangle object to make a generic elastic bounce that works for "all" shapes
     * @param s1
     * @param s2
     */
    public void basicBounce(Shape s1, Shape s2){
        Rectangle r1 = s1.getBounds();
        Rectangle r2 = s2.getBounds();

        // 1. Obtener la caja geométrica exacta de la intersección donde se cruzan
        Rectangle interseccion = r1.intersection(r2);

        // 2. Determinar el eje del impacto comparando el ancho y alto del cruce
        if (interseccion.width < interseccion.height) {
            // EL CHOQUE ES HORIZONTAL (Izquierda / Derecha)

            // Invertimos las velocidades en el eje X
            s1.setVx(-s1.getVx());
            s2.setVx(-s2.getVx());

            // Separación física para evitar que se queden pegados (Anti-stuck)
            double magnitudSeparacion = interseccion.width / 2.0;
            if (s1.getX() < s2.getX()) {
                s1.setX(s1.getX() - magnitudSeparacion);
                s2.setX(s2.getX() + magnitudSeparacion);
            } else {
                s1.setX(s1.getX() + magnitudSeparacion);
                s2.setX(s2.getX() - magnitudSeparacion);
            }
        } else {
            // EL CHOQUE ES VERTICAL (Arriba / Abajo)

            // Invertimos las velocidades en el eje Y
            s1.setVy(-s1.getVy());
            s2.setVy(-s2.getVy());

            aplicarSeparacionGenerica(s1,s2);
        }
    }

    /**
     * elastic bounce logic for circles
     * @param c1
     * @param c2
     */
    public void bounceCircles(Circulo c1, Circulo c2){
        double distancex = c1.getCenterX() - c2.getCenterX();
        double distancey = c1.getCenterY() - c2.getCenterY();
        double distancia2 = (distancex * distancex) + (distancey * distancey);

        int sumaRad = c1.getR() + c2.getR();
        double sumaRad2 = (double) sumaRad * sumaRad;

        if (distancia2 <= sumaRad2 && distancia2 > 0) {
            double distancia = Math.sqrt(distancia2);
            double nx = distancex / distancia;
            double ny = distancey / distancia;

            double relVx = c1.getVx() - c2.getVx();
            double relVy = c1.getVy() - c2.getVy();
            double velAlongNormal = (relVx * nx) + (relVy * ny);

            if (velAlongNormal > 0) return;

            c1.setVx(c1.getVx() - velAlongNormal * nx);
            c1.setVy(c1.getVy() - velAlongNormal * ny);
            c2.setVx(c2.getVx() + velAlongNormal * nx);
            c2.setVy(c2.getVy() + velAlongNormal * ny);

            aplicarSeparacionGenerica(c1,c2);
        }
    }

    private void aplicarSeparacionGenerica(Shape s1, Shape s2) {
        Rectangle interseccion = s1.getBounds().intersection(s2.getBounds());
        if (interseccion.isEmpty()) return;

        boolean esChoqueHorizontal = (interseccion.width < interseccion.height);

        double peso1 = 0.5;
        double peso2 = 0.5;

        // 2. Modificamos los pesos con un bloque if / else tradicional
        if (s1 instanceof Player) {
            peso1 = 0.0; // El jugador no se mueve nada
            peso2 = 1.0; // La pelota se mueve el 100% de la distancia
        }
        else if (s2 instanceof Player) {
            peso1 = 1.0; // La pelota se mueve el 100% de la distancia
            peso2 = 0.0; // El jugador no se mueve nada
        }
        if (esChoqueHorizontal) {
            if (s1.getX() < s2.getX()) {
                // s1 está a la izquierda, lo empujamos restando. s2 a la derecha, sumando.
                s1.setX(s1.getX() - (interseccion.width * peso1));
                s2.setX(s2.getX() + (interseccion.width * peso2));
            } else {
                // s1 está a la derecha, lo empujamos sumando. s2 a la izquierda, restando.
                s1.setX(s1.getX() + (interseccion.width * peso1));
                s2.setX(s2.getX() - (interseccion.width * peso2));
            }
        } else {
            if (s1.getY() < s2.getY()) {
                // s1 está arriba, lo empujamos restando (hacia arriba). s2 abajo, sumando.
                s1.setY(s1.getY() - (interseccion.height * peso1));
                s2.setY(s2.getY() + (interseccion.height * peso2));
            } else {
                // s1 está abajo, lo empujamos sumando. s2 arriba, restando.
                s1.setY(s1.getY() + (interseccion.height * peso1));
                s2.setY(s2.getY() - (interseccion.height * peso2));
            }

    }
}}
