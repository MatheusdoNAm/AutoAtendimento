package Back;

import java.util.ArrayList;

public class ControlePedidos
{
    private static ArrayList<Pedido> orders = new ArrayList<>();

    public void newOrder(Pedido order)
    {
        orders.add(order);
    }
}
