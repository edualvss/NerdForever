/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import control.ControleLoja;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JMenu;
import javax.swing.JMenuItem;

/**
 *
 * @author Eduardo
 */
public class ItensComunsMenu {
    
    public void addItensComuns(JMenu menu,ControleLoja control) {
        // Cria tratador de opção comuns entre usuários do sistema
        TratadorMenusComunsEntreUsuarios handlerComum = new TratadorMenusComunsEntreUsuarios(control);
        // Cria opção Visualizar Produtos Disponíveis
        JMenuItem visualizarProdutosDisponiveis = new JMenuItem("Visualizar produtos disponíveis");
        visualizarProdutosDisponiveis.addActionListener( handlerComum );
        menu.add( visualizarProdutosDisponiveis );
        // Cria opção Visualizar Produtos Disponíveis
        JMenuItem visualizarKitsDisponiveis = new JMenuItem("Visualizar kits Disponíveis");
        visualizarKitsDisponiveis.addActionListener(handlerComum);
        menu.add(visualizarKitsDisponiveis);
        // Cria opção buscar produto
        JMenuItem buscarProduto = new JMenuItem("Buscar produto");
        buscarProduto.addActionListener(handlerComum);
        menu.add(buscarProduto);
    }
    
    private class TratadorMenusComunsEntreUsuarios implements ActionListener {

        private ControleLoja controlador;

        public TratadorMenusComunsEntreUsuarios(ControleLoja controlador) {
            this.controlador = controlador;
        }

        @Override
        public void actionPerformed(ActionEvent ae) {
            JMenuItem disparou = (JMenuItem) ae.getSource();
            String operacao = disparou.getText();

            if( operacao.equals( "Visualizar produtos disponíveis" ) ) {
                controlador.visualizarProdutosDisponiveis();
            } else if( operacao.equals("Visualizar kits Disponíveis") ) {
                controlador.visualizarKitsDisponiveis();
            } else if( operacao.equals("Buscar produto") ) {
                controlador.buscarProduto();
            }
        }
    
    }
    
}
