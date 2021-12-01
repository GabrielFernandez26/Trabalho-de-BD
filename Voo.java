package src.com.example.projetoaeroporto.entity;

import java.time.LocalDate;


public class Voo {
    private int id;
    private String origem;
    private String destino;
    private LocalDate decolagem;
    private LocalDate pouso;
    private int assentosDisponiveis;
    private Double preco;

    public int getAssentosDisponiveis() {
        return assentosDisponiveis;
    }

    public void setAssentosDisponiveis(int assentosDisponiveis) {
        this.assentosDisponiveis = assentosDisponiveis;
    }

    public Double getPreco() {
        return preco;
    }

    public void setPreco(Double preco) {
        this.preco = preco;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getOrigem() {
        return origem;
    }

    public void setOrigem(String origem) {
        this.origem = origem;
    }

    public String getDestino() {
        return destino;
    }

    public void setDestino(String destino) {
        this.destino = destino;
    }

    public LocalDate getDecolagem() {
        return decolagem;
    }

    public void setDecolagem(LocalDate decolagem) {
        this.decolagem = decolagem;
    }

    public LocalDate getPouso() {
        return pouso;
    }

    public void setPouso(LocalDate pouso) {
        this.pouso = pouso;
    }
}
