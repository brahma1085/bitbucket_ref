class Generics2{   
      public static <E extends Number> List<? super E> process(List<E> nums) {}

      public static void main(String[] args){
          ArrayList<Integer> input = null;
          //List<Number> output = null;
          List<Integer> output = null;
          output = process(input);
      }
}