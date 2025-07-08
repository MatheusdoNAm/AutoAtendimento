package Front;
import Back.*;

import javax.swing.*;
import javax.swing.text.MaskFormatter;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
  * Tela de Geração de Relatórios do Sistema.
  *
  * A classe {@link TelaRelatorio} oferece uma interface para que o administrador
  * gere diferentes tipos de relatórios sobre as operações da cantina. O usuário
  * pode selecionar o tipo de relatório desejado e especificar um intervalo de datas.
  *
  * <p>
  * Funcionalidades principais:
  * <ul>
  * <li>Seleção do tipo de relatório a ser gerado (Produtos mais Vendidos, Transações Realizadas,
  * Produtos Vencidos ou Próximos de Vencer).</li>
  * <li>Definição de uma data inicial e uma data final para o período do relatório,
  * com validação de formato (DD/MM/AAAA) e lógica de datas (data inicial não pode ser
  * posterior à data final; nenhuma data pode estar no passado).</li>
  * com validação de formato (DD/MM/AAAA). A data inicial deve ser anterior ou igual à data final.</li>
  * <li>Botão "Gerar Relatório" para processar a solicitação com base nas seleções.</li>
  * <li>Botão "Voltar" para retornar à tela de administração ({@link TelaAdmin}).</li>
  * <li>Desabilitação dos campos de data para o relatório de "Produtos Vencidos ou Próximos a Vencer",
  * já que este relatório não depende de um intervalo de datas.</li>
  * </ul>
  *
  * <p>
  * Utiliza instâncias das classes {@link Estoque}, {@link Caixa} e {@link ControlePedidos}
  * para acessar os dados necessários para a geração dos relatórios.
  */
