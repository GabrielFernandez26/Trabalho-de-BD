package src.com.example.projetoaeroporto.entity;

public class Reserva {
    private int id;
    private int clienteId;
    private int vooId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCliente() {
        return clienteId;
    }

    public void setCliente(int clienteId) {
        this.clienteId = clienteId;
    }

    public int getVoo() {
        return vooId;
    }

    public void setVoo(int vooId) {
        this.vooId = vooId;
    }
}
