package hello;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.ui.Model;
import org.json.*;

@Controller
public class HelloWorldController {

    @RequestMapping(value ="/webhook", method = RequestMethod.POST)
    public @ResponseBody WebhookResponse webhook(@RequestBody String obj){

        System.out.println(obj);

        JSONObject json = new JSONObject(obj);

        return new WebhookResponse("Hello! " + obj, "Text " + obj);
    }
    @RequestMapping(value ="/", method = RequestMethod.GET)
    public @ResponseBody ModelAndView mainPage(@RequestBody String obj){

    	System.out.println("Yo YO YO");

        return new ModelAndView("HELLO");
    }
}
