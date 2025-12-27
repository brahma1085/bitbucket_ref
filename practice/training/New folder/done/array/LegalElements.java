package array;

class LegalElements {
     static int[] arrint0;
     public static void main(String[] args) {
           //int[] arrint = { 'c', (byte)6, 10, (short)20, (long)7000, 23.0}; // long, float: error
           int[] arrint = { 'c', (byte)6, 10, (short)20}; 
           for (int i : arrint)
               System.out.println(i);

           int[] arrint1;
           //arrint = arrint1;  // err: arrint1 might not been initialized.
           arrint = arrint0;
           System.out.println(arrint);  // output is string "null"
    }
}