public class Bbridge{
    int iRunningTotal=0;
    public static void main(String argv[]){
        Bbridge bb = new Bbridge();
        bb.go(argv[0]);
    }   
    public void go(String s){
        int i = Integer.parseInt(s);
        setRunningTotal(i);             
        assert (iRunningTotal > 0): getRunningTotal();
    }
    public String getRunningTotal(){
        return "Value of iRunningTotal "+iRunningTotal;
    }
    public int setRunningTotal(int i){
        iRunningTotal+=i;
        return iRunningTotal;
    }
}

