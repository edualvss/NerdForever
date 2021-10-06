/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view.abstractfactory;

import java.awt.event.ActionEvent;
import control.ControleLoja;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JSeparator;
import javax.swing.border.TitledBorder;
import view.AdicionarItensPainel;
import view.Principal;

/**
 *
 * @author Eduardo
 */
@SuppressWarnings("serial")
public class PainelLateralAdministrador extends PainelLateral {
    
    private JButton cadastrarProduto;
    private JButton cadastrarKit;
    
    private JSeparator separador;
    
    public PainelLateralAdministrador(ControleLoja controlador) {
        super(controlador);
        
        this.setBorder( BorderFactory.createTitledBorder(null,"Administrador",TitledBorder.CENTER,TitledBorder.DEFAULT_POSITION) );
                        
        // Cria itens do administrador ao menu do administrador
        criarItensPainelAdministrador();
        
        // Cria um separador, para separar as opções do administrador das opções comuns entre usuários
        separador = new JSeparator();
        separador.setPreferredSize( new Dimension( Principal.TAMANHO_PADRAO_BOTOES_PAINEL_LATERAL.width , 1) );

        // Adiciona itens ao painel
        adicionarItens();

        // TODO Procurar melhor solução
        // Obtém todos os componentes que estão no painel
        Component[] componentes = this.getComponents();
        int deslocamentoBarraRolagem = 0; // Variável de deslocamento de altura conforme qtd de componentes
        for (int i = 0; i < componentes.length; i++) {
            deslocamentoBarraRolagem += componentes[i].getPreferredSize().height;
        }
                                                                                     // SomaAlturaComponentes  + numComponentes   * espaçoEntreComponentes
        this.setPreferredSize( new Dimension(Principal.TAMANHO_SCROLL_LATERAL_X - 30 , deslocamentoBarraRolagem+componentes.length*6) );

        TratadorEventosPainelAdministrador handler = new TratadorEventosPainelAdministrador(super.controlador);
        
        this.cadastrarProduto.addActionListener(handler);
        this.cadastrarKit.addActionListener(handler);
    }

    private void criarItensPainelAdministrador() {
        cadastrarProduto = new JButton("Cadastrar Produto");
        cadastrarProduto.setPreferredSize( Principal.TAMANHO_PADRAO_BOTOES_PAINEL_LATERAL );
        cadastrarKit = new JButton("Cadastrar Kit");
        cadastrarKit.setPreferredSize( Principal.TAMANHO_PADRAO_BOTOES_PAINEL_LATERAL );
    }

    private void adicionarItens() {
        JButton[] botoesComuns = new JButton[3];
        botoesComuns[0] = super.itensComuns.getExibirProdutos();
        botoesComuns[1] = super.itensComuns.getExibirKits();
        botoesComuns[2] = super.itensComuns.getBuscarProdutos();
        
        for( int i = 0; i < botoesComuns.length; i++ ) {
            botoesComuns[i].setPreferredSize( Principal.TAMANHO_PADRAO_BOTOES_PAINEL_LATERAL );
        }
        
        AdicionarItensPainel.adicionarItensPainel(this, this.cadastrarProduto,this.cadastrarKit,this.separador);
        AdicionarItensPainel.adicionarItensPainel(this, botoesComuns);
        
        // TODO Verificar o funcionamento do scroll ,,,, teste
//        JButton[] teste = new JButton[20];
//        for (int i = 0; i < teste.length; i++) {
//            teste[i] = new JButton("i: "+i);
//            teste[i].setPreferredSize( Principal.TAMANHO_PADRAO_BOTOES_PAINEL_LATERAL );
//        }
//        AdicionarItensPainel.adicionarItensPainel(this, teste);
    }
    
    private class TratadorEventosPainelAdministrador implements ActionListener {

        private ControleLoja controlador;

        public TratadorEventosPainelAdministrador(ControleLoja controlador) {
            this.controlador = controlador;
        }
        
        @Override
        public void actionPerformed(ActionEvent ae) {
            JButton botao = (JButton) ae.getSource();
            String operacao = botao.getText();
                     
            if( operacao.equals("Cadastrar Produto") ) {
                this.controlador.cadastrarProduto();
            } else if( operacao.equals("Cadastrar Kit") ) {
                this.controlador.cadastrarKit();
            }
        }
        
    }
    
}
