/**
 ** Escreva e teste os métodos
 ** - private static int[] subArray(int[] arr, int i, int f)
 ** que, dado um array e dois índices válidos do array, retorna um novo array apenas com os elementos
 ** entre esses dois índices.
 ** - private static int[] arrayConcat(int[] arr1, int[] arr2)
 ** que recebe dois arrays e retorna o array resultado de os concatenar. O array resultante deve estar
 ** ordenado por ordem crescente.
 */

import java.util.Arrays;

public class ex45 {
    private static int[] subArray (int[] arr, int i, int f) {
        int n = f-i+1, o;
        int[] sA = new int [n];
        for (o=0; o<n; o++,i++) {
            sA[o] = arr[i];
        }
        return sA;
    }
    private static int[] arrayConcat(int[] arr1, int[] arr2) {
        int l1 = arr1.length, l2 = arr2.length, i=0,w=0;
        int[] res = new int[l1+l2];
        for (i=0; i < l1; i++) {
            res[i] = arr1[w];
            w++;
        }
        w=0;
        for (; i<l1+l2;i++) {
            res[i] = arr2[w];
            w++;
        }
        Arrays.sort(res);
        return res;
    }
    public static void main (String[] args) {
        int[] mariana = {1,2,5,3,8,9};
        mariana = subArray(mariana,1,3);
        System.out.println (Arrays.toString (mariana));
        int[] arr1 = {3,43,2,6};
        int[] arr2 = {65,44,56,5,1};
        int[] res = arrayConcat(arr1,arr2);
        System.out.println (Arrays.toString (res));
    }
}
