/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view.abstractfactory;

import control.Controlador;
import control.ControleLoja;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import view.ItensComunsMenu;

/**
 *
 * @author Eduardo
 */
public abstract class BarraMenu extends JMenuBar { 
    
    protected JMenu menuUsuario;
    protected ItensComunsMenu itensComuns;
    
    private JMenuItem logoff;
    
    protected ControleLoja controlador;
    
    public BarraMenu(ControleLoja control) {
        this.controlador = control;
        this.menuUsuario = new JMenu();
        this.menuUsuario.setText("Usuário");

        this.itensComuns = new ItensComunsMenu();
        
        this.logoff = new JMenuItem("Logoff");
        menuUsuario.add( logoff );
        
        TratadorMenuUsuario handler = new TratadorMenuUsuario();
        this.logoff.addActionListener( handler );
        

        this.add(menuUsuario);
    }
    
    
    private class TratadorMenuUsuario implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent ae) {
            ((Controlador)BarraMenu.this.controlador).limparPainelCentral();
            while( !BarraMenu.this.controlador.efetuarLogin() ) {
                if( ((Controlador)BarraMenu.this.controlador).getLogin().isBotaoFechar() ) {
                    System.exit(1);
                }
            }
        }
        
    }
    
}
