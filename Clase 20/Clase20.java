import java.nio.charset.StandardCharsets;
import java.util.Random;
import java.util.Scanner;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;


class Multihiloclase20 extends Thread {
    public void run(int n)
    {
        try {
            // Displaying the thread that is running
            System.out.println("Hilo " + Thread.currentThread().getId()+ " se esta ejecutando");
            int a;
            char d;
            String aux = "";
            String IPN = "IPN";
            int bp = 0;
            byte[] conver = new byte[n*4]; 
            byte[] cadenota = new byte[n*4];
            StringBuilder newcadenota = new StringBuilder();
    
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
    
            String Cadaux = new String(cadenota, StandardCharsets.UTF_8);
            for(int i=0;i<Cadaux.length();i=i+4){
                newcadenota.append(Cadaux.substring(i,i+3)); 
            }
            int contador = 0;
            int indexP = 0;
            int index = 0;
            
            do{
                index = newcadenota.indexOf(IPN, indexP);
                if(index!=-1){
                    contador++;
                }
                indexP = index+1;
            }while(index!=-1);
            System.out.println ("Ocurrencias: " + contador);



        }
        catch (Exception e) {
            // Throwing an exception
            System.out.println("Exception is caught");
        }
    }
}

public static String execCmd(String cmd) {
    String result = null;
    try (InputStream inputStream = Runtime.getRuntime().exec(cmd).getInputStream();
            Scanner s = new Scanner(inputStream).useDelimiter("\\A")) {
        result = s.hasNext() ? s.next() : null;
    } catch (IOException e) {
        e.printStackTrace();
    }
    return result;
}


public class Clase20 {
    public void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        int hilos = 4; // Number of threads
        for (int i = 0; i < hilos; i++) {
            Multihiloclase20 object = new Multihiloclase20();
            object.start();
        }        
    }
}
