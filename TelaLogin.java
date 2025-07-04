import javax.swing.*;
import javax.swing.border.BevelBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TelaLogin extends JDialog
{
    private JTextField userTextField;
    private JPasswordField passTextField;
    private boolean isSuccessful = false;

    public TelaLogin (JFrame owner)
    {
        super(owner, "Login Administrador", true);
        setSize(336,368);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(owner);
        setResizable(false);

        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBackground(new Color(172,179,174));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 5, 10);

        // -- Title Label --
        JLabel titleLabel = new JLabel("Acesso do Administrador");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setForeground(new Color(40,40,40));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.anchor = GridBagConstraints.CENTER;
        mainPanel.add(titleLabel, gbc);

        // -- SubTitle Label --
        JLabel subtitleLabel = new JLabel("Insira suas credenciais para continuar:");
        subtitleLabel.setFont(new Font("Arial", Font.PLAIN, 15));
        gbc.gridx = 0;
        gbc.gridy = 1;
        mainPanel.add(subtitleLabel, gbc);

        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.CENTER;               
        gbc.insets = new Insets(10, 30, 10, 30);       
        gbc.weightx = 1.0;                                    
        gbc.fill = GridBagConstraints.HORIZONTAL;            
        gbc.gridwidth = GridBagConstraints.REMAINDER;        

        // -- Form Panel --
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(new Color(172,179,174));

        GridBagConstraints gbcItem = new GridBagConstraints();
        gbcItem.insets = new Insets(5, 5, 5, 5);

        // -- User Label --
        JLabel userLabel = new JLabel("Usuário:");
        userLabel.setFont(new Font("Arial", Font.PLAIN, 15));
        gbcItem.gridy = 0;
        gbcItem.anchor = GridBagConstraints.WEST; 
        gbcItem.gridwidth = GridBagConstraints.REMAINDER; 
        gbcItem.fill = GridBagConstraints.HORIZONTAL; 
        gbcItem.weightx = 1.0;
        formPanel.add(userLabel, gbcItem);

        // -- User TextField --
        userTextField = new JTextField();
        userTextField.setBackground(new Color(172,179,174)); 
        userTextField.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
        userTextField.setFont(new Font("Arial", Font.PLAIN, 12));
        userTextField.setPreferredSize(new Dimension(0, 25));
        gbcItem.gridy = 1;
        formPanel.add(userTextField, gbcItem);

        gbcItem.insets = new Insets(20, 5, 5, 5);

        // -- Password Label --
        JLabel passLabel = new JLabel("Senha:");
        passLabel.setFont(new Font("Arial", Font.PLAIN, 15));
        gbcItem.gridy = 2;
        formPanel.add(passLabel, gbcItem);

        gbcItem.insets = new Insets(5, 5, 5, 5);

        // -- Password TextField --
        passTextField = new JPasswordField();
        passTextField.setBackground(new Color(172,179,174));
        passTextField.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
        passTextField.setFont(new Font("Arial", Font.PLAIN, 12));
        passTextField.setPreferredSize(new Dimension(0, 25));
        gbcItem.gridy = 3;
        formPanel.add(passTextField, gbcItem);

        mainPanel.add(formPanel, gbc);

        // -- Login Button --
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        buttonPanel.setBackground(new Color(172,179,174));

        JButton saveButton = createCustomButton("Entrar");
        saveButton.setBackground(new Color(40, 167, 69));
        buttonPanel.add(saveButton);

        saveButton.addActionListener(new ActionListener() 
        {
            @Override
            public void actionPerformed (ActionEvent e)
            {
                compareUserInput();
            }
            
        });

        mainPanel.add(buttonPanel);
        
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
        button.setPreferredSize(new Dimension(150,40));

        return button;
    }

    private void compareUserInput()
    {
        String enteredUser = userTextField.getText();
        String enteredPass = new String(passTextField.getPassword());

        String correctUser = "admin";
        String correctPass = "0000";

        if (enteredUser.equals(correctUser) && enteredPass.equals(correctPass))
        {
            System.out.println("Login Bem-sucedido");
            JOptionPane.showMessageDialog(this, "Login Bem-sucedido", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
            dispose();
            isSuccessful = true;
            
        } else
        {
            System.out.println("Login Falhou");
            JOptionPane.showMessageDialog(this, "Usuário ou senha incorreta", "Falha", JOptionPane.ERROR_MESSAGE);
            userTextField.setText("");
            passTextField.setText("");
            userTextField.requestFocusInWindow();
        }
    }

    public boolean isLoginSucessful ()
    {
        return isSuccessful;
    }

    
}
