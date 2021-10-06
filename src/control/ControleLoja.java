/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

/**
 *
 * @author Eduardo
 */
public interface ControleLoja {

    public void iniciarApp();
    
    public void cadastrarProduto();
    
    public void cadastrarKit();
    
    public void visualizarProdutosDisponiveis();
    
    public void visualizarKitsDisponiveis();
    
    public void buscarProduto();
    
    public boolean efetuarLogin();
    
}
