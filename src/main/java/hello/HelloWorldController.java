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


@RestController
public class HelloWorldController throws MalformedURLException, IOException {
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

        		String urlString = "https://api.flightstats.com/flex/flightstatus/rest/v2/json/flight/status/JBU/1201/dep/2018/9/8?appId=d234e79a&appKey=af4ce6fc3c6761042323676428a7d396&utc=false";
        		URL url = new URL(urlString);
        		URLConnection conn = url.openConnection();
        		

        		BufferedReader br = null;
				StringBuilder sb = new StringBuilder();
				String line;

        		try{
        			br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
					while ((line = br.readLine()) != null) {
						sb.append(line);
				    }
        		}catch(MalformedURLException ml, IOException ex){
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
      			System.out.print(sb.toString());
        		break;
        	}
        }

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

