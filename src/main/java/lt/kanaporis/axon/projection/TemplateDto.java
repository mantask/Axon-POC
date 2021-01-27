package lt.kanaporis.axon.projection;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.UUID;

@Entity
@Data
@Accessors(chain = true)
public class TemplateDto {
    @Id
    private UUID id;
    private String name;
}
