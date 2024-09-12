package KLS.indentity.controller;


import KLS.indentity.dto.ClassDTO;
import KLS.indentity.dto.StudentCreateDTO;
import KLS.indentity.model.ClassEntity;
import KLS.indentity.model.Student;
import KLS.indentity.service.ClassService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("api/v1/classes")
public class ClassController {

    @Autowired
    private ClassService classService;

    @PostMapping
    public ResponseEntity<?> addClass(@RequestBody ClassDTO classDTO) {
        try {
            ClassEntity newClass = classService.addClass(classDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(newClass);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage()); // Trả về thông báo lỗi cụ thể
        }
    }

}
