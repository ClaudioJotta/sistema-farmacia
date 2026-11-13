package entidade;

import java.sql.Date;

public class Medicamento {
    private int id;
    private String nome;
    private String fabricante;
    private String categoria;
    private double preco;
    private int quantidadeEstoque;
    private Date dataValidade;

    public Medicamento() {
    }

    public Medicamento(String nome, String fabricante, String categoria, double preco, int quantidadeEstoque, Date dataValidade) {
        this.nome = nome;
        this.fabricante = fabricante;
        this.categoria = categoria;
        this.preco = preco;
        this.quantidadeEstoque = quantidadeEstoque;
        this.dataValidade = dataValidade;
    }

    public Medicamento(int id, String nome, String fabricante, String categoria, double preco, int quantidadeEstoque, Date dataValidade) {
        this.id = id;
        this.nome = nome;
        this.fabricante = fabricante;
        this.categoria = categoria;
        this.preco = preco;
        this.quantidadeEstoque = quantidadeEstoque;
        this.dataValidade = dataValidade;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getFabricante() {
        return fabricante;
    }

    public void setFabricante(String fabricante) {
        this.fabricante = fabricante;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public double getPreco() {
        return preco;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }

    public int getQuantidadeEstoque() {
        return quantidadeEstoque;
    }

    public void setQuantidadeEstoque(int quantidadeEstoque) {
        this.quantidadeEstoque = quantidadeEstoque;
    }

    public Date getDataValidade() {
        return dataValidade;
    }

    public void setDataValidade(Date dataValidade) {
        this.dataValidade = dataValidade;
    }
}