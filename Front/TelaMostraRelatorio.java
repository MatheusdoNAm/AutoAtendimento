package Front;

import Back.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Tela para exibir os relatórios gerados.
 *
 * Esta classe {@link TelaMostraRelatorio} é um diálogo que apresenta os dados
 * de um relatório gerado, permitindo visualizar informações sobre produtos
 * vendidos, transações realizadas ou produtos vencidos/próximos ao vencimento.
 *
 * A tela ajusta seu conteúdo dinamicamente com base no tipo de relatório
 * selecionado, formatando e exibindo os dados em uma tabela.
 */
public class TelaMostraRelatorio extends JDialog
{
    /**
     * Construtor para {@link TelaMostraRelatorio}.
     *
     * Inicializa o diálogo, configura sua aparência e exibe o relatório
     * correspondente ao tipo especificado.
     *
     * @param owner A janela pai deste diálogo.
     * @param report O tipo de relatório a ser exibido ("Produtos mais Vendidos",
     *               "Transações Realizadas" ou "Produtos Vencidos ou Próximos de Vencer").
     * @param startDate A data inicial do período do relatório.
     * @param endDate A data final do período do relatório.
     * @param stock Instância de {@link Estoque} utilizada para gerenciar os produtos.
     * @param cashControl Instância de {@link Caixa} utilizada para controle financeiro.
     * @param orders Instância de {@link ControlePedidos} utilizada para manipular os pedidos feitos.
     */
    public TelaMostraRelatorio(JFrame owner, String report, LocalDate startDate, LocalDate endDate, Estoque stock, Caixa cashControl, ControlePedidos orders)
    {
        super(owner, "Relatório - " + report, true);
        setSize(600, 500);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(owner);
        setResizable(false);
        getContentPane().setBackground(new Color(197, 202, 196));
        setLayout(new BorderLayout());

        // --- Main Panel with Padding ---
        JPanel mainPanel = new JPanel(new BorderLayout(0, 15));
        mainPanel.setBorder(new EmptyBorder(15, 15, 15, 15));
        mainPanel.setBackground(new Color(197,202,196));

        String titleText;
        String[] columnNames;
        Object[][] data;

        if (report.equals("Produtos mais Vendidos"))
        {
            titleText = String.format("Produtos mais Vendidos (%s a %s)",
                    startDate.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")),
                    endDate.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));

            columnNames = new String[]{"Produto", "Quantidade Vendida"};

            Map<String, Integer> productSales = calculateProductSales(startDate, endDate, orders, stock);
            java.util.List<Map.Entry<String, Integer>> sortedSales = productSales.entrySet().stream()
                    .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
                    .collect(Collectors.toList());

            data = new Object[sortedSales.size()][2];
            int i = 0;
            for (Map.Entry<String, Integer> entry : sortedSales) {
                data[i][0] = entry.getKey();
                data[i][1] = entry.getValue();
                i++;
            }
        }
        else if (report.equals("Transações Realizadas"))
        {
            titleText = String.format("Transações Realizadas (%s a %s)",
                    startDate.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")),
                    endDate.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));

            columnNames = new String[]{"Número do Pedido", "Data", "Valor (R$)"};

