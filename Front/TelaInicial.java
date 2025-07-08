package Front;
import Back.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Tela Inicial do Sistema.
 *
 * A classe {@link TelaInicial} representa a primeira interface exibida ao usuário 
 * ao abrir o sistema. Ela oferece duas opções principais de acesso:
 * <ul>
 *     <li>Acesso como usuário comum (cliente);</li>
 *     <li>Acesso como administrador (requer login);</li>
 * </ul>
 *
 * Ao selecionar uma das opções, o sistema encaminha para a tela correspondente:
 * <ul>
 *     <li>{@link TelaIniciarCompra} para usuários;</li>
 *     <li>{@link TelaLogin} e, após autenticação, {@link TelaAdmin} para administradores.</li>
 * </ul>
 * 
 * Utiliza instâncias das classes {@link Estoque}, {@link Caixa} e {@link ControlePedidos}
 * para garantir continuidade dos dados entre as telas.
 */
public class TelaInicial extends JFrame
{

    /**
     * Construtor da classe {@link TelaInicial}.
     *
     * Responsável por construir a interface inicial, configurando os botões de acesso
     * para usuário e administrador. Também define o layout visual da aplicação.
     * 
     * @param stock Instância de {@link Estoque} utilizada para gerenciar os produtos.
     * @param cashControl Instância de {@link Caixa} utilizada para controle financeiro.
     * @param orders Instância de {@link ControlePedidos} utilizada para manipular os pedidos feitos.
     */
    public TelaInicial(Estoque stock, Caixa cashControl, ControlePedidos orders)
    {
        setTitle("Tela Inicial");
        setSize(600,800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        getContentPane().setBackground(new Color(197,202,196));
        setLayout(new BorderLayout());

        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBackground(new Color(197,202,196));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 25, 10);

        // -- Title Label --
        JLabel titleLabel = new JLabel("Bem-vindo(a) à Cantina URI!");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 40));
        titleLabel.setForeground(new Color(40,40,40));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.anchor = GridBagConstraints.NORTH;
        mainPanel.add(titleLabel, gbc);

        gbc.insets = new Insets(10, 10, 10, 10);

        // -- SubTitle Label --
        JLabel subtitleLabel = new JLabel("Escolha o tipo de acesso:");
        subtitleLabel.setFont(new Font("Arial", Font.PLAIN, 20));
        gbc.gridy = 1;
        mainPanel.add(subtitleLabel, gbc);

        // -- User Acess Button --
        JButton userButton = createCustomButton("Tela de Usuário");
        gbc.gridy = 2;
        mainPanel.add(userButton, gbc);

        userButton.addActionListener(new ActionListener() 
        {
            @Override
            public void actionPerformed (ActionEvent e)
            {
                TelaIniciarCompra telaIniciarCompra = new TelaIniciarCompra(stock, cashControl, orders);
                telaIniciarCompra.setVisible(true);
                dispose();
            }
            
        });

        // -- Adm Acess Button --
        JButton admButton = createCustomButton("Tela de Administrador");
        gbc.gridy = 3;
        mainPanel.add(admButton, gbc);

        admButton.addActionListener(new ActionListener() 
        {
            @Override
            public void actionPerformed (ActionEvent e)
            {
                System.out.println("Acessando Tela de Login");
                TelaLogin telaLogin = new TelaLogin(TelaInicial.this);
                telaLogin.setVisible(true);

                if (telaLogin.isLoginSucessful())
                {
                    System.out.println("Acessando Tela de Administração");
                    TelaAdmin telaAdmin = new TelaAdmin(stock, cashControl, orders);
                    telaAdmin.setVisible(true);
                    dispose();
                }
            }     
        });
        add(mainPanel);  
    }

    /**
     * Método auxiliar para criação de botões estilizados usados na {@link TelaInicial}.
     * 
     * Configura cor de fundo, cor da fonte, fonte, borda e tamanho do botão.
     *
     * @param text Texto a ser exibido no botão.
     * @return {@link JButton} estilizado e formatado para o layout da tela.
     */
    private JButton createCustomButton(String text)
    {
        JButton button = new JButton(text);

        button.setBackground(new Color(0,86,179));
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Arial", Font.BOLD, 20));
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setPreferredSize(new Dimension(250,140));

        return button;
    } 
}
