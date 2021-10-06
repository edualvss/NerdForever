/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view.abstractfactory;

import control.ControleLoja;
import javax.swing.JMenu;
import javax.swing.JSeparator;

/**
 *
 * @author Eduardo
 */
@SuppressWarnings("serial")
public class BarraMenuAtendente extends BarraMenu {
    
    private JMenu menuAtendente;
    
    public BarraMenuAtendente(ControleLoja control) {
        super(control);
        
        this.menuAtendente = new JMenu();
        this.menuAtendente.setText("Atendente");

        // Adiciona itens do atendente ao menu do atendente
        adicionarItensMenuAtendente();
        
        // Adiciona um separador, para separar as opções do a das opções comuns entre usuários
        JSeparator separador = new JSeparator();
        this.menuAtendente.add(separador);
        
        // Adiciona Itens comuns aos usuários
        super.itensComuns.addItensComuns(menuAtendente, super.controlador);
        
        // Adiciona o menu a barra de menu
        this.add( this.menuAtendente );
        
        
    }
    
    private void adicionarItensMenuAtendente() {
        // Atendente só possui opção comum a todos usuários
    }

    
}