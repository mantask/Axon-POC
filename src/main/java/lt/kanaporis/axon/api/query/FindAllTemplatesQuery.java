package lt.kanaporis.axon.api.query;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FindAllTemplatesQuery {
    private String name;
}
