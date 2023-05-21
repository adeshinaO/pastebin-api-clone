package adeshinaogunmodede.textbin.repository;

import adeshinaogunmodede.textbin.model.Text;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface TextRepository extends JpaRepository<Text, UUID> {

    Optional<Text> findByReference(String reference);

    @Query(value = "DELETE FROM texts t WHERE :currentDate >= t.expiry_date LIMIT :limit", nativeQuery = true)
    void deleteExpiredText(@Param("currentDate") String currentDateTimeStr, @Param("limit") int limit);
}
