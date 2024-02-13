package com.dimathicc.ens.fileservice.service;

import com.dimathicc.ens.fileservice.dto.UserRequest;
import com.dimathicc.ens.fileservice.dto.UserResponse;
import com.dimathicc.ens.fileservice.exception.FileDownloadException;
import com.dimathicc.ens.fileservice.exception.WorkbookCreateException;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;


@Service
public class FileReaderService {

    private final UserClient userClient;

    public FileReaderService(UserClient userClient) {
        this.userClient = userClient;
    }

    public boolean createUsersFromXlsx(MultipartFile file) {
        Workbook workbook;
        try {
            workbook = new XSSFWorkbook(file.getInputStream());
        } catch (IOException e) {
            throw new WorkbookCreateException(e.getMessage());
        }
        Sheet sheet = workbook.getSheetAt(0);
        for (Row row : sheet) {

            try {
                userClient.createUser(
                        UserRequest.builder()
                                .name(row.getCell(0).toString())
                                .email(row.getCell(1).toString())
                                .phone(row.getCell(2).toString())
                                .telegramId(row.getCell(3).toString())
                                .build()
                );
            } catch (RuntimeException e) {
                throw new RuntimeException(e.getMessage());
            }
        }
        return true;
    }

    public byte[] downloadFileWithUsers() {
        List<UserResponse> users = userClient.retrieveAllUsers().getBody();

        assert users != null;

        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("Users");

            Row row = sheet.createRow(0);
            row.createCell(0).setCellValue("ID");
            row.createCell(1).setCellValue("Name");
            row.createCell(2).setCellValue("Email");
            row.createCell(3).setCellValue("Phone");
            row.createCell(4).setCellValue("Telegram ID");

            int counter = 0;
            for (UserResponse user : users) {
                Row newRow = sheet.createRow(counter++);
                newRow.createCell(0).setCellValue(user.id());
                newRow.createCell(1).setCellValue(user.name());
                newRow.createCell(2).setCellValue(user.email());
                newRow.createCell(3).setCellValue(user.phone());
                newRow.createCell(4).setCellValue(user.telegramId());
            }

            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            workbook.write(outputStream);

            return outputStream.toByteArray();
        } catch (IOException e) {
            throw new FileDownloadException(e.getMessage());
        }
    }
}
