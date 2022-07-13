public class Ejercicio4 {
public static void main(String[] args){
  int n1 = 0, n2 = 1, n3 = 2, suma;
    for (int i = 0; i < 20; i++) {
        suma = n1 + n2 + n3;
        System.out.println(n1);
        n1 = n2;
        n2 = n3;
        n3 = suma;
    }
  }
}

