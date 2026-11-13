package telas;

import dao.MedicamentoDAO;
import entidade.Medicamento;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.List;

public class TelaConsultaMedicamento extends JFrame {

    private JTable tabela;
    private DefaultTableModel modeloTabela;
    private JButton btnNovo;
    private JButton btnEditar;
    private JButton btnExcluir;
    private JButton btnAtualizar;

    private MedicamentoDAO dao = new MedicamentoDAO();
    private List<Medicamento> listaMedicamentos;

    public TelaConsultaMedicamento() {
        setTitle("Sistema de Farmácia - Consulta de Medicamentos");
        setSize(780, 450);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        String[] colunas = {"ID", "Medicamento", "Fabricante", "Categoria", "Preço (R$)", "Estoque", "Validade"};
        modeloTabela = new DefaultTableModel(colunas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tabela = new JTable(modeloTabela);
        JScrollPane scroll = new JScrollPane(tabela);
        add(scroll, BorderLayout.CENTER);

        JPanel painelBotoes = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        btnNovo = new JButton("Novo Medicamento");
        btnEditar = new JButton("Editar");
        btnExcluir = new JButton("Excluir");
        btnAtualizar = new JButton("Atualizar Lista");

        painelBotoes.add(btnNovo);
        painelBotoes.add(btnEditar);
        painelBotoes.add(btnExcluir);
        painelBotoes.add(btnAtualizar);
        add(painelBotoes, BorderLayout.SOUTH);

        btnNovo.addActionListener(e -> {
            TelaCadastroMedicamento tela = new TelaCadastroMedicamento(null, this::carregarTabela);
            tela.setVisible(true);
        });

        btnEditar.addActionListener(e -> editarSelecionado());
        btnExcluir.addActionListener(e -> excluirSelecionado());
        btnAtualizar.addActionListener(e -> carregarTabela());

        carregarTabela();
    }

    public void carregarTabela() {
        modeloTabela.setRowCount(0);
        listaMedicamentos = dao.listar();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

        for (Medicamento m : listaMedicamentos) {
            modeloTabela.addRow(new Object[]{
                    m.getId(),
                    m.getNome(),
                    m.getFabricante(),
                    m.getCategoria(),
                    String.format("%.2f", m.getPreco()),
                    m.getQuantidadeEstoque(),
                    sdf.format(m.getDataValidade())
            });
        }
    }

    private void editarSelecionado() {
        int linha = tabela.getSelectedRow();
        if (linha == -1) {
            JOptionPane.showMessageDialog(this, "Selecione um medicamento na tabela para editar.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        Medicamento selecionado = listaMedicamentos.get(linha);
        TelaCadastroMedicamento tela = new TelaCadastroMedicamento(selecionado, this::carregarTabela);
        tela.setVisible(true);
    }

    private void excluirSelecionado() {
        int linha = tabela.getSelectedRow();
        if (linha == -1) {
            JOptionPane.showMessageDialog(this, "Selecione um medicamento na tabela para excluir.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        Medicamento selecionado = listaMedicamentos.get(linha);
        int resposta = JOptionPane.showConfirmDialog(
                this,
                "Deseja realmente excluir o medicamento \"" + selecionado.getNome() + "\"?",
                "Confirmar Exclusão",
                JOptionPane.YES_NO_OPTION
        );

        if (resposta == JOptionPane.YES_OPTION) {
            if (dao.excluir(selecionado.getId())) {
                JOptionPane.showMessageDialog(this, "Medicamento excluído com sucesso!");
                carregarTabela();
            } else {
                JOptionPane.showMessageDialog(this, "Erro ao excluir medicamento.", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
