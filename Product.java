import java.time.LocalDate;

public class Product 
{
    private int code;
    private String name;
    private double price;
    private LocalDate expDate;

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

    public double getPrice() 
    {
        return price;
    }
    public void setPrice(double price)
    {
        this.price = price;
    }

    public LocalDate getExpDate() 
    {
        return expDate;
    }
    public void setExpDate(LocalDate expDate) 
    {
        this.expDate = expDate;
    }

    public Product(int code, String name, double price)
    {
        this.code = code;
        this.name = name;
        this.price = price;
        this.expDate = null;
    }

    public Product(int code, String name, double price, LocalDate expDate)
    {
        this.code = code;
        this.name = name;
        this.price = price;
        this.expDate = expDate;
    }
}
