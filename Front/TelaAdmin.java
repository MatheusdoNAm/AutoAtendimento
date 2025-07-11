package Front;
import Back.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Tela principal de Administração do Sistema.
 * 
 * A classe {@link TelaAdmin} representa a interface gráfica que será exibida ao usuário 
 * após a autenticação bem-sucedida do administrador. Essa interface permite ao usuário:
 * <ul>
 *     <li>Cadastrar novos itens no sistema;</li>
 *     <li>Visualizar e gerenciar o estoque atual;</li>
 *     <li>Acessar o controle de caixa;</li>
 *     <li>Gerar relatórios;</li>
 *     <li>Retornar à tela inicial.</li>
 * </ul>
 * 
 * Utiliza instâncias das classes {@link Estoque}, {@link Caixa} e {@link ControlePedidos}
 * para realizar a gestão de dados do sistema.
 */
public class TelaAdmin extends JFrame
{
    /**
     * Construtor da classe {@link TelaAdmin}.
     * 
     * Responsável por construir a estrutura da interface de administração, com botões de
     * navegação para diferentes funcionalidades e integração com os objetos centrais do sistema.
     * 
     * @param stock Instância de {@link Estoque} utilizada para gerenciar os produtos.
     * @param cashControl Instância de {@link Caixa} utilizada para controle financeiro.
     * @param orders Instância de {@link ControlePedidos} utilizada para manipular os pedidos feitos.
     */
    public TelaAdmin(Estoque stock, Caixa cashControl, ControlePedidos orders)
    {
        setTitle("Tela de Administração");
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
        JLabel titleLabel = new JLabel("Administrar Sistema");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 40));
        titleLabel.setForeground(new Color(40,40,40));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.anchor = GridBagConstraints.NORTH;
        mainPanel.add(titleLabel, gbc);

        gbc.insets = new Insets(10, 10, 10, 10);

        // -- SubTitle Label --
        JLabel subtitleLabel = new JLabel("Selecione uma opção:");
        subtitleLabel.setFont(new Font("Arial", Font.PLAIN, 20));
        gbc.gridy = 1;
        mainPanel.add(subtitleLabel, gbc);

        // -- Item Registration Button --
        JButton registrationButton = createCustomButton("Cadastrar Item");
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.WEST;
        mainPanel.add(registrationButton, gbc);

        registrationButton.addActionListener(new ActionListener() 
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                System.out.println("Cadastro Item");
                TelaRegistro telaRegistro = new TelaRegistro(TelaAdmin.this, stock);
                telaRegistro.setVisible(true);
            }        
        });

        // -- Stock Button
        JButton stockButton = createCustomButton("Controle de Estoque");
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.EAST;
        mainPanel.add(stockButton, gbc);

        stockButton.addActionListener(new ActionListener() 
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                System.out.println("Estoque");
                TelaEstoque telaEstoque = new TelaEstoque(stock, cashControl, orders);
                telaEstoque.loadProducts(stock);
                telaEstoque.setVisible(true);
                dispose();
            }
            
        });

        // -- Reports Button --
        JButton reportsButton = createCustomButton("Gerar Relatórios");
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.anchor = GridBagConstraints.WEST;
        mainPanel.add(reportsButton, gbc);

        reportsButton.addActionListener(new ActionListener() 
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                System.out.println("Relatórios");
                TelaRelatorio telaRelatorio = new TelaRelatorio(stock, cashControl, orders);
                telaRelatorio.setVisible(true);
                dispose();
            }
            
        });

        // -- Cash Control Button
        JButton cashButton = createCustomButton("Controle de Caixa");
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.EAST;
        mainPanel.add(cashButton, gbc);

        cashButton.addActionListener(new ActionListener() 
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                System.out.println("Caixa");
                TelaControleCaixa telaControleCaixa = new TelaControleCaixa(stock, cashControl, orders);
                telaControleCaixa.setVisible(true);
                dispose();
            }
            
        });

        // -- Back Button
        gbc.insets = new Insets(50, 10, 10, 10);
        JButton backButton = createCustomButton("Voltar");
        gbc.gridy = 4;
        backButton.setPreferredSize(new Dimension(100,56));
        mainPanel.add(backButton, gbc);

        backButton.addActionListener(new ActionListener() 
        {
            @Override
            public void actionPerformed (ActionEvent e)
            {
                System.out.println("Retornando a tela inicial");
                TelaInicial telaInicial = new TelaInicial(stock, cashControl, orders);
                telaInicial.setVisible(true);
                dispose();
            } 
        });

        add(mainPanel);
    }

    /**
     * Método auxiliar para criação de botões estilizados usados na {@link TelaAdmin}.
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
