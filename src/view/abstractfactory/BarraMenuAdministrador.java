/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view.abstractfactory;

import control.ControleLoja;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JSeparator;

/**
 *
 * @author Eduardo
 */
@SuppressWarnings("serial")
public class BarraMenuAdministrador extends BarraMenu {
    
    private JMenu menuAdmin;
    
    public BarraMenuAdministrador(ControleLoja control) {
        super(control);
        
        this.menuAdmin = new JMenu();
        this.menuAdmin.setText("Administrador");

        // Adiciona itens do administrador ao menu do administrador
        adicionarItensMenuAdministrador();
        
        // Adiciona um separador, para separar as opções do administrador das opções comuns entre usuários
        JSeparator separador = new JSeparator();
        this.menuAdmin.add(separador);
        
        // Adiciona Itens comuns aos usuários
        super.itensComuns.addItensComuns(menuAdmin,super.controlador);
        
        // Adiciona o menu a barra de menu
        this.add( this.menuAdmin );
    }
    
    private void adicionarItensMenuAdministrador() {
        // Cria tratador de opções de administrador
        TratadorMenuAdministrador handler = new TratadorMenuAdministrador();
        
        // Cria opção de cadastrar produto
        JMenuItem cadastrarProduto = new JMenuItem("Cadastrar Produto");
        cadastrarProduto.addActionListener(handler);
        this.menuAdmin.add(cadastrarProduto);
        // Cria opção de cadastrar kit
        JMenuItem cadastrarKit = new JMenuItem("Cadastrar Kit");
        cadastrarKit.addActionListener(handler);
        this.menuAdmin.add(cadastrarKit);

    }
    
    private class TratadorMenuAdministrador implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent ae) {
            JMenuItem disparou = (JMenuItem) ae.getSource();
            String operacao = disparou.getText();
            
            if( operacao.equals("Cadastrar Produto") ) {
                BarraMenuAdministrador.super.controlador.cadastrarProduto();
            } else if( operacao.equals("Cadastrar Kit") ) {
                BarraMenuAdministrador.super.controlador.cadastrarKit();
            }
        }
    }

}
