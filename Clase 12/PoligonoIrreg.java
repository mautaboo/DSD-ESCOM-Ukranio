
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.*;
import java.util.Comparator;
import java.lang.*;
import java.io.*;

public class PoligonoIrreg{

    List<Coordenada> vertices1 = new ArrayList<Coordenada>();

    public PoligonoIrreg(){}

    public Comparator<Coordenada> compareByMagnitude = new Comparator<Coordenada>(){
        @Override
        public int compare(Coordenada cord1,Coordenada cord2){
          return cord1.getMagnitud() - cord2.getMagnitud();
        }
    };    

    public void anadeVertice(Coordenada cord1){
        vertices1.add(cord1);
     }

     
     public void ordenaVertices (){
        Collections.sort(vertices1,compareByMagnitude);
    }

    @Override
    public String toString(){
        vertices1.forEach((vertice) -> {
            System.out.println(vertice);
        });
        return "Total de vertices: " + vertices1.size();
    }
}


