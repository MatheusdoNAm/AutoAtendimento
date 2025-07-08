package Front;
import Back.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Tela de Opções de Edição do Estoque.
 *
 * A classe {@link TelaEditaEstoque} funciona como um hub para as diferentes operações
 * de modificação do estoque do sistema. Ela apresenta botões que direcionam
 * o usuário para telas específicas de gerenciamento.
 *
 * Funcionalidades oferecidas:
 * <ul>
 * <li>Acesso à tela para **adicionar quantidade** a um produto existente ({@link TelaAdicionaEstoque}).</li>
 * <li>Acesso à tela para **remover quantidade** de um produto existente ({@link TelaRemoveEstoque}).</li>
 * <li>Acesso à tela para **deletar um produto** completamente do estoque ({@link TelaDeletaEstoque}).</li>
 * <li>Opção para retornar à tela de visualização do estoque ({@link TelaEstoque}).</li>
 * </ul>
 *
 * Esta tela é um {@link JDialog} modal, o que significa que bloqueia a interação
 * com a tela pai enquanto estiver aberta.
 *
 * Utiliza instâncias das classes {@link Estoque} e {@link Caixa} para garantir
 * a continuidade dos dados entre as telas de edição.
 */
public class TelaEditaEstoque extends JDialog
{
    /**
     * Construtor da classe {@link TelaEditaEstoque}.
     *
     * Inicializa a interface de edição de estoque, configurando os botões
     * para as operações de adicionar, remover e deletar produtos, bem como
     * o botão para voltar. Define o layout visual e as propriedades da janela de diálogo.
     *
     * @param owner O {@link JFrame} pai desta janela de diálogo, tipicamente a {@link TelaEstoque}.
     * @param stock Instância de {@link Estoque} utilizada para gerenciar os produtos.
     * @param cashControl Instância de {@link Caixa} utilizada para controle financeiro.0
     */
    public TelaEditaEstoque(JFrame owner, Estoque stock, Caixa cashControl)
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
                TelaAdicionaEstoque telaAdicionaEstoque = new TelaAdicionaEstoque(owner, stock, cashControl);
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

    /**
     * Método auxiliar para criação de botões estilizados usados na {@link TelaEditaEstoque}.
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
        button.setFont(new Font("Arial", Font.BOLD, 15));
        button.setBorderPainted(false);     
        button.setFocusPainted(false);
        button.setPreferredSize(new Dimension(138,65));

        return button;
    }
}