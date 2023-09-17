package musicweb.backend.controller.musiccontroller;

import lombok.RequiredArgsConstructor;
import musicweb.backend.service.musicservice.RecommendService;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@RestController
@RequiredArgsConstructor
public class RecommendController {
    private final String fastAPIURL = "http://127.0.0.1:8000/";
    private final RecommendService recommendService;
    private final RestTemplate restTemplate;
    @GetMapping("/testre")
    public JSONObject testre(){
        JSONObject jsonObject = recommendService.getFirstArtistList();
        return jsonObject;
    }

    @GetMapping("/recommend")
    public ResponseEntity<?> RecommendByTrackList(){
        ResponseEntity<?> responseEntity = recommendService.finalJsonForm();
        return responseEntity;
    }
    @GetMapping("/recommend/playlist")
    public ResponseEntity<?> RecommendPlaylisyByPlaylist(){
        ResponseEntity<?> responseEntity = recommendService.recommendPlaylist();
        return responseEntity;
    }
    @GetMapping("/recommend/prefer")
    public ResponseEntity<?> RecommendPrefer(){
        ResponseEntity<?> responseEntity = recommendService.recommendGenreAndArtist();
        return responseEntity;
    }
}
