package vttp.day15.redis.controllers;

import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(path={"/", ""})
public class GreetingsController {

    // inject template
    @Autowired
    // @Qualifier("redislab")
    private RedisTemplate<String, Object> redisTemplate;
    
    @GetMapping
    public String getGreetings(Model model) {
        // System.out.println(">>> template: " + redisTemplate);
        ValueOperations<String, Object> ops = redisTemplate.opsForValue();
        Object greetings = ops.get("greetings");
        model.addAttribute("greetings", greetings.toString());
        return "index";

    }

    @PostMapping(path={"/update"})
    public String changeGreetings(@RequestBody MultiValueMap<String, String> form, Model model) {
        ValueOperations<String, Object> ops = redisTemplate.opsForValue();
        Object newGreeting = form.getFirst("input");

        System.out.println(">> " + newGreeting);
        // Override the value in redis
        ops.set("greetings", newGreeting.toString());
        // Replace the header
        model.addAttribute("greetings", newGreeting.toString());
        return "index";

    }
}
