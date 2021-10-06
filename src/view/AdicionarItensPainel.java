/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import javax.swing.JComponent;
import javax.swing.JPanel;

/**
 *
 * @author Eduardo
 */
public class AdicionarItensPainel {
    
    /**
     * 
     * @param componentes 
     */
    public static void adicionarItensPainel(JPanel painel,JComponent ... componentes) {
        
        
        for ( int i = 0; i < componentes.length; i++ ) {
            painel.add(componentes[i]);
        }
    }
    
}
