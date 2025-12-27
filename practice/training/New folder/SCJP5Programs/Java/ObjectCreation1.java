class ObjectCreation1 {
        static final int DEFAULT_DOORS = 4;

        ObjectCreation1( String m, int i) {
        }

        ObjectCreation1( String m ) {
            this( m, DEFAULT_DOORS );  // Okay!
        }

        public static void main(String[] args) { 
               ObjectCreation1 obj = new ObjectCreation1("test");
        }
}