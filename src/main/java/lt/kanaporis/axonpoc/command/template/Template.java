package lt.kanaporis.axonpoc.command.template;

import lt.kanaporis.axonpoc.api.command.CreateTemplateCommand;
import lt.kanaporis.axonpoc.api.command.DeleteTemplateCommand;
import lt.kanaporis.axonpoc.api.command.RenameTemplateCommand;
import lt.kanaporis.axonpoc.api.event.TemplateCreatedEvent;
import lt.kanaporis.axonpoc.api.event.TemplateDeletedEvent;
import lt.kanaporis.axonpoc.api.event.TemplateRenamedEvent;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.spring.stereotype.Aggregate;
import org.springframework.util.Assert;

import java.util.UUID;

import static org.axonframework.modelling.command.AggregateLifecycle.apply;
import static org.axonframework.modelling.command.AggregateLifecycle.markDeleted;

@Aggregate
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Template {
    private static final String BLANK = "(blank)";

    @AggregateIdentifier
    private UUID templateId;

    private String name;

    @CommandHandler
    public Template(final CreateTemplateCommand cmd) {
        apply(TemplateCreatedEvent.builder()
                .templateId(cmd.getTemplateId())
                .name(BLANK)
                .build());
    }

    @CommandHandler
    public void handle(final RenameTemplateCommand cmd) {
        Assert.isTrue(BLANK.equals(name), "Can rename only once.");
        Assert.isTrue(!BLANK.equals(cmd.getName()), "New name cannot be blank.");
        apply(TemplateRenamedEvent.builder()
                .templateId(cmd.getTemplateId())
                .name(cmd.getName())
                .build());
    }

    @CommandHandler
    public void handle(final DeleteTemplateCommand cmd) {
        Assert.isTrue(!BLANK.equals(name), "Cannot delete blank.");
        apply(TemplateDeletedEvent.builder()
                .templateId(cmd.getTemplateId())
                .build());
    }

    @EventSourcingHandler
    public void on(final TemplateCreatedEvent evt) {
        templateId = evt.getTemplateId();
        name = evt.getName();
    }

    @EventSourcingHandler
    public void on(final TemplateRenamedEvent evt) {
        name = evt.getName();
    }

    @EventSourcingHandler
    public void on(final TemplateDeletedEvent evt) {
        markDeleted();
    }
}
