package Back;
import java.util.*;

/**
 * Representa o estoque de produtos do sistema.
 * 
 * <p>
 * Esta classe gerencia os produtos registrados e suas respectivas quantidades disponíveis.
 * Cada produto é identificado por um código único inteiro e armazenado junto de sua quantidade
 * através de objetos do tipo {@link ProdutoEmEstoque}.
 * </p>
 * 
 * <p>
 * As principais funcionalidades incluem:
 * </p>
 * <ul>
 *   <li>Registrar e remover produtos - {@code registerProduct} e {@code deleteProduct}</li>
 *   <li>Adicionar e remover quantidades no estoque - {@code addStock} e {@code removeStock}</li>
 *   <li>Consultar informações sobre disponibilidade e nome de produtos - {@code getQuantityAvaible} e {@code getProductName}</li>
 *   <li>Listar todos os produtos em estoque - {@code listarProdutos}</li>
 * </ul>
 */
public class Estoque
{
    private Map<Integer, ProdutoEmEstoque> productsStock;
    
    /**
     * Construtor da classe {@code Estoque}.
     * Inicializa o mapa de controle de produtos ({@code Map<Integer, ProdutoEmEstoque>}) com uma instância vazia.
     */
    public Estoque()
    {
        productsStock = new HashMap<>();
    }

    /**
     * Retorna o mapa completo de produtos registrados no estoque.
     * 
     * @return um {@code Map<Integer, ProdutoEmEstoque>} com todos os produtos e suas quantidades.
     */
    public Map<Integer, ProdutoEmEstoque> getProductsStock()
    {
        return productsStock;
    }
    
    /**
     * Registra um novo produto no estoque com quantidade inicial zero.
     * 
     * @param product o {@link Produto} a ser registrado.
     * @return {@code true} se o produto foi registrado com sucesso;
     *         {@code false} se o código do produto já estiver registrado.
     */
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

    /**
     * Remove um produto do estoque com base no seu código.
     * 
     * @param code o código do produto a ser removido.
     * @return {@code true} se o produto foi removido com sucesso;
     *         {@code false} se o código não estiver registrado.
     */
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

    /**
     * Adiciona uma quantidade ao estoque de um produto já registrado.
     * 
     * @param code o código do produto.
     * @param quantity a quantidade a ser adicionada.
     * @return {@code true} se o produto existir e a quantidade for válida (> 0);
     *         {@code false} caso o produto não exista ou a quantidade seja invãlida (<= 0).
     */
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

    /**
     * Remove uma quantidade do estoque de um produto, se possível.
     * 
     * @param code o código do produto.
     * @param quantity a quantidade a ser removida.
     * @return {@code true} se o produto existir e houver quantidade suficiente;
     *         {@code false} caso o produto não exista ou não tenha quantidade suficiente para retirar.
     */
    public boolean removeStock(int code, int quantity)
    {
        if (!productsStock.containsKey(code) || quantity<=0)
            return false;
        
        if (productsStock.get(code).removeStock(quantity))
            return true;
        else
            return false;
    }

    /**
     * Retorna a quantidade disponível no estoque de um determinado produto.
     * 
     * @param code o código do produto.
     * @return a quantidade em estoque; ou 0 caso o produto não esteja registrado.
     */
    public int getQuantityAvaible(int code)
    {
        return (productsStock.containsKey(code)) ?  productsStock.get(code).getAmountStock() : 0;
    }
    
    /**
     * Retorna o nome de um produto com base no seu código.
     * 
     * @param code o código do produto.
     * @return o nome do produto se estiver registrado; ou {@code null} caso contrário.
     */
    public String getProductName(int code)
    {
        if (isProductInStock(code))
        {
            return productsStock.get(code).getProduct().getName();
        }
        else
            return null;
    }

    /**
     * Verifica se um produto está registrado no estoque.
     * 
     * @param code o código do produto.
     * @return {@code true} se o produto estiver no estoque; {@code false} caso contrário.
     */
    public boolean isProductInStock(int code)
    {
        return (productsStock.containsKey(code)) ?  true : false;
    }

    /**
     * Lista todos os produtos registrados no estoque, exibindo código, nome e quantidade.
     * 
     * Exibe uma mensagem no console caso não haja produtos cadastrados.
     */
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