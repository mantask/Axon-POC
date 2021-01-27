package lt.kanaporis.axon.projection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface TemplateDao extends JpaRepository<TemplateDto, UUID> {
    List<TemplateDto> findAllByNameLike(String name);
}
