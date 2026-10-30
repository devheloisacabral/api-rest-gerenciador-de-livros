package br.edu.univassouras.livros.exception;

import java.time.LocalDateTime;
import java.util.Map;

public record ApiError(
        int status,
        String mensagem,
        LocalDateTime dataHora,
        Map<String, String> campos
) {
}
