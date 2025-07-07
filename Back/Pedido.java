package Back;

import java.time.LocalDate;
import java.util.Map;

public class Pedido
{
    private static int orderGlobalNumber = 0;
    private int orderNumber;
    private Map<Integer, Integer> order;
    private LocalDate orderDate;

    public Pedido(Map<Integer, Integer> cart)
    {
        setOrder(cart);
        setOrderNumber((getOrderGlobalNumber()) + 1);
        orderGlobalNumber++;
        this.orderDate = LocalDate.now();
    }

    public static int getOrderGlobalNumber()
    {
        return orderGlobalNumber;
    }

    public int getOrderNumber()
    {
        return orderNumber;
    }

    public void setOrderNumber(int orderNumber)
    {
        this.orderNumber = orderNumber;
    }

    public Map<Integer, Integer> getOrder()
    {
        return order;
    }

    public void setOrder(Map<Integer, Integer> order)
    {
        this.order = order;
    }

    public LocalDate getOrderDate()
    {
        return orderDate;
    }
}
