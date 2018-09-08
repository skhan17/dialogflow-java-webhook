package hello;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.ui.Model;
import org.json.*;

@RestController
public class HelloWorldController {

    @RequestMapping(value ="/webhook", method = RequestMethod.POST)
    public @ResponseBody WebhookResponse webhook(@RequestBody String obj){

    	String firstName;
    	String lastName;
    	String airlines;
    	String numOfBags;

        JSONObject json = new JSONObject(obj);
        JSONObject result = json.getJSONObject("result");
        JSONArray context = result.getJSONArray("context");

        for(int i = 0; i < context.length; i++){
        	JSONObject current = context.getJSONObject(i);
        	if(((Integer) current.get("lifespan")) == 1){
        		JSONObject params = current.getJSONObject("parameters");
        		firstName = params.get("given-name").toString();
        		lastName = params.get("last-name").toString();
        		airlines = params.get("Airlines").toString();
        		numOfBags = parans.get("number");
			}
        }



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


}

