package com.asmadiya.student.service;

import org.springframework.web.multipart.MultipartFile;
import java.io.InputStream;
import java.util.List;
import com.asmadiya.student.entity.Student;

public interface ExcelService {
    void importStudentsFromExcel(MultipartFile file) throws Exception;
    List<Student> parseExcelFile(InputStream inputStream) throws Exception;
}
