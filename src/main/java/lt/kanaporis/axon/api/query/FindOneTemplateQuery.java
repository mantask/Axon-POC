package lt.kanaporis.axon.api.query;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.UUID;

@Data
@Accessors(chain = true)
public class FindOneTemplateQuery {
    private UUID id;
}
