/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view.abstractfactory;

import control.ControleLoja;
import java.awt.Dimension;
import javax.swing.BorderFactory;
import javax.swing.GroupLayout;
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
public class PainelLateralAtendente extends PainelLateral {

    private JSeparator separador;

    public PainelLateralAtendente(ControleLoja controlador) {
        super(controlador);
        
        this.setPreferredSize( new Dimension( Principal.TAMANHO_SCROLL_LATERAL_X - 30 , Principal.TAMANHO_SCROLL_LATERAL_Y - 30) );
                
        this.setBorder( BorderFactory.createTitledBorder(null,"Atendente",TitledBorder.CENTER,TitledBorder.DEFAULT_POSITION) );
                
        // Adiciona itens do administrador ao menu do administrador
        criarItensPainelAtendente();
        
        // Adiciona um separador, para separar as opções do administrador das opções comuns entre usuários
        separador = new JSeparator();
        separador.setPreferredSize( new Dimension( Principal.TAMANHO_PADRAO_BOTOES_PAINEL_LATERAL.width , 1) );
        
        adicionarItens();

    }

    private void criarItensPainelAtendente() {
        // Não é criado nado pois o atendente só possui função comum a todos usuários
    }

    private void adicionarItens() {
        JButton[] botoesComuns = new JButton[3];
        botoesComuns[0] = super.itensComuns.getExibirProdutos();
        botoesComuns[1] = super.itensComuns.getExibirKits();
        botoesComuns[2] = super.itensComuns.getBuscarProdutos();
        
        for ( int i = 0; i < botoesComuns.length; i++ ) {
            botoesComuns[i].setPreferredSize( Principal.TAMANHO_PADRAO_BOTOES_PAINEL_LATERAL );
        }
        
        AdicionarItensPainel.adicionarItensPainel(this, separador);
        AdicionarItensPainel.adicionarItensPainel(this, botoesComuns);
    }
    
    
}
