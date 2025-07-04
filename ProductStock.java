public class ProductStock 
{
    private Product product;
    private int amountStock;

    public Product getProduct() 
    {
        return product;
    }
    public void setProduct(Product product)
    {
        this.product = product;
    }

    public int getAmountStock()
    {
        return amountStock;
    }
    public void setAmountStock(int quantity)
    {
        if (quantity < 0)
        {
            throw new IllegalArgumentException ("Amount in stock cannot be negative. Value received: " + quantity);
        }
        this.amountStock = quantity;
    }

    public ProductStock(Product product, int amountStock)
    {
        this.product = product;
        setAmountStock(amountStock);
    }

    public void addStock (int quantity)
    {
        if (quantity < 0)
        {
            throw new IllegalArgumentException("Quantity to add cannot be negative. Value received: " + quantity);
        }
        setAmountStock(getAmountStock()+quantity);
    }
    
    public boolean removeStock(int quantity)
    {
        if (quantity < 0)
        { 
            throw new IllegalArgumentException("Quantity to remove cannot be negative. Value received: " + quantity);
        }

        if (this.amountStock < quantity)
        {
            return false;
        }
        setAmountStock(this.amountStock - quantity);
        return true;
    }
}
