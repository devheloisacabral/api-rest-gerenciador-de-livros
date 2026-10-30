package br.edu.univassouras.livros.service;

import br.edu.univassouras.livros.dto.LivroRequest;
import br.edu.univassouras.livros.exception.LivroNaoEncontradoException;
import br.edu.univassouras.livros.model.Livro;
import br.edu.univassouras.livros.repository.LivroRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LivroService {

    private final LivroRepository repository;

    public LivroService(LivroRepository repository) {
        this.repository = repository;
    }

    public Livro cadastrar(LivroRequest dados) {
        Livro livro = new Livro(
                null,
                dados.titulo().trim(),
                dados.autor().trim(),
                dados.genero().trim(),
                dados.anoPublicacao(),
                dados.statusLeitura().trim()
        );
        return repository.salvar(livro);
    }

    public List<Livro> listarTodos() {
        return repository.listarTodos();
    }

    public Livro consultar(Long id) {
        return repository.buscarPorId(id)
                .orElseThrow(() -> new LivroNaoEncontradoException(id));
    }

    public Livro atualizar(Long id, LivroRequest dados) {
        Livro livro = consultar(id);
        livro.setTitulo(dados.titulo().trim());
        livro.setAutor(dados.autor().trim());
        livro.setGenero(dados.genero().trim());
        livro.setAnoPublicacao(dados.anoPublicacao());
        livro.setStatusLeitura(dados.statusLeitura().trim());
        return repository.salvar(livro);
    }

    public void excluir(Long id) {
        consultar(id);
        repository.excluir(id);
    }
}
