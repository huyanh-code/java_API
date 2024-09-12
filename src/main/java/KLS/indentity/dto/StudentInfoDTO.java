package KLS.indentity.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class StudentInfoDTO {
    private int student_id;
    private String studentName;
    private String className;
    private List<SubjectMarkDTO> subjectMarks;

}
