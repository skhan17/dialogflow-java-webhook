package hello;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import java.util.*;
import java.io.*;
import java.net.*;

@SpringBootApplication
public class HelloWorldConfiguration {

	public static ArrayList<Ticket> arrTickets;

	public static void main(String[] args) throws IOException, MalformedURLException{
		SpringApplication.run(HelloWorldConfiguration.class, args);

	}

	public void setArrTicket(ArrayList<Ticket> tix){
		arrTickets = tix;
	}

	public static ArrayList<Ticket> returnTix(){
		return arrTickets;
	}

}
