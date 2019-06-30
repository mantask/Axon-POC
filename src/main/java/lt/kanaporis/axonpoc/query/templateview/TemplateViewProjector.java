package lt.kanaporis.axonpoc.query.templateview;

import lt.kanaporis.axonpoc.api.event.TemplateCreatedEvent;
import lt.kanaporis.axonpoc.api.event.TemplateDeletedEvent;
import lt.kanaporis.axonpoc.api.event.TemplateRenamedEvent;
import lt.kanaporis.axonpoc.api.query.FindAllTemplatesQuery;
import lt.kanaporis.axonpoc.api.query.FindOneTemplateQuery;
import lombok.AllArgsConstructor;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Component
@AllArgsConstructor
@Transactional
public class TemplateViewProjector {
    private final TemplateViewRepository templateViewRepository;

    @EventHandler
    public void on(final TemplateCreatedEvent evt) {
        var templateView = new TemplateView();
        templateView.setId(evt.getTemplateId());
        templateView.setName(evt.getName());
        templateViewRepository.save(templateView);
    }

    @EventHandler
    public void on(final TemplateRenamedEvent evt) {
        var template = templateViewRepository.findById(evt.getTemplateId())
                .orElseThrow(EntityNotFoundException::new);
        template.setName(evt.getName());
    }

    @EventHandler
    public void on(final TemplateDeletedEvent evt) {
        templateViewRepository.deleteById(evt.getTemplateId());
    }

    @QueryHandler
    public List<TemplateView> on(final FindAllTemplatesQuery qry) {
        return templateViewRepository.findAllByNameLike("%" + qry.getName() + "%");
    }

    @QueryHandler
    public TemplateView on(final FindOneTemplateQuery qry) {
        return templateViewRepository.findById(qry.getId())
                .orElseThrow(EntityNotFoundException::new);
    }
}
