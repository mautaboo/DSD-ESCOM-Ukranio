import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Hilitowh extends Thread{
    
    private int numhilo;
    public Hilitowh (int numhilo){
        this.numhilo = numhilo;
    }
    public static void main(String[] args) {
        
        int n = Integer.parseInt(args[0]);
        System.out.println("----------------e2-highcpu-4-------------------------------------");
        for(int i=0; i < n; i++){
            Hilitowh thread = new Hilitowh(i);
            thread.start();
        }
    }
    @Override
    public void run(){
        try {
            String[] curl_string = {"curl", "-v", "--header", "X-Debug:true", "--header", "X-Search:true", "--data", "1757600,IPN", "34.125.39.83:80/searchipn"};  
            Process process = Runtime.getRuntime().exec(curl_string);
            StringBuilder output = new StringBuilder();
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            BufferedReader stdError = new BufferedReader(new InputStreamReader(process.getErrorStream()));
            String line = null;
            while ((line = stdError.readLine()) != null) {
                output.append(line + "\n");
            }
            while((line = reader.readLine()) != null){
                output.append(line + "\n");
            }
            System.out.println("Hilo No." + this.numhilo + "ejecutado correctamente");
            System.out.println(output);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}