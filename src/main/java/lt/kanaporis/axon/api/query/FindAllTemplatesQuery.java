package lt.kanaporis.axon.api.query;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class FindAllTemplatesQuery {
    private String name;
}
