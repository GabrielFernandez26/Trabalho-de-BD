package src.com.example.projetoaeroporto.Telas;

import javafx.scene.layout.Pane;
import src.com.example.projetoaeroporto.control.ReservaControl;
import src.com.example.projetoaeroporto.control.VooControl;
import src.com.example.projetoaeroporto.entity.Voo;

import java.util.ArrayList;
import java.util.List;
public abstract class TelaReserva {
    private List<ExecutorComandos> listaExecutores = new ArrayList<>();

    public abstract Pane render(VooControl voo);

    public void adicionarExecutor(ExecutorComandos exec) {
        listaExecutores.add(exec);
    }

    public void removerExecutor(ExecutorComandos exec) {
        listaExecutores.remove(exec);
    }

    public void notificarComando(String comando) {
        for (ExecutorComandos exec : listaExecutores) {
            exec.executarComando(comando);
        }
    }
}
