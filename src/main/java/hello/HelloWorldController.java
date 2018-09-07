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

        JSONObject json = new JSONObject(obj);
        JSONObject result = json.getJSONObject("result");
        String query = result.get("resolvedQuery").toString();

        System.out.println(json);
        System.out.println("------------------");
        System.out.println(result);
        System.out.println("------------------");
        System.out.println("query");
        return new WebhookResponse("You wrote: " + query, "You wrote: " + query);
    }
}
