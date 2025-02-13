package com.example.cco.base;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.util.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.Date;

@Slf4j
@Component
@RequiredArgsConstructor
public class FileUtil {
    private final DateConverter dateUtil;
    @Value("${image.src}")
    private String src;
    public byte[] getFile(String src) throws IOException {
        log.info(src);
        InputStream image = new FileInputStream(src);
        byte[] imageByte = IOUtils.toByteArray(image);
        image.close();
        return imageByte;
    }
    public String createFile(MultipartFile file) throws IOException {
        String date = dateUtil.getNowDate(); // YYYY-MM-DD 형식
        if (file.isEmpty()) {
            throw new IllegalArgumentException("File is empty!");
        }

        // 디렉토리 생성
        File dir = new File(src, date); // src/date
        if (!dir.exists() && !dir.mkdirs()) { // 안전하게 모든 상위 디렉토리 포함 생성
            throw new IOException("Failed to create directory: " + dir.getAbsolutePath());
        }

        // 파일명 생성
        String uniqueFileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
        File dest = new File(dir, uniqueFileName);

        // InputStream을 사용하여 파일 복사
        try (InputStream inputStream = file.getInputStream()) {
            Files.copy(inputStream, dest.toPath(), StandardCopyOption.REPLACE_EXISTING);
        }
        log.info("File successfully saved at: {}", dest.getAbsolutePath());
        // 저장된 파일의 경로 반환
        return dest.getAbsolutePath();
    }

}
