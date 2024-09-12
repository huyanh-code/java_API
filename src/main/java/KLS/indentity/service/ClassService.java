package KLS.indentity.service;

import KLS.indentity.dto.ClassDTO;
import KLS.indentity.model.ClassEntity;
import KLS.indentity.repository.ClassRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class ClassService {

    @Autowired
    private ClassRepository classRepository;


    public ClassEntity addClass(ClassDTO classDTO) {
        // Tạo sinh viên mới
        ClassEntity newClass = new ClassEntity();
        newClass.setName(classDTO.getClassName());

        // Lưu sinh viên vào cơ sở dữ liệu
        return classRepository.save(newClass);
    }
}
