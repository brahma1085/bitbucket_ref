public class TimDig{
    public static void main(String argv[]){
	TimDig td = new TimDig();
	td.samcov();
    }
    public void samcov(){
	int i=1;
	int j=2;
	if((i==20) &&  (j==(i=i*2))){
	}

	System.out.print(i);
	if((i==20) & (j==(i=i*2))){}
	System.out.print(i);

	int x = i & 2;
	System.out.print(x);
    }	
}

