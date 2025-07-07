package Front;
import javax.swing.*;
import javax.swing.border.BevelBorder;

import Back.Caixa;
import Back.ControlePedidos;
import Back.Estoque;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Gera a Tela de Confirmação de usuário Administrador do Sistema, através de uma tela de diálogo.
 * 
 * O Acesso a Tela de Administração do Sistema somente ocorrerá se o usuário realizar o login de 
 * administrador, com um {@link JTextField} para a inserção do login, e um {@link JPasswordField}
 * para a inserção de senha, junto de um {@link JButton} para verificação dos dados.
 */
public class TelaLogin extends JDialog
{
    private JTextField userTextField;
    private JPasswordField passwordField;
    private boolean isSuccessful = false;

    /**
     * Construtor da classe {@link TelaLogin}.
     * 
     * Responsável por desenhar ao usuário toda a estrutura da tela, seus campos de inserção e
     * botão e realizar o controle do funcionamento do Botão de Confirmação.
     * 
     * @param owner Referência da Tela que chamou a Tela de Login. A Tela "Dona" é usada para
     * configurar a posição do Diálogo para o usuário e também bloquear a tela "Dona" até a
     * finalização e fechamento da Tela Diálogo de Login.
     */
    public TelaLogin (JFrame owner)
    {
        super(owner, "Login Administrador", true);
        setSize(336,368);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(owner);
        setResizable(false);

        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBackground(new Color(197,202,196));

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
        formPanel.setBackground(new Color(197,202,196));

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

        mainPanel.add(formPanel, gbc);
        add(mainPanel);

        // -- User TextField --
        userTextField = new JTextField();
        userTextField.setBackground(new Color(172,179,174)); 
        userTextField.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
        userTextField.setFont(new Font("Arial", Font.PLAIN, 12));
        userTextField.setPreferredSize(new Dimension(0, 25));
        gbcItem.gridy = 1;
        formPanel.add(userTextField, gbcItem);

        // -- Password Label --
        JLabel passLabel = new JLabel("Senha:");
        passLabel.setFont(new Font("Arial", Font.PLAIN, 15));
        gbcItem.gridy = 2;
        formPanel.add(passLabel, gbcItem);

        gbcItem.insets = new Insets(5, 5, 5, 5);

        // -- Password TextField --
        passwordField = new JPasswordField();
        passwordField.setBackground(new Color(172,179,174));
        passwordField.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
        passwordField.setFont(new Font("Arial", Font.PLAIN, 12));
        passwordField.setPreferredSize(new Dimension(0, 25));
        gbcItem.gridy = 3;
        formPanel.add(passwordField, gbcItem);

        mainPanel.add(formPanel, gbc);

        // -- Login Button --
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        buttonPanel.setBackground(new Color(197,202,196));

        JButton saveButton = createCustomButton("Entrar");
        buttonPanel.add(saveButton);

        mainPanel.add(buttonPanel);

        saveButton.addActionListener(new ActionListener() 
        {
            @Override
            public void actionPerformed (ActionEvent e)
            {
                System.out.println("Botão Login");
                compareUserInput();
            }
        });
    }
    
    /**
     * Método auxiliar para criação e configuração visual dos botões da Tela {@link TelaLogin}.
     * @param text String que aparecerá no botão.
     * @return retorna um {@link JButton} formatado visualmente.
     */
    private JButton createCustomButton(String text)
    {
        JButton button = new JButton(text);
        button.setBackground(new Color(40, 167, 69));
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Arial", Font.BOLD, 20));
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setPreferredSize(new Dimension(150,40));

        return button;
    }

    /**
     * Método para validação se os dados inseridos de login e senha condizem com o usuário
     * administrador.
     * 
     * O método converte os dados presentes em {@code userTextField} e {@code passwordField}
     * em Strings, comparando com o usuário administrador padrão
     * Caso os dados coincidam, ele altera o atributo  {@code boolean isSucessful} para true,
     * caso contrário, imprime na tela um PopUp informando que os dados não coincidem, usando
     * {@link JOptionPane}.
     */
    private void compareUserInput()
    {
        String enteredUser = userTextField.getText();
        String enteredPass = new String(passwordField.getPassword());

        String standUser = "";
        String standPass = "";

        if (enteredUser.equals(standUser) && enteredPass.equals(standPass))
        {
            System.out.println("Sucesso Login");
            dispose();
            isSuccessful = true;
        }
        else
        {
            System.out.println("Senha Incorreta");
            passwordField.setText("");
            JOptionPane.showMessageDialog(this, "Usuário ou Senha incorretos!", "Falha no Login", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Retorna o estado do Atributo {@code boolean isLoginSucessful}
     * 
     * @return isLoginSucessful (true ou false).
     */
    public boolean isLoginSucessful()
    {
        return isSuccessful;
    }
}