package lt.kanaporis.axon.domain;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lt.kanaporis.axon.api.command.CreateTemplateCommand;
import lt.kanaporis.axon.api.command.DeleteTemplateCommand;
import lt.kanaporis.axon.api.command.RenameTemplateCommand;
import lt.kanaporis.axon.api.event.TemplateCreatedEvent;
import lt.kanaporis.axon.api.event.TemplateDeletedEvent;
import lt.kanaporis.axon.api.event.TemplateRenamedEvent;
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
    public Template(CreateTemplateCommand cmd) {
        apply(new TemplateCreatedEvent()
                .setTemplateId(cmd.getTemplateId())
                .setName(BLANK));
    }

    @CommandHandler
    public void handle(RenameTemplateCommand cmd) {
        Assert.isTrue(BLANK.equals(name), "Can rename only once.");
        Assert.isTrue(!BLANK.equals(cmd.getName()), "New name cannot be blank.");
        apply(new TemplateRenamedEvent()
                .setTemplateId(cmd.getTemplateId())
                .setName(cmd.getName()));
    }

    @CommandHandler
    public void handle(DeleteTemplateCommand cmd) {
        Assert.isTrue(!BLANK.equals(name), "Cannot delete blank.");
        apply(new TemplateDeletedEvent()
                .setTemplateId(cmd.getTemplateId()));
    }

    @EventSourcingHandler
    public void on(TemplateCreatedEvent event) {
        templateId = event.getTemplateId();
        name = event.getName();
    }

    @EventSourcingHandler
    public void on(TemplateRenamedEvent event) {
        name = event.getName();
    }

    @EventSourcingHandler
    public void on(TemplateDeletedEvent event) {
        markDeleted();
    }
}
