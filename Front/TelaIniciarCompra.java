package Front;
import Back.*;
import javax.swing.*;
import java.awt.*;

public class TelaIniciarCompra extends JFrame
{
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
        
        // -- Botão Sair no canto inferior direito --
        JPanel sairPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        sairPanel.setBackground(new Color(197, 202, 196)); // Mesma cor do fundo

        JButton backButton = new JButton("Sair");
        backButton.setBackground(new Color(220, 53, 69));
        backButton.setForeground(Color.WHITE);
        backButton.setFont(new Font("Arial", Font.BOLD, 16));
        backButton.setFocusPainted(false);
        backButton.setPreferredSize(new Dimension(100, 40));
        backButton.setBorderPainted(false);
        sairPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 30));

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

        sairPanel.add(backButton);
        add(sairPanel, BorderLayout.SOUTH);

        mainPanel.add(userButton, gbc);
        add(mainPanel);
    }

    public static void main(String[] args) {
        TelaIniciarCompra telaIniciarCompra = new TelaIniciarCompra(null, null, null);
        telaIniciarCompra.setVisible(true);
    }
}
