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
public class FabricaGUIAtendente implements FabricaGUI {

    @Override
    public BarraMenu criarBarraMenu(ControleLoja controlador) {
        return new BarraMenuAtendente(controlador);
    }

    @Override
    public PainelLateral criarPainelLateral(ControleLoja controlador) {
        return new PainelLateralAtendente(controlador);
    }
    
}
