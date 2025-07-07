package Back;

/**
 * Representa a relação de um produto com sua quantidade disponível no estoque.
 * 
 * <p>
 * A classe {@code ProdutoEmEstoque} encapsula um objeto {@link Produto} e
 * a quantidade desse produto disponível no sistema de estoque.
 * </p>
 * 
 * <p>
 * Oferece métodos para adicionar, remover e validar o estoque de forma segura,
 * evitando quantidades negativas.
 * </p>
 */
public class ProdutoEmEstoque
{
    private Produto product;
    private int amountStock;

    /**
     * Construtor da classe {@code ProdutoEmEstoque}.
     * 
     * @param product o produto associado.
     * @param amountStock a quantidade inicial em estoque.
     */
    public ProdutoEmEstoque(Produto product, int amountStock)
    {
        this.product = product;
        this.amountStock = amountStock;
    }

    /**
     * Retorna o produto associado a este item em estoque.
     * 
     * @return o objeto {@code Produto}.
     */
    public Produto getProduct()
    {
        return product;
    }

    /**
     * Define o produto associado a este item em estoque.
     * 
     * @param product o novo objeto {@code Produto}.
     */
    public void setProduct(Produto product)
    {
        this.product = product;
    }

    /**
     * Retorna a quantidade disponível deste produto no estoque.
     * 
     * @return a quantidade atual em estoque.
     */
    public int getAmountStock()
    {
        return amountStock;
    }

    /**
     * Define diretamente a quantidade em estoque.
     * 
     * @param amountStock a nova quantidade a ser definida.
     * @return {@code true} se a quantidade for válida (maior ou igual a 0); {@code false} caso contrário.
     */
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

    /**
     * Adiciona uma quantidade ao estoque atual.
     * 
     * @param quantity a quantidade a ser adicionada.
     * @return {@code true} se a quantidade for válida (maior ou igual a 0); {@code false} caso contrário.
     */
    public boolean addStock(int quantity)
    {
        if (quantity < 0)
            return false;
        setAmountStock(getAmountStock() + quantity);
        return true;
    }

    /**
     * Remove uma quantidade do estoque atual, se houver quantidade suficiente.
     * 
     * @param quantity a quantidade a ser removida.
     * @return {@code true} se a operação for bem-sucedida; {@code false} se a quantidade for inválida ou insuficiente.
     */
    public boolean removeStock(int quantity)
    {
        if (quantity < 0 || this.amountStock < quantity)
            return false;
        else
        {
            setAmountStock(getAmountStock() - quantity);
            return true;
        }
    }
}