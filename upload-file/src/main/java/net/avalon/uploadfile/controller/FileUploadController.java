package net.avalon.uploadfile.controller;

import lombok.extern.slf4j.Slf4j;
import net.avalon.common.util.R;
import net.avalon.common.util.ResponseUtil;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @Author: Weiyin
 * @Create: 2023/5/8 - 16:03
 */

@RestController
@Slf4j
public class FileUploadController {

    private static final String TARGET_DIRECTORY = "D:/file/";


    @PostMapping("/")
    public R handleFileUpload(@RequestParam("file") MultipartFile file) throws IOException {

        log.info("上传的文件：{}",file.getOriginalFilename());
        log.info("文件大小：{} bytes",file.getSize());

        String fileName = file.getOriginalFilename();

        String suffix = fileName.substring(fileName.lastIndexOf("."));
        log.info("文件后缀：{}",suffix);

        String newName = String.format("%s%s%s",TARGET_DIRECTORY,UUID.randomUUID().toString(),suffix);
        log.info("新名字:{}",newName);

        //上传文件
        file.transferTo(Paths.get(newName));
        log.info("文件上传成功");

        return ResponseUtil.created(newName);
    }

    @GetMapping("/all")
    public R getAll() throws IOException {

        List<String> ret = new ArrayList<>();

        Path path = Paths.get(TARGET_DIRECTORY);
        
        Files.walk(path).forEach(p -> {

            if(!Files.isDirectory(p)){
                String fullPath = String.format("%s",p);
                ret.add(fullPath);
            }

        });
        
        return ResponseUtil.ok(ret);
    }
}
