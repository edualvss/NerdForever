/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view.abstractfactory;

import control.ControleLoja;
import javax.swing.JPanel;
import view.ItensComunsPainel;

/**
 *
 * @author Eduardo
 */
public abstract class PainelLateral extends JPanel {

    protected ItensComunsPainel itensComuns;
    protected ControleLoja controlador;
    
    public PainelLateral(ControleLoja control) {
        this.controlador = control;
        this.itensComuns = new ItensComunsPainel(this.controlador);
    }
    
    
}
