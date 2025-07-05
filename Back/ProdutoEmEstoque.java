package Back;
public class ProdutoEmEstoque
{
    private Produto product;
    private int amountStock;
    
    public ProdutoEmEstoque(Produto product, int amountStock)
    {
        this.product = product;
        this.amountStock = amountStock;
    }
    public Produto getProduct()
    {
        return product;
    }
    public void setProduct(Produto product)
    {
        this.product = product;
    }

    public int getAmountStock()
    {
        return amountStock;
    }
    public boolean setAmountStock(int amountStock)
    {
        if (amountStock < 0)
            return false;
        else
        {
            this.amountStock = amountStock;
            return true;
        }
    }

    public boolean addStock(int quantity)
    {
        if (quantity < 0)
            return false;
        setAmountStock(getAmountStock()+quantity);
        return true;
    }

    public boolean removeStock(int quantity)
    {
        if (quantity < 0 || this.amountStock < quantity)
            return false;
        else
        {
            setAmountStock(getAmountStock()-quantity);
            return true;
        }
    }
    
}
