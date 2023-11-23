//1.3.3 Creation of the Transfert, Credit, Debit classes
package components;

import java.time.LocalDate;

public class Debit extends Flow {
    public Debit(String comment, double amount, int targetAccountNumber, boolean effect, LocalDate date) {
        super(comment, amount, targetAccountNumber, effect, date);
    }
    
    @Override
    public FlowType getFlowType() {
        return FlowType.DEBIT;
    }
}