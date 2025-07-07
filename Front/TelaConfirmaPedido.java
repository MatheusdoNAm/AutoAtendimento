package Front;

import Back.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.util.Map;

public class TelaConfirmaPedido extends JDialog {

    public TelaConfirmaPedido(JFrame owner, Map<Integer, Integer> cart, Estoque stock, Caixa cashControl, ControlePedidos orders) {
        super(owner, "Confirmação do Pedido", true);
        setSize(450, 320);
        setLocationRelativeTo(owner);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);
        setLayout(new BorderLayout());

        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(197,202,196));
        panel.setBorder(new EmptyBorder(15, 15, 15, 15));

        JLabel title = new JLabel("Confirmação do Pedido", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 18));
        panel.add(title, BorderLayout.NORTH);

        // Montar dados da tabela
        String[] columnNames = {"Produto", "Quantidade"};
        Object[][] data = cart.entrySet().stream()
                .filter(e -> e.getValue() > 0)
                .map(e -> {
                    Produto p = stock.getProductsStock().get(e.getKey()).getProduct();
                    return new Object[]{p.getName(), e.getValue()};
                })
                .toArray(Object[][]::new);

        JTable table = new JTable(data, columnNames);
        table.setBackground(new Color(197,202,196));
        table.setEnabled(false);
        table.setShowGrid(false);
        table.setIntercellSpacing(new Dimension(0, 0));
        table.setRowHeight(28);
        table.setFont(new Font("Arial", Font.PLAIN, 14));

        // Estilizar cabeçalho
        JTableHeader header = table.getTableHeader();
        header.setBackground(new Color(0, 86, 179));
        header.setForeground(Color.WHITE);
        header.setFont(new Font("Arial", Font.BOLD, 13));
        header.setReorderingAllowed(false);
        table.setTableHeader(header);

        // Centralizar colunas
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        table.getColumnModel().getColumn(0).setCellRenderer(centerRenderer); // Produto
        table.getColumnModel().getColumn(1).setCellRenderer(centerRenderer); // Quantidade

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBackground(new Color(197,202,196));
        scrollPane.getViewport().setBackground(new Color(197,202,196));
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        panel.add(scrollPane, BorderLayout.CENTER);

        // Total
        double total = cart.entrySet().stream()
                .mapToDouble(e -> stock.getProductsStock().get(e.getKey()).getProduct().getPrice() * e.getValue())
                .sum();

        JLabel totalLabel = new JLabel("Total: R$ " + String.format("%.2f", total), SwingConstants.RIGHT);
        totalLabel.setFont(new Font("Arial", Font.BOLD, 15));
        totalLabel.setBorder(new EmptyBorder(10, 0, 10, 0));
        panel.add(totalLabel, BorderLayout.SOUTH);

        // Botões
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        buttonPanel.setBackground(new Color(197,202,196));

        JButton cancelButton = new JButton("Cancelar");
        cancelButton.setBackground(new Color(220, 53, 69));
        cancelButton.setForeground(Color.WHITE);
        cancelButton.setFocusPainted(false);
        cancelButton.setFont(new Font("Arial", Font.BOLD, 14));
        cancelButton.setBorderPainted(false);
        cancelButton.setFocusPainted(false);

        cancelButton.addActionListener(e ->
        {
            JOptionPane.showMessageDialog(this, "Pedido Cancelado!", "Pedido Cancelado", JOptionPane.INFORMATION_MESSAGE);
            for (Integer code : cart.keySet())
                cart.put(code,0);

            TelaIniciarCompra telaIniciarCompra = new TelaIniciarCompra(stock, cashControl, orders);
            telaIniciarCompra.setVisible(true);
            owner.dispose();
            dispose();
        });

        JButton confirmButton = new JButton("Confirmar Pedido");
        confirmButton.setBackground(new Color(40, 167, 69));
        confirmButton.setForeground(Color.WHITE);
        confirmButton.setFocusPainted(false);
        confirmButton.setFont(new Font("Arial", Font.BOLD, 14));
        confirmButton.setBorderPainted(false);
        confirmButton.setFocusPainted(false);

        confirmButton.addActionListener(e ->
        {
            boolean confirmOrder = true;
            for (Integer codeProduct : cart.keySet())
            {
                if (cart.get(codeProduct) > stock.getQuantityAvaible(codeProduct))
                    confirmOrder = false;
            }
            if(!confirmOrder)
            {
                JOptionPane.showMessageDialog(this, "Estoque insuficiente!", "Pedido Cancelado", JOptionPane.INFORMATION_MESSAGE);
                for (Integer code : cart.keySet())
                    cart.put(code,0);
                dispose();
            }
            else
            {
                JOptionPane.showMessageDialog(this, "Pedido Confirmado!", "Pedido Confirmado", JOptionPane.INFORMATION_MESSAGE);
                System.out.println("Indo para a tela de pagamento");
                Pedido order = new Pedido(cart);
                
                TelaPagamento telaPagamento = new TelaPagamento(owner, cart, stock, cashControl, orders, order);
                dispose();
                telaPagamento.setVisible(true);
            }
        });

        buttonPanel.add(cancelButton);
        buttonPanel.add(confirmButton);

        add(panel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }
}