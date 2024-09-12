package KLS.indentity.service;

import KLS.indentity.dto.StudentCreateDTO;
import KLS.indentity.dto.StudentDTO;
import KLS.indentity.dto.StudentInfoDTO;
import KLS.indentity.dto.SubjectMarkDTO;
import KLS.indentity.model.ClassEntity;
import KLS.indentity.model.Marks;
import KLS.indentity.model.Student;
import KLS.indentity.model.Subject;
import KLS.indentity.repository.ClassRepository;
import KLS.indentity.repository.MarksRepository;
import KLS.indentity.repository.StudentRepository;
import KLS.indentity.repository.SubjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class StudentService {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private ClassRepository classRepository;

    @Autowired
    private SubjectRepository subjectRepository;

    @Autowired
    private MarksRepository marksRepository;

    public List<StudentInfoDTO> findAll() {
        List<Student> students = studentRepository.findAll();  // Truy vấn tất cả sinh viên
        List<StudentInfoDTO> studentInfoDTOList = new ArrayList<>();

        for (Student student : students) {
            StudentInfoDTO studentInfoDTO = new StudentInfoDTO();
            studentInfoDTO.setStudent_id(student.getId());
            studentInfoDTO.setStudentName(student.getName());
            studentInfoDTO.setClassName(student.getClassEntity().getName());

            // Lấy danh sách điểm thi và môn học
            List<SubjectMarkDTO> subjectMarks = new ArrayList<>();
            for (Marks mark : student.getMarks()) {
                SubjectMarkDTO subjectMarkDTO = new SubjectMarkDTO();
                subjectMarkDTO.setSubjectName(mark.getSubject().getName());
                subjectMarkDTO.setMark((int) mark.getMark());
                subjectMarks.add(subjectMarkDTO);
            }

            studentInfoDTO.setSubjectMarks(subjectMarks);
            studentInfoDTOList.add(studentInfoDTO);
        }

        return studentInfoDTOList;
    }

    public StudentDTO getStudentById(int id) {
        return studentRepository.findById(id)
                .map(student -> {
                    StudentDTO dto = new StudentDTO();
                    dto.setId(student.getId());
                    dto.setName(student.getName());
                    dto.setClassName(student.getClassEntity().getName());
                    return dto;
                })
                .orElse(null);
    }

    public Student addStudent(StudentCreateDTO studentCreateDTO) {
        // Tìm lớp học theo ID
        ClassEntity classEntity = classRepository.findById(studentCreateDTO.getClassId())
                .orElseThrow(() -> new RuntimeException("Class not found with id " + studentCreateDTO.getClassId()));

        // Tạo sinh viên mới
        Student student = new Student();
        student.setName(studentCreateDTO.getName());
        student.setClassEntity(classEntity);

        // Lưu sinh viên vào cơ sở dữ liệu
        return studentRepository.save(student);
    }

    public void changeClass(int studentId, int classId) {
        // Tìm sinh viên theo ID
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Student not found with id " + studentId));

        // Tìm lớp học theo ID
        ClassEntity classEntity = classRepository.findById(classId)
                .orElseThrow(() -> new RuntimeException("Class not found with id " + classId));

        // Cập nhật lớp học cho sinh viên
        student.setClassEntity(classEntity);

        // Lưu thay đổi vào cơ sở dữ liệu
        studentRepository.save(student);
    }

    public void deleteStudent(int studentId) {
        // Kiểm tra xem sinh viên có tồn tại không
        if (!studentRepository.existsById(studentId)) {
            throw new RuntimeException("Student not found with id " + studentId);
        }

        // Xóa sinh viên
        studentRepository.deleteById(studentId);
    }

    public void updateMarks(int studentId, int subjectId, int mark) {
        // Kiểm tra xem sinh viên có tồn tại không
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Student not found with id " + studentId));

        // Kiểm tra xem môn học có tồn tại không
        Subject subject = subjectRepository.findById(subjectId)
                .orElseThrow(() -> new RuntimeException("Subject not found with id " + subjectId));

        // Kiểm tra xem điểm đã tồn tại chưa
        Marks existingMarks = marksRepository.findByStudentAndSubject(student, subject);

        if (existingMarks == null) {
            // Nếu điểm chưa tồn tại, tạo mới
            Marks newMarks = new Marks();
            newMarks.setStudent(student);
            newMarks.setSubject(subject);
            newMarks.setMark(mark);
            marksRepository.save(newMarks);
        } else {
            // Nếu điểm đã tồn tại, cập nhật
            existingMarks.setMark(mark);
            marksRepository.save(existingMarks);
        }
    }

    public Page<StudentDTO> searchStudents(String character, String  className, int pageNumber, int pageSize, String sortDirection) {
        // Create Sort object for sorting by name in ascending or descending order
        Sort sort = sortDirection.equalsIgnoreCase("asc") ? Sort.by("name").ascending() : Sort.by("name").descending();

        // Create Pageable object for pagination and sorting
        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);

        // Call the repository method to fetch paginated results
        Page<StudentDTO> studentsPage = studentRepository.findStudentsByName(character, className, pageable);

        // Map the results to StudentDTO, ensuring that className is returned instead of classId
        return studentsPage.map(student -> new StudentDTO(
                student.getId(),
                student.getName(),
                student.getClassName() // Returning className here
        ));
    }
}


