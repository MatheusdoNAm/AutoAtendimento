package Back;
import java.util.*;

/**
 * Representa o caixa do sistema, responsável por controlar fisicamente as cédulas e moedas disponíveis.
 * 
 * <p>
 * A classe {@code Caixa} gerencia o dinheiro em espécie usado nas operações de pagamento e troco.
 * Mantém um mapa de denominações monetárias ({@code double}) associadas às suas respectivas quantidades ({@code int}),
 * permitindo as seguintes operações:
 * </p>
 * 
 * <ul>
 *   <li>Adicionar e remover cédulas ou moedas específicas - {@code addCash} e {@code removeCash}</li>
 *   <li>Calcular troco automaticamente com base nas denominações disponíveis -{@code calculateChange}</li>
 *   <li>Consultar o total em caixa - {@code getTotalCash}</li>
 *   <li>Zerar o conteúdo do caixa - {@code clearCashControl}</li>
 *   <li>Verificar a quantidade de uma determinada denominação - {@code getQuantityBill}</li>
 * </ul>
 * 
 * <p>
 * O mapa {@code cashControl} armazena as denominações em ordem decrescente
 * (do maior valor para o menor), o que facilita a lógica de cálculo de troco com uso de notas maiores primeiro.
 * </p>
 */
public class Caixa
{
    private final TreeMap<Double, Integer> cashControl = new TreeMap<>(Collections.reverseOrder());

    /**
     * Construtor da classe {@link Caixa}.
     * 
     * Inicializa o controle de caixa {@code cashControl} com todas as denominações de cédulas e moedas
     * utilizadas no sistema, atribuindo a cada uma a quantidade inicial igual a zero.
     * 
     * As denominações incluídas são:
     * <ul>
     *   <li>100.00; 50.00; 20.00; 10.00; 5.00; 2.00</li>
     *   <li>1.00; 0.50; 0.20; 0.10; 0.05</li>
     * </ul>
     * 
     * Este construtor garante que todas as denominações estejam representadas no caixa,
     * mesmo que não haja nenhuma unidade disponível no momento da criação do objeto.
     */
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

    /**
     * Retorna o mapa que representa o controle de cédulas e moedas disponíveis no caixa.
     * 
     * O mapa contém as denominações como chave ({@code Double}) e a quantidade de cada uma como valor ({@code Integer}).
     * 
     * @return um {@code Map<Double, Integer>} representando as denominações e suas respectivas quantidades no caixa.
     */
    public Map<Double, Integer> getCashControl()
    {
        return cashControl;
    }

    /**
     * Adiciona uma determinada quantidade de uma denominação específica ao controle de caixa.
     * 
     * Se a denominação informada já existir no mapa {@code cashControl}, a quantidade fornecida
     * será somada à quantidade atual daquela denominação.
     * 
     * @param cash a denominação da cédula ou moeda a ser adicionada (ex: 10.0, 0.5).
     * @param quantity a quantidade a ser adicionada da denominação especificada.
     * @return {@code true} se a denominação existir e a adição for realizada com sucesso;
     *         {@code false} caso contrário (denominação não cadastrada no caixa).
     */
    public boolean addCash(double cash, int quantity)
    {
        if (cashControl.containsKey(cash))
        {
            cashControl.put(cash, cashControl.get(cash) + quantity);
            return true;
        }
        return false;
    }

    /**
     * Remove uma determinada quantidade de uma denominação específica do controle de caixa.
     * 
     * Se a denominação existir no mapa {@code cashControl} e houver quantidade suficiente disponível,
     * a quantidade especificada será subtraída da quantidade atual.
     * 
     * @param cash a denominação da cédula ou moeda a ser removida (ex: 50.0, 0.25).
     * @param quantity a quantidade a ser removida da denominação especificada.
     * @return {@code true} se a denominação existir e houver quantidade suficiente para remoção;
     *         {@code false} se a denominação não existir ou se a quantidade disponível for insuficiente.
     */
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

    /**
     * Calcula o troco com base no valor informado e nas denominações disponíveis no caixa.
     * 
     * O método tenta utilizar as maiores denominações possíveis para formar o troco, respeitando a
     * quantidade disponível de cada uma. Caso o troco não possa ser completado, desfaz as alterações
     * no caixa e retorna {@code null}.
     * 
     * @param change o valor total de troco a ser fornecido.
     * @return um mapa com as denominações e quantidades utilizadas no troco, ou {@code null} se o troco não puder ser formado.
     */
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
                cashControl.put(entry.getKey(), cashControl.get(entry.getKey()) + entry.getValue());
            return null;
        }
        return result;
    } 

    /**
     * Retorna o valor total de dinheiro disponível no caixa.
     * 
     * O total é calculado multiplicando cada denominação pela quantidade disponível no caixa.
     * 
     * @return o valor total em dinheiro disponível.
     */
    public Double getTotalCash()
    {
        double totalCash = 0.0;

        for(Map.Entry<Double, Integer> entry : cashControl.entrySet())
            totalCash += entry.getKey() * entry.getValue();

        return totalCash; 
    }

    /**
     * Zera todas as quantidades de cédulas e moedas no controle de caixa.
     * 
     * Após a execução, todas as denominações continuarão registradas, mas com quantidade igual a zero.
     */
    public void clearCashControl()
    {
        for (Double value : cashControl.keySet())
            cashControl.put(value, 0);
    }

    /**
     * Retorna a quantidade disponível de uma determinada denominação no caixa.
     * 
     * @param cash a denominação a ser consultada (ex: 5.0, 0.25).
     * @return a quantidade disponível da denominação, ou {@code 0} se ela não estiver registrada.
     */
    public int getQuantityBill(double cash)
    {
        if (cashControl.containsKey(cash))
            return cashControl.get(cash);
        return 0;
    }
}