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
package networking;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.concurrent.CompletableFuture;

public class WebClient {
    private HttpClient client; //objeto Http client para instanciar clientes Http

    public WebClient() { //constructor que que crea un objeto http client con el protocolo http version 1.1
        this.client = HttpClient.newBuilder()
                .version(HttpClient.Version.HTTP_1_1)
                .build();
    }

    public CompletableFuture<String> sendTask(String url, byte[] requestPayload) {  //devuelve los CompletableFuture en un string 
        HttpRequest request = HttpRequest.newBuilder()
                .POST(HttpRequest.BodyPublishers.ofByteArray(requestPayload)) //metodo post con las tareas a enviar 
                .uri(URI.create(url)) //direccion de destino 
                .header("X-debug","true") //se agrego esta linea para enviar un TRUE al header X-Debug para obtener los tiempos de procesamiento
                .build();

        return client.sendAsync(request, HttpResponse.BodyHandlers.ofString()) //envia la solicitud request de una manera asyncrona
                .thenApply(HttpResponses -> { 
                return 
                HttpResponses.headers().toString() + "\n" //regresa en string los headers
                + HttpResponses.version().toString() + "\n" //regresa en string la version
                + HttpResponses.request().toString() + "\n" //regresa en string el cuerpo del mensaje   
                + HttpResponses.uri().toString() + "\n" //regresa en string el uri de esta respuesta
                + HttpResponses.body().toString() + "\n" ;//regresa en string el cuerpo del mensaje
            });     
    }
}
