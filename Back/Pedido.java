package Back;

import java.time.LocalDate;
import java.util.Map;

/**
 * Representa um pedido realizado no sistema.
 * 
 * <p>
 * Cada pedido contém um número único incremental, uma data de criação e um mapa que
 * associa o código de cada produto à sua quantidade solicitada.
 * </p>
 * 
 * <p>
 * A classe mantém também um contador global de pedidos ({@code orderGlobalNumber}) usado
 * para gerar automaticamente novos números de pedido.
 * </p>
 */
public class Pedido
{
    private static int orderGlobalNumber = 0;
    private int orderNumber;
    private Map<Integer, Integer> order;
    private LocalDate orderDate;

    /**
     * Construtor da classe {@code Pedido}.
     * 
     * Cria um novo pedido com base no mapa de itens fornecido, define a data atual
     * como data do pedido e gera automaticamente o número sequencial do pedido.
     * 
     * @param cart um {@code Map<Integer, Integer>} contendo os produtos e suas quantidades.
     */
    public Pedido(Map<Integer, Integer> cart)
    {
        setOrder(cart);
        setOrderNumber((getOrderGlobalNumber()) + 1);
        orderGlobalNumber++;
        this.orderDate = LocalDate.now();
    }

    /**
     * Define o número global de pedidos do sistema.
     * 
     * Esse valor define o ponto de partida para a contagem de novos pedidos.
     * Só será alterado se o valor informado for maior que zero.
     * 
     * @param number o novo número global de pedidos.
     */
    public static void setOrderGlobalNumber(int number)
    {
        if (number > 0)
            orderGlobalNumber = number;
    }

    /**
     * Retorna o número global atual de pedidos registrados.
     * 
     * @return o valor do contador global de pedidos.
     */
    public static int getOrderGlobalNumber()
    {
        return orderGlobalNumber;
    }

    /**
     * Retorna o número único deste pedido específico.
     * 
     * @return o número do pedido.
     */
    public int getOrderNumber()
    {
        return orderNumber;
    }

    /**
     * Define o número deste pedido específico.
     * 
     * @param orderNumber o número do pedido.
     */
    public void setOrderNumber(int orderNumber)
    {
        this.orderNumber = orderNumber;
    }

    /**
     * Retorna o conteúdo do pedido.
     * 
     * O conteúdo é representado como um mapa onde a chave é o código do produto
     * e o valor é a quantidade solicitada.
     * 
     * @return um {@code Map<Integer, Integer>} representando o pedido.
     */
    public Map<Integer, Integer> getOrder()
    {
        return order;
    }

    /**
     * Define o conteúdo do pedido.
     * 
     * @param order um mapa com os produtos e suas respectivas quantidades.
     */
    public void setOrder(Map<Integer, Integer> order)
    {
        this.order = order;
    }

    /**
     * Retorna a data em que o pedido foi realizado.
     * 
     * @return a data do pedido como {@code LocalDate}.
     */
    public LocalDate getOrderDate()
    {
        return orderDate;
    }
}