package lt.kanaporis.axon.web;

import lombok.AllArgsConstructor;
import lt.kanaporis.axon.api.command.CreateTemplateCommand;
import lt.kanaporis.axon.api.command.DeleteTemplateCommand;
import lt.kanaporis.axon.api.command.RenameTemplateCommand;
import lt.kanaporis.axon.api.query.FindAllTemplatesQuery;
import lt.kanaporis.axon.api.query.FindOneTemplateQuery;
import lt.kanaporis.axon.projection.TemplateDto;
import lt.kanaporis.axon.web.model.IdResponse;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/templates")
@AllArgsConstructor
public class TemplateController {
    private final QueryGateway queryGateway;
    private final CommandGateway commandGateway;

    @PostMapping
    public CompletableFuture<?> create() {
        var cmd = new CreateTemplateCommand()
                .setTemplateId(UUID.randomUUID());
        return commandGateway.send(cmd)
                .thenApply(IdResponse::new);
    }

    @PutMapping("/{id}")
    public CompletableFuture<?> rename(@PathVariable UUID id, @RequestBody RenameTemplateCommand cmd) {
        cmd.setTemplateId(id);
        return commandGateway.send(cmd);
    }

    @DeleteMapping("/{id}")
    public CompletableFuture<?> delete(@PathVariable UUID id) {
        var cmd = new DeleteTemplateCommand()
                .setTemplateId(id);
        return commandGateway.send(cmd);
    }

    @GetMapping
    public CompletableFuture<List<TemplateDto>> index(FindAllTemplatesQuery query) {
        return queryGateway.query(query, ResponseTypes.multipleInstancesOf(TemplateDto.class));
    }

    @GetMapping("/{id}")
    public CompletableFuture<TemplateDto> view(@PathVariable UUID id) {
        var query = new FindOneTemplateQuery()
                .setId(id);
        return queryGateway.query(query, TemplateDto.class);
    }
}
