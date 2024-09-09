package KLS.indentity.repository;

import KLS.indentity.model.Marks;
import KLS.indentity.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MarksRepository extends JpaRepository<Marks, Integer> {
    List<Marks> findByStudent(Student student);
}