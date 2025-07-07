package Back;

import java.time.LocalDate;

/**
 * Representa um produto comercializado no sistema.
 * 
 * Cada produto possui um código identificador, nome, tipo, preço e, opcionalmente,
 * uma data de validade (útil para produtos perecíveis).
 */
public class Produto
{
    private int code;
    private String name;
    private String type;
    private double price;
    private LocalDate validity;

    /**
     * Retorna o código identificador do produto.
     * 
     * @return o código do produto.
     */
    public int getCode()
    {
        return code;
    }

    /**
     * Define o código identificador do produto.
     * 
     * @param code o código a ser atribuído ao produto.
     */
    public void setCode(int code)
    {
        this.code = code;
    }

    /**
     * Retorna o nome do produto.
     * 
     * @return o nome do produto.
     */
    public String getName()
    {
        return name;
    }

    /**
     * Define o nome do produto.
     * 
     * @param name o nome a ser atribuído ao produto.
     */
    public void setName(String name)
    {
        this.name = name;
    }

    /**
     * Retorna o tipo ou categoria do produto.
     * 
     * @return o tipo do produto.
     */
    public String getType()
    {
        return type;
    }

    /**
     * Define o tipo ou categoria do produto.
     * 
     * @param type o tipo a ser atribuído ao produto.
     */
    public void setType(String type)
    {
        this.type = type;
    }

    /**
     * Retorna o preço unitário do produto.
     * 
     * @return o preço do produto.
     */
    public double getPrice()
    {
        return price;
    }

    /**
     * Define o preço unitário do produto.
     * 
     * @param price o preço a ser atribuído ao produto.
     */
    public void setPrice(double price)
    {
        this.price = price;
    }

    /**
     * Retorna a data de validade do produto (se aplicável).
     * 
     * @return a data de validade, ou {@code null} se não tiver validade definida.
     */
    public LocalDate getValidity()
    {
        return validity;
    }

    /**
     * Define a data de validade do produto.
     * 
     * @param validity a data de validade a ser atribuída.
     */
    public void setValidity(LocalDate validity)
    {
        this.validity = validity;
    }

    /**
     * Construtor do produto sem validade definida.
     * 
     * @param code o código do produto.
     * @param name o nome do produto.
     * @param type o tipo do produto.
     * @param price o preço do produto.
     */
    public Produto(int code, String name, String type, double price)
    {
        this.code = code;
        this.name = name;
        this.type = type;
        this.price = price;
        this.validity = null;
    }

    /**
     * Construtor completo do produto, com validade definida.
     * 
     * @param code o código do produto.
     * @param name o nome do produto.
     * @param type o tipo do produto.
     * @param price o preço do produto.
     * @param validity a data de validade do produto.
     */
    public Produto(int code, String name, String type, double price, LocalDate validity)
    {
        this.code = code;
        this.name = name;
        this.type = type;
        this.price = price;
        this.validity = validity;
    }
}