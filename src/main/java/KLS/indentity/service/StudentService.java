package KLS.indentity.service;

import KLS.indentity.dto.StudentInfoDTO;
import KLS.indentity.dto.SubjectMarkDTO;
import KLS.indentity.model.Marks;
import KLS.indentity.model.Student;
import KLS.indentity.repository.MarksRepository;
import KLS.indentity.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class StudentService {
    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private MarksRepository marksRepository;

    public List<StudentInfoDTO> findAll() {
        List<Student> students = studentRepository.findAll();
        List<StudentInfoDTO> studentInfoDTOList = new ArrayList<>();

        for (Student student : students) {
            StudentInfoDTO studentInfoDTO = new StudentInfoDTO();
            studentInfoDTO.setStudentName(student.getName());
            studentInfoDTO.setClassName(student.getClassEntity().getName());

            // Fetch marks and subjects
            List<Marks> marksList = marksRepository.findByStudent(student);
            List<SubjectMarkDTO> subjectMarks = new ArrayList<>();
            for (Marks mark : marksList) {
                SubjectMarkDTO subjectMarkDTO = new SubjectMarkDTO();
                subjectMarkDTO.setSubjectName(String.valueOf(mark.getSubject().getClass()));
                subjectMarkDTO.setMark(mark.getMark());
                subjectMarks.add(subjectMarkDTO);
            }
            studentInfoDTO.setSubjectMarks(subjectMarks);
            studentInfoDTOList.add(studentInfoDTO);
        }
        return studentInfoDTOList;
    }

    public Student getStudentById(int id) {
        return studentRepository.findById(id).orElse(null);
    }
}

