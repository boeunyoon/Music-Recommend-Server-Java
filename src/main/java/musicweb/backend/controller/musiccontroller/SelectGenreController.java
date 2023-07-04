package musicweb.backend.controller.musiccontroller;
import lombok.RequiredArgsConstructor;
import musicweb.backend.dto.ArtistDTO;
import musicweb.backend.entity.musicentity.ArtistEntity;
import musicweb.backend.service.musicservice.SelectGenreService;
import org.json.simple.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class SelectGenreController {
    private final SelectGenreService selectGenreService;

    @GetMapping("/selectgenre")
    public ResponseEntity<JSONObject> getAllArtistGenre(){
        JSONObject allArtist = selectGenreService.getAllArtist();
        return ResponseEntity.ok(allArtist);
    }
    @GetMapping("/selectgenre/{artist}")
    public ResponseEntity<List<ArtistEntity>> getSearchArtistGenre(@PathVariable String artist){
        List<ArtistEntity> searchArtist = selectGenreService.getArtist(artist);
        return ResponseEntity.ok(searchArtist);
    }
    @PostMapping("/sendartist")
    public ResponseEntity<List<String>> receiveArtistList(@RequestBody List<ArtistDTO> artistDTOList){
        List<String> stringList = selectGenreService.receiveArtistListService(artistDTOList);
        System.out.println(stringList);
        return ResponseEntity.ok(stringList);
    }
}
