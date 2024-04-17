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
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tb_produtos")
public class Product {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "nome")
    @JsonProperty("nome")
    private String name;
    @Column(name = "image")
    @JsonProperty("image_url")
    private String imageUrl;
    @Column(name = "descricao")
    @JsonProperty("descrição")
    private String description;
    @Column(name = "em_destaque")
    @JsonProperty("em_destaque")
    private Boolean featured;
    @Column(name = "em_promocao")
    @JsonProperty("em_promoção")
    private Boolean promotion;
    @Column(name = "arquivado")
    private Boolean filed;
    @Column(name = "publicado")
    private Boolean published;
    @CreationTimestamp
    private Instant createdOn;
    @UpdateTimestamp
    private Instant lastUpdatedOn;
}
