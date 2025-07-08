package Front;
import Back.*;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * Tela para visualizar e gerenciar o estoque de produtos.
 *
 * A classe {@link TelaEstoque} exibe os produtos do estoque agrupados por categoria
 * em tabelas organizadas. Permite ao administrador acessar as funcionalidades
 * de alteração do estoque, como adicionar, remover ou deletar produtos.
 *
 * Funcionalidades principais:
 * <ul>
 * <li>Exibição dos produtos do estoque, incluindo código, nome, preço, quantidade e validity.</li>
 * <li>Organização dos produtos em tabelas por categoria.</li>
 * <li>Botão para acessar a tela de edição de estoque ({@link TelaEditaEstoque}).</li>
 * <li>Botão para retornar à tela de administração ({@link TelaAdmin}).</li>
 * <li>Método para recarregar a list de produtos, atualizando a exibição.</li>
 * </ul>
 */
public class TelaEstoque extends JFrame
{

    private JPanel mainPanel;

    /**
     * Construtor da classe {@link TelaEstoque}.
     *
     * Inicializa a interface da tela de controle de estoque, configurando o layout
     * e os componentes visuais, incluindo as tabelas de produtos e os botões de ação.
     *
     * @param stock Instância de {@link Estoque} utilizada para gerenciar os produtos.
     * @param cashControl Instância de {@link Caixa} utilizada para controle financeiro.
     * @param orders Instância de {@link ControlePedidos} utilizada para manipular os pedidos feitos.
     */
    public TelaEstoque(Estoque stock, Caixa cashControl, ControlePedidos orders)
    {
        setTitle("Controle de Estoque");
        setSize(600, 800);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setResizable(false);
        getContentPane().setBackground(new Color(197, 202, 196));
        setLayout(new BorderLayout());

        mainPanel = new JPanel();
        mainPanel.setBackground(new Color(197, 202, 196));
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

        JLabel titleLabel = new JLabel("Controle de Estoque");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 26));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        mainPanel.add(titleLabel);

        JScrollPane scrollPane = new JScrollPane(mainPanel);
        scrollPane.setBorder(null);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        add(scrollPane, BorderLayout.CENTER);

        // Painel de botões
        JButton addButton = createCustomButton("Alterar Estoque", new Color(40, 167, 69));
        addButton.addActionListener(e -> 
        {
            System.out.println("Alterando Estoque");
            TelaEditaEstoque telaEditaEstoque = new TelaEditaEstoque(this, TelaEstoque.this, stock, cashControl);
            telaEditaEstoque.setVisible(true);
        });

        JButton backButton = createCustomButton("Voltar", new Color(220, 53, 69));

        backButton.addActionListener(e -> 
        {
            TelaAdmin telaAdmin = new TelaAdmin(stock, cashControl, orders);
            telaAdmin.setVisible(true);
            dispose();
        });

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 15));
        buttonPanel.setBackground(new Color(197, 202, 196));
        buttonPanel.add(addButton);
        buttonPanel.add(backButton);

        add(buttonPanel, BorderLayout.SOUTH);
    }

    /**
     * Carrega e exibe os produtos do estoque na tela.
     *
     * Este método remove todos os componentes de produto existentes e recria as tabelas
     * com os data atuais do estoque, agrupando os produtos por categoria.
     *
     * @param stock A instância de {@link Estoque} da qual os produtos serão carregados.
     */
    public void loadProducts(Estoque stock)
    {
        mainPanel.removeAll();

        JLabel titleLabel = new JLabel("Controle de Estoque");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 26));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        mainPanel.add(titleLabel);

        // -- Products by Category --
        Map<String, List<ProdutoEmEstoque>> category = new HashMap<>();
        for (ProdutoEmEstoque pe : stock.getProductsStock().values())
        {
            String type = pe.getProduct().getType();
            category.computeIfAbsent(type, k -> new ArrayList<>()).add(pe);
        }

        // Sections by Category
        for (Map.Entry<String, List<ProdutoEmEstoque>> entry : category.entrySet())
        {
            String type = entry.getKey();
            List<ProdutoEmEstoque> list = entry.getValue();

            Object[][] data = new Object[list.size()][5];
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

            for (int i = 0; i < list.size(); i++)
            {
                Produto p = list.get(i).getProduct();
                data[i][0] = p.getCode();
                data[i][1] = p.getName();
                data[i][2] = String.format("R$ %.2f", p.getPrice());
                data[i][3] = list.get(i).getAmountStock();

                LocalDate validity = p.getValidity();
                data[i][4] = (validity != null) ? validity.format(formatter) : "—";            
            }

            mainPanel.add(createCategory(type, data));
        }

        mainPanel.revalidate();
        mainPanel.repaint();
    }

    /**
     * Cria um painel JPanel contendo o título de uma categoria e uma tabela
     * com os dados dos produtos pertencentes a essa categoria.
     *
     * O painel inclui um título estilizado para a categoria e uma {@link JTable}
     * não editável que exibe o código, produto, preço, quantidade e validity dos itens.
     * As células da tabela são centralizadas.
     *
     * @param title O título da categoria a ser exibido.
     * @param data Um array bidimensional de {@link Object} contendo os dados dos produtos,
     * onde cada linha representa um produto e cada coluna, uma de suas propriedades.
     * @return Um {@link JPanel} estilizado contendo o título da categoria e a tabela de produtos.
     */
    private JPanel createCategory(String title, Object[][] data)
    {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.setBackground(new Color(197, 202, 196));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 15, 20, 15));

        // Category Title
        JLabel categoryTitleLabel = new JLabel(title);
        categoryTitleLabel.setOpaque(true);
        categoryTitleLabel.setBackground(new Color(160, 167, 161));
        categoryTitleLabel.setForeground(Color.WHITE);
        categoryTitleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        categoryTitleLabel.setBorder(BorderFactory.createEmptyBorder(8, 10, 8, 10));
        categoryTitleLabel.setHorizontalAlignment(SwingConstants.CENTER); // ← centralizado
        panel.add(categoryTitleLabel, BorderLayout.NORTH);

        // Columns
        String[] columns = {"Código", "Produto", "Preço", "Qtd", "validity"};

        DefaultTableModel model = new DefaultTableModel(data, columns)
        {
            @Override
            public boolean isCellEditable(int row, int column)
            {
                return false;
            }
        };

        JTable table = new JTable(model);
        table.setRowHeight(25);
        table.setFont(new Font("Arial", Font.PLAIN, 14));
        table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));
        table.setBackground(Color.WHITE);
        table.setFillsViewportHeight(true);
        table.getTableHeader().setResizingAllowed(false);   
        table.getTableHeader().setReorderingAllowed(false);   

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        for (int i = 0; i < table.getColumnCount(); i++)
        {
            table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }

        JScrollPane tabelaScroll = new JScrollPane(table);
        tabelaScroll.setPreferredSize(new Dimension(550, 100));

        panel.add(tabelaScroll, BorderLayout.CENTER);

        return panel;
    }
    
    /**
     * Método auxiliar para criação de botões estilizados usados na {@link TelaEstoque}.
     * 
     * Configura cor de fundo, cor da fonte, fonte, borda e tamanho do botão.
     *
     * @param text Texto a ser exibido no botão.
     * @return {@link JButton} estilizado e formatado para o layout da tela.
     */
    private JButton createCustomButton(String text, Color bg)
    {
        JButton button = new JButton(text);
        button.setBackground(bg);
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Arial", Font.BOLD, 16));
        button.setFocusPainted(false);
        button.setPreferredSize(new Dimension(180, 40));
        button.setBorderPainted(false);
        return button;
    }
}
