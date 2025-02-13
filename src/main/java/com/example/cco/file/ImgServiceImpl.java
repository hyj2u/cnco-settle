package com.example.cco.file;

import com.example.cco.exception.DuplicatedException;
import com.example.cco.member.*;
import com.example.cco.security.RefreshTknRepository;
import lombok.RequiredArgsConstructor;
import org.apache.poi.util.IOUtils;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ImgServiceImpl implements ImgService {

    private final FileRepository fileRepository;
    private final ResourceLoader resourceLoader;

    @Override
    public byte[] getImage(Long pkey) throws Exception {
        FileEntity file = fileRepository.findById(pkey).get();
        InputStream imageStream = new FileInputStream(file.getFilePath());
        return IOUtils.toByteArray(imageStream);
    }
}

