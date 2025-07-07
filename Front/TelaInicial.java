package Front;
import Back.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Gera a Tela Inicial do Sistema, com botões de acesso à área do Usuário ({@code JButton userButton}),
 * que leva para a Tela Principal do usuário, a Tela para Iniciar Compra ({@link TelaIniciarCompra}), e
 * acesso à área de Administração do sistema ({@code JButton admButton}), que leva para a Tela Principal
 * de Administração do Sistema, a Tela Admin ({@link TelaAdmin}).
 * 
 * O Acesso a Tela de Administração do Sistema somente ocorrerá se o usuário realizar o login de 
 * administrador, que é entregue ao usuário, quando clica no botão {@code JButton admButton}, pela
 * {@link TelaLogin}.
 */
public class TelaInicial extends JFrame
{
    /**
     * Construtor da classe {@link TelaInicial}.
     * 
     * Responsável por desenhar ao usuário toda a estrutura da tela, seus botões, realizar o controle
     * e direcionamento dos botões e verificação de sucesso no login de administrador para direcionar
     * o sistema para a {@link TelaAdmin}.
     * 
     * @param stock Objeto da classe {@link Estoque}, responsável por registrar todo o controle de 
     * estoque do sistema. Iniciado na tela de Start do sistema ({@link StartApp}) e enviado como
     * parâmetro por todo o sistema.
     * @param cashControl Objeto da classe {@link Caixa}, responsável por registrar todo o controle de 
     * Caixa do sistema. Iniciado na tela de Start do sistema ({@link StartApp}) e enviado como
     * parâmetro por todo o sistema.
     * @param orders Objeto da classe {@link ControlePedidos}, responsável por registrar todos os pedidos
     * realizados no sistema. Iniciado na tela de Start do sistema ({@link StartApp}) e enviado como
     * parâmetro por todo o sistema.
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
     * Método auxiliar para criação e configuração visual dos botões da Tela {@link TelaInicial}.
     * @param text String que aparecerá no botão.
     * @return retorna um {@link JButton} formatado visualmente.
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
