package br.edu.univassouras.livros.exception;

public class LivroNaoEncontradoException extends RuntimeException {

    public LivroNaoEncontradoException(Long id) {
        super("Livro com identificador " + id + " não encontrado");
    }
}
