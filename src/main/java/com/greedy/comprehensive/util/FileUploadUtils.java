package com.greedy.comprehensive.util;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.apache.commons.io.FilenameUtils;
import org.springframework.web.multipart.MultipartFile;

public class FileUploadUtils {

    //저장하고 싶은 경로, 저장하고 싶은 파일명, 멀티파트 파일 안에 정보가 담겨있어서 -> 그걸 전달 받아서
    //실제 내가 원하는 경로에다가 원하는 파일이름을 저장시키겠다.
    public static String saveFile(String uploadDir, String fileName, MultipartFile multipartFile) throws IOException {
        Path uploadPath = Paths.get(uploadDir);

        //업로드 경로가 존재하지 않을 경우 경로를 먼저 생성한다.
        if(!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        // 파일명 리네임 FilenameUtils.getExtension(multipartFile.getOriginalFilename()); 확장자 추출
        // 네임 + 확장자 추출하기
        String replaceFileName = fileName + "." + FilenameUtils.getExtension(multipartFile.getOriginalFilename());

        //파일 저장
        try(InputStream inputStream = multipartFile.getInputStream()) {
            Path filePath = uploadPath.resolve(replaceFileName);
            Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new IOException("파일을 저장하지 못하였습니다. filename : " +fileName);
        }

        //리네임 된 파일 명칭을 db쪽에서 가져다 써야 함.
        return replaceFileName;
    }

    public static void deleteFile(String uploadDir, String filename) throws IOException {

        Path uploadPath = Paths.get(uploadDir);
        Path filePath = uploadPath.resolve(filename);

        try {
            Files.delete(filePath);
        } catch (IOException e) {
            throw new IOException("파일을 삭제하지 못하였소. filename : " + filename);
        }

    }
}
