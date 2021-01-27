package lt.kanaporis.axon.projection;

import lombok.AllArgsConstructor;
import lt.kanaporis.axon.api.event.TemplateCreatedEvent;
import lt.kanaporis.axon.api.event.TemplateDeletedEvent;
import lt.kanaporis.axon.api.event.TemplateRenamedEvent;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
@AllArgsConstructor
public class TemplateProjector {
    private final TemplateDao templateDao;

    @EventHandler
    public void on(TemplateCreatedEvent event) {
        var template = new TemplateDto()
                .setId(event.getTemplateId())
                .setName(event.getName());
        templateDao.save(template);
    }

    @EventHandler
    public void on(TemplateRenamedEvent event) {
        templateDao.findById(event.getTemplateId())
                .ifPresent(template -> template.setName(event.getName()));
    }

    @EventHandler
    public void on(TemplateDeletedEvent event) {
        templateDao.deleteById(event.getTemplateId());
    }
}
