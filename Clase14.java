/*
 *  MIT License
 *
 *  Copyright (c) 2019 Michael Pogrebinsky - Distributed Systems & Cloud Computing with Java
 *
 *  Permission is hereby granted, free of charge, to any person obtaining a copy
 *  of this software and associated documentation files (the "Software"), to deal
 *  in the Software without restriction, including without limitation the rights
 *  to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 *  copies of the Software, and to permit persons to whom the Software is
 *  furnished to do so, subject to the following conditions:
 *
 *  The above copyright notice and this permission notice shall be included in all
 *  copies or substantial portions of the Software.
 *
 *  THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 *  IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 *  FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 *  AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 *  LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 *  OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 *  SOFTWARE.
 */
import com.sun.net.httpserver.Headers;      //Librerias para construir un servidor HTTP en java
import com.sun.net.httpserver.HttpContext;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;   
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigInteger;
import java.net.InetSocketAddress;
import java.util.Arrays;
import java.util.concurrent.Executors;
public class WebServer {
    private static final String TASK_ENDPOINT = "/task";        //Cadenas correspondientes a los endpoints del servidor
    private static final String STATUS_ENDPOINT = "/status";
    private static final String SEARCHIPN_ENDPOINT = "/searchipn";
    private final int port;     //Variables privadas del servidor
    private HttpServer server;  //Implementa un servidor HTTP sencillo
    //private static int n;
    //private static String subcadena;
    public static void main(String[] args) {
        int serverPort = 8080;  //Puerto por defecto del servidor
        if (args.length >= 1) {
            serverPort = Integer.parseInt(args[0]); //En caso de que se pase por la línea de comandos
            // n = Integer.parseInt(args[1]);
            // subcadena = args[2].toString();
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
        HttpContext taskContext = server.createContext(TASK_ENDPOINT);
        HttpContext searchIpnContext = server.createContext(SEARCHIPN_ENDPOINT);
        statusContext.setHandler(this::handleStatusCheckRequest); //Vincula con el handler
        taskContext.setHandler(this::handleTaskRequest);
        searchIpnContext.setHandler(this::handleSearchIPNRequest);
        server.setExecutor(Executors.newFixedThreadPool(8)); //Establece un objeto, un pool de 8 hilos
        server.start();
    }
    private void handleSearchIPNRequest(HttpExchange exchange) throws IOException {
        if (!exchange.getRequestMethod().equalsIgnoreCase("post")) { //Verifica si el método corresponde a post
            exchange.close();
            return;
        }
        long startTime = System.nanoTime();
        
        Headers headers = exchange.getRequestHeaders();
        String Response = "";
        if (headers.containsKey("X-Search") && headers.get("X-Search").get(0).equalsIgnoreCase("true")) { //Si entre los header se encuentra X-test y el valor asociado es true
            
            byte[] requestBytes = exchange.getRequestBody().readAllBytes();
            //String Response = new String(requestBytes);
            
            ex6_v2 search = new ex6_v2(requestBytes);
            Response = search.SearchSubcadena();
            //sendResponse(Response.getBytes(), exchange); //Se envía una cadena como respuesta
            
            //return;
        }
        boolean isDebugMode = false;
        if (headers.containsKey("X-Debug") && headers.get("X-Debug").get(0).equalsIgnoreCase("true")) { //Si entre los header se encuentra X-debug y el valor asociado es true
            isDebugMode = true; //Si es true, recolenctaremos una serie de información
        }
        long finishTime = System.nanoTime();
        if (isDebugMode) {
            String debugMessage = String.format("La operación tomó %d nanosegundos", finishTime - startTime); // Devolvemos la cantidad de timepo 
            exchange.getResponseHeaders().put("X-Debug-Info", Arrays.asList(debugMessage)); //Lo introducimos en el header X-Debug-info
        }
        sendResponse(Response.getBytes(), exchange);
        
    }
    private void handleTaskRequest(HttpExchange exchange) throws IOException { //Manejador del endpoint task
        if (!exchange.getRequestMethod().equalsIgnoreCase("post")) { //Verifica si el método corresponde a post
            exchange.close();
            return;
        }
        Headers headers = exchange.getRequestHeaders();
        if (headers.containsKey("X-Test") && headers.get("X-Test").get(0).equalsIgnoreCase("true")) { //Si entre los header se encuentra X-test y el valor asociado es true
            String dummyResponse = "123\n";
            sendResponse(dummyResponse.getBytes(), exchange); //Se envía una cadena como respuesta
            return;
        }
        boolean isDebugMode = false;
        if (headers.containsKey("X-Debug") && headers.get("X-Debug").get(0).equalsIgnoreCase("true")) { //Si entre los header se encuentra X-debug y el valor asociado es true
            isDebugMode = true; //Si es true, recolenctaremos una serie de información
        }
        long startTime = System.nanoTime();
        byte[] requestBytes = exchange.getRequestBody().readAllBytes(); //Recupera toda la información del cuerpo del mensaje
        byte[] responseBytes = calculateResponse(requestBytes); //Pasa por este método 
        long finishTime = System.nanoTime();
        if (isDebugMode) {
            String debugMessage = String.format("La operación tomó %d nanosegundos", finishTime - startTime); // Devolvemos la cantidad de timepo 
            exchange.getResponseHeaders().put("X-Debug-Info", Arrays.asList(debugMessage)); //Lo introducimos en el header X-Debug-info
        }
        sendResponse(responseBytes, exchange);
    }
    private byte[] calculateResponse(byte[] requestBytes) {
        String bodyString = new String(requestBytes);
        String[] stringNumbers = bodyString.split(",");
        BigInteger result = BigInteger.ONE;
        for (String number : stringNumbers) {
            BigInteger bigInteger = new BigInteger(number);
            result = result.multiply(bigInteger);
        }
        return String.format("El resultado de la multiplicación es %s\n", result).getBytes();
    }
    private void handleStatusCheckRequest(HttpExchange exchange) throws IOException {
        if (!exchange.getRequestMethod().equalsIgnoreCase("get")) {  //Verifica si el método corresponde a get
            exchange.close();
            return;
        }
        String responseMessage = "El servidor está vivo\n";
        sendResponse(responseMessage.getBytes(), exchange);
    }
    private void sendResponse(byte[] responseBytes, HttpExchange exchange) throws IOException {
        exchange.sendResponseHeaders(200, responseBytes.length);  //Envía la respuesta con código 200
        OutputStream outputStream = exchange.getResponseBody();
        outputStream.write(responseBytes); //Se escribe en el stream
        outputStream.flush();
        outputStream.close();
        exchange.close();
    }
    
}