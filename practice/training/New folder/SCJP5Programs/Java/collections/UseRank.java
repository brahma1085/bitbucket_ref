package collections;

import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

class Rank {
     private int rank;

     public Rank(int rank) {
       this.rank = rank;
     }

     public int getRankInt(){
       return rank;
     }

     public String toString() {
       return "" + rank;
     }

}

class GenericShuffleAndSort<T> {
     List<T> miniDeck = new ArrayList<T>(6);

     void initializeDeck() {
      for (int i = 0; i < 6; i++) {
         miniDeck.add(Class<T>.new Instance(i));
       }
     }

     void printDeck(String message) {
       System.out.println(message);
       for (int i = 0; i < 6; i++) {
         System.out.println("card " + i +
           " = " + miniDeck.get(i));
       }
       System.out.println("============");
     }

     void sort(){
       Collections.sort(miniDeck);
     }

     void sortReverse(){
       Collections.sort(miniDeck,   Collections.reverseOrder());
     }  

     void exerciseDeck() {
       initializeDeck();
       Collections.shuffle(miniDeck);
       printDeck("Deck Shuffled:");
       sort();
       printDeck("Deck Sorted:");
       sortReverse();
       printDeck("Deck Sorted in Reverse Order:");
     }

     public static void main(String[] args) {
       new GenericShuffleAndSort<Rank>.exerciseDeck();
     }
   }
