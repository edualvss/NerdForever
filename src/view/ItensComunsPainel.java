/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import control.ControleLoja;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;

/**
 *
 * @author Eduardo
 */
public class ItensComunsPainel {

    private JButton exibirProdutos;
    private JButton exibirKits;
    private JButton buscarProdutos;

    private ControleLoja controlador;
    
    public ItensComunsPainel(ControleLoja control) {
        this.controlador = control;
        this.exibirProdutos = new JButton("Exibir produtos");
        this.exibirKits = new JButton("Exibir kits");
        this.buscarProdutos = new JButton("Buscar Produtos");
        
        TratadorEventosPainelComum handler = new TratadorEventosPainelComum();
        exibirProdutos.addActionListener( handler );
        exibirKits.addActionListener(handler);
        buscarProdutos.addActionListener(handler);
    }

    public JButton getBuscarProdutos() {
        return buscarProdutos;
    }

    public JButton getExibirKits() {
        return exibirKits;
    }

    public JButton getExibirProdutos() {
        return exibirProdutos;
    }
    
    private class TratadorEventosPainelComum implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent ae) {
            JButton botao = (JButton) ae.getSource();
            String operacao = botao.getText();
            
            if( operacao.equals("Exibir produtos") ) {
                ItensComunsPainel.this.controlador.visualizarProdutosDisponiveis();
            } else if( operacao.equals("Exibir kits") ) {
                ItensComunsPainel.this.controlador.visualizarKitsDisponiveis();
            } else if( operacao.equals("Buscar Produtos") ) {
                ItensComunsPainel.this.controlador.buscarProduto();
            }
        }
        
    }
}
