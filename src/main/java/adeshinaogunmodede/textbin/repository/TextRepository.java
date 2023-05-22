package adeshinaogunmodede.textbin.repository;

import adeshinaogunmodede.textbin.model.Text;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface TextRepository extends JpaRepository<Text, UUID> {

    Optional<Text> findByReference(String reference);

    @Query(value = "SELECT * FROM texts t WHERE t.has_expiry_date = 1 AND :currentDate > t.expiry_date LIMIT :size", nativeQuery = true)
    List<Text> fetchExpiredTexts(@Param("currentDate") String currentDateTimeStr, @Param("size") int limit);
}