public class TelaRelatorio extends JFrame
{
    private JFormattedTextField startTextField, endTextField;
    /**
     * Construtor da classe {@link TelaRelatorio}.
     *
     * Inicializa a interface de relatórios, configurando o título, os campos de
     * seleção de tipo de relatório e datas, e os botões de ação. Define o layout
     * visual e as propriedades da janela.
     *
     * @param stock Instância de {@link Estoque} utilizada para gerenciar os produtos.
     * @param cashControl Instância de {@link Caixa} utilizada para controle financeiro.
     * @param orders Instância de {@link ControlePedidos} utilizada para manipular os pedidos feitos.
     */
    public TelaRelatorio(Estoque stock, Caixa cashControl, ControlePedidos orders)
    {
        setTitle("Relatórios");
        setSize(600,800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        getContentPane().setBackground(new Color(197,202,196));
        setLayout(new BorderLayout());

        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBackground(new Color(197,202,196));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.gridwidth = GridBagConstraints.REMAINDER;

        // -- Title Label --
        JLabel titleLabel = new JLabel("Relatórios do Sistema", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 30));
        titleLabel.setForeground(new Color(40,40,40));
        mainPanel.add(titleLabel, gbc);

        // -- Type Label --
        JLabel typeLabel = new JLabel("Tipo de Relatório:");
        typeLabel.setFont(new Font("Arial", Font.BOLD, 20));
        typeLabel.setForeground(new Color(40,40,40));
        mainPanel.add(typeLabel, gbc);

        String[] types = {
            "Produtos mais Vendidos",
            "Transações Realizadas",
            "Produtos Vencidos ou Próximos de Vencer"
        };
        JComboBox<String> comboType = new JComboBox<>(types);
        comboType.setPreferredSize(new Dimension(200, 30));
        mainPanel.add(comboType, gbc);

        // -- Start Date --
        JLabel startDateLabel = new JLabel("Data Inicial:");
        startDateLabel.setFont(new Font("Arial", Font.BOLD, 20));
        startDateLabel.setForeground(new Color(40,40,40));
        mainPanel.add(startDateLabel, gbc);

        try
        {
            MaskFormatter dateMask = new MaskFormatter("##/##/####");
            dateMask.setPlaceholderCharacter('_');
            startTextField = new JFormattedTextField(dateMask);
            startTextField.setFont(new Font("Arial", Font.PLAIN, 16));
            startTextField.setEnabled(true);
            startTextField.setPreferredSize(new Dimension(200,30));
            startTextField.setFocusLostBehavior(JFormattedTextField.PERSIST);
            mainPanel.add(startTextField, gbc);
        } 
        catch (Exception e)
        {
            e.printStackTrace();
        }
        
        // -- End Date --
        JLabel endDateLabel = new JLabel("Data Final:");
        endDateLabel.setFont(new Font("Arial", Font.BOLD, 20));
        endDateLabel.setForeground(new Color(40,40,40));
        mainPanel.add(endDateLabel, gbc);

        try
        {
            MaskFormatter dateMask = new MaskFormatter("##/##/####");
            dateMask.setPlaceholderCharacter('_');
            endTextField = new JFormattedTextField(dateMask);
            endTextField.setFont(new Font("Arial", Font.PLAIN, 16));
            endTextField.setEnabled(true);
            endTextField.setPreferredSize(new Dimension(200,30));
            endTextField.setFocusLostBehavior(JFormattedTextField.PERSIST);
            mainPanel.add(endTextField, gbc);
        } 
        catch (Exception e)
        {
            e.printStackTrace();
        }

        updateDateFieldsEditability(comboType);

        comboType.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateDateFieldsEditability(comboType);
            }
        });
        // -- Generate Report Button --
        JButton generateButton = new JButton("Gerar Relatório");
        generateButton.setBackground(new Color(0, 86, 179));
        generateButton.setForeground(Color.WHITE);
        generateButton.setFont(new Font("Arial", Font.BOLD, 16));
        generateButton.setFocusPainted(false);
        generateButton.setBorderPainted(false);
        mainPanel.add(generateButton, gbc);

        generateButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e) {
               String selectedReport = comboType.getSelectedItem().toString();

               if (selectedReport.equals("Produtos Vencidos ou Próximos de Vencer")) {
                   // Para "Produtos Vencidos", não importa o valor das datas
                   TelaMostraRelatorio telaMostraRelatorio = new TelaMostraRelatorio(
                           TelaRelatorio.this,
                           selectedReport,
                           null, // Datas não são relevantes para este relatório
                           null,
                           stock,
                           cashControl,
                           orders
                   );
                   telaMostraRelatorio.setVisible(true);
               } else {
                   // Para outros relatórios, as datas são necessárias e validadas
                   if (startTextField.getText().isEmpty() || endTextField.getText().isEmpty()
                           || startTextField.getText().equals("__/__/____") || endTextField.getText().equals("__/__/____")) {
                       JOptionPane.showMessageDialog(TelaRelatorio.this,
                               "Preencha Todas as Datas",
                               "Erro Cadastro",
                               JOptionPane.ERROR_MESSAGE);
                       return;
                   }

                   DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                   try {
                       LocalDate startDate = LocalDate.parse(startTextField.getText(), formatter);
                       LocalDate endDate = LocalDate.parse(endTextField.getText(), formatter);

                       if (startDate.isAfter(endDate)) {
                           JOptionPane.showMessageDialog(TelaRelatorio.this,
                                   "A data inicial deve ser anterior ou igual à data final.",
                                   "Erro de Intervalo",
                                   JOptionPane.ERROR_MESSAGE);
                           return;
                       }

                       TelaMostraRelatorio telaMostraRelatorio = new TelaMostraRelatorio(TelaRelatorio.this, selectedReport, startDate, endDate, stock, cashControl, orders);
                       telaMostraRelatorio.setVisible(true);

                   } catch (DateTimeParseException ex) {
                       JOptionPane.showMessageDialog(TelaRelatorio.this,
                               "Formato de data inválido. Use DD/MM/AAAA.",
                               "Erro de Formato",
                               JOptionPane.ERROR_MESSAGE);
                   }
               }
           }
       });

        // -- Back Button --
        JButton backButton = new JButton("Voltar");
        backButton.setBackground(new Color(0, 86, 179));
        backButton.setForeground(Color.WHITE);
        backButton.setFont(new Font("Arial", Font.BOLD, 16));
        backButton.setFocusPainted(false);
        backButton.setBorderPainted(false);
        gbc.fill = 0;
        gbc.anchor = GridBagConstraints.EAST;
        mainPanel.add(backButton, gbc);

        backButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed (ActionEvent e)
            {
                TelaAdmin telaAdmin = new TelaAdmin(stock, cashControl, orders);
                telaAdmin.setVisible(true);
                dispose();
            }
            
        });

        add(mainPanel, BorderLayout.CENTER);
    }

    /**
     * Atualiza a editabilidade e a aparência dos campos de data.
     *
     * Este método habilita ou desabilita os campos de data inicial e final,
     * e altera a cor do texto para indicar visualmente se eles estão ativos.
     * Os campos são desabilitados e ficam com a cor cinza escuro quando o tipo de
     * relatório selecionado é "Produtos Vencidos ou Próximos de Vencer", pois
     * este relatório não utiliza um intervalo de datas. Para outros tipos de
     * relatório, os campos são habilitados e a cor do texto volta para preto.
     *
     * @param comboType O {@link JComboBox} que contém o tipo de relatório selecionado.
     */
    private void updateDateFieldsEditability(JComboBox<String> comboType) {
        boolean enableDates = !comboType.getSelectedItem().equals("Produtos Vencidos ou Próximos de Vencer");
        startTextField.setEditable(enableDates);
        endTextField.setEditable(enableDates);
        startTextField.setForeground(enableDates ? Color.BLACK : new Color(43, 45, 47));
        endTextField.setForeground(enableDates ? Color.BLACK : new Color(43, 45, 47));
    }
}