package hello;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import java.util.*;

@SpringBootApplication
public class HelloWorldConfiguration {

	public ArrayList<Ticket> arrTickets;

	public static void main(String[] args) {

		Ticket t1 = new Ticket("Shyaan Khan");
		Ticket t2 = new Ticket("Daniel Schofield");
		Ticket t3 = new Ticket("Justin Frankert");
		Ticket t4 = new Ticket("Mikayla Weiner");
		Ticket t5 = new Ticket("Abir Rashid");

		tix = new ArrayList<Ticket>();
		tix.add(t1);
		tix.add(t2);
		tix.add(t3);
		tix.add(t4);
		tix.add(t5);
		SpringApplication.run(HelloWorldConfiguration.class, args);

	}

	public void setArrTicket(ArrayList<Ticket> tix){
		arrTickets = tix;
	}

	public ArrayList<Ticket> returnTix(){
		return arrTickets;
	}

}
