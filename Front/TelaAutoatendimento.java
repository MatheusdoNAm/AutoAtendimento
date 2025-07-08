package Front;

import Back.*;
import javax.swing.*;
import java.util.List;
import java.util.Arrays;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Tela de Autoatendimento da Cantina.
 *
 * A classe {@link TelaAutoatendimento} representa a interface onde os usuários
 * podem selecionar produtos do estoque para realizar uma compra. Os produtos são
 * exibidos por categoria, e o usuário pode adicionar ou remover quantidades de cada item
 * em um carrinho virtual. O total da compra é atualizado dinamicamente.
 *
 * Funcionalidades principais:
 * <ul>
 * <li>Exibição de produtos disponíveis, categorizados por tipo (Comida, Bebida, Sobremesa).</li>
 * <li>Controle de quantidade de itens no carrinho através de botões de adição e remoção.</li>
 * <li>Atualização em tempo real do valor total do pedido.</li>
 * <li>Confirmação do pedido, que leva à tela de pagamento ({@link TelaConfirmaPedido}).</li>
 * <li>Opção para retornar à tela inicial de compra ({@link TelaIniciarCompra}).</li>
 * </ul>
 *
 * Utiliza instâncias das classes {@link Estoque}, {@link Caixa} e {@link ControlePedidos}
 * para gerenciar os dados do sistema durante o processo de compra.
 */
public class TelaAutoatendimento extends JFrame
{
    private JLabel totalLabel;
    private JPanel contentPanel;
    private Map<Integer, Integer> cart = new HashMap<>();

    /**
     * Construtor da classe {@link TelaAutoatendimento}.
     *
     * Inicializa a interface de autoatendimento, configurando o layout,
     * o painel de conteúdo rolável para exibir os produtos, o rótulo do total
     * e os botões de ação (Confirmar e Voltar). Popula a tela com os produtos
     * disponíveis no estoque.
     *
     * @param stock Instância de {@link Estoque} utilizada para gerenciar os produtos.
     * @param cashControl Instância de {@link Caixa} utilizada para controle financeiro.
     * @param orders Instância de {@link ControlePedidos} utilizada para manipular os pedidos feitos.
     */
    public TelaAutoatendimento(Estoque stock, Caixa cashControl, ControlePedidos orders)
    {
        setTitle("Cantina - Autoatendimento");
        setSize(600, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        getContentPane().setBackground(new Color(197, 202, 196));
        setLayout(new BorderLayout());

        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBackground(new Color(197, 202, 196));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 25, 10);

        // -- Title Label --
        JLabel titleLabel = new JLabel("Cantina - Autoatendimento");
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

        // -- Content Panel (Scrollable) --
        contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setBackground(new Color(197, 202, 196));

        JScrollPane scrollPanel = new JScrollPane(contentPanel);
        scrollPanel.setPreferredSize(new Dimension(500, 550));
        scrollPanel.setBackground(new Color(197, 202, 196));
        scrollPanel.getViewport().setBackground(new Color(197, 202, 196));
        scrollPanel.setBorder(BorderFactory.createEmptyBorder());
        scrollPanel.getVerticalScrollBar().setBackground(new Color(197, 202, 196));
        scrollPanel.getHorizontalScrollBar().setBackground(new Color(197, 202, 196));

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        mainPanel.add(scrollPanel, gbc);

        // -- Footer with Total and Buttons --
        gbc.gridy = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weighty = 0;

        JPanel footerPanel = new JPanel(new BorderLayout());
        footerPanel.setBackground(new Color(197, 202, 196));
        footerPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        totalLabel = new JLabel("Total: R$ 0.00");
        totalLabel.setFont(new Font("Arial", Font.BOLD, 18));

        JButton confirmButton = createCustomButton("Confirmar");

        confirmButton.addActionListener(_ -> {
            TelaConfirmaPedido telaConfirmacao = new TelaConfirmaPedido(TelaAutoatendimento.this, cart, stock, cashControl, orders);
            telaConfirmacao.setVisible(true);
        });

        JButton backButton = createCustomButton("Voltar");

        backButton.addActionListener(e -> {
            TelaIniciarCompra telaIniciarCompra = new TelaIniciarCompra(stock, cashControl, orders);
            telaIniciarCompra.setVisible(true);
            dispose();
        });

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        buttonPanel.setBackground(new Color(197, 202, 196));
        buttonPanel.add(backButton);
        buttonPanel.add(confirmButton);

        footerPanel.add(totalLabel, BorderLayout.WEST);
        footerPanel.add(buttonPanel, BorderLayout.EAST);

        mainPanel.add(footerPanel, gbc);
        add(mainPanel);

        generateProducts(stock.getProductsStock());
    }

