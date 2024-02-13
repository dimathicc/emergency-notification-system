package com.dimathicc.ens.userservice.service;

import com.dimathicc.ens.userservice.dto.UserRequest;
import com.dimathicc.ens.userservice.exception.FileDownloadException;
import com.dimathicc.ens.userservice.exception.ReadingUserFromXlsxException;
import com.dimathicc.ens.userservice.model.User;
import com.dimathicc.ens.userservice.repository.UserRepository;
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

    private final UserService userService;
    private final UserRepository userRepository;

    public FileReaderService(UserService userService, UserRepository userRepository) {
        this.userService = userService;
        this.userRepository = userRepository;
    }

    public boolean readUsersFromXlsx(MultipartFile file) {
        Workbook workbook;
        try {
            workbook = new XSSFWorkbook(file.getInputStream());
            Sheet sheet = workbook.getSheetAt(0);

            for (Row row : sheet) {
                String name = row.getCell(0).toString();
                String email = row.getCell(1).toString();
                String phone = row.getCell(2).toString();
                String telegramId = row.getCell(3).toString();

                UserRequest userRequest = new UserRequest(name, email, phone, telegramId);
                userService.createUser(userRequest);

            }
        } catch (IOException e) {
            throw new ReadingUserFromXlsxException(e.getMessage());
        }
        return true;
    }

    public byte[] downloadFileWithUsers() {
        List<User> users = userRepository.findAll();

        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("Users");

            Row row = sheet.createRow(0);
            row.createCell(0).setCellValue("ID");
            row.createCell(1).setCellValue("Name");
            row.createCell(2).setCellValue("Email");
            row.createCell(3).setCellValue("Phone");
            row.createCell(4).setCellValue("Telegram ID");

            int counter = 0;
            for (User user : users) {
                Row newRow = sheet.createRow(counter++);
                newRow.createCell(0).setCellValue(user.getId());
                newRow.createCell(1).setCellValue(user.getName());
                newRow.createCell(2).setCellValue(user.getEmail());
                newRow.createCell(3).setCellValue(user.getPhone());
                newRow.createCell(4).setCellValue(user.getTelegramId());
            }

            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            workbook.write(outputStream);

            return outputStream.toByteArray();
        } catch (IOException e) {
            throw new FileDownloadException(e.getMessage());
        }
    }
}
