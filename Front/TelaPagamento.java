package Front;
import Back.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;

public class TelaPagamento extends JDialog
{
    public TelaPagamento(JFrame owner, Map<Integer, Integer> cart, Estoque stock, Caixa cashControl, ControlePedidos orders, Pedido order)
    {
        super(owner, "Tela de Pagamento", true);
        setSize(550, 320);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(owner);
        setResizable(false);

        getContentPane().setBackground(new Color(197, 202, 196));
        setLayout(new BorderLayout());

        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBackground(new Color(197, 202, 196));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        // -- Title Label --
        JLabel titleLabel = new JLabel("Pagamento");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 25));
        titleLabel.setForeground(new Color(40, 40, 40));
        titleLabel.setBackground(new Color(197, 202, 196));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titleLabel.setPreferredSize(new Dimension(500, 40));

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.anchor = GridBagConstraints.NORTH;
        mainPanel.add(titleLabel, gbc);

        gbc.insets = new Insets(0, 10, 0, 10);

        // -- Order Number Label --
        JLabel orderNumberLabel = new JLabel("Pedido " + order.getOrderNumber());
        orderNumberLabel.setFont(new Font("Arial", Font.BOLD, 18));
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.WEST;
        mainPanel.add(orderNumberLabel, gbc);

        // -- Total Itens Label --
        JLabel itensTotalLabel = new JLabel("Itens: " + cart.values().stream().mapToInt(Integer::intValue).sum());
        itensTotalLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        gbc.gridy = 2;
        mainPanel.add(itensTotalLabel, gbc);

        gbc.insets = new Insets(15, 10, 0, 10);

        // -- Total Cash Label --
        double totalOrderValue = cart.entrySet().stream()
                .mapToDouble(entry -> {
                    Produto produto = stock.getProductsStock().get(entry.getKey()).getProduct();
                    return produto.getPrice() * entry.getValue();
                }).sum();

        JLabel itensCashLabel = new JLabel("Total: R$ " + String.format("%.2f", totalOrderValue));
        itensCashLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        gbc.gridy = 3;
        gbc.anchor = GridBagConstraints.EAST;
        mainPanel.add(itensCashLabel, gbc);

        // -- Selection Buttons --
        JPanel selectionPanel = new JPanel(new GridBagLayout());
        selectionPanel.setBackground(new Color(197, 202, 196));

        JRadioButton cashButton = createSelectButton("Dinheiro");

        JRadioButton cardButton = createSelectButton("Cartão");

        JRadioButton pixButton = createSelectButton("Pix");

        cashButton.setBackground(new Color(197, 202, 196));
        cardButton.setBackground(new Color(197, 202, 196));
        pixButton.setBackground(new Color(197, 202, 196));

        ButtonGroup buttonGroup = new ButtonGroup();
        buttonGroup.add(cashButton);
        buttonGroup.add(cardButton);
        buttonGroup.add(pixButton);

        GridBagConstraints gbcSelect = new GridBagConstraints();
        gbcSelect.insets = new Insets(0, 0, 0, 0);
        gbcSelect.gridy = 0;

        gbcSelect.gridx = 0;
        gbcSelect.anchor = GridBagConstraints.WEST;
        selectionPanel.add(cashButton, gbcSelect);

        gbcSelect.gridx = 1;
        selectionPanel.add(cardButton, gbcSelect);

        gbcSelect.gridx = 2;
        selectionPanel.add(pixButton, gbcSelect);

        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.anchor = GridBagConstraints.WEST;
        mainPanel.add(selectionPanel, gbc);

        // -- Buttons --
        JPanel buttonsPanel = new JPanel(new GridBagLayout());
        buttonsPanel.setBackground(new Color(197, 202, 196));

        GridBagConstraints gbcButtons = new GridBagConstraints();
        gbcButtons.gridy = 0;
        gbcButtons.insets = new Insets(10, 10, 10, 10);
        gbcButtons.fill = GridBagConstraints.HORIZONTAL;
        gbcButtons.weighty = 0;

        // Painel Voltar (esquerda)
        JPanel leftPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        leftPanel.setOpaque(false);
        JButton backButton = createCustomButton("Voltar");
        backButton.setPreferredSize(new Dimension(100, 40));
        leftPanel.add(backButton);
        gbcButtons.gridx = 0;
        gbcButtons.weightx = 1.0;
        buttonsPanel.add(leftPanel, gbcButtons);

        backButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                TelaConfirmaPedido telaConfirmaPedido = new TelaConfirmaPedido(owner, cart, stock, cashControl, orders);
                dispose();
                telaConfirmaPedido.setVisible(true);
            }
            
        });

        // Painel Cancelar (centro)
        JPanel centerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        centerPanel.setOpaque(false);
        JButton cancelButton = createCustomButton("Cancelar");
        cancelButton.setBackground(new Color(220, 53, 69));
        cancelButton.setPreferredSize(new Dimension(120, 40));
        centerPanel.add(cancelButton);
        gbcButtons.gridx = 1;
        buttonsPanel.add(centerPanel, gbcButtons);

        cancelButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                TelaIniciarCompra telaIniciarCompra = new TelaIniciarCompra(stock, cashControl, orders);
                telaIniciarCompra.setVisible(true);
                owner.dispose();
                dispose();
            }
            
        });

        // Painel Confirmar (direita)
        JPanel rightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        rightPanel.setOpaque(false);
        JButton confirmButton = createCustomButton("Confirmar Pagamento");
        confirmButton.setBackground(new Color(40, 167, 69));
        confirmButton.setPreferredSize(new Dimension(200, 40));
        rightPanel.add(confirmButton);
        gbcButtons.gridx = 2;
        buttonsPanel.add(rightPanel, gbcButtons);

        confirmButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                if (buttonGroup.getSelection() == null)
                    JOptionPane.showMessageDialog(TelaPagamento.this, "Selecione a Forma de Pagamento!", "Forma de Pagamento não Selecionada", JOptionPane.ERROR_MESSAGE);
                else
                {
                    if (cardButton.isSelected() || pixButton.isSelected())
                    {
                        System.out.println("Tela Final");
                        for (Integer codeProduct : cart.keySet())
                        {
                            stock.removeStock(codeProduct, cart.get(codeProduct));
                        }
                        JOptionPane.showMessageDialog(TelaPagamento.this,
                                "Pedido Confirmado! Aguarde para Retirar",
                                "Pedido Confirmado",
                                JOptionPane.INFORMATION_MESSAGE);
                        orders.newOrder(order);
                        TelaIniciarCompra telaIniciarCompra = new TelaIniciarCompra(stock, cashControl, orders);
                        telaIniciarCompra.setVisible(true);
                        owner.dispose();
                        dispose();
                    }
                    else
                    {
                        TelaPagamentoDinheiro telaPagamentoDinheiro = new TelaPagamentoDinheiro(owner, cart, stock, cashControl, orders, order);
                        dispose();
                        telaPagamentoDinheiro.setVisible(true);
                        System.out.println("Confirmar Notas e Troco");
                    }
                }
            }
            
        });

        // Adiciona o buttonsPanel ao mainPanel
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        mainPanel.add(buttonsPanel, gbc);

        add(mainPanel);
    }

    public JButton createCustomButton(String text)
    {
        JButton button = new JButton(text);

        button.setForeground(Color.WHITE);
        button.setBackground(new Color(0,86,179));
        button.setPreferredSize(new Dimension(150, 40));
        button.setFont(new Font("Arial", Font.BOLD, 15));
        button.setBorderPainted(false);
        button.setFocusPainted(false);

        return button;
    }

    public JRadioButton createSelectButton(String text)
    {
        JRadioButton button = new JRadioButton(text);

        button.setForeground(Color.BLACK);
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setFocusPainted(false);
        button.setBorderPainted(false);

        return button;
    }
}