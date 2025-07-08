package Front;
import Back.*;
import javax.swing.*;
import javax.swing.border.BevelBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Diálogo para adicionar estoque a um item existente.
 *
 * A classe {@link TelaAdicionaEstoque} oferece uma interface para que o usuário
 * insira o código de um produto e a quantidade a ser adicionada ao estoque.
 * Realiza validações de entrada para garantir a integridade dos dados.
 *
 * As operações incluem:
 * <ul>
 * <li>Validação de campos vazios.</li>
 * <li>Validação de quantidade maior que zero.</li>
 * <li>Verificação da existência do produto no estoque.</li>
 * <li>Atualização da tabela de produtos na {@link TelaEstoque} após a adição bem-sucedida.</li>
 * <li>Tratamento de erros para entradas não numéricas.</li>
 * </ul>
 */
public class TelaAdicionaEstoque extends JDialog
{
    private JTextField codeTextField, quantityTextField;

    /**
     * Construtor da classe {@link TelaAdicionaEstoque}.
     *
     * Configura a janela de diálogo, seus componentes visuais e os listeners de eventos
     * para os botões de "Adicionar" e "Sair".
     *
     * @param telaEstoque A instância de {@link TelaEstoque} para recarregar a lista de produtos após a adição.
     * @param owner O {@link JFrame} pai desta janela de diálogo.
     * @param stock Instância de {@link Estoque} utilizada para gerenciar os produtos.
     * @param cashControl Instância de {@link Caixa} utilizada para controle financeiro.
     */
    public TelaAdicionaEstoque (TelaEstoque telaEstoque, JFrame owner, Estoque stock, Caixa cashControl)
    {
        super(owner, "Adiciona Estoque de um Item", true);
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
        JLabel titleLabel = new JLabel("Adicionar Estoque");
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
    
        gbcForm.insets = new Insets(20, 5, 5, 5);

        // -- Quantity Label --
        JLabel quantityLabel = new JLabel("Quantidade:");
        quantityLabel.setFont(new Font("Arial", Font.PLAIN, 20));
        gbcForm.gridy = 2;
        formPanel.add(quantityLabel, gbcForm);

        gbcForm.insets = new Insets(5, 5, 5, 5);

         // -- Quantity TextField --
        quantityTextField = new JTextField();
        quantityTextField.setBackground(new Color(172,179,174)); 
        quantityTextField.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
        quantityTextField.setFont(new Font("Arial", Font.PLAIN, 18));
        quantityTextField.setPreferredSize(new Dimension(0, 25));
        gbcForm.gridy = 3;
        formPanel.add(quantityTextField, gbcForm);

        mainPanel.add(formPanel, gbc);

        // -- Button Panel --
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        buttonPanel.setBackground(new Color(197,202,196));

        // -- Add Button --
        JButton addButton = createCustomButton("Adicionar");
        titleLabel.setForeground(new Color(40,40,40));

        addButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed (ActionEvent e)
            {
                System.out.println("Adicionando");

                if (quantityTextField.getText().isEmpty() || codeTextField.getText().isEmpty())
                {
                    JOptionPane.showMessageDialog(TelaAdicionaEstoque.this, "Preencha todos os Campos", "Erro Adição", JOptionPane.ERROR_MESSAGE);
                }
                else
                {   
                    try
                    {
                        int quantity = Integer.parseInt(quantityTextField.getText());
                        int code = Integer.parseInt(codeTextField.getText());
    
                        if (quantity <=0)
                            JOptionPane.showMessageDialog(TelaAdicionaEstoque.this, "A quantidade deve ser maior que 0!", "Quantidade Invalida", JOptionPane.ERROR_MESSAGE);
                        else
                        {
                            if (!stock.addStock(code, quantity))
                                JOptionPane.showMessageDialog(TelaAdicionaEstoque.this, "Produto não encontrado!", "Produto inexistente", JOptionPane.ERROR_MESSAGE);
                            else
                            {
                                telaEstoque.loadProducts(stock);
                                JOptionPane.showMessageDialog(TelaAdicionaEstoque.this, "Quantidade adicionada com Sucesso!", "Quantidade Adicionada", JOptionPane.INFORMATION_MESSAGE);
                                codeTextField.setText("");
                                quantityTextField.setText("");
                            }
                        }
                        
                    } 
                    catch (Exception ex)
                    {
                        JOptionPane.showMessageDialog(TelaAdicionaEstoque.this, "Insira apenas valores inteiros", "Valor Invalido", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });
        buttonPanel.add(addButton);

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
     * Método auxiliar para criação de botões estilizados usados na {@link TelaAdicionaEstoque}.
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
