package Front;
import Back.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TelaEditaEstoque extends JDialog
{
    public TelaEditaEstoque(JFrame owner, Estoque stock)
    {
        super(owner, "Edição Estoque", true);
        setSize(336,368);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(owner);
        setResizable(false);

        getContentPane().setBackground(new Color(197,202,196));
        setLayout(new BorderLayout());

        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBackground(new Color(197,202,196));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 0, 10);

        // -- Title Label --
        JLabel titleLabel = new JLabel("Alterar Estoque");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 25));
        titleLabel.setForeground(new Color(40,40,40));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.anchor = GridBagConstraints.NORTH;
        mainPanel.add(titleLabel, gbc);
    
        gbc.insets = new Insets(10, 10, 10, 10);

        // -- SubTitle Label --
        JLabel subtitleLabel = new JLabel("Selecione uma opção:");
        subtitleLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        gbc.gridy = 1;
        mainPanel.add(subtitleLabel, gbc);

        gbc.insets = new Insets(5, 10, 5, 10);

        // -- Add Quantity Button --
        JButton addButton = createCustomButton("<html><center>Adicionar<br>Estoque</center></html>");
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.WEST;
        mainPanel.add(addButton, gbc);

        addButton.addActionListener(new ActionListener() 
        {
            @Override
            public void actionPerformed (ActionEvent e)
            {
                TelaAdicionaEstoque telaAdicionaEstoque = new TelaAdicionaEstoque(owner, stock);
                dispose();
                telaAdicionaEstoque.setVisible(true);
            }
            
        });

        // -- Remove Quantity Button --
        JButton removeButton = createCustomButton("<html><center>Remover<br>Estoque</center></html>");
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.EAST;
        mainPanel.add(removeButton, gbc);

        removeButton.addActionListener(new ActionListener() 
        {
            @Override
            public void actionPerformed (ActionEvent e)
            {
                TelaRemoveEstoque telaRemoveEstoque = new TelaRemoveEstoque(owner, stock);
                dispose();
                telaRemoveEstoque.setVisible(true);
            }
            
        });

        // -- Delete Product Button --
        JButton deleteButton = createCustomButton("<html><center>Deletar<br>Produto</center></html>");
        deleteButton.setBackground(new Color(220, 53, 69));
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        gbc.gridy = 3;
        gbc.anchor = GridBagConstraints.CENTER;
        mainPanel.add(deleteButton, gbc);

        deleteButton.addActionListener(new ActionListener() 
        {
            @Override
            public void actionPerformed (ActionEvent e)
            {
                TelaDeletaEstoque telaDeletaEstoque = new TelaDeletaEstoque(owner, stock);
                dispose();
                telaDeletaEstoque.setVisible(true);
            }
            
        });
    
        gbc.insets = new Insets(10, 10, 10, 10);

        // -- Back Button --
        JButton backButton = createCustomButton("Voltar");
        deleteButton.setBackground(new Color(220, 53, 69));
        gbc.gridx = 1;
        gbc.gridwidth = 1;
        gbc.gridy = 4;
        gbc.anchor = GridBagConstraints.EAST;
        backButton.setPreferredSize(new Dimension(78,41));
        mainPanel.add(backButton, gbc);

        backButton.addActionListener(new ActionListener() 
        {
            @Override
            public void actionPerformed (ActionEvent e)
            {
                dispose();
            }
            
        });

        add(mainPanel);
    }

    private JButton createCustomButton(String text)
    {
        JButton button = new JButton(text);

        button.setBackground(new Color(0,86,179));
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Arial", Font.BOLD, 15));
        button.setBorderPainted(false);     
        button.setFocusPainted(false);
        button.setPreferredSize(new Dimension(138,65));

        return button;
    }
}