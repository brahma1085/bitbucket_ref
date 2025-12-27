   package generics;

   interface Food {
      int glycemicIndex();
   }

   interface Animal <F extends Food> {
      public void eat(F food);
   }

   class Grass implements Food {
      public int glycemicIndex() {
         return 20; 
      }
   }

   class Antelope implements Food, Animal<Grass> {
      public int glycemicIndex() {
         return 5; 
      }

      public void eat(Grass food) {
      }
   }

   class Lion implements Animal<Antelope> {
      public void eat(Antelope food) {
      }
   }

   public class Example {
      static <F extends Food> void feed (Animal<F> animal, F food) {
            animal.eat(food);
      }

      public static void main(String... args) {
         feed(new Lion(), new Antelope());
      }
}
