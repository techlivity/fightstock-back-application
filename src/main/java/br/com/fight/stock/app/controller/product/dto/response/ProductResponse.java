package br.com.fight.stock.app.controller.product.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder(value = {"id","nome","image", "descrição"})
public record ProductResponse(
        @JsonProperty("ID") Long id,
        @JsonProperty("arquivado") Boolean filed,
        @JsonProperty("publicado") Boolean published,
        @JsonProperty("criado_em") String createdOn,
        @JsonProperty("atualizado_em") String lastUpdatedOn,
        @JsonProperty("nome") String name,
        @JsonProperty("image_data") String imageData,
        @JsonProperty("descrição") String description,
        @JsonProperty("em_destaque") Boolean featured,
        @JsonProperty("em_promoção") Boolean promotion) {
}
