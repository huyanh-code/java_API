package KLS.indentity.repository;

import KLS.indentity.dto.ClassDTO;
import KLS.indentity.model.ClassEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ClassRepository extends JpaRepository<ClassEntity, Integer> {
    Optional<Object> existsByName(String className);
}
