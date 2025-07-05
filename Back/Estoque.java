package Back;
import java.util.*;

public class Estoque
{
    private Map<Integer, ProdutoEmEstoque> productsStock;
    
    public Estoque()
    {
        productsStock = new HashMap<>();
    }
    
    public Map<Integer, ProdutoEmEstoque> getProductsStock()
    {
        return productsStock;
    }
    
    public boolean registerProduct(Produto product)
    {
        int code = product.getCode();

        if (productsStock.containsKey(code))
            return false;
        else
        {
            productsStock.put(code, new ProdutoEmEstoque(product, 0));
            return true;
        }
    }

    public boolean deleteProduct(int code)
    {
        if (productsStock.containsKey(code))
        {
            productsStock.remove(code);
            return true;
        } 
        else
            return false;
    }

    public boolean addStock(int code, int quantity)
    {
        if (!productsStock.containsKey(code) || quantity<=0)
            return false;
        else
        {
            productsStock.get(code).addStock(quantity);
            return true;
        }
    }

    public boolean removeStock(int code, int quantity)
    {
        if (!productsStock.containsKey(code) || quantity<=0)
            return false;
        
        if (productsStock.get(code).removeStock(quantity))
            return true;
        else
            return false;
    }

    public int getQuantityAvaible(int code)
    {
        return (productsStock.containsKey(code)) ?  productsStock.get(code).getAmountStock() : 0;
    }

    public void listarProdutos()
    {
        if (productsStock.isEmpty()) {
            System.out.println("Nenhum produto cadastrado no estoque.");
            return;
        }

        System.out.println("=== Produtos em Estoque ===");
        for (ProdutoEmEstoque p : productsStock.values()) {
            Produto prod = p.getProduct(); // acessa o produto interno
            System.out.println(
                "Código: " + prod.getCode() +
                " | Nome: " + prod.getName() +
                " | Quantidade: " + p.getAmountStock()
            );
        }
    }

}