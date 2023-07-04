package musicweb.backend.entity.musicentity;

import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "billboard")
@Entity
@Getter
public class Top100 {
    @Id
    @Column(name = "billboard_date")
    private String billboard_date;
    @Column(name = "billboard_data")
    private String billboard_data;

}
