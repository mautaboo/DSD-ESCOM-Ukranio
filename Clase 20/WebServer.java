import com.sun.net.httpserver.Headers; 
import com.sun.net.httpserver.HttpContext;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;   
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigInteger;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import java.util.Random;
import java.util.Arrays;
import java.util.concurrent.Executors;
public class WebServer {
    private static final String TASK_ENDPOINT = "/task";        //Cadenas correspondientes a los endpoints del servidor
    private static final String STATUS_ENDPOINT = "/status";
    private static final String SEARCHIPN_ENDPOINT = "/searchipn";
    private final int port;     //Variables privadas del servidor
    private HttpServer server;  //Implementa un servidor HTTP sencillo
    public static void main(String[] args) {
        int serverPort = 80;  //Puerto por defecto del servidor
        if (args.length >= 1) {
            serverPort = Integer.parseInt(args[0]); //En caso de que se pase por la línea de comandos
        }
        WebServer webServer = new WebServer(serverPort); //Se instancia un objeto del tipo webserver
        webServer.startServer();                         //Inicualiza la cnofiguración del servidor   
        System.out.println("Servidor escuchando en el puerto " + serverPort); //Imprime el puerto en el cual está escuchando dicho servidor
    }
    public WebServer(int port) {    //Constructor qwue recibe como variable el puerto
        this.port = port;
    }
    public void startServer() {
        try {
            this.server = HttpServer.create(new InetSocketAddress(port), 0); //Se le asigna a nuestra variable srever la creación de una instancia de socket TCP vinculada a una IP y al puerto, el segundo parámetro es el tamaño de la lista de solicitudes pendientes que permitimos a nuetsro servidor HTTP mantene en un cola de espera, si se pone 0 se deja la decisión al sistema 
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        HttpContext statusContext = server.createContext(STATUS_ENDPOINT); //Crea un objeto HTTPContext con una ruta relativa
        HttpContext searchIpnContext = server.createContext(SEARCHIPN_ENDPOINT);
        statusContext.setHandler(this::handleStatusCheckRequest); //Vincula con el handler
        searchIpnContext.setHandler(this::handleSearchIPNRequest);
        server.setExecutor(Executors.newFixedThreadPool(8)); //Establece un objeto, un pool de 8 hilos
        server.start();
    }

//----------------------------------------------------------------------------------------------------------------------------
    private void handleSearchIPNRequest(HttpExchange exchange) throws IOException {
        if (!exchange.getRequestMethod().equalsIgnoreCase("post")) { //Verifica si el método corresponde a post
            exchange.close();
            return;
        }
        Headers headers = exchange.getRequestHeaders();
        boolean isDebugMode = false;
        if (headers.containsKey("X-Debug") && headers.get("X-Debug").get(0).equalsIgnoreCase("true")) { //Si entre los header se encuentra X-debug y el valor asociado es true
            isDebugMode = true; //Si es true, recolenctaremos una serie de información
        }
        String respuestacad = "";
        long startTime = System.nanoTime();
        if (headers.containsKey("X-Search") && headers.get("X-Search").get(0).equalsIgnoreCase("true")) { //Si entre los header se encuentra X-test y el valor asociado es true
            byte[] requestBytes = exchange.getRequestBody().readAllBytes();
            String bodyString = new String(requestBytes);
            String[] stringcomps = bodyString.split(",");
            int nt = Integer.parseInt(stringcomps[0]);
            String CAB = stringcomps[1];
            respuestacad = buscarCad(nt,CAB);
        }
        byte[] RF = respuestacad.getBytes();
         
        long finishTime = System.nanoTime();
        long totaltime = finishTime - startTime;
        
        if (isDebugMode) {
            String debugMessage = String.format("TNanoseg: %d", totaltime); // Devolvemos la cantidad de timepo 
            exchange.getResponseHeaders().put("X-Debug-Info", Arrays.asList(debugMessage)); //Lo introducimos en el header X-Debug-info
            sendResponse(debugMessage.getBytes(),exchange);
        }
        sendResponse(RF, exchange);  
    }

    private String buscarCad(int nt,String CAB) {
        int a;
        char d;
        String aux = "";
        int bp = 0;
        byte[] conver = new byte[nt*4]; 
        byte[] cadenota = new byte[nt*4];
        StringBuilder newcadenota = new StringBuilder();
        for(int i=0;i<nt;i++){
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
            index = newcadenota.indexOf(CAB, indexP);
            if(index!=-1){
                contador++;
            }
            indexP = index+1;
        }while(index!=-1);
        return String.format("Ocurrencias: %s\n", contador);
       }

       private void handleStatusCheckRequest(HttpExchange exchange) throws IOException {
        if (!exchange.getRequestMethod().equalsIgnoreCase("get")) {  //Verifica si el método corresponde a get
            exchange.close();
            return;
        }
        String responseMessage = "El servidor está vivo\n";
        sendResponse(responseMessage.getBytes(), exchange);
    }
//-----------------------------------------------------------------------------------------------------------------------------------

    private void sendResponse(byte[] responseBytes, HttpExchange exchange) throws IOException {
        exchange.sendResponseHeaders(200, responseBytes.length);  //Envía la respuesta con código 200
        OutputStream outputStream = exchange.getResponseBody();
        outputStream.write(responseBytes); //Se escribe en el stream
        outputStream.flush();
        outputStream.close();
        exchange.close();
    }
    
}