package br.com.fight.stock.app.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "carrossel")
public class Carousel {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne(cascade = CascadeType.ALL)
    @JsonProperty("imagem")
    private Image image;
    @JsonProperty("redirecionamento_url")
//    TODO: criar validadores de URL com pattern
    private String url;
    @CreationTimestamp
    @JsonProperty("criado_em")
    private Instant createdOn;
    @UpdateTimestamp
    @JsonProperty("atualizado_em")
    private Instant lastUpdatedOn;

    public Carousel(Image image, String url) {
        this.image = image;
        this.url = url;
    }
}
