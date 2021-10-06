/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import control.ControleLoja;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.swing.GroupLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.LayoutStyle;
import javax.swing.table.DefaultTableModel;
import model.Kit;
import model.Produto;
import view.abstractfactory.BarraMenu;
import view.abstractfactory.FabricaGUI;
import view.abstractfactory.PainelLateral;


/**
 *
 * @author Eduardo
 */
@SuppressWarnings("serial")
public class Principal extends JFrame implements VisaoLojaNerd {

    public static final int TAMANHO_SCROLL_LATERAL_X = 200;
    public static final int TAMANHO_SCROLL_LATERAL_Y = 540;
    public static final int TAMANHO_SCROLL_CENTRAL_X = 615;
    public static final int TAMANHO_SCROLL_CENTRAL_Y = 540;
    
    public static final Dimension TAMANHO_PADRAO_BOTOES_PAINEL_LATERAL = new Dimension(122, 24);
    
    private FabricaGUI gui;
    private BarraMenu barraMenu;
    private PainelLateral painelLateral;
    
    private ControleLoja controlador;

    private JScrollPane scrollPainelLateral;
    private JScrollPane scrollPainelCentral;
    
    private JTable tabela;

    public Principal() {
        this.setSize( new Dimension(800, 600) );
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
        this.setTitle( "Nerd Forever" );
                
        inicializarScrolls();
        
        tabela = new JTable();
        
        configurarLayout();
    }
    
    private void inicializarScrolls() {
        this.scrollPainelLateral = new JScrollPane();
        this.scrollPainelCentral = new JScrollPane();
    }
    
