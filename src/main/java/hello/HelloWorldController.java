package hello;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.ui.Model;
import org.json.*;
import java.util.*;
import java.io.*;
import java.net.*;
import java.text.*;


@RestController
public class HelloWorldController{
	public String firstName;
    public String lastName;
   	public String airlines;	
    public String numOfBags;
    public String departureAirport;
    public String arrivalAirport;
    public String departureTime;
    public String arrivalTime;
    public String departureTerminal;
    public String departureGate;

    @RequestMapping(value ="/webhook", method = RequestMethod.POST)
    public @ResponseBody WebhookResponse webhook(@RequestBody String obj){

        JSONObject json = new JSONObject(obj);
        JSONObject result = json.getJSONObject("result");
        JSONArray context = result.getJSONArray("contexts");

        for(int i = 0; i < context.length(); i++){
        	JSONObject current = context.getJSONObject(i);
        	if(((Integer) current.get("lifespan")) == 1){
        		JSONObject params = current.getJSONObject("parameters");
        		firstName = params.get("any").toString();
        		lastName = params.get("any1").toString();
        		airlines = params.get("Airlines").toString();
        		numOfBags = params.get("number").toString();
			}
        }

        System.out.println(json.toString());

       System.out.println("---------");


        ArrayList<Ticket> allTickets = db();

        String response = "Sorry "+firstName+" I couldn't find your ticket. Please double check your name.";

        for(Ticket ticket: allTickets){
        	if(ticket.name.equalsIgnoreCase(firstName+" "+lastName)){
        		ticket.setAirlines(airlines);
        		ticket.setNumBags(numOfBags);

        		BufferedReader br = null;
				StringBuilder sb = new StringBuilder();
				String line;

        		try{
                    String urlString = "https://api.flightstats.com/flex/flightstatus/rest/v2/json/flight/status/"
                    +ticket.airlinePrefix+"/"+ticket.flightNumber+"/dep/2018/9/10?appId=d234e79a&appKey=af4ce6fc3c6761042323676428a7d396&utc=false";
                    URL url = new URL(urlString);
                    URLConnection conn = url.openConnection();
        			br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
					while ((line = br.readLine()) != null) {
						sb.append(line);
				    }
                    System.out.println(urlString);
        		}catch(IOException ex){
        			ex.printStackTrace();
        		}finally{
        			if (br != null) {
						try {
							br.close();
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
        		}
                JSONObject apiJson = new JSONObject(sb.toString());
                System.out.println("-----------");
                System.out.println(apiJson);
                JSONArray flightStatuses = apiJson.getJSONArray("flightStatuses");
                for(int j = 0; j < flightStatuses.length(); j++){
                    JSONObject flightStatus = flightStatuses.getJSONObject(j);
                    departureAirport = flightStatus.get("departureAirportFsCode").toString();
                    arrivalAirport = flightStatus.get("arrivalAirportFsCode").toString();
                    JSONObject departureDate = flightStatus.getJSONObject("departureDate");
                    departureTime = departureDate.get("dateLocal").toString();
                    JSONObject arrivalDate = flightStatus.getJSONObject("arrivalDate");
                    arrivalTime = arrivalDate.get("dateLocal").toString(); 
                    JSONObject resources = flightStatus.getJSONObject("airportResources");
                    departureTerminal = resources.get("departureTerminal").toString();
                    
                    if(resources.has("departureGate")){
                        departureGate = resources.get("departureGate").toString();
                    }
                    else{
                        departureGate = "";
                    }
                }
                    String formattedDepartureTime = formatTime(departureTime);
                    String formattedArrivalTime = formatTime(arrivalTime);

                    String deptTimeEdit = departureTime.substring(0, departureTime.indexOf("T"));
                    String deptDate = deptTimeEdit.replace("-", "");
                    response = firstName+", I've got you all checked in! You're traveling from "+departureAirport+" to "+
                    arrivalAirport+" with "+airlines+" ("+ticket.airlinePrefix+" "+ticket.flightNumber+"). The flight is scheduled to depart "+formattedDepartureTime+
                    " and will arrive at "+formattedArrivalTime+". Go to Terminal "+departureTerminal+" Gate "+departureGate+
                    ". To view more flight details, visit https://flightaware.com/live/flight/"+ticket.airlinePrefix+"L"+ticket.flightNumber+
                        "/history/"+deptDate+"/1800Z/K"+departureAirport+"/K"+arrivalAirport;

        		break;
        	}
        }

        return new WebhookResponse(response, response);

    
    }
    
    @RequestMapping("/")
    public String index() {
        return "Hello SoftBank Robotics!";
    }

    public ArrayList<Ticket> db(){

    	Ticket t1 = new Ticket("Shyaan Khan", "UA", "2363");
		Ticket t2 = new Ticket("Daniel Schofield", "DL", "2520");
		Ticket t3 = new Ticket("Justin Frankert", "DL", "2240");
		Ticket t4 = new Ticket("Laura Meyer", "UA", "2063");
		Ticket t5 = new Ticket("Alizer Khan", "UA", "888");


    	ArrayList<Ticket> tix = new ArrayList<Ticket>();
		tix.add(t1);
		tix.add(t2);
		tix.add(t3);
		tix.add(t4);
		tix.add(t5);
		return tix;
    }

    public String formatTime(String time){
        String formattedTime = "";
        //2018-09-08T13:46:00.


        String ampm = "";

        if(Integer.parseInt(time.substring(time.indexOf("T")+1, time.indexOf("T")+3)) > 12){
            ampm = "pm";
        }
        else{
            ampm = "am";
        }

        String date = time.substring(0, time.indexOf("T"));
        String t = time.substring(time.indexOf("T")+1, time.length());
        String newDate = date.substring(5, 7) +"/"+date.substring(8,10)+"/"+date.substring(0,4);
        String newT = t.substring(0, 5);
        String finalT = "";

        try {
            SimpleDateFormat sdf = new SimpleDateFormat("H:mm");
            Date dateObj = sdf.parse(newT);
            finalT = new SimpleDateFormat("K:mm").format(dateObj).toString();
        } catch (final ParseException e) {
            e.printStackTrace();
        }
        

       formattedTime = newDate+" "+finalT+ampm;
        return formattedTime;
    }

}

