package lt.kanaporis.axon.query.templateview;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface TemplateViewRepository extends JpaRepository<TemplateView, UUID> {
    List<TemplateView> findAllByNameLike(String name);
}
