import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TelaUser extends JFrame
{
    public TelaUser()
    {
        setTitle("Tela User");
        setSize(600, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        getContentPane().setBackground(new Color(172,179,174));
        setLayout(new BorderLayout());

        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBackground(new Color(172,179,174));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        // -- Title Label --
        JLabel titleLabel = new JLabel("Bem-vindo(a)!");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 40));
        titleLabel.setForeground(new Color(40,40,40));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.anchor = GridBagConstraints.NORTH;
        mainPanel.add(titleLabel, gbc);

        gbc.insets = new Insets(10, 10, 25, 10);

        // -- SubTitle Label --
        JLabel subtitleLabel = new JLabel("Pronto(a) para fazer seu pedido?");
        subtitleLabel.setFont(new Font("Arial", Font.PLAIN, 20));
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.anchor = GridBagConstraints.NORTH;
        mainPanel.add(subtitleLabel, gbc);

        // -- Start Button --
        JButton startButton = createCustomButton("Iniciar Compra");
        gbc.gridy = 2;
        
        mainPanel.add(startButton, gbc);

        startButton.addActionListener(new ActionListener() 
        {
            @Override
            public void actionPerformed (ActionEvent e)
            {
                System.out.println("Iniciando Compras");
            }
            
        });

        // -- Back Button --
        JButton backButton = createCustomButton("Sair");
        backButton.setPreferredSize(new Dimension(66,35));
        backButton.setFont(new Font("Arial", Font.BOLD, 17));
        gbc.gridy = 3;
        gbc.gridx = 2;
        gbc.anchor = GridBagConstraints.EAST;
        gbc.insets = new Insets(0, 10, 10, 31);

        mainPanel.add(backButton, gbc);

        backButton.addActionListener(new ActionListener() 
        {
            @Override
            public void actionPerformed (ActionEvent e)
            {
                System.out.println("Voltando");
            }
            
        });

        add(mainPanel);

    }

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

    public static void main (String[] args)
    {
        TelaUser telaUser = new TelaUser();
        telaUser.setVisible(true);
    }
}