    private void generateProducts(Map<Integer, ProdutoEmEstoque> stock)
    {
        contentPanel.removeAll();

        Map<String, List<ProdutoEmEstoque>> byType = new HashMap<>();

        for (ProdutoEmEstoque pe : stock.values())
        {
            String type = pe.getProduct().getType();
            byType.putIfAbsent(type, new ArrayList<>());
            byType.get(type).add(pe);
        }

        List<String> orderedTypes = Arrays.asList("Comida", "Bebida", "Sobremesa");

        for (String type : orderedTypes)
        {
            if (!byType.containsKey(type)) continue;

            JLabel categoryLabel = new JLabel(type);
            categoryLabel.setFont(new Font("Arial", Font.BOLD, 14));
            categoryLabel.setOpaque(true);
            categoryLabel.setBackground(new Color(0, 86, 179));
            categoryLabel.setForeground(Color.WHITE);
            categoryLabel.setBorder(BorderFactory.createEmptyBorder(8, 12, 8, 8));
            categoryLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

            contentPanel.add(Box.createVerticalStrut(10));
            contentPanel.add(categoryLabel);

            for (ProdutoEmEstoque pe : byType.get(type))
            {
                Produto p = pe.getProduct();

                JPanel productPanel = new JPanel(new BorderLayout());
                productPanel.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(Color.LIGHT_GRAY),
                        BorderFactory.createEmptyBorder(10, 10, 10, 10)
                ));
                productPanel.setBackground(new Color(197, 202, 196));
                productPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 70));
                productPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

                JPanel textPanel = new JPanel();
                textPanel.setLayout(new BoxLayout(textPanel, BoxLayout.Y_AXIS));
                textPanel.setOpaque(false);

                JLabel nameLabel = new JLabel(p.getName());
                nameLabel.setFont(new Font("Arial", Font.BOLD, 14));
                JLabel priceLabel = new JLabel("R$ " + String.format("%.2f", p.getPrice()));
                priceLabel.setFont(new Font("Arial", Font.PLAIN, 13));

                textPanel.add(nameLabel);
                textPanel.add(priceLabel);

                productPanel.add(textPanel, BorderLayout.WEST);

                JPanel controlPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
                controlPanel.setOpaque(false);

                JButton minusButton = new JButton("−");
                minusButton.setBackground(new Color(0, 86, 179));
                minusButton.setForeground(Color.WHITE);
                minusButton.setBorderPainted(false);
                minusButton.setFocusPainted(false);
                minusButton.setPreferredSize(new Dimension(50, 30));

                JButton plusButton = new JButton("+");
                plusButton.setForeground(Color.WHITE);
                plusButton.setBackground(new Color(0, 86, 179));
                plusButton.setBorderPainted(false);
                plusButton.setFocusPainted(false);
                plusButton.setPreferredSize(new Dimension(50, 30));

                JLabel quantityLabel = new JLabel("0");
                quantityLabel.setPreferredSize(new Dimension(20, 30));
                quantityLabel.setHorizontalAlignment(SwingConstants.CENTER);

                int code = p.getCode();
                cart.put(code, 0);

                minusButton.addActionListener(e -> {
                    int qtd = cart.get(code);
                    if (qtd > 0)
                    {
                        qtd--;
                        cart.put(code, qtd);
                        quantityLabel.setText(String.valueOf(qtd));
                        updateTotal(stock);
                    }
                });

                plusButton.addActionListener(e -> {
                    int qtd = cart.get(code);
                    qtd++;
                    cart.put(code, qtd);
                    quantityLabel.setText(String.valueOf(qtd));
                    updateTotal(stock);
                });

                controlPanel.add(minusButton);
                controlPanel.add(quantityLabel);
                controlPanel.add(plusButton);
                productPanel.add(controlPanel, BorderLayout.EAST);

                contentPanel.add(Box.createVerticalStrut(8));
                contentPanel.add(productPanel);
            }
        }

        contentPanel.revalidate();
        contentPanel.repaint();
    }

    /**
     * Atualiza o valor total exibido no rodapé da tela com base nos itens do carrinho.
     *
     * Percorre todos os itens no carrinho, calcula o subtotal de cada um
     * (quantidade * preço) e soma para obter o total geral, que é então formatado
     * e exibido no {@link JLabel} correspondente.
     *
     * @param stock Um {@link Map} contendo os produtos em estoque, usado para obter
     * o preço de cada {@link ProdutoEmEstoque} no carrinho.
     */
    private void updateTotal(Map<Integer, ProdutoEmEstoque> stock)
    {
        double total = 0;
        for (Map.Entry<Integer, Integer> entry : cart.entrySet())
        {
            int code = entry.getKey();
            int quantity = entry.getValue();
            Produto p = stock.get(code).getProduct();
            total += quantity * p.getPrice();
        }

        totalLabel.setText("Total: R$ " + String.format("%.2f", total));
    }

    /**
     * Método auxiliar para criação de botões estilizados usados na {@link TelaAutoatendimento}.
     * 
     * Configura cor de fundo, cor da fonte, fonte, borda e tamanho do botão.
     *
     * @param text Texto a ser exibido no botão.
     * @return {@link JButton} estilizado e formatado para o layout da tela.
     */
    private JButton createCustomButton(String text)
    {
        JButton button = new JButton(text);
        button.setBackground(new Color(0, 86, 179));
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Arial", Font.BOLD, 18));
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        return button;
    }
}
