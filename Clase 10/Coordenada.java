import java.util.*;

public class Coordenada {

    private double x; 
    private double y;
    public int mag;

    public Coordenada(double x, double y) {
        this.x = x;
        this.y = y;
        mag = (int)(Math.sqrt((x*x)+(y*y)));
    }
    //Metodo getter de x
    public double setAbcisa(double X) { return x;}
    public double abcisa( ) { return x; }

    //Metodo getter de y
    public double setOrdenada(double y) { return y;}
    public double ordenada( ) { return y; }

    //mag
    public int setMagnitud(int mag){return mag;}
    public int getMagnitud(Coordenada corde){
        mag=corde.mag;
        return mag;}
    public int magnitud( ){ return mag; }

    //Sobreescritura del método de la superclase objeto para imprimir con System.out.println( )
    @Override
    public String toString( ) {
        return "[" + x + "," + y +"," + mag + "]";
    }

}
