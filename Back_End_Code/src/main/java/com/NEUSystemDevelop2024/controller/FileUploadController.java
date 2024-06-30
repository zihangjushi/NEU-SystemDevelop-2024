package com.NEUSystemDevelop2024.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;
import org.springframework.beans.factory.annotation.Value;

@RestController
@RequestMapping("/api")
public class FileUploadController {

    @Autowired
    private ResourceLoader resourceLoader;

    // 注入文件上传目录
    @Value("${file.upload-dir}")
    private String uploadDir;

    @PostMapping("/upload")
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file) {
        try {
            // 检查上传的文件是否为空
            if (file.isEmpty()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("请选择上传的文件");
            }

            // 获取文件名
            String fileName = file.getOriginalFilename();
            if (fileName == null) {
                fileName = Objects.requireNonNull(file.getOriginalFilename());
            }

            // 目标文件路径
            Path targetLocation = Paths.get(uploadDir + "/" + fileName);

            // 如果目录不存在则创建
            if (!Files.exists(targetLocation.getParent())) {
                Files.createDirectories(targetLocation.getParent());
            }

            // 将文件保存到目标位置
            try (InputStream inputStream = file.getInputStream();
                 FileOutputStream outputStream = new FileOutputStream(targetLocation.toFile())) {
                int bytesRead;
                byte[] buffer = new byte[1024];
                while ((bytesRead = inputStream.read(buffer)) != -1) {
                    outputStream.write(buffer, 0, bytesRead);
                }
            }

            // 返回文件上传成功的消息
            String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                    .path("/api/download/")
                    .path(fileName)
                    .toUriString();

            return ResponseEntity.ok().body("文件上传成功: " + fileDownloadUri);
        } catch (IOException ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("文件上传失败: " + ex.getMessage());
        }
    }

    @GetMapping("/download/{fileName:.+}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String fileName) {
        try {
            // 加载文件作为资源
            Path filePath = Paths.get(uploadDir).resolve(fileName).normalize();
            Resource resource = resourceLoader.getResource("file:" + filePath.toString());

            // 确保文件存在并可读
            if (resource.exists() && resource.isReadable()) {
                return ResponseEntity.ok()
                        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                        .body(resource);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    // 删除文件的方法
    @DeleteMapping("/deleteFile/{fileName:.+}")
    public ResponseEntity<String> deleteFile(@PathVariable String fileName) {
        try {
            // 构建文件路径
            Path filePath = Paths.get(uploadDir).resolve(fileName).normalize();
            File file = filePath.toFile();

            // 检查文件是否存在
            if (file.exists() && file.isFile()) {
                // 尝试删除文件
                if (file.delete()) {
                    return ResponseEntity.ok().body("文件删除成功: " + fileName);
                } else {
                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("无法删除文件: " + fileName);
                }
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("文件不存在: " + fileName);
            }
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("删除文件时发生错误: " + ex.getMessage());
        }
    }
}

