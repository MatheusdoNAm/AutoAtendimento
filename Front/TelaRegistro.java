package Front;
import Back.*;
import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.text.MaskFormatter;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class TelaRegistro extends JDialog
{
    JTextField codeTextField, nameTextField, priceTextField;
    JFormattedTextField dateTextField;
    String type;

    public TelaRegistro(JFrame owner, Estoque stock)
    {
        super(owner, "Tela Registro", true);
        setSize(480,640);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        getContentPane().setBackground(new Color(197,202,196));
        setLayout(new BorderLayout());

        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBackground(new Color(197,202,196));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        // -- Title Label --
        JLabel titleLabel = new JLabel("Cadastrar Novo Item");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 30));
        titleLabel.setForeground(new Color(40,40,40));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.anchor = GridBagConstraints.CENTER;
        mainPanel.add(titleLabel, gbc);

        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.CENTER;               
        gbc.insets = new Insets(10, 30, 10, 30);       
        gbc.weightx = 1.0;                                    
        gbc.fill = GridBagConstraints.HORIZONTAL;            
        gbc.gridwidth = GridBagConstraints.REMAINDER;        

        // -- Form Panel --
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(new Color(197,202,196));

        GridBagConstraints gbcForm = new GridBagConstraints();
        gbcForm.insets = new Insets(5, 5, 5, 5);

        // -- Code Label --
        JLabel codeLabel = new JLabel("Código:");
        codeLabel.setFont(new Font("Arial", Font.PLAIN, 20));
        gbcForm.gridy = 0;
        gbcForm.anchor = GridBagConstraints.WEST; 
        gbcForm.gridwidth = GridBagConstraints.REMAINDER; 
        gbcForm.fill = GridBagConstraints.HORIZONTAL; 
        gbcForm.weightx = 1.0;
        formPanel.add(codeLabel, gbcForm);

         // -- Code TextField --
        codeTextField = new JTextField();
        codeTextField.setBackground(new Color(172,179,174)); 
        codeTextField.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
        codeTextField.setFont(new Font("Arial", Font.PLAIN, 18));
        codeTextField.setPreferredSize(new Dimension(0, 25));
        gbcForm.gridy = 1;
        formPanel.add(codeTextField, gbcForm);

        gbcForm.insets = new Insets(20, 5, 5, 5);

        // -- Name Label --
        JLabel nameLabel = new JLabel("Nome:");
        nameLabel.setFont(new Font("Arial", Font.PLAIN, 20));
        gbcForm.gridy = 2;
        formPanel.add(nameLabel, gbcForm);

        gbcForm.insets = new Insets(5, 5, 5, 5);

        // -- Name TextField --
        nameTextField = new JTextField();
        nameTextField.setBackground(new Color(172,179,174));
        nameTextField.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
        nameTextField.setFont(new Font("Arial", Font.PLAIN, 18));
        nameTextField.setPreferredSize(new Dimension(0, 25));
        gbcForm.gridy = 3;
        formPanel.add(nameTextField, gbcForm);

        gbcForm.insets = new Insets(20, 5, 5, 5);

        // -- Type Label --
        JLabel typeLabel = new JLabel("Tipo:");
        typeLabel.setFont(new Font("Arial", Font.PLAIN, 20));
        gbcForm.gridy = 4;
        formPanel.add(typeLabel, gbcForm);

        gbcForm.insets = new Insets(5, 5, 5, 5);

        // -- Type JComboBox --
        String[] options = {"Comida", "Bebida", "Sobremesa"};
        JComboBox<String> typeComboBox = new JComboBox<>(options);
        typeComboBox.setBackground(new Color(172,179,174));
        gbcForm.gridy = 5;
        formPanel.add(typeComboBox, gbcForm);

        gbcForm.insets = new Insets(20, 5, 5, 5);

        // -- Price Label --
        JLabel priceLabel = new JLabel("Preço (R$):");
        priceLabel.setFont(new Font("Arial", Font.PLAIN, 20));
        gbcForm.gridy = 6;
        formPanel.add(priceLabel, gbcForm);

        gbcForm.insets = new Insets(5, 5, 5, 5);

        // -- Price TextField --
        priceTextField = new JTextField();
        priceTextField.setBackground(new Color(172,179,174));
        priceTextField.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
        priceTextField.setFont(new Font("Arial", Font.PLAIN, 18));
        priceTextField.setPreferredSize(new Dimension(0, 25));
        gbcForm.gridy = 7;
        formPanel.add(priceTextField, gbcForm);

        gbcForm.insets = new Insets(20, 5, 5, 5);

        // -- Date Label --
        JLabel dateLabel = new JLabel("Data de Validade:");
        dateLabel.setFont(new Font("Arial", Font.PLAIN, 20));
        gbcForm.gridy = 8;
        formPanel.add(dateLabel, gbcForm);

        gbcForm.insets = new Insets(5, 5, 5, 5);

        // -- Date Field --
        try {
            MaskFormatter dateMask = new MaskFormatter("##/##/####");
            dateMask.setPlaceholderCharacter('_');
            dateTextField = new JFormattedTextField(dateMask);
            dateTextField.setFont(new Font("Arial", Font.PLAIN, 18));
            dateTextField.setEnabled(true);
            dateTextField.setBackground(new Color(172,179,174));
            dateTextField.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
            dateTextField.setPreferredSize(new Dimension(0, 25));
            dateTextField.setFocusLostBehavior(JFormattedTextField.PERSIST);
            gbcForm.gridy = 9;
            formPanel.add(dateTextField, gbcForm);
        } catch (Exception e) {
            e.printStackTrace();
        }

        mainPanel.add(formPanel, gbc);

        // -- Save Button --
        JButton saveButton = createCustomButton("Salvar Item");
        saveButton.setBackground(new Color(40, 167, 69));

        saveButton.addActionListener(new ActionListener() 
        {
            public void actionPerformed(ActionEvent e)
            {   
                if (codeTextField.getText().isEmpty() || nameTextField.getText().isEmpty() || priceTextField.getText().isEmpty())
                {
                    JOptionPane.showMessageDialog(TelaRegistro.this, "Preencha os Campos Obrigatórios", "Erro Cadastro", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                int code;
                double price;

                try {
                    code = Integer.parseInt(codeTextField.getText());
                } catch (NumberFormatException nfe) {
                    JOptionPane.showMessageDialog(TelaRegistro.this, "O código deve ser um número inteiro!", "Erro de Formato", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                try {
                    price = Double.parseDouble(priceTextField.getText().replace(",", "."));
                } catch (NumberFormatException nfe) {
                    JOptionPane.showMessageDialog(TelaRegistro.this, "O preço deve ser um número válido (ex: 10.50)!", "Erro de Formato", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                String name = nameTextField.getText();
                String type = (String) typeComboBox.getSelectedItem();
                String validityText = dateTextField.getText().trim();
                LocalDate date = null;

                if (!dateTextField.getText().isEmpty() && !dateTextField.getText().equals("__/__/____"))
                {
                    try 
                    {
                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                        date = LocalDate.parse(validityText, formatter);

                        if (date.isBefore(LocalDate.now()))
                        {
                            JOptionPane.showMessageDialog(TelaRegistro.this, 
                                    "A Data de Validade não pode ser no passado!",
                                    "Data Inválida",
                                    JOptionPane.WARNING_MESSAGE);
                                    dateTextField.setText("");
                            return;
                        }
                    }
                    catch (DateTimeParseException dtpe)
                    {
                        JOptionPane.showMessageDialog(TelaRegistro.this, 
                                    "Formato de Data Invalido. Use DD/MM/AAAA",
                                    "Erro de Formato de Data",
                                    JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                }

                boolean sucesso;

                if(dateTextField.getText().isEmpty() || dateTextField.getText().equals("__/__/____"))
                    sucesso = stock.registerProduct(new Produto(code, name, type, price));
                else
                    sucesso = stock.registerProduct(new Produto(code, name, type, price, date));

                if (!sucesso)
                {
                    JOptionPane.showMessageDialog(TelaRegistro.this, 
                            "Código já registrado",
                            "Item repetido",
                            JOptionPane.ERROR_MESSAGE);
                }
                else
                {
                    JOptionPane.showMessageDialog(TelaRegistro.this, "Produto cadastrado com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                    codeTextField.setText("");
                    nameTextField.setText("");
                    priceTextField.setText("");
                    dateTextField.setText("");
                }

                stock.listarProdutos();
            }
        });
        // -- Back Button --
        JButton backButton = createCustomButton("Voltar");
        backButton.setBackground(new Color(220, 53, 69));
        backButton.addActionListener(new ActionListener() 
        {
            public void actionPerformed(ActionEvent e)
            {
                dispose();
            }
        });

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        buttonPanel.setBackground(new Color(197,202,196));
        buttonPanel.add(saveButton);
        buttonPanel.add(backButton);

        gbcForm.gridy = 10;
        gbcForm.gridwidth = GridBagConstraints.REMAINDER;
        gbcForm.insets = new Insets(30, 5, 5, 5); 
        formPanel.add(buttonPanel, gbcForm);

        add(mainPanel);
    }

    public JButton createCustomButton (String text)
    {
        JButton button = new JButton(text);

        button.setForeground(Color.WHITE);
        button.setBackground(new Color(0,86,179));
        button.setFont(new Font("Arial", Font.BOLD, 16));
        button.setFocusPainted(false);
        button.setPreferredSize(new Dimension(150, 40));
        button.setBorderPainted(false);
        button.setFocusPainted(false);

        return button;
    }
}
