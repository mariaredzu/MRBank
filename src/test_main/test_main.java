//1.1.2 Creation of main class for tests
//1.2.3 Creation of the tablea account
package test_main;

import java.util.Arrays;
import components.Account;
import components.Client;
import components.CurrentAccount;
import components.SavingsAccount;

public class test_main {
	
	//Part 1.1
    private static Client newClient(String f_name, String name) {
        return new Client(f_name, name);
    }
    
    private static void insClients(Client[] clients) {
        for (int i = 0; i < clients.length; i++) {
            clients[i] = newClient("name" + (i + 1), "firstname" + (i + 1));
        }
    }

    private static void showClients(Client[] clients) {
        System.out.println("Clients:\n");
        Arrays.stream(clients).forEach(client -> System.out.println(client.toString()));		
	}
    
    //Part 1.2
    private static void insAccounts(Account[] accounts, Client[] clients) {
        for (int i = 0; i < accounts.length; i++) {
            if (i % 2 == 0) {
                accounts[i] = new SavingsAccount("Savings", clients[i / 2]);
            } else {
                accounts[i] = new CurrentAccount("Current", clients[i / 2]);
            }
        }
    }

    private static void showAccounts(Account[] accounts) {
        System.out.println("Accounts:\n");
        Arrays.stream(accounts).forEach(account -> System.out.println(account.toString()));
    }
    
	
	public static void main(String[] args) {
		
	    //Part 1.1
        Client[] clients = new Client[3];
        insClients(clients);
        showClients(clients);
        
        //Part 1.2
        Account[] accounts = new Account[3];
        insAccounts(accounts, clients);
        showAccounts(accounts);
	}
}
