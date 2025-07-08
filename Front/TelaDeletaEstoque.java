package Front;
import Back.*;
import javax.swing.*;
import javax.swing.border.BevelBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Diálogo para deletar um item completo do estoque.
 *
 * A classe {@link TelaDeletaEstoque} fornece uma interface para o usuário
 * inserir o código de um produto e removê-lo completamente do estoque.
 * Antes da exclusão, uma confirmação é solicitada para evitar remoções acidentais.
 *
 * As operações incluem:
 * <ul>
 * <li>Validação de campo de código vazio.</li>
 * <li>Verificação da existência do produto.</li>
 * <li>Confirmação do usuário antes de deletar o produto.</li>
 * <li>Atualização da tabela de produtos na {@link TelaEstoque} após a exclusão.</li>
 * <li>Tratamento de erros para entradas não numéricas.</li>
 * </ul>
 */
public class TelaDeletaEstoque extends JDialog
{
    private JTextField codeTextField;

    /**
     * Construtor da classe {@link TelaDeletaEstoque}.
     *
     * Configura a janela de diálogo, seus componentes visuais e os listeners de eventos
     * para os botões de "Deletar" e "Sair".
     *
     * @param telaEstoque A instância de {@link TelaEstoque} para recarregar a lista de produtos após a exclusão.
     * @param owner O {@link JFrame} pai desta janela de diálogo.
     * @param stock Instância de {@link Estoque} utilizada para gerenciar os produtos.
     */
    public TelaDeletaEstoque (TelaEstoque telaEstoque, JFrame owner, Estoque stock)
    {
        super(owner, "Deleta um Item do Estoque", true);
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
        JLabel titleLabel = new JLabel("Deletar Produto");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 25));
        titleLabel.setForeground(new Color(40,40,40));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.anchor = GridBagConstraints.NORTH;
        mainPanel.add(titleLabel, gbc);

         gbc.insets = new Insets(10, 10, 10, 10);

        // -- SubTitle Label --
        JLabel subtitleLabel = new JLabel("Preencha os campos abaixo:");
        subtitleLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        gbc.gridy = 1;
        mainPanel.add(subtitleLabel, gbc);

        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.CENTER;               
        gbc.insets = new Insets(10, 30, 5, 30);       
        gbc.weightx = 1.0;                                    
        gbc.fill = GridBagConstraints.HORIZONTAL;            
        gbc.gridwidth = GridBagConstraints.REMAINDER;

        // -- Form Panel --
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(new Color(197,202,196));

        GridBagConstraints gbcForm = new GridBagConstraints();
        gbcForm.insets = new Insets(5, 5, 5, 5);

        // -- Code Label --
        JLabel codeLabel = new JLabel("Código:");
        codeLabel.setFont(new Font("Arial", Font.PLAIN, 20));
        gbcForm.gridy = 0;
        gbcForm.anchor = GridBagConstraints.WEST; 
        gbcForm.gridwidth = GridBagConstraints.REMAINDER; 
        gbcForm.fill = GridBagConstraints.HORIZONTAL; 
        gbcForm.weightx = 1.0;
        formPanel.add(codeLabel, gbcForm);

         // -- Code TextField --
        codeTextField = new JTextField();
        codeTextField.setBackground(new Color(172,179,174)); 
        codeTextField.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
        codeTextField.setFont(new Font("Arial", Font.PLAIN, 18));
        codeTextField.setPreferredSize(new Dimension(0, 25));
        gbcForm.gridy = 1;
        formPanel.add(codeTextField, gbcForm);

        mainPanel.add(formPanel, gbc);

        // -- Button Panel --
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        buttonPanel.setBackground(new Color(197,202,196));

        // -- Remove Button --
        JButton removeButton = createCustomButton("Deletar");
        titleLabel.setForeground(new Color(40,40,40));

        removeButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed (ActionEvent e)
            {
                System.out.println("Deletando");
                if (codeTextField.getText().isEmpty())
                {
                    JOptionPane.showMessageDialog(TelaDeletaEstoque.this, "Preencha o campo Código", "Erro Deletar", JOptionPane.ERROR_MESSAGE);
                }
                else
                {
                    try 
                    {
                        int code = Integer.parseInt(codeTextField.getText());
                        if (!stock.isProductInStock(code))
                        {
                            JOptionPane.showMessageDialog(TelaDeletaEstoque.this, "Produto não encontrado", "Produto Inexistente", JOptionPane.ERROR_MESSAGE);
                        }
                        else
                        {
                            int option = JOptionPane.showConfirmDialog
                            (
                                TelaDeletaEstoque.this,
                                "Tem certeza que deseja deletar " + stock.getProductName(code) + " do Estoque?",
                                "Confirmação Delete",
                                JOptionPane.YES_NO_OPTION,
                                JOptionPane.QUESTION_MESSAGE
                            );
    
                            if (option == JOptionPane.YES_OPTION)
                            {
                                String productName = stock.getProductName(code);
                                stock.deleteProduct(code);
                                telaEstoque.loadProducts(stock);
                                JOptionPane.showMessageDialog(TelaDeletaEstoque.this, productName + " deletado com sucesso", "Produto Deletado", JOptionPane.INFORMATION_MESSAGE);
                            }
                            codeTextField.setText("");
                        }
                    }
                    catch (Exception ex)
                    {
                        JOptionPane.showMessageDialog(TelaDeletaEstoque.this, "Insira apenas valores inteiros", "Valor Invalido", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });
        buttonPanel.add(removeButton);

        // -- Back Button --
        JButton backButton = createCustomButton("Sair");
        backButton.setBackground(new Color(220, 53, 69));

        backButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                System.out.println("Saindo");
                dispose();
            }
        });
        buttonPanel.add(backButton);
        
        gbcForm.gridy = 3;
        gbcForm.gridwidth = GridBagConstraints.REMAINDER;
        gbcForm.insets = new Insets(5, 5, 5, 5);
        mainPanel.add(buttonPanel, gbcForm);

        add(mainPanel);
    }

    /**
     * Método auxiliar para criação de botões estilizados usados na {@link TelaDeletaEstoque}.
     * 
     * Configura cor de fundo, cor da fonte, fonte, borda e tamanho do botão.
     *
     * @param text Texto a ser exibido no botão.
     * @return {@link JButton} estilizado e formatado para o layout da tela.
     */
    public JButton createCustomButton (String text)
    {
        JButton button = new JButton(text);

        button.setForeground(Color.WHITE);
        button.setBackground(new Color(0,86,179));
        button.setFont(new Font("Arial", Font.BOLD, 16));
        button.setFocusPainted(false);
        button.setPreferredSize(new Dimension(120, 40));
        button.setBorderPainted(false);
        button.setFocusPainted(false);

        return button;
    }   
}