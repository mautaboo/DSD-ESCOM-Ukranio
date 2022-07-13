import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.*;
import java.util.Comparator;
import java.lang.*;
import java.io.*;

public class PoligonoIrreg{

    List<Coordenada> vertices1 = new ArrayList<Coordenada>();
    List<Coordenada> vertices2 = new ArrayList<Coordenada>();

    public PoligonoIrreg(){}

    public void anadeVertice(Coordenada c1){
        vertices1.add(c1);
        vertices2.add(c1);
     }

    class Sortbymag implements Comparator<Coordenada>{
    public int compare(Coordenada a,Coordenada b) {
        return a.mag - b.mag;
       }
    }

    public void ordenaVertices (){
        Collections.sort(vertices2, new Sortbymag());
    }
    
    @Override
    public String toString(){
        vertices1.forEach((vertice) -> {
            System.out.println(vertice);
        });
        System.out.println("---------------------------------\n");
        vertices2.forEach((vertice) -> {
            System.out.println(vertice);
        });
        for (int i=0; i<vertices2.size(); i++){
            System.out.println(vertices2.get(i));
        }
       
        return "Total de vertices: " + vertices1.size();
    }
}


