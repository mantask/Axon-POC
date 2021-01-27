package lt.kanaporis.axon.api.command;

import lombok.Data;
import lombok.experimental.Accessors;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

import java.util.UUID;

@Data
@Accessors(chain = true)
public class CreateTemplateCommand {
    @TargetAggregateIdentifier
    private UUID templateId;
}
