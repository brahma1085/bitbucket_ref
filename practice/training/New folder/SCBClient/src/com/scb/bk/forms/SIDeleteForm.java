package com.scb.bk.forms;

import org.apache.struts.action.ActionForm;

import com.scb.common.forms.PageIdForm;

/**
 * Created by IntelliJ IDEA.
 * User: admin
 * Date: Nov 22, 2007
 * Time: 11:52:32 AM
 * To change this template use File | Settings | File Templates.
 */
public class SIDeleteForm  extends ActionForm{
 private String deleted,del ,delete_ins,sucess_del,sucess_verify;

public String getDeleted() {
		return deleted;
	}
	public void setDeleted(String deleted) {
		this.deleted = deleted;
	}
public double getAmount() { return amount;}
public void setAmount(double amount) {this.amount = amount; }

public int getDays() { return days; }
public void setDays(int days) {this.days = days;}

public String getDue_date() { return due_date;}
public void setDue_date(String due_date) {this.due_date = due_date;   }

public int getExpiry_days() { return expiry_days;}
public void setExpiry_days(int expiry_days) {this.expiry_days = expiry_days;  }

public int getFrom_ac_no() {return from_ac_no;}
public void setFrom_ac_no(int from_ac_no) {this.from_ac_no = from_ac_no; }

public String getFrom_ac_ty() {return from_ac_ty;}
public void setFrom_ac_ty(String from_ac_ty) {this.from_ac_ty = from_ac_ty;}

public int getInstruction_num() {return instruction_num;}
public void setInstruction_num(int instruction_num) { this.instruction_num = instruction_num;  }

public String getLast_exec_date() {return last_exec_date;  }
public void setLast_exec_date(String last_exec_date) { this.last_exec_date = last_exec_date;  }

   
public int getNo_of_times_exec() {return no_of_times_exec; }
public void setNo_of_times_exec(int no_of_times_exec) { this.no_of_times_exec = no_of_times_exec; }

public String getPageId() {return pageId;}
public void setPageId(String pageId) { this.pageId = pageId;}

public int getPeriod_mon() {return period_mon;}
public void setPeriod_mon(int period_mon) {this.period_mon = period_mon; }

public int getPriority_num() {return priority_num; }
public void setPriority_num(int priority_num) { this.priority_num = priority_num;}

public int getTo_ac_no() {return to_ac_no;  }
public void setTo_ac_no(int to_ac_no) {this.to_ac_no = to_ac_no;}

public String getTo_ac_ty() { return to_ac_ty; }
public void setTo_ac_ty(String to_ac_ty) { this.to_ac_ty = to_ac_ty; }

        private int instruction_num;
        private int priority_num;
        private int from_ac_no;
        private int to_ac_no;
        private int period_mon;
        private int days;
        private int no_of_times_exec;
        private int expiry_days;
        private String from_ac_ty,to_ac_ty;
        private String loan_option;
        private double amount;
        private String due_date,last_exec_date;
        private String pageId;
        private String Details;
        private String delete;
        private String forward;
        private String clear;
        private String deleteinstnum;
        PageIdForm pageidform=new PageIdForm();
        

public double getAmount_adjusted() { return amount_adjusted; }
public void setAmount_adjusted(double amount_adjusted) { this.amount_adjusted = amount_adjusted; }

    private double amount_adjusted;


public String getDetails() {return Details;	}
public void setDetails(String details) {Details = details;	}

public PageIdForm getPageidform() {	return pageidform;}
public void setPageidform(PageIdForm pageidform) { this.pageidform = pageidform;}

public String getDelete() {	return delete;	}
public void setDelete(String delete) {
	System.out.println("i m here renuka");
	this.delete = delete;
	}

public String getForward() {return forward;	}
public void setForward(String forward) {this.forward = forward;	}

public void setLoan_option(String loan_option) {this.loan_option = loan_option;	}

public String getClear() {	return clear;	}
public void setClear(String clear) {this.clear = clear;	}
public String getDeleteinstnum() {
	return deleteinstnum;
}
public void setDeleteinstnum(String deleteinstnum) {
	this.deleteinstnum = deleteinstnum;
}
public String getDelete_ins() {
	return delete_ins;
}
public void setDelete_ins(String delete_ins) {
	this.delete_ins = delete_ins;
}
public String getSucess_del() {
	return sucess_del;
}
public void setSucess_del(String sucess_del) {
	this.sucess_del = sucess_del;
}
public String getSucess_verify() {
	return sucess_verify;
}
public void setSucess_verify(String sucess_verify) {
	this.sucess_verify = sucess_verify;
}
public String getDel() {
	return del;
}
public void setDel(String del) {
	this.del = del;
}

}
