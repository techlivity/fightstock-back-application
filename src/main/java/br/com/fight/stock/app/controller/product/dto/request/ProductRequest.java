package br.com.fight.stock.app.controller.product.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;

public record ProductRequest(@JsonProperty("nome") String name,
                             @JsonProperty("image_url") String imageURL,
                             @JsonProperty("descrição") String description,
                             @JsonProperty("em_destaque") Boolean featured,
                             @JsonProperty("em_promoção") Boolean promotion) {
}
