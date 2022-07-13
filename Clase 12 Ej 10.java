import java.util.ArrayList;
import java.util.List;

public class PoligonoIrreg{

    List<Coordenada> Vertices = new ArrayList<Coordenada>();  
    public PoligonoIrreg(){ }
    public void anadeVertice(Coordenada cord){
        Vertices.add(cord);
    }

    @Override
    public String toString( ) {
        Vertices.forEach((vertice) -> {
            System.out.println(vertice);
        });
        return "No. de Vertices: " + Vertices.size();
    }
}