            java.util.List<Object[]> transactionData = calculateTransactionData(startDate, endDate, orders, stock);
            data = transactionData.toArray(new Object[0][]);
        }
        else if (report.equals("Produtos Vencidos ou Próximos de Vencer"))
         {
            titleText = "Produtos Vencidos ou Próximos do Vencimento";
            columnNames = new String[]{"Produto", "Validade", "Status"};
            java.util.List<Object[]> expiredProductsData = calculateExpiredProductsData(stock);
            data = expiredProductsData.toArray(new Object[0][]);
         }
        else {
            // Fallback for unknown report type
            titleText = "Relatório Inválido";
            columnNames = new String[]{"Erro"};
            data = new Object[][]{{"Tipo de relatório não reconhecido."}};
        }

        // --- Title ---
        JLabel titleLabel = new JLabel(titleText, SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 22));
        titleLabel.setForeground(new Color(40, 40, 40));
        mainPanel.add(titleLabel, BorderLayout.NORTH);

        // --- Table ---
        DefaultTableModel tableModel = new DefaultTableModel(data, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Make table non-editable
            }
        };

        JTable reportTable = new JTable(tableModel);
        styleTable(reportTable);

        JScrollPane scrollPane = new JScrollPane(reportTable);
        scrollPane.getViewport().setBackground(new Color(197, 202, 196));
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        // --- Back Button ---
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 10));
        buttonPanel.setBackground(new Color(197, 202, 196));

        JButton backButton = createCustomButton("Voltar");
        backButton.setBackground(new Color(220, 53, 69));
        backButton.addActionListener(e -> dispose());
        buttonPanel.add(backButton);

        add(mainPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    /**
     * Coleta os dados de produtos vencidos ou próximos do vencimento.
     *
     * Este método itera sobre todos os produtos no estoque, verifica a
     * validade de cada um e coleta os dados dos produtos que estão vencidos,
     * vencem hoje ou estão próximos do vencimento (até 7 dias).
     *
     * @param stock A instância de {@link Estoque} para acessar os produtos.
     * @return Uma lista de arrays de objetos, onde cada array contém o nome do
     *         produto, a data de validade formatada e o status de validade.
     */
    private java.util.List<Object[]> calculateExpiredProductsData(Estoque stock)
     {
         java.util.List<Object[]> expiredProductsData = new ArrayList<>();
         DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
         LocalDate today = LocalDate.now();
 
         for (ProdutoEmEstoque productInStock : stock.getProductsStock().values())
         {
             Produto product = productInStock.getProduct();
             LocalDate expirationDate = product.getValidity();
 
             if (expirationDate != null)
             {
                 String status = getExpirationStatus(expirationDate, today);
                 expiredProductsData.add(new Object[]{
                         product.getName(),
                         expirationDate.format(formatter),
                         status
                 });
             }
         }
         return expiredProductsData;
     }

    /**
     * Styles a JTable to match the application's visual theme.
     * @param table The JTable to be styled.
     */
    private void styleTable(JTable table) {
        table.setRowHeight(28);
        table.setFont(new Font("Arial", Font.PLAIN, 14));
        table.setBackground(Color.WHITE);
        table.setFillsViewportHeight(true);
        table.getTableHeader().setReorderingAllowed(false);
        table.setShowGrid(true);
        table.setGridColor(new Color(220, 220, 220));

        // Style the header
        JTableHeader header = table.getTableHeader();
        header.setFont(new Font("Arial", Font.BOLD, 14));
        header.setBackground(new Color(0, 86, 179));
        header.setForeground(Color.WHITE);
        header.setResizingAllowed(false);

        // Center cell content
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        for (int i = 0; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }
    }

    /**
     * Creates a styled JButton.
     * @param text The text for the button.
     * @return A styled JButton.
     */
    private JButton createCustomButton(String text) {
        JButton button = new JButton(text);
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Arial", Font.BOLD, 16));
        button.setFocusPainted(false);
        button.setPreferredSize(new Dimension(150, 40));
        button.setBorderPainted(false);
        return button;
    }

    /**
     * Calcula os dados das transações realizadas em um período.
     *
     * Este método itera sobre os pedidos dentro do intervalo de datas e
     * coleta informações como número do pedido, data e valor total.
     *
     * @param startDate A data inicial do período.
     * @param endDate A data final do período.
     * @param orders A instância de {@link ControlePedidos} para acessar os pedidos.
     * @param stock A instância de {@link Estoque} para calcular o valor total dos pedidos.
     * @return Uma lista de arrays de objetos, onde cada array representa uma transação.
     */
    private java.util.List<Object[]> calculateTransactionData(LocalDate startDate, LocalDate endDate, ControlePedidos orders, Estoque stock) 
    {
        java.util.List<Object[]> transactionData = new ArrayList<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        for (Pedido order : ControlePedidos.getOrders()) {
            LocalDate orderDate = order.getOrderDate();

            if (!(orderDate.isBefore(startDate) || orderDate.isAfter(endDate))) {
                double orderTotal = calculateOrderTotal(order, stock);

                transactionData.add(new Object[]{
                        order.getOrderNumber(),
                        orderDate.format(formatter),
                        String.format("R$ %.2f", orderTotal)
                });
            }
        }

        return transactionData;
    }

    /**
     * Calcula a quantidade vendida para cada produto dentro de um período.
     *
     * Este método processa os pedidos para determinar a quantidade total
     * vendida de cada produto no intervalo de datas fornecido.
     *
     * @param startDate A data inicial do período.
     * @param endDate A data final do período.
     * @param orders A instância de {@link ControlePedidos} para acessar os pedidos.
     * @param stock A instância de {@link Estoque} para obter o nome dos produtos.
     * @return Um mapa onde a chave é o nome do produto e o valor é a quantidade vendida.
     */
    private Map<String, Integer> calculateProductSales(LocalDate startDate, LocalDate endDate, ControlePedidos orders, Estoque stock)
    {
        Map<String, Integer> productSales = new HashMap<>();

        for (Pedido order : ControlePedidos.getOrders())
        {
            LocalDate orderDate = order.getOrderDate();

            if (!(orderDate.isBefore(startDate) || orderDate.isAfter(endDate)))
            {
                for (Map.Entry<Integer, Integer> entry : order.getOrder().entrySet())
                {
                    int productCode = entry.getKey();
                    int quantity = entry.getValue();
                    String productName = stock.getProductName(productCode);
                    productSales.put(productName, productSales.getOrDefault(productName, 0) + quantity);
                }
            }
        }

        return productSales;
    }

    /**
     * Calcula o valor total de um pedido.
     *
     * Este método itera sobre os itens de um pedido, multiplicando a quantidade
     * de cada produto pelo seu preço para calcular o valor total.
     *
     * @param order O pedido para calcular o valor total.
     * @param stock A instância de {@link Estoque} para acessar os preços dos produtos.
     * @return O valor total do pedido.
     */
    private double calculateOrderTotal(Pedido order, Estoque stock) 
    {
        double total = 0;
        for (Map.Entry<Integer, Integer> entry : order.getOrder().entrySet()) {
            int productCode = entry.getKey();
            int quantity = entry.getValue();
            total += stock.getProductsStock().get(productCode).getProduct().getPrice() * quantity;
        }
        return total;
    }

    /**
     * Determina o status de validade de um produto.
     *
     * Compara a data de validade de um produto com a data atual para
     * determinar se ele está vencido, vence hoje, está próximo do vencimento
     * (até 7 dias) ou ainda está dentro da validade.
     *
     * @param expirationDate A data de validade do produto.
     * @param today A data atual.
     * @return Uma string representando o status de validade do produto.
     */
    private String getExpirationStatus(LocalDate expirationDate, LocalDate today) 
    {
        if (expirationDate.isBefore(today)) 
        {
            return "Vencido";
        } else if (expirationDate.isEqual(today)) 
        {
            return "Vence Hoje";
        } else if (expirationDate.isBefore(today.plusDays(8))) 
        {
            return "Próximo do Vencimento";
        } else 
        {
            return "Dentro da Validade";
        }
    }
}
