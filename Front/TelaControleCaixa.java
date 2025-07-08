package Front;
import Back.*;
import javax.swing.*;
import javax.swing.border.BevelBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Tela de Visualização e Controle do Caixa.
 *
 * A classe {@link TelaControleCaixa} exibe o estado atual do caixa do sistema,
 * detalhando a quantidade de cada cédula e moeda disponível.
 * Ela oferece opções para o administrador gerenciar o dinheiro no caixa.
 *
 * Funcionalidades principais:
 * <ul>
 * <li>Exibe o valor total em dinheiro no caixa.</li>
 * <li>Mostra a contagem individual de cédulas (R$100, R$50, R$20, R$10, R$5, R$2)
 * e moedas (R$1, R$0.50, R$0.25, R$0.10, R$0.05).</li>
 * <li>Permite o acesso à tela para **adicionar dinheiro** ({@link TelaAdicionaCaixa}) ao caixa.</li>
 * <li>Permite o acesso à tela para **remover dinheiro** ({@link TelaRemoveCaixa}) do caixa.</li>
 * <li>Oferece um botão para retornar à tela de administração ({@link TelaAdmin}).</li>
 * </ul>
 *
 * Utiliza instâncias das classes {@link Estoque}, {@link Caixa} e {@link ControlePedidos}
 * para interagir com os dados do sistema, focando no controle financeiro.
 */
public class TelaControleCaixa extends JFrame
{
    private JTextField bill100TextField, bill50TextField, bill20TextField, bill10TextField,
                    bill5TextField, bill2TextField, coin1TextField, coin50TextField, coin25TextField,
                    coin10TextField, coin5TextField;

    /**
     * Construtor da classe {@link TelaControleCaixa}.
     *
     * Responsável por construir a interface de controle de caixa, exibindo o total
     * e as quantidades de cada denominação de dinheiro. Configura os botões para
     * adicionar ou remover dinheiro e para retornar à tela anterior.
     * Define o layout visual e as propriedades da janela.
     *
     * @param stock Instância de {@link Estoque} utilizada para gerenciar os produtos.
     * @param cashControl Instância de {@link Caixa} utilizada para controle financeiro.
     * @param orders Instância de {@link ControlePedidos} utilizada para manipular os pedidos feitos.
     */
    public TelaControleCaixa(Estoque stock, Caixa cashRegister, ControlePedidos orders)
    {
        setTitle("Visualizar Caixa");
        setSize(600,800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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
        JLabel subtitleLabel = new JLabel("Caixa Atual: R$ " + String.format("%.2f", cashRegister.getTotalCash()));
        subtitleLabel.setFont(new Font("Arial", Font.BOLD, 15));
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.WEST;
        mainPanel.add(subtitleLabel, gbc);

        gbc.insets = new Insets(20, 0, 0, 0);

        // -- Add Cash Label --
        JLabel addCashLabel = new JLabel("Caixa Atual em Cédulas");
        addCashLabel.setFont(new Font("Arial", Font.BOLD, 17));
        gbc.gridy = 2;
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.anchor = GridBagConstraints.CENTER;
        mainPanel.add(addCashLabel, gbc);

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
        bill100TextField.setText(String.valueOf(cashRegister.getQuantityBill(100)));
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
        bill50TextField.setText(String.valueOf(cashRegister.getQuantityBill(50)));
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
        bill20TextField.setText(String.valueOf(cashRegister.getQuantityBill(20)));
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
        bill10TextField.setText(String.valueOf(cashRegister.getQuantityBill(10)));
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
        bill5TextField.setText(String.valueOf(cashRegister.getQuantityBill(5)));
        bill5TextField.setFont(new Font("Arial", Font.BOLD, 14));
        gbc.gridx = 3;
        insertsPanel.add(bill5TextField, gbc);

        gbc.insets = new Insets(10, 10, 10, 0);

        // -- 2 Bill --
        JLabel bill2Label = new JLabel("R$ 2,00: ");
        bill2Label.setFont(new Font("Arial", Font.BOLD, 14));
        gbc.gridx = 4;
        insertsPanel.add(bill2Label, gbc);

        gbc.insets = new Insets(10, 0, 10, 0);

        bill2TextField = createTextField();
        bill2TextField.setText(String.valueOf(cashRegister.getQuantityBill(2)));
        bill2TextField.setFont(new Font("Arial", Font.BOLD, 14));
        gbc.gridx = 5;
        insertsPanel.add(bill2TextField, gbc);

        gbc.insets = new Insets(10, 10, 10, 0);
        
        // -- 1 Coin --
        JLabel coin1Label = new JLabel("R$ 1,00: ");
        coin1Label.setFont(new Font("Arial", Font.BOLD, 14));
        gbc.gridy = 2;
        gbc.gridx = 0;
        insertsPanel.add(coin1Label, gbc);

        gbc.insets = new Insets(10, 0, 10, 0);

        coin1TextField = createTextField();
        coin1TextField.setText(String.valueOf(cashRegister.getQuantityBill(1)));
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
        coin50TextField.setText(String.valueOf(cashRegister.getQuantityBill(0.5)));
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
        coin25TextField.setText(String.valueOf(cashRegister.getQuantityBill(0.25)));
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
        coin10TextField.setText(String.valueOf(cashRegister.getQuantityBill(0.1)));
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
        coin5TextField.setText(String.valueOf(cashRegister.getQuantityBill(0.05)));
        coin5TextField.setFont(new Font("Arial", Font.BOLD, 14));
        gbc.gridx = 3;
        insertsPanel.add(coin5TextField, gbc);

        gbc.insets = new Insets(5, 0, 0, 0);

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.anchor = GridBagConstraints.CENTER;
        mainPanel.add(insertsPanel, gbc);
        
        // -- Add Cash Button --
        JButton addButton = createCustomButton("Adicionar Dinheiro");
        gbc.gridy = 4;
        gbc.gridx = 0;
        gbc.anchor = GridBagConstraints.WEST;
        mainPanel.add(addButton, gbc);

        addButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed (ActionEvent e)
            {
                TelaAdicionaCaixa telaAdicionaCaixa = new TelaAdicionaCaixa(TelaControleCaixa.this, stock, cashRegister, orders);
                telaAdicionaCaixa.setVisible(true);
            }
            
        });

