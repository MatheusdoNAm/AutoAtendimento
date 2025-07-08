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
 * Tela de Controle de Estoque.
 *
 * A classe {@link TelaEstoque} exibe os produtos disponíveis no estoque,
 * agrupados por categoria. Permite a visualização do código, nome, preço,
 * quantidade em estoque e validade de cada produto.
 *
 * Oferece funcionalidades para:
 * <ul>
 * <li>Visualizar o estoque atual do sistema;</li>
 * <li>Acessar a tela de edição de estoque ({@link TelaEditaEstoque});</li>
 * <li>Retornar à tela de administração ({@link TelaAdmin}).</li>
 * </ul>
 *
 * Utiliza instâncias das classes {@link Estoque}, {@link Caixa} e {@link ControlePedidos}
 * para manter a continuidade dos dados e funcionalidades do sistema.
 */
public class TelaEstoque extends JFrame
{

    private JPanel mainPanel;

    /**
     * Construtor da classe {@link TelaEstoque}.
     *
     * Responsável por construir a interface de controle de estoque, configurando a tabela
     * de produtos e os botões de ação para alterar o estoque e voltar à tela anterior.
     * Define o layout visual e as propriedades da janela.
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
            TelaEditaEstoque telaEditaEstoque = new TelaEditaEstoque(TelaEstoque.this, stock, cashControl);
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
     * Carrega e exibe os produtos no estoque na interface.
     *
     * Este método limpa o conteúdo atual do painel principal, adiciona o título
     * novamente e, em seguida, agrupa os produtos por categoria. Para cada categoria,
     * é criada e adicionada uma tabela com os detalhes dos produtos.
     *
     * @param productsStock Um {@link Map} contendo os produtos em estoque, onde a chave é
     * o código do produto e o valor é uma instância de {@link ProdutoEmEstoque}.
     */
    public void loadProducts(Map<Integer, ProdutoEmEstoque> productsStock)
    {
        mainPanel.removeAll();

        JLabel titleLabel = new JLabel("Controle de Estoque");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 26));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        mainPanel.add(titleLabel);

        // Agrupar produtos por categoria
        Map<String, List<ProdutoEmEstoque>> categorias = new HashMap<>();
        for (ProdutoEmEstoque pe : productsStock.values())
        {
            String tipo = pe.getProduct().getType();
            categorias.computeIfAbsent(tipo, k -> new ArrayList<>()).add(pe);
        }

        // Adicionar seções por categoria
        for (Map.Entry<String, List<ProdutoEmEstoque>> entry : categorias.entrySet())
        {
            String tipo = entry.getKey();
            List<ProdutoEmEstoque> lista = entry.getValue();

            Object[][] dados = new Object[lista.size()][5];
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

            for (int i = 0; i < lista.size(); i++)
            {
                Produto p = lista.get(i).getProduct();
                dados[i][0] = p.getCode();
                dados[i][1] = p.getName();
                dados[i][2] = String.format("R$ %.2f", p.getPrice());
                dados[i][3] = lista.get(i).getAmountStock();

                LocalDate validade = p.getValidity();
                dados[i][4] = (validade != null) ? validade.format(formatter) : "—";            
            }

            mainPanel.add(createCategory(tipo, dados));
        }

        mainPanel.revalidate();
        mainPanel.repaint();
    }

    /**
     * Cria um painel JPanel contendo o título de uma categoria e uma tabela
     * com os dados dos produtos pertencentes a essa categoria.
     *
     * O painel inclui um título estilizado para a categoria e uma {@link JTable}
     * não editável que exibe o código, produto, preço, quantidade e validade dos itens.
     * As células da tabela são centralizadas.
     *
     * @param titulo O título da categoria a ser exibido.
     * @param dados Um array bidimensional de {@link Object} contendo os dados dos produtos,
     * onde cada linha representa um produto e cada coluna, uma de suas propriedades.
     * @return Um {@link JPanel} estilizado contendo o título da categoria e a tabela de produtos.
     */
    private JPanel createCategory(String titulo, Object[][] dados)
    {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.setBackground(new Color(197, 202, 196));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 15, 20, 15));

        // Título da categoria
        JLabel label = new JLabel(titulo);
        label.setOpaque(true);
        label.setBackground(new Color(160, 167, 161));
        label.setForeground(Color.WHITE);
        label.setFont(new Font("Arial", Font.BOLD, 18));
        label.setBorder(BorderFactory.createEmptyBorder(8, 10, 8, 10));
        label.setHorizontalAlignment(SwingConstants.CENTER); // ← centralizado
        panel.add(label, BorderLayout.NORTH);

        // Colunas da tabela
        String[] colunas = {"Código", "Produto", "Preço", "Qtd", "Validade"};

        DefaultTableModel model = new DefaultTableModel(dados, colunas)
        {
            @Override
            public boolean isCellEditable(int row, int column)
            {
                return false; // impede edição
            }
        };

        JTable tabela = new JTable(model);
        tabela.setRowHeight(25);
        tabela.setFont(new Font("Arial", Font.PLAIN, 14));
        tabela.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));
        tabela.setBackground(Color.WHITE);
        tabela.setFillsViewportHeight(true);

        // Travar ações do cabeçalho
        tabela.getTableHeader().setResizingAllowed(false);     // trava redimensionamento
        tabela.getTableHeader().setReorderingAllowed(false);   // trava movimentação

        // Centralizar todas as células da tabela
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        for (int i = 0; i < tabela.getColumnCount(); i++)
        {
            tabela.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }

        // Scroll da tabela
        JScrollPane tabelaScroll = new JScrollPane(tabela);
        tabelaScroll.setPreferredSize(new Dimension(550, 100));

        panel.add(tabelaScroll, BorderLayout.CENTER);

        return panel;
    }
    
    /**
     * Método auxiliar para criação de botões estilizados usados na {@link TelaLogin}.
     * 
     * Configura cor de fundo, cor da fonte, fonte, borda e tamanho do botão.
     *
     * @param text Texto a ser exibido no botão.
     * @param bg A cor de fundo do botão.
     * @return Um {@link JButton} estilizado e formatado para o layout da tela.
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
