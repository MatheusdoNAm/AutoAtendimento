package Front;

import Back.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
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
        super(owner, "Mostrar Relatório", true);
        setSize(600,400);
        setLocationRelativeTo(owner);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setResizable(false);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(197,202,196));

        if (report.equals("Produtos mais Vendidos"))
        {
            String title = String.format("Produtos mais Vendidos (%s a %s)",
                    startDate.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")),
                    endDate.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));

            JLabel titleLabel = new JLabel(title, SwingConstants.CENTER);
            titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
            mainPanel.add(titleLabel, BorderLayout.NORTH);

            DefaultTableModel tableModel = new DefaultTableModel();
            tableModel.addColumn("Produto");
            tableModel.addColumn("Quantidade Vendida");

            Map<String, Integer> productSales = calculateProductSales(startDate, endDate, orders, stock);

            java.util.List<Map.Entry<String, Integer>> sortedSales = productSales.entrySet().stream()
                    .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
                    .collect(Collectors.toList());

            for (Map.Entry<String, Integer> entry : sortedSales) {
                tableModel.addRow(new Object[]{entry.getKey(), entry.getValue()});
            }

            JTable reportTable = new JTable(tableModel);
            JScrollPane scrollPane = new JScrollPane(reportTable);
            mainPanel.add(scrollPane, BorderLayout.CENTER);

            add(mainPanel);
        }
        else if (report.equals("Transações Realizadas"))
        {
            String title = String.format("Transações Realizadas (%s a %s)",
                    startDate.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")),
                    endDate.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));

            JLabel titleLabel = new JLabel(title, SwingConstants.CENTER);
            titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
            mainPanel.add(titleLabel, BorderLayout.NORTH);

            DefaultTableModel tableModel = new DefaultTableModel();
            tableModel.addColumn("Número do Pedido");
            tableModel.addColumn("Data");
            tableModel.addColumn("Valor");

            java.util.List<Object[]> transactionData = calculateTransactionData(startDate, endDate, orders, stock);

            for (Object[] rowData : transactionData) {
                tableModel.addRow(rowData);
            }

            JTable reportTable = new JTable(tableModel);
            JScrollPane scrollPane = new JScrollPane(reportTable);
            mainPanel.add(scrollPane, BorderLayout.CENTER);

            add(mainPanel);
        }
        else if (report.equals("Produtos Vencidos ou Próximos de Vencer"))
         {
             String title = "Produtos Vencidos ou Próximos do Vencimento";
             JLabel titleLabel = new JLabel(title, SwingConstants.CENTER);
             titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
             mainPanel.add(titleLabel, BorderLayout.NORTH);
 
             DefaultTableModel tableModel = new DefaultTableModel();
             tableModel.addColumn("Produto");
             tableModel.addColumn("Validade");
             tableModel.addColumn("Status");
 
             java.util.List<Object[]> expiredProductsData = calculateExpiredProductsData(stock);
 
             for (Object[] rowData : expiredProductsData)
             {
                 tableModel.addRow(rowData);
             }
 
             JTable reportTable = new JTable(tableModel);
             JScrollPane scrollPane = new JScrollPane(reportTable);
             mainPanel.add(scrollPane, BorderLayout.CENTER);
 
             add(mainPanel);
         }     
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
                        String.format("%.2f", orderTotal)
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
