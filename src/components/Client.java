//1.1.1 Creation of the client class
package components;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Client {

	private static int count_cli = 1;

	private String f_name, name;
	@XmlElement(name = "n_client")
	private int n_client;

	public Client() {
    }
	public Client(String f_name, String name) {

		this.f_name = f_name;
		this.name = name;
		this.n_client = count_cli++;
	}
	

	public String getF_name() {
		return f_name;
	}

	public void setF_name(String f_name) {
		this.f_name = f_name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getN_client() {
		return n_client;
	}

	@Override
	public String toString() {
		return "first name = " + f_name + "\nname = " + name + "\nclient number = " + n_client + "\n";
	}
}
