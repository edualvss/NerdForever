/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

/**
 *
 * @author Eduardo
 */
public interface ControleUsuario {
 
    public static final int USUARIO_INVALIDO = 0;
    public static final int USUARIO_ADMINISTRADOR = 1;
    public static final int USUARIO_ATENDENTE = 2;
    
    public void realizarAutenticacao(String nome,String senha);
    
}
