//1.1.2 Creation of main class for tests
package test_main;

import java.util.Arrays;
import components.Client;

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

	public static void main(String[] args) {
		
	    //Part 1.1
        Client[] clients = new Client[3];
        insClients(clients);
        showClients(clients);
	}
}
