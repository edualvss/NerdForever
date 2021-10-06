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
public class FabricaGUIAdministrador implements FabricaGUI {

    @Override
    public BarraMenu criarBarraMenu(ControleLoja controlador) {
        return new BarraMenuAdministrador(controlador);
    }

    @Override
    public PainelLateral criarPainelLateral(ControleLoja controlador) {
        return new PainelLateralAdministrador(controlador);
    }
    
}
