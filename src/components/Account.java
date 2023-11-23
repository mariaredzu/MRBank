//1.2.1 Creation of the account class
package components;

public class Account {

	private static int count_acc = 1;
	
	private String label;
	private int n_account;
	private double balance;
	private Client client;
	
	public Account(String label, Client client) {

        this.n_account = count_acc++;
        this.label = label;
        this.client = client;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public int getN_account() {
		return n_account;
	}

	public double getBalance() {
		return balance;
	}

	public void setBalance(Flow flow) {
		switch (flow.getFlowType()) {
			case DEBIT:
				this.balance -= flow.getAmount();
				break;
				
			case CREDIT:
				this.balance += flow.getAmount();
				break;
				
			case TRANSFER:
				this.balance += flow.getAmount();
				break;
		}
	}

	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
	}

	@Override
	public String toString() {
		return "label = " + label + "\naccount number = " + n_account + "\nbalance = " + balance + "\nclient :\n" + client
				+ "\n";
	}
}
