package Back;
import java.time.LocalDate;

public class Produto
{
    private int code;
    private String name;
    private String type;
    private double price;
    private LocalDate validity;

    public int getCode()
    {
        return code;
    }
    public void setCode(int code)
    {
        this.code = code;
    }

    public String getName()
    {
        return name;
    }
    public void setName(String name)
    {
        this.name = name;
    }
    
    public String getType()
    {
        return type;
    }
    public void setType(String type)
    {
        this.type = type;
    }

    public double getPrice()
    {
        return price;
    }
    public void setPrice(double price)
    {
        this.price = price;
    }

    public LocalDate getValidity()
    {
        return validity;
    }
    public void setValidity(LocalDate validity)
    {
        this.validity = validity;
    }

    public Produto(int code, String name, String type, double price)
    {
        this.code = code;
        this.name = name;
        this.type = type;
        this.price = price;
        this.validity = null;
    }
    public Produto(int code, String name, String type, double price, LocalDate validity)
    {
        this.code = code;
        this.name = name;
        this.type = type;
        this.price = price;
        this.validity = validity;
    }
}
