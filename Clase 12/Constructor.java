public class Constructor {

    public static void main (String[] args) {
          int a,b,auxa,auxb,magni;
          double auxaa,auxbb;
          PoligonoIrreg poligono = new PoligonoIrreg();
          double t0 = System.nanoTime();
          for(int i=0;i<10;i++){
              auxaa=(Math.random()*100)-(Math.random()*90);
              auxbb=(Math.random()*100)-(Math.random()*90);
              magni=(int)(Math.sqrt((auxaa*auxaa)+(auxbb*auxbb)));
              poligono.anadeVertice(new Coordenada(a=(int)(auxaa),b=(int)(auxbb),magni));
          }
  
          double t1 = System.nanoTime();
          System.out.println(poligono);
          poligono.ordenaVertices();
          System.out.println(poligono);
          System.out.println("El tiempo fue: " + (t1-t0) + " nanosegs");
      }
  }
  