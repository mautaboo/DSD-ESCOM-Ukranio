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
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Aggregator {
    private WebClient webClient;

    public Aggregator() {
	//El constructor instancia un objeto de tipo WebClient
        this.webClient = new WebClient();
    }

    /*
     * Este mé todo recibe la lista de direcciones de los trabajadores y la lista de las tareas
     * que tienen que desarrollar.
     * Para el manejo de la comunicación asíncrona se hace por medio de la clase COmpletableFuture
     * la cual permite continuar la ejecución de cñodigo bloqueante.
     * En este caso se tiene un arreglo llamado future para almacenar las respuestas futuras de los
     * dos servidores.
     * */
    public List<String> sendTasksToWorkers(List<String> workersAddresses, List<String> tasks) {
        CompletableFuture<String>[] futures = new CompletableFuture[workersAddresses.size()];

	//Se itera sobre todos los métodos de la lista y se van obteniendo las direcciones de los trabajadores y las tareas
        for (int i = 0; i < workersAddresses.size(); i++) {
            String workerAddress = workersAddresses.get(i);
            String task = tasks.get(i);

	    //SE almacenan las tareas en un formato de byte y se envían las tareas asíncronas con el método sendTask()
            byte[] requestPayload = task.getBytes();
	    //Aquí se asocian las tareas a cada uno de los futures
            futures[i] = webClient.sendTask(workerAddress, requestPayload);
        }

	//Se declara una lista de resultados para regresar la lista de todas las respuestas asíncronas
        List<String> results = Stream.of(futures).map(CompletableFuture::join).collect(Collectors.toList());

        return results;
    }
}