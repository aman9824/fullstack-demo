package com.example.demo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@RequestMapping(value = "/api")
public class controller {

    @Autowired
    private ProfileRepo li;

    @GetMapping(value="/profiles")
    public List<String> listAll() {
        List<model> listProfiles = li.findAll();
        List<String> response = new ArrayList<>();
        for (model l: listProfiles) {
            response.add(l.toString());
        }
        return response;
    }

    @PostMapping(value = "/link",
            consumes = MediaType.ALL_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, String> profiles (@RequestBody Map<String, String> url){
        Map<String, String> res = new HashMap<String, String>();
        Map<String, String> res2 = new HashMap<String, String>();
        boolean flag = false;
        String data  = "";
        try{
            System.out.print(url.values());
            String url2 = url.get("url");
            urlValidator u = new urlValidator(url2);
            if(u.validate(url2)) {
                try {
                    data = li.findById(url2).toString();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if(!li.existsById(url2)) {
                    scraper s = new scraper();
                    res2 = s.getData(url2);
                }
                model mo = new model(url2, res2.get("Experience"), res2.get("Education"));
                li.save(mo);
                res.put("data", mo.toString());
                return res;
            } else {
                res.put("error", u.getError());
                return res;
            }
        } catch (Exception e){
            e.printStackTrace();
        }finally {
            if(!res.containsKey("error") && (!res.containsKey("Education") || !res.containsKey("Experience"))) {
                res.put("error", "Unusual Error Occurred");
            }
            return res;
        }
    }

    @GetMapping(value = "/remove")
    public String deleteAll() {
        li.deleteAll();
        if(li.count() == 0) {
            return "All rows deleted from database";
        } else {
            return "Error deleting rows from database";
        }
    }
}
