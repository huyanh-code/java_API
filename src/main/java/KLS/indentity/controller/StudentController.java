package KLS.indentity.controller;

import KLS.indentity.dto.StudentCreateDTO;
import KLS.indentity.dto.StudentDTO;
import KLS.indentity.dto.StudentInfoDTO;
import KLS.indentity.model.Student;
import KLS.indentity.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/students")
public class StudentController {
    @Autowired
    private StudentService studentService;

    @GetMapping
    public ResponseEntity<List<StudentInfoDTO>> getAllStudents() {
        List<StudentInfoDTO> students = studentService.findAll();
        if (students.isEmpty()) {
            return ResponseEntity.noContent().build();  // Trả về 204 No Content nếu không có sinh viên nào
        }
        return ResponseEntity.ok(students);  // Trả về danh sách sinh viên với mã 200 OK
    }


    @GetMapping("/{id}")
    public ResponseEntity<StudentDTO> getStudentById(@PathVariable int id) {
        StudentDTO studentDTO = studentService.getStudentById(id);
        return studentDTO != null ? ResponseEntity.ok(studentDTO) : ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<?> addStudent(@RequestBody StudentCreateDTO studentCreateDTO) {
        try {
            Student newStudent = studentService.addStudent(studentCreateDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(newStudent);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage()); // Trả về thông báo lỗi cụ thể
        }
    }

    @PutMapping("/{studentId}/transfer")
    public ResponseEntity<String> transferStudent(
            @PathVariable int studentId,
            @RequestParam int classId) {
        try {
            studentService.changeClass(studentId, classId);
            return ResponseEntity.ok("Student transferred successfully.");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{studentId}")
    public ResponseEntity<String> deleteStudent(@PathVariable int studentId) {
        try {
            studentService.deleteStudent(studentId);
            return ResponseEntity.ok("Student deleted successfully.");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{studentId}/marks")
    public ResponseEntity<String> updateMarks(
            @PathVariable int studentId,
            @RequestParam int subjectId,
            @RequestParam int mark) {
        try {
            studentService.updateMarks(studentId, subjectId, mark);
            return ResponseEntity.ok("Marks updated successfully.");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/search")
    public ResponseEntity<Page<StudentDTO>> searchStudents(
            @RequestParam String character,
            @RequestParam String className,
            @RequestParam int page,
            @RequestParam int size,
            @RequestParam String sortDirection) {

        Page<StudentDTO> students = studentService.searchStudents(character, className, page, size, sortDirection);
        return ResponseEntity.ok(students);
    }

//    @GetMapping("/pageList")
//    public  ResponseEntity<Page<StudentDTO>> pageList(
//            @RequestParam(defaultValue = "0") Integer page,
//            @RequestParam(defaultValue = "2") Integer size,
//            @RequestParam(defaultValue = "asc") String sortDirection){
//        Page<StudentDTO> students =  studentService.pageLists(page, size, sortDirection);
//        if(students.isEmpty()){
//            return ResponseEntity.noContent().build(); // Trả về 204 No Content nếu không có kết quả
//        }
//        return ResponseEntity.ok(students);
//    }

}

