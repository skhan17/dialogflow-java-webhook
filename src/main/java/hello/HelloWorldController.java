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

@RestController
public class HelloWorldController {
	public String firstName;
    public String lastName;
   	public String airlines;	
    public String numOfBags;

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
        		lastName = params.get("last-name").toString();
        		airlines = params.get("Airlines").toString();
        		numOfBags = params.get("number").toString();
			}
        }

        ArrayList<Ticket> allTickets = db();

        for(Ticket ticket: allTickets){
        	if(ticket.name.equals(firstName+" "+lastName)){
        		ticket.setAirlines(airlines);
        		ticket.setNumBags(numOfBags);
        	}
        }
        System.out.println(HelloWorldConfiguration.returnTix());
        System.out.println("------------------");
        System.out.println(firstName + " " + lastName);
        System.out.println("------------------");
        System.out.println(airlines);
        System.out.println("------------------");
        System.out.println(numOfBags);
        return new WebhookResponse("You wrote: \n\n" + json, "You wrote: \n\n" + json);
    
    }
    
    @RequestMapping("/")
    public String index() {
        return "Hello SoftBank Robotics!";
    }

    public ArrayList<Ticket> db(){

    	Ticket t1 = new Ticket("Shyaan Khan");
		Ticket t2 = new Ticket("Daniel Schofield");
		Ticket t3 = new Ticket("Justin Frankert");
		Ticket t4 = new Ticket("Mikayla Weiner");
		Ticket t5 = new Ticket("Abir Rashid");

    	ArrayList<Ticket> tix = new ArrayList<Ticket>();
		tix.add(t1);
		tix.add(t2);
		tix.add(t3);
		tix.add(t4);
		tix.add(t5);
		return tix;
    }


}

