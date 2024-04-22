package br.com.fight.stock.app.controller.categories.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;

public record CategoriesRequest(@JsonProperty("nome") String name, @JsonProperty("descrição") String description) {
}
