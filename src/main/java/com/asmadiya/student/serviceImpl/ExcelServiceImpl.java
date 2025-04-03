package com.asmadiya.student.serviceImpl;

import com.asmadiya.student.entity.Student;
import com.asmadiya.student.repository.StudentRepository;
import com.asmadiya.student.service.ExcelService;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
public class ExcelServiceImpl implements ExcelService{

    private final StudentRepository studentRepository;

    public ExcelServiceImpl(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public void importStudentsFromExcel(MultipartFile file) throws Exception {
        if (file.isEmpty()) {
            throw new RuntimeException("File is empty") ;
        }
        List<Student> students = parseExcelFile(file.getInputStream());
        studentRepository.saveAll(students);
    }

    public List<Student> parseExcelFile(InputStream inputStream) throws Exception {
        List<Student> studentList = new ArrayList<>();
        Workbook workbook = null;

        try {
            workbook = new XSSFWorkbook(inputStream);
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
        } finally {
            if (workbook != null) {
                workbook.close();
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
