/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import control.ControleUsuario;

/**
 *
 * @author Eduardo
 */
public class Usuario {
    
    private int tipoUsuario;
    private String nomeUsuario;
    private String senhaUsuario;

    public Usuario(int tipoUsuario, String nomeUsuario, String senhaUsuario) {
        this.tipoUsuario = tipoUsuario;
        this.nomeUsuario = nomeUsuario;
        this.senhaUsuario = senhaUsuario;
    }

    public boolean isAdministrador() {
        switch( this.tipoUsuario ) {
            case ControleUsuario.USUARIO_ADMINISTRADOR:
                return true;
            case ControleUsuario.USUARIO_ATENDENTE:
                return false;
            case ControleUsuario.USUARIO_INVALIDO:
                return false;
        }
        return false;
    }
    
    
    
}
