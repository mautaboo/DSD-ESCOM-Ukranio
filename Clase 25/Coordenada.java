import java.util.*;

public class Coordenada {

    private double x;
    private double y;
    public int mag;

    public Coordenada(double x, double y,int mag) {
        this.x = x;
        this.y = y;
        this.mag = mag;
    }

    public double getY(){ return this.x;}
    public double getX() {return this.y;}
    public int getMagnitud() {return this.mag;}

    //Sobreescritura del método de la superclase objeto para imprimir con System.out.println( )
    @Override
    public String toString( ) {
        return "["+x+","+y+","+mag+"]";
    }
}
