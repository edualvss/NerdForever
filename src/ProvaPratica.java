
import control.Controlador;
import control.ControleLoja;
import javax.swing.UIManager;
import view.Principal;
import view.VisaoLojaNerd;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Eduardo
 */
public class ProvaPratica {

    /**
     * Inicia a aplicação através do controle
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO Deve-se iniciar o banco de dados antes de executar a aplicação
        
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        
        VisaoLojaNerd telaPrincipal = new Principal();
        ControleLoja controlador = new Controlador(telaPrincipal);
        controlador.iniciarApp();        
    }
}
