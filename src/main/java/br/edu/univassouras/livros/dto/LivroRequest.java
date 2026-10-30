package br.edu.univassouras.livros.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record LivroRequest(
        @NotBlank(message = "O título é obrigatório")
        String titulo,

        @NotBlank(message = "O autor é obrigatório")
        String autor,

        @NotBlank(message = "O gênero é obrigatório")
        String genero,

        @NotNull(message = "O ano de publicação é obrigatório")
        Integer anoPublicacao,

        @NotBlank(message = "O status de leitura é obrigatório")
        String statusLeitura
) {
}
