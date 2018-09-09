package hello;


public class Ticket{

	public String name;
	public String airlines;
	public String numOfBags;
	public String flightNumber;
	public String airlinePrefix;

	public Ticket(String name, String airlinePrefix, String flightNumber){
		this.name = name;
		this.airlinePrefix = airlinePrefix;
		this.flightNumber = flightNumber;
		if(airlinePrefix.equals("DL")){
			this.airlines = "Delta Airlines";
		}else if(airlinePrefix.equals("UA")){
			this.airlines = "United Airlines"
		}

	}	

	public void setAirlines(String airlines){
		//this.airlines = airlines;
		/*if(airlines.equals("Delta Airlines")){
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
		}*/
	}

	public void setNumBags(String numOfBags){
		this.numOfBags = numOfBags;
	}




}