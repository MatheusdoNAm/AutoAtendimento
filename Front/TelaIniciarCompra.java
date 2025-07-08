package Front;
import Back.*;
import javax.swing.*;
import java.awt.*;

/**
 * Tela Inicial para Iniciar Compras.
 *
 * A classe {@link TelaIniciarCompra} atua como a interface inicial para usuários
 * comuns que desejam realizar compras no sistema de cantina. Ela oferece a opção
 * principal para iniciar um novo pedido no modo de autoatendimento.
 *
 * Funcionalidades principais:
 * <ul>
 * <li>Permite iniciar um novo pedido, direcionando o usuário para a tela de autoatendimento ({@link TelaAutoatendimento}).</li>
 * <li>Oferece uma opção para "Sair", que, neste contexto de usuário comum,
 * redireciona para a {@link TelaLogin} para validação de administrador, visto que um usuário comum não deve poder sair do terminal,
 * e após login bem-sucedido, volta para a {@link TelaInicial} (tela principal de seleção de perfil).</li>
 * </ul>
 *
 * Utiliza instâncias das classes {@link Estoque}, {@link Caixa} e {@link ControlePedidos}
 * para manter a continuidade dos dados e funcionalidades do sistema ao transitar entre as telas.
 */
public class TelaIniciarCompra extends JFrame
{
    /**
     * Construtor da classe {@link TelaIniciarCompra}.
     *
     * Responsável por construir a interface de início de compras, configurando
     * o botão principal para "Fazer Pedido" e o botão "Sair". Define o layout visual
     * e as propriedades da janela.
     *
     * @param stock Instância de {@link Estoque} utilizada para gerenciar os produtos.
     * @param cashControl Instância de {@link Caixa} utilizada para controle financeiro.
     * @param orders Instância de {@link ControlePedidos} utilizada para manipular os pedidos feitos.
     */
    public TelaIniciarCompra(Estoque stock, Caixa cashControl, ControlePedidos orders)
    {
        setTitle("Iniciar Compras");
        setSize(600, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        getContentPane().setBackground(new Color(197, 202, 196));
        setLayout(new BorderLayout());

        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBackground(new Color(197, 202, 196));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 25, 10);

        // -- Title Label --
        JLabel titleLabel = new JLabel("Cantina - Autoatendimento");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 25));
        titleLabel.setForeground(new Color(40, 40, 40));
        titleLabel.setBackground(new Color(197, 202, 196));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titleLabel.setPreferredSize(new Dimension(500, 40));

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.anchor = GridBagConstraints.NORTH;

        mainPanel.add(titleLabel, gbc);

        gbc.insets = new Insets(10, 10, 10, 10);

        // -- User Button --
        JButton userButton = new JButton("Fazer Pedido");
        userButton.setBackground(new Color(0,86,179));
        userButton.setForeground(Color.WHITE);
        userButton.setFont(new Font("Arial", Font.BOLD, 20));
        userButton.setBorderPainted(false);
        userButton.setFocusPainted(false);
        userButton.setPreferredSize(new Dimension(250,140));

        userButton.addActionListener(e -> {
            TelaAutoatendimento telaAutoatendimento = new TelaAutoatendimento(stock, cashControl, orders);
            telaAutoatendimento.setVisible(true);
            dispose();
        });

        gbc.gridy = 1;
        
        // -- Back Button --
        JPanel backPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        backPanel.setBackground(new Color(197, 202, 196)); 

        JButton backButton = new JButton("Sair");
        backButton.setBackground(new Color(220, 53, 69));
        backButton.setForeground(Color.WHITE);
        backButton.setFont(new Font("Arial", Font.BOLD, 16));
        backButton.setFocusPainted(false);
        backButton.setPreferredSize(new Dimension(100, 40));
        backButton.setBorderPainted(false);
        backPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 30));

        backButton.addActionListener(e -> {
            System.out.println("Acessando Tela de Login");
                TelaLogin telaLogin = new TelaLogin(TelaIniciarCompra.this);
                telaLogin.setVisible(true);

                if (telaLogin.isLoginSucessful())
                {
                    System.out.println("Acessando Tela de Administração");
                    TelaInicial telaInicial = new TelaInicial(stock, cashControl, orders);
                    telaInicial.setVisible(true);
                    dispose();
                }
            });

        backPanel.add(backButton);
        add(backPanel, BorderLayout.SOUTH);

        mainPanel.add(userButton, gbc);
        add(mainPanel);
    }
}