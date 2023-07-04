package musicweb.backend.controller.musiccontroller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import lombok.RequiredArgsConstructor;
import musicweb.backend.entity.musicentity.Top100;
import musicweb.backend.repository.musicrepository.Top100Repository;
import musicweb.backend.service.musicservice.Top100Service;
import net.bytebuddy.description.method.MethodDescription;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/top100")
@CrossOrigin(origins = "http://localhost:3000")
@RequiredArgsConstructor
public class Top100Controller {
    private final Top100Repository top100Repository;
    private final Top100Service top100Service;
    @GetMapping("/gettop100/{date}")
    public JSONObject GetTop100List(@PathVariable String date){
        JSONObject top100List = top100Service.getTop100List(date);
        return top100List;
    }
}
