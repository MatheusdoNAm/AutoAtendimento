package Front;
import Back.*;
import javax.swing.*;
import javax.swing.border.BevelBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;

public class TelaPagamentoDinheiro extends JDialog
{
    private JTextField bill100TextField, bill50TextField, bill20TextField, bill10TextField,
                    bill5TextField, bill2TextField, coin1TextField, coin50TextField, coin25TextField,
                    coin10TextField, coin5TextField;

    public TelaPagamentoDinheiro(JFrame owner, Map<Integer, Integer> cart, Estoque stock, Caixa cashControl, ControlePedidos orders, Pedido order)
    {
        super(owner, "Pagamento com Dinheiro", true);
        setSize(480,450);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        getContentPane().setBackground(new Color(197,202,196));
        setLayout(new BorderLayout());

        JPanel mainPanel = new JPanel (new GridBagLayout());
        mainPanel.setBackground(new Color(197,202,196));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(0, 0, 5, 0);

        // -- Title Label --
        JLabel titleLabel = new JLabel("Pagamento com Dinheiro");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 30));
        titleLabel.setForeground(new Color(40,40,40));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.anchor = GridBagConstraints.CENTER;
        mainPanel.add(titleLabel, gbc);

        // -- Subtitle Label --
        double totalOrderValue = cart.entrySet().stream()
                .mapToDouble(entry -> {
                    Produto produto = stock.getProductsStock().get(entry.getKey()).getProduct();
                    return produto.getPrice() * entry.getValue();
                }).sum();

        JLabel subtitleLabel = new JLabel("Valor do Pedido: R$ " + String.format("%.2f", totalOrderValue));
        subtitleLabel.setFont(new Font("Arial", Font.BOLD, 15));
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.WEST;
        mainPanel.add(subtitleLabel, gbc);

        gbc.insets = new Insets(20, 0, 0, 0);

        // -- Add Cash Label --
        JLabel addCashLabel = new JLabel("Selecionar Cédulas Usadas");
        addCashLabel.setFont(new Font("Arial", Font.BOLD, 17));
        gbc.gridy = 2;
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.anchor = GridBagConstraints.CENTER;
        mainPanel.add(addCashLabel, gbc);

        JPanel insertsPanel = new JPanel (new GridBagLayout());
        insertsPanel.setBackground(new Color(197,202,196));

        gbc.insets = new Insets(0, 0, 0, 0);

        // -- 100 Bill --
        JLabel bill100Label = new JLabel("R$ 100,00: ");
        bill100Label.setFont(new Font("Arial", Font.BOLD, 14));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.CENTER;
        insertsPanel.add(bill100Label, gbc);

        bill100TextField = createTextField();
        bill100TextField.setFont(new Font("Arial", Font.BOLD, 14));
        gbc.gridx = 1;
        insertsPanel.add(bill100TextField, gbc);
    
        gbc.insets = new Insets(10, 10, 10, 0);
        
        // -- 50 Bill --
        JLabel bill50Label = new JLabel("R$ 50,00: ");
        bill50Label.setFont(new Font("Arial", Font.BOLD, 14));
        gbc.gridx = 2;
        insertsPanel.add(bill50Label, gbc);

        gbc.insets = new Insets(10, 0, 10, 0);

        bill50TextField = createTextField();
        bill50TextField.setFont(new Font("Arial", Font.BOLD, 14));
        gbc.gridx = 3;
        insertsPanel.add(bill50TextField, gbc);

        gbc.insets = new Insets(10, 10, 10, 0);
        
        // -- 20 Bill --
        JLabel bill20Label = new JLabel("R$ 20,00: ");
        bill20Label.setFont(new Font("Arial", Font.BOLD, 14));
        gbc.gridx = 4;
        insertsPanel.add(bill20Label, gbc);

        gbc.insets = new Insets(10, 0, 10, 0);

        bill20TextField = createTextField();
        bill20TextField.setFont(new Font("Arial", Font.BOLD, 14));
        gbc.gridx = 5;
        insertsPanel.add(bill20TextField, gbc);

        // -- 10 Bill --
        JLabel bill10Label = new JLabel("R$ 10,00: ");
        bill10Label.setFont(new Font("Arial", Font.BOLD, 14));
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.CENTER;
        insertsPanel.add(bill10Label, gbc);

        gbc.insets = new Insets(10, 0, 10, 0);

        bill10TextField = createTextField();
        bill10TextField.setFont(new Font("Arial", Font.BOLD, 14));
        gbc.gridx = 1;
        insertsPanel.add(bill10TextField, gbc);
    
        gbc.insets = new Insets(10, 10, 10, 0);
        
        // -- 5 Bill --
        JLabel bill5Label = new JLabel("R$ 5,00: ");
        bill5Label.setFont(new Font("Arial", Font.BOLD, 14));
        gbc.gridx = 2;
        insertsPanel.add(bill5Label, gbc);

        gbc.insets = new Insets(10, 0, 10, 0);

        bill5TextField = createTextField();
        bill5TextField.setFont(new Font("Arial", Font.BOLD, 14));
        gbc.gridx = 3;
        insertsPanel.add(bill5TextField, gbc);

        gbc.insets = new Insets(10, 10, 10, 0);
        
        // -- 2 Coin --
        JLabel bill2Label = new JLabel("R$ 2,00: ");
        bill2Label.setFont(new Font("Arial", Font.BOLD, 14));
        gbc.gridx = 4;
        insertsPanel.add(bill2Label, gbc);

        gbc.insets = new Insets(10, 0, 10, 0);

        bill2TextField = createTextField();
        bill2TextField.setFont(new Font("Arial", Font.BOLD, 14));
        gbc.gridx = 5;
        insertsPanel.add(bill2TextField, gbc);

        gbc.insets = new Insets(10, 0, 10, 0);

        // -- 1 Coin --
        JLabel coin1Label = new JLabel("R$ 1,00: ");
        coin1Label.setFont(new Font("Arial", Font.BOLD, 14));
        gbc.gridx = 0;
        gbc.gridy = 2;
        insertsPanel.add(coin1Label, gbc);

        coin1TextField = createTextField();
        coin1TextField.setFont(new Font("Arial", Font.BOLD, 14));
        gbc.gridx = 1;
        insertsPanel.add(coin1TextField, gbc);

        // -- 0.5 Coin --
        JLabel coin50Label = new JLabel("R$ 0,50: ");
        coin50Label.setFont(new Font("Arial", Font.BOLD, 14));
        gbc.gridx = 2;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.CENTER;
        insertsPanel.add(coin50Label, gbc);

        gbc.insets = new Insets(10, 0, 10, 0);

        coin50TextField = createTextField();
        coin50TextField.setFont(new Font("Arial", Font.BOLD, 14));
        gbc.gridx = 3;
        insertsPanel.add(coin50TextField, gbc);
    
        gbc.insets = new Insets(10, 10, 10, 0);
        
        // -- 0.25 Coin --
        JLabel coin25Label = new JLabel("R$ 0,25: ");
        coin25Label.setFont(new Font("Arial", Font.BOLD, 14));
        gbc.gridx = 4;
        insertsPanel.add(coin25Label, gbc);

        gbc.insets = new Insets(10, 0, 10, 0);

        coin25TextField = createTextField();
        coin25TextField.setFont(new Font("Arial", Font.BOLD, 14));
        gbc.gridx = 5;
        insertsPanel.add(coin25TextField, gbc);

        gbc.insets = new Insets(10, 10, 10, 0);
        
        // -- 0.1 Coin --
        JLabel coin10Label = new JLabel("R$ 0,10: ");
        coin10Label.setFont(new Font("Arial", Font.BOLD, 14));
        gbc.gridy = 3;
        gbc.gridx = 0;
        insertsPanel.add(coin10Label, gbc);

        gbc.insets = new Insets(10, 0, 10, 0);

        coin10TextField = createTextField();
        coin10TextField.setFont(new Font("Arial", Font.BOLD, 14));
        gbc.gridx = 1;
        insertsPanel.add(coin10TextField, gbc);

        // -- 0.05 Coin --
        JLabel coin5Label = new JLabel("R$ 0,05: ");
        coin5Label.setFont(new Font("Arial", Font.BOLD, 14));
        gbc.gridx = 2;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.CENTER;
        insertsPanel.add(coin5Label, gbc);

        gbc.insets = new Insets(10, 0, 10, 0);

        coin5TextField = createTextField();
        coin5TextField.setFont(new Font("Arial", Font.BOLD, 14));
        gbc.gridx = 3;
        insertsPanel.add(coin5TextField, gbc);

        gbc.insets = new Insets(5, 0, 0, 0);

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.anchor = GridBagConstraints.WEST;
        mainPanel.add(insertsPanel, gbc);

        // -- Confirm Button --
        gbc.insets = new Insets(10, 0, 25, 0);

        JButton confirmButton = createCustomButton("Realizar Pagamento");
        gbc.gridy = 4;
        mainPanel.add(confirmButton, gbc);

        confirmButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed (ActionEvent e)
            {
                System.out.println("Realizando Pagamento");
                double totalCashInsered = 0;
                try {
                    totalCashInsered += 100.0 * Integer.parseInt(bill100TextField.getText());
                    totalCashInsered += 50.0  * Integer.parseInt(bill50TextField.getText());
                    totalCashInsered += 20.0  * Integer.parseInt(bill20TextField.getText());
                    totalCashInsered += 10.0  * Integer.parseInt(bill10TextField.getText());
                    totalCashInsered += 5.0   * Integer.parseInt(bill5TextField.getText());
                    totalCashInsered += 2.0   * Integer.parseInt(bill2TextField.getText());
                    totalCashInsered += 1.0   * Integer.parseInt(coin1TextField.getText());
                    totalCashInsered += 0.5   * Integer.parseInt(coin50TextField.getText());
                    totalCashInsered += 0.25  * Integer.parseInt(coin25TextField.getText());
                    totalCashInsered += 0.10  * Integer.parseInt(coin10TextField.getText());
                    totalCashInsered += 0.05  * Integer.parseInt(coin5TextField.getText());

                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(TelaPagamentoDinheiro.this,
                        "Por favor, preencha todos os campos com números inteiros válidos.",
                        "Erro de Entrada",
                        JOptionPane.ERROR_MESSAGE);
                    return;
                }

                if(totalOrderValue > totalCashInsered)
                {
                    JOptionPane.showMessageDialog(TelaPagamentoDinheiro.this,
                        "Valor insuficiente! Falta R$ " + String.format("%.2f", (totalOrderValue - totalCashInsered)),
                        "Pagamento Incompleto",
                        JOptionPane.WARNING_MESSAGE);
                    return;
                }
                else
                {
                    if (cashControl.calculateChange(totalCashInsered - totalOrderValue) == null) {
                        JOptionPane.showMessageDialog(TelaPagamentoDinheiro.this,
                                "Troco Insuficiente.",
                                "Troco Insuficiente.",
                                JOptionPane.ERROR_MESSAGE);
                    } 
                    else
                    {
                        try {
                            cashControl.addCash(100.0, Integer.parseInt(bill100TextField.getText()));
                            cashControl.addCash(50.0, Integer.parseInt(bill50TextField.getText()));
                            cashControl.addCash(20.0, Integer.parseInt(bill20TextField.getText()));
                            cashControl.addCash(10.0, Integer.parseInt(bill10TextField.getText()));
                            cashControl.addCash(5.0, Integer.parseInt(bill5TextField.getText()));
                            cashControl.addCash(2.0, Integer.parseInt(bill2TextField.getText()));
                            cashControl.addCash(1.0, Integer.parseInt(coin1TextField.getText()));
                            cashControl.addCash(0.5, Integer.parseInt(coin50TextField.getText()));
                            cashControl.addCash(0.25, Integer.parseInt(coin25TextField.getText()));
                            cashControl.addCash(0.10, Integer.parseInt(coin10TextField.getText()));
                            cashControl.addCash(0.05, Integer.parseInt(coin5TextField.getText()));
                        } catch (NumberFormatException ex) {
                            JOptionPane.showMessageDialog(TelaPagamentoDinheiro.this,
                                    "Por favor, preencha todos os campos com números inteiros válidos.",
                                    "Erro de Entrada",
                                    JOptionPane.ERROR_MESSAGE);
                            return;
                        }

                        for (Integer codeProduct : cart.keySet())
                        {
                            stock.removeStock(codeProduct, cart.get(codeProduct));
                        }
                        JOptionPane.showMessageDialog(TelaPagamentoDinheiro.this,
                                "Pedido Confirmado! Troco: "+ String.format("%.2f", totalCashInsered - totalOrderValue) + ". Aguarde para Retirar",
                                "Pedido Confirmado",
                                JOptionPane.INFORMATION_MESSAGE);
                        orders.newOrder(order);
                        TelaIniciarCompra telaIniciarCompra = new TelaIniciarCompra(stock, cashControl, orders);
                        telaIniciarCompra.setVisible(true);
                        owner.dispose();
                        dispose();   
                    }
                }
            }
            
        });
            
        // -- Back Button --
        JButton backButton = createCustomButton("Voltar");
        backButton.setPreferredSize(new Dimension(80, 30));
        gbc.anchor = GridBagConstraints.EAST;
        gbc.gridx = 2;
        mainPanel.add(backButton, gbc);

        backButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                System.out.println("Voltando");
                TelaAdmin telaAdmin = new TelaAdmin(stock, cashControl, orders);
                telaAdmin.setVisible(true);
                dispose();
            }
            
        });

        add(mainPanel);
    }

    public JTextField createTextField()
    {
        JTextField textField = new JTextField("0");
        textField.setBackground(new Color(172,179,174)); 
        textField.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
        textField.setPreferredSize(new Dimension(50, 25));

        return textField;
    }

    public JButton createCustomButton (String text)
    {
        JButton button = new JButton(text);

        button.setForeground(Color.WHITE);
        button.setBackground(new Color(0,86,179));
        button.setFont(new Font("Arial", Font.BOLD, 13));
        button.setFocusPainted(false);
        button.setPreferredSize(new Dimension(160, 30));
        button.setBorderPainted(false);
        button.setFocusPainted(false);

        return button;
    } 
}
