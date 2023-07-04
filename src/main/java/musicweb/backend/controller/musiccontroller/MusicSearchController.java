package musicweb.backend.controller.musiccontroller;

import lombok.RequiredArgsConstructor;
import musicweb.backend.entity.musicentity.SongEntity;
import musicweb.backend.service.musicservice.SongSearchService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class MusicSearchController {
    private final SongSearchService songSearchService;

    @GetMapping("/search")
    public ResponseEntity<List<SongEntity>> searchSongs(@RequestParam("keyword") String keyword) {
        List<SongEntity> searchResults = songSearchService.searchByArtistAndTitle(keyword);
        return ResponseEntity.ok(searchResults);
    }
}
