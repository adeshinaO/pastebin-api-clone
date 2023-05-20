package adeshinaogunmodede.textbin.repository;

import adeshinaogunmodede.textbin.model.Text;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TextRepository extends JpaRepository<Text, UUID> {

    // todo: write query or stick to Spring Data DSL
    Optional<Text> findByReference(String reference);
}
