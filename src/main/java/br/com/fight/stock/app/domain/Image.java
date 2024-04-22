package br.com.fight.stock.app.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.Serializable;
import java.time.Instant;
import java.util.Base64;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Image")
public class Image implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @JsonProperty("nome")
    private String name;
    @JsonProperty("altura")
    private Integer height;
    @JsonProperty("largura")
    private Integer width;
    @JsonProperty("dados")
    private String data;
    @JsonProperty("extensão")
    private String extension;
    @CreationTimestamp
    @JsonProperty("criado_em")
    private Instant createdOn;
    @UpdateTimestamp
    @JsonProperty("atualizado_em")
    private Instant lastUpdatedOn;

    public Image(String name, Integer height, Integer width, String data, String extension) {
        this.name = name;
        this.height = height;
        this.width = width;
        this.data = data;
        this.extension = extension;
    }

    public static Image createImage(MultipartFile file) throws IOException {
        ByteArrayInputStream bis = new ByteArrayInputStream(file.getBytes());
        BufferedImage imageBuffered = ImageIO.read(bis);
        String fileName = file.getOriginalFilename();
        String name = "";
        String extension = "";
        if(fileName != null) {
            int lastDotIndex = fileName.lastIndexOf(".");
            name = fileName.substring(0, lastDotIndex);
            extension = fileName.substring(lastDotIndex + 1);
        }
        return new Image(name,
                imageBuffered.getHeight(),
                imageBuffered.getWidth(),
                Base64.getEncoder()
                        .encodeToString(file.getBytes()), extension);
    }
}
