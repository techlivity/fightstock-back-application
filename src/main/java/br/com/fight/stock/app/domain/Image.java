package br.com.fight.stock.app.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.Base64;

@Getter
@Setter
@Entity
@Table(name = "Image")
@AllArgsConstructor
@NoArgsConstructor
public class Image implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private Integer height;
    private Integer width;
    private String data;
    private String extension;

    public Image(String name, Integer height, Integer width, String data) {
        this.name = name;
        this.height = height;
        this.width = width;
        this.data = data;
    }

    public static Image createImage(MultipartFile file) throws IOException {
        ByteArrayInputStream bis = new ByteArrayInputStream(file.getBytes());
        BufferedImage imageBuffered = ImageIO.read(bis);
        return new Image(file.getOriginalFilename(),
                imageBuffered.getHeight(),
                imageBuffered.getWidth(),
                Base64.getEncoder()
                        .encodeToString(file.getBytes()));
    }
}
