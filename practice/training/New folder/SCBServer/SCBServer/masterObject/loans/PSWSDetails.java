package masterObject.loans;
import java.io.Serializable;

public class PSWSDetails implements Serializable
{
		int noOfBorrowers;
		double loan_sanc,amtAdvanced,bal,amt_overdue;
			
		public int getNoOfBorrowers(){return noOfBorrowers;}
		public void setNoOfBorrowers(int a){this.noOfBorrowers=a;}
		
		public double getSanctionedAmt(){return loan_sanc;}
		public void setSanctionedAmt(double a){this.loan_sanc=a;}
		
		public double getAmtAdvanced(){return amtAdvanced;}
		public void setAmtAdvanced(double a){this.amtAdvanced=a;}
		
		public double getLoanBalance(){return bal;}
		public void setLoanBalance(double a){this.bal=a;}
		
		public double getAmtOverDue(){return amt_overdue;}
		public void setAmtOverDue(double a){this.amt_overdue=a;}
			
}