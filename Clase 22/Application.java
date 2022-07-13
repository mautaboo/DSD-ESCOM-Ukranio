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
import java.util.Arrays;
import java.util.List;

public class Application {
    //definimos las direcciones junto con los endpoitns de los workers//
    private static final String WORKER_ADDRESS_1 = "http://34.125.63.197:80/searchipn";
    private static final String WORKER_ADDRESS_2 = "http://34.125.132.148:80/searchipn";
    private static final String WORKER_ADDRESS_3 = "http://34.125.188.41:80/searchipn";
    private static final String WORKER_ADDRESS_4 = "http://34.125.128.203:80/searchipn";

    public static void main(String[] args) {
        Aggregator aggregator = new Aggregator(); //se inicializa un nuevo objeto de la clase aggregator
        String task1 = "1757600,IPN"; 
        //se definen las tareas que se enviaran 
        double[] aux = new double[4]; 
        //varible auxiliar para almacenar los tiempos
        double tiem_fin = 0;
        int conttime = 0;
        double consta = 0.000000001;

        List<String> results = aggregator.sendTasksToWorkers(Arrays.asList(WORKER_ADDRESS_1, WORKER_ADDRESS_2, WORKER_ADDRESS_3, WORKER_ADDRESS_4), Arrays.asList(task1, task1, task1, task1)); 
        //Envía las tareas a los trabajadores
        //Se envian las direcciones de los trabajadores y las tareas con el metodo sendTasktoWorkers
        //la varibale result va a contener los resultados obtenidos de los servidores 
        
        for (String result : results) { 
            System.out.println(result); //se imprimen los resultados obtenidos
            String[] linea = result.split("\n"); //divimos las lineas obtenidas
            String[] tempos = linea[4].split(":"); //almacenamos cada tiempo en la varible tempos como un string 
            aux[conttime] = Double.parseDouble(tempos[1]) ; //hacemos un cast de string a double para manejar los tiempos
            conttime++;
        }
      
        for(double t : aux){
            tiem_fin = tiem_fin + t; //Le sumamos cada tiempo a la varible tiem_fin
        }

        System.out.println("El tiempo de procesamiento total es: " + tiem_fin*consta + " segundos\n"); //se imprimen los tiempos obtenidos
        System.out.println("El tiempo de procesamiento promedio es: " + (tiem_fin*consta)/4 + " segundos\n"); //se imprimen los tiempos obtenidos
    }
}