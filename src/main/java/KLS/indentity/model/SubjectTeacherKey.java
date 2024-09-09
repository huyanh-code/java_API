package KLS.indentity.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@Embeddable
public class SubjectTeacherKey implements Serializable {
    @Column(name = "subject_id")
    private int subjectId;

    @Column(name = "teacher_id")
    private int teacherId;
    
}
