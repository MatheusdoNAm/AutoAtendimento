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
    private JTextField codeTextField, nameTextField, priceTextField;
    private JFormattedTextField dateTextField;

    public TelaRegistro (JFrame owner, Stock stock)
    {
        super(owner, "Cadastrar Item", true);
        setSize(480,640);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        getContentPane().setBackground(new Color(172,179,174));
        setLayout(new BorderLayout());

        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBackground(new Color(172,179,174));

        GridBagConstraints gbcMain = new GridBagConstraints(); 
        gbcMain.insets = new Insets(10, 10, 10, 10); 

        // -- Title Label --
        JLabel titleLabel = new JLabel("Cadastrar Novo Item");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 30));
        titleLabel.setForeground(new Color(40,40,40));
        gbcMain.gridx = 0;
        gbcMain.gridy = 0;
        gbcMain.gridwidth = GridBagConstraints.REMAINDER;
        gbcMain.anchor = GridBagConstraints.CENTER;
        mainPanel.add(titleLabel, gbcMain);

        gbcMain.gridy = 1;
        gbcMain.anchor = GridBagConstraints.CENTER;               
        gbcMain.insets = new Insets(10, 30, 10, 30);       
        gbcMain.weightx = 1.0;                                    
        gbcMain.fill = GridBagConstraints.HORIZONTAL;            
        gbcMain.gridwidth = GridBagConstraints.REMAINDER;        

        // -- Form Panel --
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(new Color(172,179,174));

        GridBagConstraints gbcItem = new GridBagConstraints();
        gbcItem.insets = new Insets(5, 5, 5, 5);

        // -- Code Label --
        JLabel codeLabel = new JLabel("Código:");
        codeLabel.setFont(new Font("Arial", Font.PLAIN, 20));
        gbcItem.gridy = 0;
        gbcItem.anchor = GridBagConstraints.WEST; 
        gbcItem.gridwidth = GridBagConstraints.REMAINDER; 
        gbcItem.fill = GridBagConstraints.HORIZONTAL; 
        gbcItem.weightx = 1.0;
        formPanel.add(codeLabel, gbcItem);

        // -- Code TextField --
        codeTextField = new JTextField();
        codeTextField.setBackground(new Color(172,179,174)); 
        codeTextField.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
        codeTextField.setFont(new Font("Arial", Font.PLAIN, 18));
        codeTextField.setPreferredSize(new Dimension(0, 25));
        gbcItem.gridy = 1;
        formPanel.add(codeTextField, gbcItem);

        gbcItem.insets = new Insets(20, 5, 5, 5);

        // -- Name Label --
        JLabel nameLabel = new JLabel("Nome:");
        nameLabel.setFont(new Font("Arial", Font.PLAIN, 20));
        gbcItem.gridy = 2;
        formPanel.add(nameLabel, gbcItem);

        gbcItem.insets = new Insets(5, 5, 5, 5);

        // -- Name TextField --
        nameTextField = new JTextField();
        nameTextField.setBackground(new Color(172,179,174));
        nameTextField.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
        nameTextField.setFont(new Font("Arial", Font.PLAIN, 18));
        nameTextField.setPreferredSize(new Dimension(0, 25));
        gbcItem.gridy = 3;
        formPanel.add(nameTextField, gbcItem);

        gbcItem.insets = new Insets(20, 5, 5, 5);

        // -- Price Label --
        JLabel priceLabel = new JLabel("Preço (R$):");
        priceLabel.setFont(new Font("Arial", Font.PLAIN, 20));
        gbcItem.gridy = 4;
        formPanel.add(priceLabel, gbcItem);

        gbcItem.insets = new Insets(5, 5, 5, 5);

        // -- Price TextField --
        JTextField priceTextField = new JTextField();
        priceTextField.setBackground(new Color(172,179,174));
        priceTextField.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
        priceTextField.setFont(new Font("Arial", Font.PLAIN, 18));
        priceTextField.setPreferredSize(new Dimension(0, 25));
        gbcItem.gridy = 5;
        formPanel.add(priceTextField, gbcItem);

        gbcItem.insets = new Insets(20, 5, 5, 5);

        // -- Date Label --
        JLabel dateLabel = new JLabel("Data de Validade:");
        dateLabel.setFont(new Font("Arial", Font.PLAIN, 20));
        gbcItem.gridy = 6;
        formPanel.add(dateLabel, gbcItem);

        gbcItem.insets = new Insets(5, 5, 5, 5);


        // -- Date Field (Formatted)
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
            gbcItem.gridy = 7;
            formPanel.add(dateTextField, gbcItem);
        } catch (Exception e) {
            e.printStackTrace();
        }

        mainPanel.add(formPanel, gbcMain);
        
        // -- Save Button --
        JButton saveButton = createCustomButton("Salvar Item");
        saveButton.setBackground(new Color(40, 167, 69));

        saveButton.addActionListener(new ActionListener() 
        {
            public void actionPerformed(ActionEvent e)
            {
                if (codeTextField.getText().isEmpty() || nameTextField.getText().isEmpty() || priceTextField.getText().isEmpty())
                {
                    JOptionPane.showMessageDialog(TelaRegistro.this, "Preencha todos os campos obrigatórios!", "Falha", JOptionPane.ERROR_MESSAGE);
                }
                else
                {
                    int code = Integer.parseInt(codeTextField.getText());
                    String name = nameTextField.getText();
                    double price = Double.parseDouble((priceTextField.getText()).replace(",", "."));
                    String validityText = dateTextField.getText().trim();
                    LocalDate validityDate = null;

                    if (!dateTextField.getText().isEmpty() && !dateTextField.getText().equals("__/__/____"))
                    {
                        try {
                            // Define o formato esperado para a data
                            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                            validityDate = LocalDate.parse(validityText, formatter);

                            // Validação adicional: Data de validade não pode ser no passado
                            if (validityDate.isBefore(LocalDate.now())) {
                                JOptionPane.showMessageDialog(TelaRegistro.this,
                                        "A data de validade não pode ser no passado.",
                                        "Data Inválida",
                                        JOptionPane.WARNING_MESSAGE);
                                        dateTextField.setText("");

                                return;
                            }
                        } catch (DateTimeParseException dtpe) {
                            JOptionPane.showMessageDialog(TelaRegistro.this,
                                    "Formato de data inválido. Use DD/MM/AAAA.",
                                    "Erro de Data",
                                    JOptionPane.ERROR_MESSAGE);
                            return; 
                        }
                    }
                    
                    try 
                    {
                        if (dateTextField.getText().isEmpty()) 
                        {
                            stock.registerProduct(code, name, price);
                        }
                        else 
                        {
                            stock.registerProduct(code, name, price, validityDate);
                        }
                        JOptionPane.showMessageDialog(TelaRegistro.this, "Item cadastrado com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
  
                        codeTextField.setText("");
                        nameTextField.setText("");
                        priceTextField.setText("");
                        dateTextField.setText("");
                    } 
                    catch (IllegalArgumentException ex)
                    {
                       JOptionPane.showMessageDialog(TelaRegistro.this, ex.getMessage(), "Erro ao cadastrar", JOptionPane.ERROR_MESSAGE);
                    }            
                }
            }
        });

        // -- Cancel Button --
        JButton cancelButton = createCustomButton("Cancelar");
        cancelButton.setBackground(new Color(220, 53, 69));
        
        cancelButton.addActionListener(new ActionListener() 
        {
            public void actionPerformed(ActionEvent e)
            {
                dispose();
            }
        });

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        buttonPanel.setBackground(new Color(172,179,174));
        buttonPanel.add(saveButton);
        buttonPanel.add(cancelButton);

        gbcItem.gridy = 8;
        gbcItem.gridwidth = GridBagConstraints.REMAINDER;
        gbcItem.insets = new Insets(30, 5, 5, 5); 
        formPanel.add(buttonPanel, gbcItem);
        
        add(mainPanel);
    }

    public JButton createCustomButton (String text)
    {
        JButton button = new JButton(text);

        button.setForeground(Color.WHITE);
        button.setFont(new Font("Arial", Font.BOLD, 16));
        button.setFocusPainted(false);
        button.setPreferredSize(new Dimension(150, 40));
        button.setBorderPainted(false);
        button.setFocusPainted(false);

        return button;
    }
}
