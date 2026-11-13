package telas;

import dao.MedicamentoDAO;
import entidade.Medicamento;

import javax.swing.*;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class TelaCadastroMedicamento extends JFrame {

    private JTextField txtId;
    private JTextField txtNome;
    private JTextField txtFabricante;
    private JTextField txtCategoria;
    private JTextField txtPreco;
    private JTextField txtEstoque;
    private JTextField txtValidade;
    private JButton btnSalvar;
    private JButton btnCancelar;

    private Medicamento medicamentoEdicao;
    private Runnable aoSalvar;

    public TelaCadastroMedicamento(Medicamento medicamentoParaEditar, Runnable aoSalvar) {
        this.medicamentoEdicao = medicamentoParaEditar;
        this.aoSalvar = aoSalvar;

        configurarJanela();
        criarComponentes();

        if (medicamentoEdicao != null) {
            carregarDadosEdicao();
        }
    }

    private void configurarJanela() {
        setTitle(medicamentoEdicao == null ? "Cadastro de Medicamento" : "Editar Medicamento");
        setSize(440, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null);
    }

    private void criarComponentes() {
        JLabel lblId = new JLabel("Código (ID):");
        lblId.setBounds(30, 20, 130, 25);
        add(lblId);

        txtId = new JTextField();
        txtId.setBounds(170, 20, 220, 25);
        txtId.setEnabled(false); // Chave primária bloqueada na edição
        add(txtId);

        JLabel lblNome = new JLabel("Medicamento:");
        lblNome.setBounds(30, 60, 130, 25);
        add(lblNome);

        txtNome = new JTextField();
        txtNome.setBounds(170, 60, 220, 25);
        add(txtNome);

        JLabel lblFabricante = new JLabel("Fabricante:");
        lblFabricante.setBounds(30, 100, 130, 25);
        add(lblFabricante);

        txtFabricante = new JTextField();
        txtFabricante.setBounds(170, 100, 220, 25);
        add(txtFabricante);

        JLabel lblCategoria = new JLabel("Categoria/Tarja:");
        lblCategoria.setBounds(30, 140, 130, 25);
        add(lblCategoria);

        txtCategoria = new JTextField();
        txtCategoria.setBounds(170, 140, 220, 25);
        add(txtCategoria);

        JLabel lblPreco = new JLabel("Preço (R$):");
        lblPreco.setBounds(30, 180, 130, 25);
        add(lblPreco);

        txtPreco = new JTextField();
        txtPreco.setBounds(170, 180, 220, 25);
        add(txtPreco);

        JLabel lblEstoque = new JLabel("Qtd. em Estoque:");
        lblEstoque.setBounds(30, 220, 130, 25);
        add(lblEstoque);

        txtEstoque = new JTextField();
        txtEstoque.setBounds(170, 220, 220, 25);
        add(txtEstoque);

        JLabel lblValidade = new JLabel("Validade (dd/mm/aaaa):");
        lblValidade.setBounds(30, 260, 140, 25);
        add(lblValidade);

        txtValidade = new JTextField();
        txtValidade.setBounds(170, 260, 220, 25);
        add(txtValidade);

        btnSalvar = new JButton("Salvar");
        btnSalvar.setBounds(95, 310, 110, 30);
        btnSalvar.addActionListener(e -> salvar());
        add(btnSalvar);

        btnCancelar = new JButton("Cancelar");
        btnCancelar.setBounds(225, 310, 110, 30);
        btnCancelar.addActionListener(e -> dispose());
        add(btnCancelar);
    }

    private void carregarDadosEdicao() {
        txtId.setText(String.valueOf(medicamentoEdicao.getId()));
        txtNome.setText(medicamentoEdicao.getNome());
        txtFabricante.setText(medicamentoEdicao.getFabricante());
        txtCategoria.setText(medicamentoEdicao.getCategoria());
        txtPreco.setText(String.valueOf(medicamentoEdicao.getPreco()));
        txtEstoque.setText(String.valueOf(medicamentoEdicao.getQuantidadeEstoque()));

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        txtValidade.setText(sdf.format(medicamentoEdicao.getDataValidade()));
    }

    private void salvar() {
        String nome = txtNome.getText().trim();
        String fabricante = txtFabricante.getText().trim();
        String categoria = txtCategoria.getText().trim();
        String precoTexto = txtPreco.getText().trim().replace(",", ".");
        String estoqueTexto = txtEstoque.getText().trim();
        String validadeTexto = txtValidade.getText().trim();

        if (nome.isBlank() || fabricante.isBlank() || categoria.isBlank() || precoTexto.isBlank() || estoqueTexto.isBlank() || validadeTexto.isBlank()) {
            JOptionPane.showMessageDialog(this, "Preencha todos os campos obrigatórios.", "Validação", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            double preco = Double.parseDouble(precoTexto);
            int estoque = Integer.parseInt(estoqueTexto);

            if (preco <= 0 || estoque < 0) {
                JOptionPane.showMessageDialog(this, "O preço deve ser maior que zero e o estoque não pode ser negativo.", "Erro de Validação", JOptionPane.ERROR_MESSAGE);
                return;
            }

            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            sdf.setLenient(false);
            java.util.Date dataConvertida = sdf.parse(validadeTexto);
            Date dataValidade = new Date(dataConvertida.getTime());

            MedicamentoDAO dao = new MedicamentoDAO();

            if (medicamentoEdicao == null) {
                Medicamento novo = new Medicamento(nome, fabricante, categoria, preco, estoque, dataValidade);
                if (dao.inserir(novo)) {
                    JOptionPane.showMessageDialog(this, "Medicamento cadastrado com sucesso!");
                    if (aoSalvar != null) aoSalvar.run();
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(this, "Erro ao salvar medicamento no banco de dados.", "Erro", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                medicamentoEdicao.setNome(nome);
                medicamentoEdicao.setFabricante(fabricante);
                medicamentoEdicao.setCategoria(categoria);
                medicamentoEdicao.setPreco(preco);
                medicamentoEdicao.setQuantidadeEstoque(estoque);
                medicamentoEdicao.setDataValidade(dataValidade);

                if (dao.atualizar(medicamentoEdicao)) {
                    JOptionPane.showMessageDialog(this, "Medicamento atualizado com sucesso!");
                    if (aoSalvar != null) aoSalvar.run();
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(this, "Erro ao atualizar medicamento no banco de dados.", "Erro", JOptionPane.ERROR_MESSAGE);
                }
            }

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Preço e Quantidade em Estoque devem ser números válidos.", "Erro de Tipo", JOptionPane.ERROR_MESSAGE);
        } catch (ParseException e) {
            JOptionPane.showMessageDialog(this, "Data de validade inválida. Utilize o formato dd/mm/aaaa.", "Erro de Data", JOptionPane.ERROR_MESSAGE);
        }
    }
}
