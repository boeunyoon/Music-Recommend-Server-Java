package musicweb.backend.service.musicservice;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import musicweb.backend.entity.musicentity.Top100;
import musicweb.backend.repository.musicrepository.Top100Repository;
import org.json.simple.JSONObject;
import org.springframework.stereotype.Service;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class Top100Service {
    private final Top100Repository top100Repository;

    public JSONObject getTop100List(String date){
        Optional<Top100> top100 = top100Repository.findById(date);
        String billboard_data = "";
        log.info("date = ", billboard_data);
        JSONObject jsonObject = new JSONObject();
        if(top100.isPresent()){
            jsonObject.put("date",top100.get().getBillboard_date());
            billboard_data = top100.get().getBillboard_data();
        }
        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<Map<String, String>>>() {}.getType();
        ArrayList<Map<String, String>> data = gson.fromJson(billboard_data, type);
        jsonObject.put("data", data);
        return jsonObject;
    }
}
