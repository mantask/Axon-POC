package lt.kanaporis.axon.api.query;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
public class FindOneTemplateQuery {
    private UUID id;
}
