//1.3.2 Creation of the Flow class
package components;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonSubTypes;

import java.time.LocalDate;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type")
@JsonSubTypes({
    @JsonSubTypes.Type(value = Debit.class, name = "DEBIT"),
    @JsonSubTypes.Type(value = Credit.class, name = "CREDIT"),
    @JsonSubTypes.Type(value = Transfer.class, name = "TRANSFER")
})
public abstract class Flow {
    private static int flowCounter = 1;

    private String comment;
    private int identifier;
    private double amount;
    private int targetAccountNumber;
    private boolean effect;
    private LocalDate date;
    
    public Flow() {
    }

    public Flow(String comment, double amount, int targetAccountNumber, boolean effect, LocalDate date) {
        this.identifier = flowCounter++;
        this.comment = comment;
        this.amount = amount;
        this.targetAccountNumber = targetAccountNumber;
        this.effect = effect;
        this.date = date;
    }

    public int getIdentifier() {
        return identifier;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public int getTargetAccountNumber() {
        return targetAccountNumber;
    }

    public void setTargetAccountNumber(int targetAccountNumber) {
        this.targetAccountNumber = targetAccountNumber;
    }

    public boolean isEffect() {
        return effect;
    }

    public void setEffect(boolean effect) {
        this.effect = effect;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

	public abstract FlowType getFlowType();

	@Override
	public String toString() {
		return "comment = " + comment + "\nidentifier = " + identifier + "\namount = " + amount
				+ "\ntarget account number = " + targetAccountNumber + "\neffect = " + effect + "\ndate=" + date;
	}
}
