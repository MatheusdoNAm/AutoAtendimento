package Back;
import java.util.*;

public class Caixa
{
    private final TreeMap<Double, Integer> cashControl = new TreeMap<>(Collections.reverseOrder());

    public Caixa()
    {
        cashControl.put(100.0, 0);
        cashControl.put(50.0, 0);
        cashControl.put(20.0, 0);
        cashControl.put(10.0, 0);
        cashControl.put(5.0, 0);
        cashControl.put(2.0, 0);
        cashControl.put(1.0, 0);
        cashControl.put(0.5, 0);
        cashControl.put(0.25, 0);
        cashControl.put(0.1, 0);
        cashControl.put(0.05, 0);
    }

    public Map<Double, Integer> getCashControl()
    {
        return cashControl;
    }

    public boolean addCash(double cash, int quantity)
    {
        if (cashControl.containsKey(cash))
        {
            cashControl.put(cash, cashControl.get(cash) + quantity);
            return true;
        }
        return false;
    }

    public boolean removeCash(double cash, int quantity)
    {
        if (cashControl.containsKey(cash))
        {
            if (cashControl.get(cash) < quantity)
                return false;
            else
            {
                cashControl.put(cash, cashControl.get(cash) - quantity);
                return true;
            }
        }
        return false;
    }

    public Map<Double, Integer> calculateChange (double change)
    {   
        change = Math.ceil(change * 20.0) / 20.0;
        Map<Double, Integer> result = new LinkedHashMap<>();
        TreeMap<Double, Integer> copyCashControl = new TreeMap<>(cashControl);

        for(Map.Entry<Double, Integer> entry : copyCashControl.entrySet())
        {
            double value = entry.getKey();
            int quantity = entry.getValue();
            int uses = 0;

            while (change >= value && quantity > 0)
            {
                change = Math.round((change - value) * 100.0) / 100.0;
                quantity--;
                uses++;
            }

            if (uses > 0)
            {
                result.put(value, uses);
                cashControl.put(value, cashControl.get(value) - uses);
            }
        }

        if (change > 0.009)
        {
            for (Map.Entry<Double, Integer> entry : result.entrySet())
            {
                cashControl.put(entry.getKey(), cashControl.get(entry.getKey()) + entry.getValue());
            }
            return null;
        }

        return result;
    } 

    public Double getTotalCash()
    {
        double totalCash = 0.0;

        for(Map.Entry<Double, Integer> entry : cashControl.entrySet())
            totalCash += entry.getKey() * entry.getValue();

        return totalCash; 
    }

    public void clearCashControl()
    {
        for (Double value : cashControl.keySet())
        {
            cashControl.put(value, 0);
        }
    }

    public int getQuantityBill(double cash)
    {
        if (cashControl.containsKey(cash))
        {
            return cashControl.get(cash);
        }
        return 0;
    }
}