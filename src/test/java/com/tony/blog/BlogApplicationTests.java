package com.tony.blog;

import com.tony.blog.utils.Md5Utils;
import net.coobird.thumbnailator.Thumbnails;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URLEncoder;

@SpringBootTest
class BlogApplicationTests {

    @Test
    void contextLoads() throws IOException {
       /* Thumbnails.of(new File("C:\\Users\\Tony\\Pictures\\Camera Roll\\WIN_20211128_16_58_24_Pro.mp4"))
                .size(270, 270)
                .toFile(new File("C:\\Users\\Tony\\Pictures\\thumbnail.jpg"));*/
    }

}
