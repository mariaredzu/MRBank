//1.1.2 Creation of main class for tests
//1.2.3 Creation of the tablea account
package test_main;

import components.*;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;



public class test_main {
	
	//Part 1.1 
    private static void insClients(Client[] clients) {
        for (int i = 0; i < clients.length; i++) {
            clients[i] = new Client("name" + (i + 1), "firstname" + (i + 1));
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
  
    //Part 2
    //2.2 XML file of account
    private static Account[] loadAccountsFromXmlFile(String filePath) {
        try {
            Path path = Paths.get(filePath);

            if (Files.exists(path)) {
                JAXBContext context = JAXBContext.newInstance(Accounts.class);
                Unmarshaller unmarshaller = context.createUnmarshaller();
                Accounts accountsWrapper = (Accounts) unmarshaller.unmarshal(path.toFile());
                return accountsWrapper.getAccounts();
            } else {
                System.out.println("The XML file does not exist.");
                return new Account[0];
            }
        } catch (JAXBException e) {
            e.printStackTrace();
            return new Account[0];
        }
    }
    
    //Part 1.3
    //1.3.1 Adaptation of the table of accounts
    private static Hashtable<Integer, Account> createAccountHashtable(Account[] accounts) {
        Hashtable<Integer, Account> accountHashtable = new Hashtable<>();
        Arrays.stream(accounts).forEach(account -> accountHashtable.put(account.getN_account(), account));
        return accountHashtable;
    }
    
    // Part 2
    //2.1 JSON file of flows
    private static Flow[] JsonFlows(String filePath) {
        try {
            Path path = Paths.get(filePath);

            if (Files.exists(path)) {

                ObjectMapper objectMapper = new ObjectMapper();
                objectMapper.registerModule(new JavaTimeModule());
                objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);

                byte[] bytes = Files.readAllBytes(Paths.get(filePath));
                String jsonContent = new String(bytes);

                Flows flowContainer = objectMapper.readValue(jsonContent, Flows.class);
                return flowContainer.getFlow().toArray(new Flow[0]);
            } else {
                System.out.println("The JSON file does not exist.");
                return new Flow[0];
            }
        } catch (IOException e) {
            e.printStackTrace();
            return new Flow[0];
        }
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
    	
    	for (int i = 0; i < flows.length; i++) {
            Account account = accountHashtable.get(flows[i].getTargetAccountNumber());
            
            if (account != null) {
                account.setBalance(flows[i]);
                if (flows[i] instanceof Transfer) {
                    Transfer transfer = (Transfer) flows[i];
                    Account issuingAccount = accountHashtable.get(transfer.getIssuingAccountNumber());
                    if (issuingAccount != null) {
                        issuingAccount.setBalance(new Debit(flows[i].getComment(), flows[i].getAmount(), transfer.getIssuingAccountNumber(), false, flows[i].getDate()));
                    }
                }
            } else if  (flows[i].getTargetAccountNumber() == 0) {
        		for (Entry<Integer, Account> acc_Has : accountHashtable.entrySet()) {
        			account = acc_Has.getValue();
        			if (account.getLabel().equals("Current")) {
        				flows[i].setTargetAccountNumber(account.getN_account());
                		account.setBalance(flows[i]);
        			}
        		}
            } else {
        		for (Entry<Integer, Account> acc_Has : accountHashtable.entrySet()) {
        			account = acc_Has.getValue();
        			if (account.getLabel().equals("Savings")) {
        				flows[i].setTargetAccountNumber(account.getN_account());
                		account.setBalance(flows[i]);
        			}
        		}
            }
        }

        Optional<Account> negativeBalanceAccount = accountHashtable.values().stream()
                .filter(account -> account.getBalance() < 0)
                .findFirst();

        negativeBalanceAccount.ifPresent(account ->
                System.out.println("Warning: Account " + account.getN_account() + " has a negative balance."));
    }
    
    private static void showAccounts(Account[] accounts) {
    	 System.out.println("Accounts:\n");
    	    if (accounts != null) {
    	        Arrays.stream(accounts).forEach(account -> System.out.println(account.toString()));
    	    } else {
    	        System.out.println("The array of accounts is null.");
    	    }
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
        System.out.println("(Answer exercise 1.3)");
        Hashtable<Integer, Account> accountHashtable = createAccountHashtable(accounts);
        Flow[] flows = createFlows(accounts);
        updateBalances(accountHashtable, flows);
        displayAccountHashtable(accountHashtable);
        
        //Part 2
        System.out.println("(Answer exercise 2)");
        Account[] accounts2 = loadAccountsFromXmlFile("src/resources/accounts.xml");
        Hashtable<Integer, Account> accountHashtable2 = createAccountHashtable(accounts2);
        Flow[] flows2 = JsonFlows("src/resources/flows.json");
        updateBalances(accountHashtable2, flows2);
        displayAccountHashtable(accountHashtable2);
	}
}