        // -- Remove Cash Button --
        JButton removeButton = createCustomButton("Remover Dinheiro");
        gbc.gridx = 2;
        gbc.anchor = GridBagConstraints.EAST;
        mainPanel.add(removeButton, gbc);

        removeButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed (ActionEvent e)
            {
                TelaRemoveCaixa telaRemoveCaixa = new TelaRemoveCaixa(TelaControleCaixa.this, stock, cashRegister, orders);
                telaRemoveCaixa.setVisible(true);
            }
            
        });

        gbc.insets = new Insets(40, 0, 0, 0);
        
        // -- Back Button --
        JButton backButton = createCustomButton("Voltar");
        backButton.setPreferredSize(new Dimension(80, 30));
        gbc.gridy = 5;
        gbc.gridx = 2; 
        gbc.anchor = GridBagConstraints.EAST;
        mainPanel.add(backButton, gbc);

        backButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                System.out.println("Voltando");
                TelaAdmin telaAdmin = new TelaAdmin(stock, cashRegister, orders);
                telaAdmin.setVisible(true);
                dispose();
            }
            
        });
        

        add(mainPanel);
    }

    /**
     * Método auxiliar para criar um {@link JTextField} padronizado para exibir quantidades de dinheiro.
     *
     * O campo de texto é configurado como **não editável**, possui uma cor de fundo específica,
     * uma borda de chanfro rebaixada e um tamanho preferencial fixo.
     *
     * @return Um {@link JTextField} configurado para exibição de valores.
     */
    public JTextField createTextField()
    {
        JTextField textField = new JTextField();
        textField.setEditable(false);
        textField.setBackground(new Color(172,179,174)); 
        textField.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
        textField.setPreferredSize(new Dimension(80, 25));

        return textField;
    } 

    /**
     * Método auxiliar para criação de botões estilizados usados na {@link TelaControleCaixa}.
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
        button.setPreferredSize(new Dimension(160, 30));
        button.setBorderPainted(false);
        button.setFocusPainted(false);

        return button;
    }  
}
