package lt.kanaporis.axon.api.command;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

import java.util.UUID;

@Data
@AllArgsConstructor
public class DeleteTemplateCommand {
    @TargetAggregateIdentifier
    private UUID templateId;
}
