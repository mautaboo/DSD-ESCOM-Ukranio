import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

public class Ejercicio5 {
    public static void main(String[] args){
        long time0 = System.nanoTime();
        int n = Integer.parseInt(args[0]),a;
        char d;
        String aux = "";
        String IPN = "IPN";
        int bp = 0;
        byte[] conver = new byte[n*4]; 
        byte[] cadenota = new byte[n*4]; 
        for(int i=0;i<n;i++){
            for(int j=0;j<3;j++){
             a = (int) (Math.random () * 26 + 65);
             d = (char) a;
            aux = aux + String.valueOf(d);
            }
            aux = aux + " ";
            conver = aux.getBytes();
            System.arraycopy(conver, 0, cadenota, bp, conver.length);
            bp += conver.length;
            aux = "";
        }
        String cadenaB = new String(cadenota, StandardCharsets.UTF_8);
        int contador = 0;
        int indexP = 0;
        int index = 0;

        do{
            index = cadenaB.indexOf(IPN, indexP);
            if(index!=-1){
                contador++;
            }
            indexP = index+1;
        }while(index!=-1);

        long time1 = System.nanoTime();
        System.out.println("Tiempo en nseg: " + (time1-time0)); 
        System.out.println ("Ocurrencias: " + contador);

        }
}