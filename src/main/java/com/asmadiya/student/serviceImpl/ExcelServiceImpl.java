package com.asmadiya.student.service;

import com.asmadiya.student.entity.Student;
import com.asmadiya.student.repository.StudentRepository;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
public class ExcelServiceImpl implements ExcelService {

    private final StudentRepository studentRepository;

    public ExcelServiceImpl(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @Override
    public void importStudentsFromExcel(MultipartFile file) throws IOException {
        List<Student> students = parseExcelFile(file.getInputStream());
        studentRepository.saveAll(students);
    }

    @Override
    public List<Student> parseExcelFile(InputStream inputStream) throws IOException {
        List<Student> studentList = new ArrayList<>();

        try (Workbook workbook = new XSSFWorkbook(inputStream)) {
            Sheet sheet = workbook.getSheetAt(0);
            Iterator<Row> rows = sheet.iterator();

            // Skip first two rows (headers)
            if (rows.hasNext()) rows.next(); // Skip Row 1
            if (rows.hasNext()) rows.next(); // Skip Row 2

            while (rows.hasNext()) {
                Row row = rows.next();

                Student student = new Student();
                student.setName(getCellValue(row.getCell(1)));
                student.setPercentage(getNumericCellValue(row.getCell(3)));
                student.setState(getCellValue(row.getCell(2)));
                student.setYop((int) getNumericCellValue(row.getCell(4)));

                studentList.add(student);
            }
        }
        return studentList;
    }

    private String getCellValue(Cell cell) {
        if (cell == null) {
            return "";
        }
        return switch (cell.getCellType()) {
            case STRING -> cell.getStringCellValue();
            case NUMERIC -> String.valueOf((long) cell.getNumericCellValue());
            case BOOLEAN -> String.valueOf(cell.getBooleanCellValue());
            default -> "";
        };
    }

    private double getNumericCellValue(Cell cell) {
        if (cell == null) {
            return 0.0;
        }
        return switch (cell.getCellType()) {
            case NUMERIC -> cell.getNumericCellValue();
            case STRING -> {
                try {
                    yield Double.parseDouble(cell.getStringCellValue());
                } catch (NumberFormatException e) {
                    yield 0.0;
                }
            }
            default -> 0.0;
        };
    }
}
