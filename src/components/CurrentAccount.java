//1.2.2 Creation of the CurrentAccount and SavingsAccount
package components;

public class CurrentAccount extends Account {

    public CurrentAccount() {
    }
	
    public CurrentAccount(String label, Client client) {
        super(label, client);
    }
}
