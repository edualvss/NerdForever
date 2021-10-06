/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view.abstractfactory;

import control.ControleLoja;

/**
 *
 * @author Eduardo
 */
public interface FabricaGUI {
    
    public BarraMenu criarBarraMenu(ControleLoja controlador);
    
    public PainelLateral criarPainelLateral(ControleLoja controlador);
    
}
