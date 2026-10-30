package br.edu.univassouras.livros.repository;

import br.edu.univassouras.livros.model.Livro;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class LivroRepository {

    private final ConcurrentHashMap<Long, Livro> livros = new ConcurrentHashMap<>();
    private final AtomicLong proximoId = new AtomicLong(1);

    public Livro salvar(Livro livro) {
        if (livro.getId() == null) {
            livro.setId(proximoId.getAndIncrement());
        }
        livros.put(livro.getId(), livro);
        return livro;
    }

    public List<Livro> listarTodos() {
        List<Livro> resultado = new ArrayList<>(livros.values());
        resultado.sort(Comparator.comparing(Livro::getId));
        return resultado;
    }

    public Optional<Livro> buscarPorId(Long id) {
        return Optional.ofNullable(livros.get(id));
    }

    public void excluir(Long id) {
        livros.remove(id);
    }

    public void limpar() {
        livros.clear();
        proximoId.set(1);
    }
}
