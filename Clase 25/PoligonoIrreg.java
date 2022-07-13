
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Comparator;
import java.util.*;
import java.lang.*;
import java.io.*;

public class PoligonoIrreg implements java.io.Serializable{

    List<Coordenada> vertices = new ArrayList<Coordenada>();

    public PoligonoIrreg(){}

    public Comparator<Coordenada> compareByMagnitude = new Comparator<Coordenada>(){
        @Override
        public int compare(Coordenada cord1,Coordenada cord2){
          return cord1.getMagnitud() - cord2.getMagnitud();
        }
    };    

    int a,b,auxa,auxb,magni;
    double auxaa,auxbb;

    public void anadeVertice(){
        auxaa=(Math.random()*100)-(Math.random()*90);
        auxbb=(Math.random()*100)-(Math.random()*90);
        magni=(int)(Math.sqrt((auxaa*auxaa)+(auxbb*auxbb)));
        Coordenada cord1 = new Coordenada(a=(int)(auxaa),b=(int)(auxbb),magni);
        vertices.add(cord1);
     }

     
     public void ordenaVertices (){
        Collections.sort(vertices,compareByMagnitude);
    }

    @Override
    public String toString(){
        vertices.forEach((vertice) -> {
            System.out.println(vertice);
        });
        return "Total de vertices: " + vertices.size();
    }
}


