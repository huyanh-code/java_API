package KLS.indentity.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
@Entity
@Table(name = "t_students")
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name")
    private String name;

    @ManyToOne
    @JoinColumn(name = "class_id", referencedColumnName = "id")
    private ClassEntity classEntity;


    @OneToMany(mappedBy = "student")
    private List<Marks> marks;

}

