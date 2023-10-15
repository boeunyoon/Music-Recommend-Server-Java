package musicweb.backend.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ArtistDTO {
    private String artistId;
    private String artistImage;
    private String artistName;
    private Integer artistPopularity;

    public ArtistDTO(String artistId, String artistImage, String artistName, Integer artistPopularity) {
        this.artistId = artistId;
        this.artistImage = artistImage;
        this.artistName = artistName;
        this.artistPopularity = artistPopularity;
    }
    public ArtistDTO(){

    }

}
