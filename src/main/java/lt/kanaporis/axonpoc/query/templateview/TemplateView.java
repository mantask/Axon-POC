package lt.kanaporis.axonpoc.query.templateview;

import lt.kanaporis.axonpoc.query.QueryResponse;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.UUID;

@Entity
@Getter
@Setter
public class TemplateView implements QueryResponse {
    @Id
    private UUID id;
    private String name;
}
