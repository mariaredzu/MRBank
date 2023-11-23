//1.1.2 Creation of main class for tests
//1.2.3 Creation of the tablea account
package test_main;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;

import components.Account;
import components.Client;
import components.Credit;
import components.CurrentAccount;
import components.Debit;
import components.Flow;
import components.FlowType;
import components.SavingsAccount;
import components.Transfer;

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
                accounts[i] = new SavingsAccount("Savings", clients[i]);
            } else {
                accounts[i] = new CurrentAccount("Current", clients[i]);
            }
        }
    }
    
    //Part 1.3
    //1.3.1 Adaptation of the table of accounts
    private static Hashtable<Integer, Account> createAccountHashtable(Account[] accounts) {
        Hashtable<Integer, Account> accountHashtable = new Hashtable<>();
        Arrays.stream(accounts).forEach(account -> accountHashtable.put(account.getN_account(), account));
        return accountHashtable;
    }

    //1.3.4 Creation of the flow array
    private static Flow[] createFlows(Account[] accounts) {
        LocalDate currentDate = LocalDate.now();
        LocalDate flowDate = currentDate.plusDays(2);

        return new Flow[]{
                new Debit("Debit of 50€ from account 1", 50, 1, false, flowDate),
                new Credit("Credit of 100.50€ on all current accounts", 100.50, 0, true, flowDate),
                new Credit("Credit of 1500€ on all savings accounts", 1500, -1, true, flowDate),
                new Transfer("Transfer of 50€ from account 1 to account 2", 50, 2, 1, true, flowDate)
        };
    }

    //1.3.5 Updating accounts
    private static void updateBalances(Hashtable<Integer, Account> accountHashtable, Flow[] flows) {
        Arrays.stream(flows).forEach(flow -> {
            Account account = accountHashtable.get(flow.getTargetAccountNumber());
            
            if (account != null) {
                account.setBalance(flow);

                if (flow instanceof Transfer) {
                    Transfer transfer = (Transfer) flow;
                    Account issuingAccount = accountHashtable.get(transfer.getIssuingAccountNumber());

                    if (issuingAccount != null) {
                        issuingAccount.setBalance(new Debit(flow.getComment(), flow.getAmount(), transfer.getIssuingAccountNumber(), false, flow.getDate()));
                    }
                }
            } else if  (flow.getTargetAccountNumber() == 0) {
        		for (Entry<Integer, Account> acc_Has : accountHashtable.entrySet()) {
        			account = acc_Has.getValue();
        			if (account.getLabel().equals("Current")) {
            			flow.setTargetAccountNumber(account.getN_account());
                		account.setBalance(flow);
        			}
        		}
            } else {
        		for (Entry<Integer, Account> acc_Has : accountHashtable.entrySet()) {
        			account = acc_Has.getValue();
        			if (account.getLabel().equals("Savings")) {
            			flow.setTargetAccountNumber(account.getN_account());
                		account.setBalance(flow);
        			}
        		}
            }
        });

        Optional<Account> negativeBalanceAccount = accountHashtable.values().stream()
                .filter(account -> account.getBalance() < 0)
                .findFirst();

        negativeBalanceAccount.ifPresent(account ->
                System.out.println("Warning: Account " + account.getN_account() + " has a negative balance."));
    }

    private static FlowType getFlowType(Flow flow) {
        return flow.isEffect() ? FlowType.CREDIT : FlowType.DEBIT;
    }
    
    private static void showAccounts(Account[] accounts) {
        System.out.println("Accounts:\n");
        Arrays.stream(accounts).forEach(account -> System.out.println(account.toString()));
    }
    
    private static void displayAccountHashtable(Hashtable<Integer, Account> accountHashtable) {
        Map<Integer, Account> updatedMap = new Hashtable<>(accountHashtable);
        
        System.out.println("After processing the operations, these are the new values on balance of the accounts:\n");
        
        updatedMap.entrySet().stream()
                .sorted(Map.Entry.comparingByValue((account1, account2) -> Double.compare(account1.getBalance(), account2.getBalance())))
                .forEach(entry -> System.out.println(entry.getValue()));
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
        
        //Part 1.3
        Hashtable<Integer, Account> accountHashtable = createAccountHashtable(accounts);
        Flow[] flows = createFlows(accounts);
        updateBalances(accountHashtable, flows);
        displayAccountHashtable(accountHashtable);
	}
}
