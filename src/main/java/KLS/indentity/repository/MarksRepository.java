package KLS.indentity.repository;

import KLS.indentity.model.Marks;
import KLS.indentity.model.Student;
import KLS.indentity.model.Subject;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MarksRepository extends JpaRepository<Marks, Integer> {

    Marks findByStudentAndSubject(Student student, Subject subject);
}