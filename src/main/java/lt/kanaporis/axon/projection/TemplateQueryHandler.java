package lt.kanaporis.axon.projection;

import lombok.AllArgsConstructor;
import lt.kanaporis.axon.api.query.FindAllTemplatesQuery;
import lt.kanaporis.axon.api.query.FindOneTemplateQuery;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
@AllArgsConstructor
public class TemplateQueryHandler {
    private final TemplateDao templateDao;

    @QueryHandler
    public List<TemplateDto> on(FindAllTemplatesQuery query) {
        return templateDao.findAllByNameLike("%" + query.getName() + "%");
    }

    @QueryHandler
    public TemplateDto on(FindOneTemplateQuery query) {
        return templateDao.findById(query.getId())
                .orElseThrow(EntityNotFoundException::new);
    }
}
