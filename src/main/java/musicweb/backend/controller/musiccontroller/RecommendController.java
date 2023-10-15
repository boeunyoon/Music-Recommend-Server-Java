package musicweb.backend.controller.musiccontroller;

import lombok.RequiredArgsConstructor;
import musicweb.backend.service.musicservice.RecommendService;
import org.json.simple.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;


@RestController
@RequiredArgsConstructor
public class RecommendController {
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
    @GetMapping("/recommend/prefer/artist")
    public ResponseEntity<?> RecommendPreferArtist(){
        ResponseEntity<?> responseEntity = recommendService.recommendArtistPlaylist();
        return responseEntity;
    }
}