    private void configurarLayout() {
        GroupLayout layout = new GroupLayout(this.getContentPane());
        this.getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(this.scrollPainelLateral, GroupLayout.PREFERRED_SIZE, TAMANHO_SCROLL_LATERAL_X, GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(this.scrollPainelCentral, GroupLayout.DEFAULT_SIZE, TAMANHO_SCROLL_CENTRAL_X, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addComponent(this.scrollPainelCentral, GroupLayout.DEFAULT_SIZE, TAMANHO_SCROLL_CENTRAL_Y, Short.MAX_VALUE)
                    .addComponent(this.scrollPainelLateral, GroupLayout.DEFAULT_SIZE, TAMANHO_SCROLL_LATERAL_Y, Short.MAX_VALUE))
                .addContainerGap())
        );
    }
    
    public void setControlador(ControleLoja controlador) {
        this.controlador = controlador;
    }
    
    @Override
    public void setFabricaGUI(FabricaGUI fabricaGUI) {
        this.gui = fabricaGUI;
        recriarGUI();
    }
    private void recriarGUI() {
        if( this.gui != null ) {
            this.barraMenu = gui.criarBarraMenu(controlador);
            this.setJMenuBar(this.barraMenu);
            
            this.painelLateral = gui.criarPainelLateral(controlador);
            scrollPainelLateral.setViewportView(this.painelLateral);
            
            // ajusta o tamanho do scrollPane onde é colocado o menu
            scrollPainelLateral.setPreferredSize( new Dimension(150, 400) );
            scrollPainelLateral.setMinimumSize( scrollPainelLateral.getPreferredSize() );
            revalidate();
        }

    }

    @Override
    public void exibirProdutos(Iterator<Produto> it) {
        
        List<Produto> produtos = new ArrayList<Produto>();
        while( it.hasNext() ) {
            produtos.add(it.next());
        }
        int qtdProdutos = produtos.size();
        Object[][] itensTabela = new Object[qtdProdutos][4];
        
        for ( int i = 0; i < qtdProdutos; i++ ) {
            Produto produto = produtos.get(i);
            itensTabela[i][0] = produto.getCodigoProduto();
            itensTabela[i][1] = produto.getDescricao();
            itensTabela[i][2] = produto.getDistribuicaoLinux();
            itensTabela[i][3] = produto.getPreco();
        }
        
        String[] titulosTabela = {"Código","Descrição","Distribuição Linux","Preço"};
        DefaultTableModel modeloTabela = new DefaultTableModel(itensTabela, titulosTabela);
        
        tabela.setModel(modeloTabela);
        tabela.setEnabled(false);
        scrollPainelCentral.setViewportView(tabela);
    }

    @Override
    public void exibirKits(Iterator<Kit> it) {
        
        List<Kit> kits = new ArrayList<Kit>();
        while( it.hasNext() ) {
            kits.add( it.next() );
        }
        int qtdKits = kits.size();
        List<Object> linhas = new ArrayList<Object>();
        Object[] colunas;
        
        for ( int i = 0; i < qtdKits; i++ ) {
            Kit kit = kits.get(i);
            Iterator<Produto> produtosKit = kit.getProdutosDoKit();
            colunas = new Object[5];
            colunas[0] = "Kit "+(i+1);
            colunas[1] = "";
            colunas[2] = "";
            colunas[3] = "";
            colunas[4] = "";
            linhas.add(colunas);
            while( produtosKit.hasNext() ) {
                Produto produto = produtosKit.next();
                colunas = new Object[5];
                colunas[0] = "";
                colunas[1] = produto.getCodigoProduto();
                colunas[2] = produto.getDescricao();
                colunas[3] = produto.getDistribuicaoLinux();
                colunas[4] = produto.getPreco();
                linhas.add(colunas);
            }
            colunas = new Object[5];
            colunas[0] = "";
            colunas[1] = "";
            colunas[2] = "";
            colunas[3] = "Preço total do kit "+(i+1);
            colunas[4] = kit.getPrecoDoKit();
            linhas.add(colunas);
        }
        
        int qtdLinhas = linhas.size();
        Object[][] itensTabela = new Object[qtdLinhas][5];
        for ( int i = 0; i < qtdLinhas; i++ ) {
            itensTabela[i] = (Object[]) linhas.get(i);
        }
        String[] titulosTabela = { "Kits","Código","Descrição","Distribuição Linux","Preço" };
        
        DefaultTableModel modeloTabela = new DefaultTableModel(itensTabela, titulosTabela);
        tabela.setModel(modeloTabela);
        tabela.setEnabled(false);
        scrollPainelCentral.setViewportView(tabela);
    }
        
    @Override
    public void exibirMsg(String msg) {
        JOptionPane.showMessageDialog(this, msg);
    }

    /**
     * Tela de cadastro de um produto
     * @return O produto cadastrado com os dados de entrada, ou null
     * caso seja cancelada a operação
     */
    @Override
    public Produto cadastrarProduto() {
        
        JLabel jlabelCodigoProduto = new JLabel("Código do produto");
        JTextField jtfCodigoProduto = new JTextField();
        
        JLabel jlabelDescricao = new JLabel("Descrição do produto");
        JTextField jtfDescricao = new JTextField();
        
        JLabel jlabelDistribuicaoLinux = new JLabel("Distribuição linux");
        JTextField jtfDistribuicaoLinux = new JTextField();
        
        JLabel jlabelPreco = new JLabel("Preço do produto");
        JTextField jtfPreco = new JTextField();
        
        
        Object[] componentes = { jlabelCodigoProduto, jtfCodigoProduto,jlabelDescricao,jtfDescricao,
            jlabelDistribuicaoLinux,jtfDistribuicaoLinux,jlabelPreco,jtfPreco };
        
        Produto produto = null;
        do{
            if( JOptionPane.showConfirmDialog(this, componentes, "Cadastro de produto",
                    JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE) == 0 ) {
                String codigoProduto = jtfCodigoProduto.getText();
                String descricao = jtfDescricao.getText();
                String distLinux = jtfDistribuicaoLinux.getText();
                String preco = jtfPreco.getText();
                if(codigoProduto.isEmpty() || descricao.isEmpty() || distLinux.isEmpty() || preco.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Algum(ns) campo(s) esta(ão) vazio(s)!\n"
                            + "Por favor corrija!");
                    continue;
                }
                produto = new Produto(descricao, distLinux, Float.parseFloat(preco), Integer.parseInt(codigoProduto));
            } else {
                this.exibirMsg("Cadastro cancelado");
                break;
            }
        } while ( produto == null );
        
        return produto;
    }

    /**
     * Tela de cadastro de Kit
     * @param exibicaoProdutos
     * @return Uma String contendo o que o usuário digitou
     */
    @Override
    public String cadastrarKit(String exibicaoProdutos) {
        exibicaoProdutos += "\n\nDigite os códigos do produtos desejados, separados por vírgula.\n"
                + "Ex: 37,59,73";
        return JOptionPane.showInputDialog(this,exibicaoProdutos);
    }

    /**
     * Tela de busca de produto por descrição
     * @return Uma String contendo a descrição do que deve ser buscado
     */
    @Override
    public String buscarProduto() {
        return JOptionPane.showInputDialog(this,"Digite a descrição do produto que desejas");
    }

    public void limparPainelCentral() {
        this.scrollPainelCentral.setViewportView(null);
    }

}
