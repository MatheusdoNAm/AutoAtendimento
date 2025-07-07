import Back.*;
import Front.*;

public class StartApp
{
    public static void main(String[] args)
    {
        Estoque stock = new Estoque();
        Caixa cashControl = new Caixa();
        ControlePedidos orders = new ControlePedidos();

        TelaInicial telaInicial = new TelaInicial(stock, cashControl, orders);
        telaInicial.setVisible(true);
    }
}
