package Front;
import Back.*;
import javax.swing.*;
import javax.swing.border.BevelBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

/**
 * Diálogo para remover cédulas e moedas do controle de caixa.
 *
 * A classe {@link TelaRemoveCaixa} permite ao administrador remover quantidades específicas
 * de notas e moedas do caixa, atualizando o total e as contagens individuais.
 * Fornece também a opção de zerar completamente o caixa.
 *
 * As operações incluem:
 * <ul>
 * <li>Remoção de cédulas e moedas com base na entrada do usuário.</li>
 * <li>Validação para garantir que a quantidade de cédulas/moedas a ser removida não exceda a disponível.</li>
 * <li>Opção de zerar todo o caixa.</li>
 * <li>Atualização da tela de controle de caixa principal ({@link TelaControleCaixa}).</li>
 * <li>Tratamento de erros para entradas não numéricas.</li>
 * </ul>
 */
public class TelaRemoveCaixa extends JDialog
{
    private JTextField bill100TextField, bill50TextField, bill20TextField, bill10TextField,
                    bill5TextField, bill2TextField, coin1TextField, coin50TextField, coin25TextField,
                    coin10TextField, coin5TextField;
    private JLabel subtitleLabel;

    /**
     * Construtor da classe {@link TelaRemoveCaixa}.
     *
     * Configura a janela de diálogo, seus componentes visuais para entrada de valuees
     * de cédulas e moedas, e os listeners de eventos para os botões de "Remover" e "Zerar Caixa".
     *
     * @param telaControleCaixa A instância de {@link TelaControleCaixa} para recarregar a tela após as operações.
     * @param owner O {@link JFrame} pai desta janela de diálogo.
     * @param stock Instância de {@link Estoque} utilizada para gerenciar os produtos.
     * @param cashControl Instância de {@link Caixa} utilizada para controle financeiro.
     * @param orders Instância de {@link ControlePedidos} utilizada para manipular os pedidos feitos.
     */
    public TelaRemoveCaixa(TelaControleCaixa telaControleCaixa, JFrame owner, Estoque stock, Caixa cashControl, ControlePedidos orders)
    {
        super(owner, "Remover do Caixa", true);
        setSize(480,450);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        getContentPane().setBackground(new Color(197,202,196));
        setLayout(new BorderLayout());

        JPanel mainPanel = new JPanel (new GridBagLayout());
        mainPanel.setBackground(new Color(197,202,196));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(0, 0, 5, 0);

        // -- Title Label --
        JLabel titleLabel = new JLabel("Controle de Caixa");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 30));
        titleLabel.setForeground(new Color(40,40,40));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.anchor = GridBagConstraints.CENTER;
        mainPanel.add(titleLabel, gbc);

        // -- Subtitle Label --
        subtitleLabel = new JLabel("Caixa Atual: R$ " + String.format("%.2f", cashControl.getTotalCash()));
        subtitleLabel.setFont(new Font("Arial", Font.BOLD, 15));
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.WEST;
        mainPanel.add(subtitleLabel, gbc);

        gbc.insets = new Insets(20, 0, 0, 0);

        // -- Remove Cash Label --
        JLabel removeCashLabel = new JLabel("Remover Dinheiro do Caixa");
        removeCashLabel.setFont(new Font("Arial", Font.BOLD, 17));
        gbc.gridy = 2;
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.anchor = GridBagConstraints.CENTER;
        mainPanel.add(removeCashLabel, gbc);

        JPanel insertsPanel = new JPanel (new GridBagLayout());
        insertsPanel.setBackground(new Color(197,202,196));

        gbc.insets = new Insets(0, 0, 0, 0);

        // -- 100 Bill --
        JLabel bill100Label = new JLabel("R$ 100,00: ");
        bill100Label.setFont(new Font("Arial", Font.BOLD, 14));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.CENTER;
        insertsPanel.add(bill100Label, gbc);

        bill100TextField = createTextField();
        bill100TextField.setFont(new Font("Arial", Font.BOLD, 14));
        gbc.gridx = 1;
        insertsPanel.add(bill100TextField, gbc);
    
        gbc.insets = new Insets(10, 10, 10, 0);
        
        // -- 50 Bill --
        JLabel bill50Label = new JLabel("R$ 50,00: ");
        bill50Label.setFont(new Font("Arial", Font.BOLD, 14));
        gbc.gridx = 2;
        insertsPanel.add(bill50Label, gbc);

        gbc.insets = new Insets(10, 0, 10, 0);

        bill50TextField = createTextField();
        bill50TextField.setFont(new Font("Arial", Font.BOLD, 14));
        gbc.gridx = 3;
        insertsPanel.add(bill50TextField, gbc);

        gbc.insets = new Insets(10, 10, 10, 0);
        
        // -- 20 Bill --
        JLabel bill20Label = new JLabel("R$ 20,00: ");
        bill20Label.setFont(new Font("Arial", Font.BOLD, 14));
        gbc.gridx = 4;
        insertsPanel.add(bill20Label, gbc);

        gbc.insets = new Insets(10, 0, 10, 0);

        bill20TextField = createTextField();
        bill20TextField.setFont(new Font("Arial", Font.BOLD, 14));
        gbc.gridx = 5;
        insertsPanel.add(bill20TextField, gbc);

        // -- 10 Bill --
        JLabel bill10Label = new JLabel("R$ 10,00: ");
        bill10Label.setFont(new Font("Arial", Font.BOLD, 14));
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.CENTER;
        insertsPanel.add(bill10Label, gbc);

        gbc.insets = new Insets(10, 0, 10, 0);

        bill10TextField = createTextField();
        bill10TextField.setFont(new Font("Arial", Font.BOLD, 14));
        gbc.gridx = 1;
        insertsPanel.add(bill10TextField, gbc);
    
        gbc.insets = new Insets(10, 10, 10, 0);
        
        // -- 5 Bill --
        JLabel bill5Label = new JLabel("R$ 5,00: ");
        bill5Label.setFont(new Font("Arial", Font.BOLD, 14));
        gbc.gridx = 2;
        insertsPanel.add(bill5Label, gbc);

        gbc.insets = new Insets(10, 0, 10, 0);

        bill5TextField = createTextField();
        bill5TextField.setFont(new Font("Arial", Font.BOLD, 14));
        gbc.gridx = 3;
        insertsPanel.add(bill5TextField, gbc);

        gbc.insets = new Insets(10, 10, 10, 0);
        
        // -- 2 Coin --
        JLabel bill2Label = new JLabel("R$ 2,00: ");
        bill2Label.setFont(new Font("Arial", Font.BOLD, 14));
        gbc.gridx = 4;
        insertsPanel.add(bill2Label, gbc);

        gbc.insets = new Insets(10, 0, 10, 0);

        bill2TextField = createTextField();
        bill2TextField.setFont(new Font("Arial", Font.BOLD, 14));
        gbc.gridx = 5;
        insertsPanel.add(bill2TextField, gbc);

        gbc.insets = new Insets(10, 0, 10, 0);

        // -- 1 Coin --
        JLabel coin1Label = new JLabel("R$ 1,00: ");
        coin1Label.setFont(new Font("Arial", Font.BOLD, 14));
        gbc.gridx = 0;
        gbc.gridy = 2;
        insertsPanel.add(coin1Label, gbc);

        coin1TextField = createTextField();
        coin1TextField.setFont(new Font("Arial", Font.BOLD, 14));
        gbc.gridx = 1;
        insertsPanel.add(coin1TextField, gbc);

        // -- 0.5 Coin --
        JLabel coin50Label = new JLabel("R$ 0,50: ");
        coin50Label.setFont(new Font("Arial", Font.BOLD, 14));
        gbc.gridx = 2;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.CENTER;
        insertsPanel.add(coin50Label, gbc);

        gbc.insets = new Insets(10, 0, 10, 0);

        coin50TextField = createTextField();
        coin50TextField.setFont(new Font("Arial", Font.BOLD, 14));
        gbc.gridx = 3;
        insertsPanel.add(coin50TextField, gbc);
    
        gbc.insets = new Insets(10, 10, 10, 0);
        
        // -- 0.25 Coin --
        JLabel coin25Label = new JLabel("R$ 0,25: ");
        coin25Label.setFont(new Font("Arial", Font.BOLD, 14));
        gbc.gridx = 4;
        insertsPanel.add(coin25Label, gbc);

        gbc.insets = new Insets(10, 0, 10, 0);

        coin25TextField = createTextField();
        coin25TextField.setFont(new Font("Arial", Font.BOLD, 14));
        gbc.gridx = 5;
        insertsPanel.add(coin25TextField, gbc);

        gbc.insets = new Insets(10, 10, 10, 0);
        
        // -- 0.1 Coin --
        JLabel coin10Label = new JLabel("R$ 0,10: ");
        coin10Label.setFont(new Font("Arial", Font.BOLD, 14));
        gbc.gridy = 3;
        gbc.gridx = 0;
        insertsPanel.add(coin10Label, gbc);

        gbc.insets = new Insets(10, 0, 10, 0);

        coin10TextField = createTextField();
        coin10TextField.setFont(new Font("Arial", Font.BOLD, 14));
        gbc.gridx = 1;
        insertsPanel.add(coin10TextField, gbc);

        // -- 0.05 Coin --
        JLabel coin5Label = new JLabel("R$ 0,05: ");
        coin5Label.setFont(new Font("Arial", Font.BOLD, 14));
        gbc.gridx = 2;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.CENTER;
        insertsPanel.add(coin5Label, gbc);

        gbc.insets = new Insets(10, 0, 10, 0);

        coin5TextField = createTextField();
        coin5TextField.setFont(new Font("Arial", Font.BOLD, 14));
        gbc.gridx = 3;
        insertsPanel.add(coin5TextField, gbc);

        gbc.insets = new Insets(5, 0, 0, 0);

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.anchor = GridBagConstraints.WEST;
        mainPanel.add(insertsPanel, gbc);

        // -- Remove Button --
        gbc.insets = new Insets(10, 0, 10, 0);

        JButton removeButton = createCustomButton("Remover do Caixa");
        gbc.gridy = 4;
        mainPanel.add(removeButton, gbc);

        removeButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed (ActionEvent e)
            {
                System.out.println("Removendo");

                try {
                    Map<Double, Integer> cashToRemove = new HashMap<>();
                    cashToRemove.put(100.0, Integer.parseInt(bill100TextField.getText()));
                    cashToRemove.put(50.0, Integer.parseInt(bill50TextField.getText()));
                    cashToRemove.put(20.0, Integer.parseInt(bill20TextField.getText()));
                    cashToRemove.put(10.0, Integer.parseInt(bill10TextField.getText()));
                    cashToRemove.put(5.0, Integer.parseInt(bill5TextField.getText()));
                    cashToRemove.put(2.0, Integer.parseInt(bill2TextField.getText()));
                    cashToRemove.put(1.0, Integer.parseInt(coin1TextField.getText()));
                    cashToRemove.put(0.5, Integer.parseInt(coin50TextField.getText()));
                    cashToRemove.put(0.25, Integer.parseInt(coin25TextField.getText()));
                    cashToRemove.put(0.10, Integer.parseInt(coin10TextField.getText()));
                    cashToRemove.put(0.05, Integer.parseInt(coin5TextField.getText()));

                    for (Map.Entry<Double, Integer> entry : cashToRemove.entrySet()) {
                        double value = entry.getKey();
                        int quantity = entry.getValue();

                        if (cashControl.getQuantityBill(value) < quantity) {
                            JOptionPane.showMessageDialog(TelaRemoveCaixa.this,
                                    "Quantidade insuficiente em Caixa",
                                    "Falta de Notas",
                                    JOptionPane.ERROR_MESSAGE);
                            return;
                        }
                    }

                    for (Map.Entry<Double, Integer> entry : cashToRemove.entrySet()) {
                        cashControl.removeCash(entry.getKey(), entry.getValue());
                    }

                    bill100TextField.setText("0");
                    bill50TextField.setText("0");
                    bill20TextField.setText("0");
                    bill10TextField.setText("0");
                    bill5TextField.setText("0");
                    bill2TextField.setText("0");
                    coin1TextField.setText("0");
                    coin50TextField.setText("0");
                    coin25TextField.setText("0");
                    coin10TextField.setText("0");
                    coin5TextField.setText("0");
                    subtitleLabel.setText("Caixa Atual: R$ " + String.format("%.2f", cashControl.getTotalCash()));

                    telaControleCaixa.reloadScreen();

                    JOptionPane.showMessageDialog(TelaRemoveCaixa.this,
                            "Cédulas Removidas do caixa!",
                            "Removido com Sucesso",
                            JOptionPane.INFORMATION_MESSAGE);

                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(TelaRemoveCaixa.this,
                            "Por favor, preencha todos os campos com números inteiros válidos.",
                            "Erro de Entrada",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // -- Clear Cash Button --
        JButton clearButton = createCustomButton("Zerar Caixa");
        clearButton.setBackground(new Color(220, 53, 69));
        gbc.gridx = 2;
        gbc.anchor = GridBagConstraints.EAST;
        mainPanel.add(clearButton, gbc);

        clearButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed (ActionEvent e)
            {
                int option = JOptionPane.showConfirmDialog
                (
                    TelaRemoveCaixa.this,
                    "Tem certeza que deseja Zerar o Caixa?",
                    "Confirmação Zerar Caixa",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE
                );

                if (option == JOptionPane.YES_OPTION)
                {   
                    cashControl.clearCashControl();
                    telaControleCaixa.reloadScreen();
                    bill100TextField.setText("0");
                    bill50TextField.setText("0");
                    bill20TextField.setText("0");
                    bill10TextField.setText("0");
                    bill5TextField.setText("0");
                    bill2TextField.setText("0");
                    coin1TextField.setText("0");
                    coin50TextField.setText("0");
                    coin25TextField.setText("0");
                    coin10TextField.setText("0");
                    coin5TextField.setText("0");
                    subtitleLabel.setText("Caixa Atual: R$ " + String.format("%.2f", cashControl.getTotalCash()));
                    JOptionPane.showMessageDialog(TelaRemoveCaixa.this, "Caixa Zerado com Sucesso!", "Caixa Zerado", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });
            
        // -- Back Button --
        JButton backButton = createCustomButton("Voltar");
        backButton.setPreferredSize(new Dimension(80, 30));
        gbc.gridy = 5;
        gbc.gridx = 2;
        mainPanel.add(backButton, gbc);

        backButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                System.out.println("Voltando");
                dispose();
            }
        });

        add(mainPanel);
    }

    /**
     * Cria um campo de texto ({@link JTextField}) predefinido para entrada de quantidades de cédulas/moedas.
     * O campo é inicializado com o value "0" e possui um estilo visual específico.
     *
     * @return Um {@link JTextField} estilizado.
     */
    public JTextField createTextField()
    {
        JTextField textField = new JTextField("0");
        textField.setBackground(new Color(172,179,174)); 
        textField.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
        textField.setPreferredSize(new Dimension(50, 25));

        return textField;
    }

    /**
     * Método auxiliar para criação de botões estilizados usados na {@link TelaRemoveCaixa}.
     * 
     * Configura cor de fundo, cor da fonte, fonte, borda e tamanho do botão.
     *
     * @param text Texto a ser exibido no botão.
     * @return {@link JButton} estilizado e formatado para o layout da tela.
     */
    public JButton createCustomButton (String text)
    {
        JButton button = new JButton(text);

        button.setForeground(Color.WHITE);
        button.setBackground(new Color(0,86,179));
        button.setFont(new Font("Arial", Font.BOLD, 13));
        button.setFocusPainted(false);
        button.setPreferredSize(new Dimension(160, 30));
        button.setBorderPainted(false);
        button.setFocusPainted(false);

        return button;
    } 
}
