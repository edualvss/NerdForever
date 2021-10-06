/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import java.util.Iterator;
import model.Kit;
import model.Produto;
import view.abstractfactory.FabricaGUI;

/**
 *
 * @author Eduardo
 */
public interface VisaoLojaNerd {

    public void setFabricaGUI(FabricaGUI fabricaGUI);

    public void exibirMsg(String string);
    
    public void exibirProdutos(Iterator<Produto> produtos);
    
    public void exibirKits(Iterator<Kit> kits);

    public Produto cadastrarProduto();

    public String cadastrarKit(String exibicaoProdutos);

    public String buscarProduto();
}
