package hello;


public class Ticket{

	public String name;
	public String airlines;
	public String numOfBags;
	public String flightNumber;
	public String airlinePrefix;

	public Ticket(String name){
		this.name = name;
	}	

	public void setAirlines(String airlines){
		this.airlines = airlines;
		if(airlines.equals("Delta Airlines")){
			this.flightNumber = "854";
			this.airlinePrefix = "DL";
		}
		else if(airlines.equals("United Airlines")){
			this.flightNumber = "1723";
			this.airlinePrefix = "UA";
		}
		else if(airlines.equals("Southwest Airlines")){
			this.flightNumber = "6181";
			this.airlinePrefix = "SWA";
		}
	}

	public void setNumBags(String numOfBags){
		this.numOfBags = numOfBags;
	}




}