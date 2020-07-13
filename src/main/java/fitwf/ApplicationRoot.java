package fitwf;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class ApplicationRoot implements WebMvcConfigurer {
    @Value("${upload.path.bin}")
    private String binUploadPath;

    @Value("${upload.path.gif}")
    private String gifUploadPath;

    public static void main(String[] args) {
        SpringApplication.run(ApplicationRoot.class, args);
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/api/public/files/*.bin")
                .addResourceLocations("file:///" + binUploadPath);
        registry.addResourceHandler("/api/public/files/*.gif")
                .addResourceLocations("file:///" + gifUploadPath);
    }
}
