package KLS.indentity.repository;

import KLS.indentity.model.SubjectTeacher;
import KLS.indentity.model.SubjectTeacherKey;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubjectTeacherRepository extends JpaRepository<SubjectTeacher, SubjectTeacherKey> {}
