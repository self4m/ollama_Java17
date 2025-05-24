package org.self4m.ollama_java.pojo;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class ChatParam {

    // 用户的问题
    String text;

    // 媒体文件列表
    MultipartFile[] file;
}
