//1.3.3 Creation of the Transfert, Credit, Debit classes
package components;

import java.time.LocalDate;

public class Transfer extends Flow {
	
    private int issuingAccountNumber;
    
    public Transfer() {
    }

    public Transfer(String comment, double amount, int targetAccountNumber, int issuingAccountNumber, boolean effect, LocalDate date) {
        super(comment, amount, targetAccountNumber, effect, date);
        this.issuingAccountNumber = issuingAccountNumber;
    }

    public int getIssuingAccountNumber() {
        return issuingAccountNumber;
    }

    public void setIssuingAccountNumber(int issuingAccountNumber) {
        this.issuingAccountNumber = issuingAccountNumber;
    }

    @Override
    public FlowType getFlowType() {
        return FlowType.TRANSFER;
    }
}