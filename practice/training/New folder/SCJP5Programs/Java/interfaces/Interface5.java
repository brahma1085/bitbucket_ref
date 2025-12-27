interface if1 { Integer getNum();}
interface if2 { int[] getNum(); }

class Interface2 implements if1, if2 {
     public Integer getNum() {return 1; };
     public int[] getNum() {return new int[3];};   // err
}