package src.com.example.projetoaeroporto.DAO;

import src.com.example.projetoaeroporto.entity.Reserva;

import java.util.List;

public interface ReservaDAO {
    void salvar(Reserva r);
    List<Reserva> pesquisarPorId(Integer id);
    void remover(int id);
    void atualizar(int id, Reserva r);
}
