/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package view;

import model.Client;
import model.ClientDAO;

import javax.swing.*;
import javax.swing.text.MaskFormatter;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.List;
/**
 *
 * @author enzoc
 */

/**
 * JFrame que exibe o formulário de cadastro e lista com filtro.
 */

public class ClientForm extends JFrame {
     // Campos do formulário
    private JTextField nameField;
    private JFormattedTextField cpfField, phoneField;
    private JTextField emailField;

    // Botões de ação
    private JButton addButton, searchButton, updateButton, deleteButton, filterButton;

    // Filtro e tabela
    private JTextField filterField;
    private JTable filterTable;
    
     // DAO de acesso a dados
    private ClientDAO dao = new ClientDAO();

    
    public ClientForm() {
        super("Cadastro de Clientes");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(600, 500);
        setLocationRelativeTo(null);

        initComponents();  // monta UI
    }
     /** Constrói e posiciona todos os componentes na tela. */
    private void initComponents() {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints()
;       gbc.insets = new Insets(5,5,5,5);
        gbc.fill   = GridBagConstraints.HORIZONTAL;

        // Máscaras para CPF e Telefone
        try {
            MaskFormatter cpfMask   = new MaskFormatter("###.###.###-##");
            MaskFormatter phoneMask = new MaskFormatter("(##)####-####");
            cpfMask.setPlaceholderCharacter('_');
            phoneMask.setPlaceholderCharacter('_');
            cpfField   = new JFormattedTextField(cpfMask);
            phoneField = new JFormattedTextField(phoneMask);
        } catch (ParseException e) {
            // fallback sem máscara
            cpfField   = new JFormattedTextField();
            phoneField = new JFormattedTextField();
        }

        nameField  = new JTextField();
        emailField = new JTextField();
        
         // Botões
        addButton    = new JButton("Adicionar");
        searchButton = new JButton("Buscar (CPF)");
        updateButton = new JButton("Atualizar");
        deleteButton = new JButton("Excluir");
        filterButton = new JButton("Filtrar Nome");

        // Campo de filtro
        filterField = new JTextField();

        // Tabela de resultados do filtro
        filterTable = new JTable(new DefaultTableModel(
            new Object[]{"ID","Nome","CPF","Telefone","Email"}, 0
        ));
        JScrollPane tableScroll = new JScrollPane(filterTable);
        tableScroll.setPreferredSize(new Dimension(550, 200));
        
        // Layout do formulário
        gbc.gridx = 0; gbc.gridy = 0; panel.add(new JLabel("Nome:"), gbc);
        gbc.gridx = 1;              panel.add(nameField, gbc);

        gbc.gridx = 0; gbc.gridy = 1; panel.add(new JLabel("CPF:"), gbc);
        gbc.gridx = 1;              panel.add(cpfField, gbc);

        gbc.gridx = 0; gbc.gridy = 2; panel.add(new JLabel("Telefone:"), gbc);
        gbc.gridx = 1;              panel.add(phoneField, gbc);

        gbc.gridx = 0; gbc.gridy = 3; panel.add(new JLabel("Email:"), gbc);
        gbc.gridx = 1;              panel.add(emailField, gbc);

        // Botões de CRUD
        gbc.gridx = 0; gbc.gridy = 4; panel.add(addButton, gbc);
        gbc.gridx = 1;              panel.add(searchButton, gbc);
        gbc.gridx = 0; gbc.gridy = 5; panel.add(updateButton, gbc);
        gbc.gridx = 1;              panel.add(deleteButton, gbc);

        // Filtro de busca
        gbc.gridx = 0; gbc.gridy = 6; panel.add(new JLabel("Filtro Nome:"), gbc);
        gbc.gridx = 1;              panel.add(filterField, gbc);
        gbc.gridx = 1; gbc.gridy = 7; panel.add(filterButton, gbc);

        // Tabela
        gbc.gridx = 0; gbc.gridy = 8; gbc.gridwidth = 2;
        panel.add(tableScroll, gbc);

        add(panel);

        // Associations: vincula ações aos métodos
        addButton.addActionListener(e -> addClient());
        searchButton.addActionListener(e -> searchClientByCPF());
        updateButton.addActionListener(e -> updateClient());
        deleteButton.addActionListener(e -> deleteClient());
        filterButton.addActionListener(e -> filterByName());

        // Clique na tabela carrega dados no formulário
        filterTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = filterTable.getSelectedRow();
                if (row >= 0) {
                    nameField.setText(filterTable.getValueAt(row, 1).toString());
                    cpfField .setText(filterTable.getValueAt(row, 2).toString());
                    phoneField.setText(filterTable.getValueAt(row, 3).toString());
                    emailField.setText(filterTable.getValueAt(row, 4).toString());
                }
            }
        });
    }
    /** Adiciona novo cliente após validações e confirmação. */
    private void addClient() {
        String name  = nameField.getText().trim();
        String cpf   = cpfField.getText().trim();
        String phone = phoneField.getText().trim();
        String email = emailField.getText().trim();

        // Validação básica
        if (name.isEmpty() || cpf.contains("_")) {
            JOptionPane.showMessageDialog(this, "Preencha Nome e CPF corretamente.");
            return;
        }
        if (!email.matches("^.+@.+\\..+$")) {
            JOptionPane.showMessageDialog(this, "Informe um e-mail válido.");
            return;
        }

        // Confirmação da ação
        int confirm = JOptionPane.showConfirmDialog(
            this,
            "Confirmar cadastro do cliente?",
            "Confirmar",
            JOptionPane.YES_NO_OPTION
        );
        if (confirm != JOptionPane.YES_OPTION) {
            return;  // usuário cancelou
        }

        try {
            dao.addClient(new Client(0, name, cpf, phone, email));
            JOptionPane.showMessageDialog(this, "Cliente adicionado com sucesso!");
            clearFields();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Erro ao adicionar: " + ex.getMessage());
        }
    }
    /** Busca cliente pelo CPF exato e preenche o formulário. */
    private void searchClientByCPF() {
        String cpf = cpfField.getText().trim();
        if (cpf.contains("_")) {
            JOptionPane.showMessageDialog(this, "Informe um CPF completo para buscar.");
            return;
        }
        try {
            Client c = dao.getClientByCPF(cpf);
            if (c != null) {
                nameField .setText(c.getName());
                phoneField.setText(c.getPhone());
                emailField.setText(c.getEmail());
            } else {
                JOptionPane.showMessageDialog(this, "Cliente não encontrado.");
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Erro na busca: " + ex.getMessage());
        }
    }
    /** Atualiza cliente após confirmação. */
    private void updateClient() {
        String cpf   = cpfField.getText().trim();
        if (cpf.contains("_")) {
            JOptionPane.showMessageDialog(this, "CPF inválido para atualização.");
            return;
        }
        // Confirmação
        int confirm = JOptionPane.showConfirmDialog(
            this,
            "Confirmar atualização do cliente?",
            "Confirmar",
            JOptionPane.YES_NO_OPTION
        );
        if (confirm != JOptionPane.YES_OPTION) {
            return;
        }

        try {
            dao.updateClient(new Client(
                0,
                nameField.getText().trim(),
                cpf,
                phoneField.getText().trim(),
                emailField.getText().trim()
            ));
            JOptionPane.showMessageDialog(this, "Cliente atualizado!");
            clearFields();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Erro ao atualizar: " + ex.getMessage());
        }
    }
    /** Exclui cliente após confirmação. */
    private void deleteClient() {
        String cpf = cpfField.getText().trim();
        if (cpf.contains("_")) {
            JOptionPane.showMessageDialog(this, "CPF inválido para exclusão.");
            return;
        }
        // Confirmação
        int confirm = JOptionPane.showConfirmDialog(
            this,
            "Deseja realmente excluir este cliente?",
            "Confirmação de Exclusão",
            JOptionPane.YES_NO_OPTION
        );
        if (confirm != JOptionPane.YES_OPTION) {
            return;
        }

        try {
            dao.deleteClient(cpf);
            JOptionPane.showMessageDialog(this, "Cliente excluído!");
            clearFields();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Erro ao excluir: " + ex.getMessage());
        }
    }
     /** Aplica filtro de nome e atualiza tabela. */
    private void filterByName() {
        String filter = filterField.getText().trim();
        DefaultTableModel model = (DefaultTableModel) filterTable.getModel();
        model.setRowCount(0);  // limpa tabela

        try {
            List<Client> list = dao.getClientsByName(filter);
            for (Client c : list) {
                model.addRow(new Object[]{
                    c.getId(), c.getName(), c.getCpf(), c.getPhone(), c.getEmail()
                });
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Erro no filtro: " + ex.getMessage());
        }
    }
    
    /** Limpa todos os campos do formulário. */
    private void clearFields() {
        nameField.setText("");
        cpfField .setValue(null);
        phoneField.setValue(null);
        emailField .setText("");
    }

    public static void main(String[] args) {
        // Garante que a interface seja criada na thread correta
        SwingUtilities.invokeLater(() -> new ClientForm().setVisible(true));
    }
}
