package br.com.fight.stock.app.controller.product.dto.response;

import br.com.fight.stock.app.domain.Image;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.List;

@JsonPropertyOrder(value = {"id","nome","image", "descrição"})
public record ProductResponse(
        @JsonProperty("ID") Long id,
        @JsonProperty("arquivado") Boolean filed,
        @JsonProperty("publicado") Boolean published,
        @JsonProperty("criado_em") String createdOn,
        @JsonProperty("atualizado_em") String lastUpdatedOn,
        @JsonProperty("nome") String name,
        @JsonProperty("imagem") Image image,
        @JsonProperty("descrição") List<String> description,
        @JsonProperty("em_destaque") Boolean featured,
        @JsonProperty("em_promoção") Boolean promotion) {
}
