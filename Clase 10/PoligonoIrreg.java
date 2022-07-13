public class Constructor5{
        //Ejercicio con Setters

        public static void main (String[] args) {
            PoligonoIrreg poligono = new PoligonoIrreg(5);
            double a,b;
            Coordenada cord = new Coordenada(0,0);
            double t0 = System.nanoTime();

            for(int i=0;i<10000000;i++){
                a=(double)(Math.random()*10);
                b=(double)(Math.random()*10);
                cord.SetAbcisa(a);
                cord.SetOrdenada(b);
                poligono.anadeVertice(cord);
            }

            double t1 = System.nanoTime();
            System.out.println("El tiempo fue: " + (t1-t0) + " nanosegs");
    
        }
    }