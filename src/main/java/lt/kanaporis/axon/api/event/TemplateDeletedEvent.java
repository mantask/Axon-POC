package lt.kanaporis.axon.api.event;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.UUID;

@Data
@Accessors(chain = true)
public class TemplateDeletedEvent {
    private UUID templateId;
}
