package pl.com.app.service;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;
import pl.com.app.exceptions.ExceptionCode;
import pl.com.app.exceptions.MyException;

import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Base64;

@Service
public class FileService {

    @Value("${imgPath}")
    private String imgPath;

    private String createFilename(MultipartFile file) {
        final String originalFilename = file.getOriginalFilename();
        final String[] arr = originalFilename.split("\\.");
        final String extension = arr[arr.length - 1];
        final String filename = Base64.getEncoder().encodeToString(
                LocalDateTime.now()
                        .format(DateTimeFormatter.ofPattern("yyyy-MM-dd-HH-mm-ss"))
                        .getBytes()
        );
        return String.join(".", filename, extension);
    }

    public String addFile(MultipartFile file) {
        try {

            if (file == null) {
                throw new NullPointerException("FILE IS NULL");
            }

            final String filename = createFilename(file);
            final String fullPath = imgPath + filename;
            FileCopyUtils.copy(file.getBytes(), new File(fullPath));
            return filename;
        } catch (Exception e) {
            throw new MyException(ExceptionCode.FILE, e.getMessage());
        }
    }

    public String updateFile(MultipartFile file, String filename) {
        try {

            if (file == null || file.getBytes().length == 0) {
                return filename;
            }

            if (filename == null) {
                throw new NullPointerException("FILENAME IS NULL");
            }

            final String fullPath = imgPath + filename;
            FileCopyUtils.copy(file.getBytes(), new File(fullPath));
            return filename;
        } catch (Exception e) {
            throw new MyException(ExceptionCode.FILE, e.getMessage());
        }
    }

    public String deleteFile(String filename) {
        try {

            if (filename == null) {
                throw new NullPointerException("FILENAME IS NULL");
            }
            if (!filename.startsWith("pic")){
                final String fullPath = imgPath + filename;
                new File(fullPath).delete();
            }
            return filename;
        } catch (Exception e) {
            throw new MyException(ExceptionCode.FILE, e.getMessage());
        }
    }
}
