package KLS.indentity.repository;


import KLS.indentity.dto.StudentDTO;
import KLS.indentity.model.Student;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;



public interface StudentRepository extends JpaRepository<Student, Integer> {
    @Query("SELECT new  KLS.indentity.dto.StudentDTO(s.id, s.name, c.name) " +
            "FROM Student s JOIN s.classEntity c " +
            "WHERE s.name LIKE %:character% AND c.name = :className")
    Page<StudentDTO> findStudentsByName(
            @Param("character") String character,
            @Param("className") String className,
            Pageable pageable);
}
