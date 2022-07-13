public class Constructor4 {
    //Ejercicio con New
    public static void main (String[] args) {
        int a,b;
        PoligonoIrreg poligono = new PoligonoIrreg(10000000);
        double t0 = System.nanoTime();
        for(int i=0;i<10000000;i++){
            poligono.anadeVertice(new Coordenada(a=(int)(Math.random()*10),b=(int)(Math.random () * 10 )));
        }

        double t1 = System.nanoTime();
        System.out.println(poligono);
        System.out.println("El tiempo fue: " + (t1-t0) + " nanosegs");
    }

}