package lt.kanaporis.axon.web;

import lombok.AllArgsConstructor;
import lt.kanaporis.axon.api.command.CreateTemplateCommand;
import lt.kanaporis.axon.api.command.DeleteTemplateCommand;
import lt.kanaporis.axon.api.command.RenameTemplateCommand;
import lt.kanaporis.axon.api.query.FindAllTemplatesQuery;
import lt.kanaporis.axon.api.query.FindOneTemplateQuery;
import lt.kanaporis.axon.query.QueryResponse;
import lt.kanaporis.axon.web.request.TemplateRequest;
import lt.kanaporis.axon.web.response.IdResponse;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
        var templateId = UUID.randomUUID();
        return commandGateway.send(new CreateTemplateCommand(templateId))
                .thenApply(IdResponse::new);
    }

    @PutMapping("/{id}")
    public CompletableFuture<?> rename(@PathVariable final UUID id, @RequestBody final TemplateRequest request) {
        return commandGateway.send(new RenameTemplateCommand(id, request.getName()));
    }

    @DeleteMapping("/{id}")
    public CompletableFuture<?> delete(@PathVariable final UUID id) {
        return commandGateway.send(new DeleteTemplateCommand(id));
    }

    @GetMapping
    public CompletableFuture<?> index(@RequestParam(defaultValue = "") final String name) {
        return queryGateway.query(new FindAllTemplatesQuery(name),
                ResponseTypes.multipleInstancesOf(QueryResponse.class));
    }

    @GetMapping("/{id}")
    public CompletableFuture<?> view(@PathVariable final UUID id) {
        return queryGateway.query(new FindOneTemplateQuery(id), QueryResponse.class);
    }
}
