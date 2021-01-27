package lt.kanaporis.axon.api.event;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.UUID;

@Data
@Accessors(chain = true)
public class TemplateRenamedEvent {
    private UUID templateId;
    private String name;
}
