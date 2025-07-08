package Back;

import java.util.ArrayList;

/**
 * Classe responsável por gerenciar os pedidos registrados no sistema.
 * 
 * A {@code ControlePedidos} mantém uma lista estática de objetos {@code Pedido},
 * representando todos os pedidos feitos durante a execução do programa.
 * 
 * Atua como camada de controle entre a interface e os dados dos pedidos,
 * permitindo registrar novos pedidos por meio do método {@code newOrder}.
 */
public class ControlePedidos
{
    private static ArrayList<Pedido> orders = new ArrayList<>();

    /**
     * Adiciona um novo pedido à lista de pedidos registrados no caixa.
     * 
     * @param order o objeto {@code Pedido} que representa o pedido a ser adicionado.
     */
    public void newOrder(Pedido order)
    {
        orders.add(order);
    }

    public static ArrayList<Pedido> getOrders()
    {
        return orders;
    }
}
