/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

import java.sql.SQLException;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.*;
import view.Principal;
import view.TelaLogin;
import view.VisaoLojaNerd;
import view.abstractfactory.FabricaGUIAdministrador;
import view.abstractfactory.FabricaGUIAtendente;

/**
 * @since 01/09/2011
 * @author Eduardo
 */
public class Controlador implements ControleLoja,ControleUsuario {
    // Constantes com o endereço do banco de dados e arquivo de texto
    public static final String URL_BANCO = "jdbc:derby://localhost:1527/lojanerd";
    public static final String ARQUIVO_TXT = "KIT.txt";
    
    private VisaoLojaNerd visao;
    private TelaLogin login;

    private Loja loja;

    public Controlador(VisaoLojaNerd visao) {
        this.visao = visao;
        try {
            this.loja = new Loja();
        } catch (SQLException ex) {
            visao.exibirMsg("Problema de conexão com o banco de dados.\nPor favor verifique");
            System.exit(1);
        }
    }
    

    @Override
    public void iniciarApp() {
        Principal tela = (Principal) this.visao;
        tela.setControlador(this);
        tela.setVisible(true);
        
        // Cria uma tela de login
        this.login = new TelaLogin(tela, true);
        login.setLocationRelativeTo(tela);

        while( !efetuarLogin() ) {
            if( login.isBotaoFechar() ) {
                System.exit(1);
            }
        }
        // Após isto o controle da aplicação é passado para a tela( atributo visao // Classe Principal )
    }


    /**
     * Solicita a visão um produto com atributos preenchidos,
     * e tenta adicionar o produto ao banco de dados
     */
    @Override
    public void cadastrarProduto() {
        Produto produtoCadastrado = visao.cadastrarProduto();
        if(produtoCadastrado == null) {
            return;
        }
        if( this.loja.adicionarNovoProduto(produtoCadastrado) ) {
            visao.exibirMsg("Cadastro efetuado com sucesso!");
        } else {
            visao.exibirMsg("Erro ao efetuar cadastro");
        }
    }

    /**
     * Solicita a construção de uma String contendo a especificação
     * dos produtos existentes na loja, exibe-os e solicita a visão 
     * uma String contendo uma lista de códigos para formar um kit de
     * produtos, solicita uma busca ao banco de dados dos produtos
     * através do código inserido pelo usuário, e monta um Kit com todos
     * os produtos, manda o kit para o domínio inseri-lo no arquivo de texto
     */
    @Override
    public void cadastrarKit() {
        
        String exibicaoProdutos = this.montarStringExibicaoProdutos();
        
        if(exibicaoProdutos == null) {
            visao.exibirMsg("Não há produtos cadastrados\nVerifique se a conexão com o banco foi estabelecida");
            return;
        }
        
        String codigosProdutosKit = visao.cadastrarKit(exibicaoProdutos);
        
        if(codigosProdutosKit == null) {
            visao.exibirMsg("Erro ao efetuar cadastro");
            return;
        }
        
        String[] codigoProduto = codigosProdutosKit.split(",");
        
        if(codigoProduto.length < 2){
            visao.exibirMsg("Quantidade insuficiente de produtos cadastrados para criar um kit.\n"
                + "Cadastre mais produtos para poder formar um kit!");
            return;
        }
        
        Kit kit = new Kit();
        for(int i = 0; i < codigoProduto.length; i++) {
            Produto produto = null;
            try {
                produto = this.loja.buscarProdutoPorCodigo(Integer.parseInt(codigoProduto[i]));
            } catch (SQLException ex) {
                Logger.getLogger(Controlador.class.getName()).log(Level.SEVERE, null, ex);
            }
            if(produto == null) {
                visao.exibirMsg("Produto não encontrado");
                visao.exibirMsg("Erro ao efetuar cadastro");
                return;
            }
            kit.adicionarProdutoAoKit(produto);
        }
        
        if( this.loja.adicionarNovoKit(kit) ) {
            visao.exibirMsg("Cadastro efetuado com sucesso!");
        } else {
            visao.exibirMsg("Erro ao efetuar cadastro");
        }
        
    }
    
    /**
     * Solicita a montagem de uma String para exibição dos produtos
     * disponíveis para a venda na loja, e manda para visão exibir
     */
    @Override
    public void visualizarProdutosDisponiveis() {
        Iterator<Produto> it = null;
        try {
            it = this.loja.listarProdutosDisponíveis();
        } catch (SQLException ex) {
            Logger.getLogger(Controlador.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        if(it == null) {
            visao.exibirMsg("Não há produtos cadastrados"
                    + "\nVerifique se a conexão com o banco foi estabelecida");
            return;
        }
        
        visao.exibirProdutos(it);
    }

    /**
     * Solicita ao domínio uma consulta ao banco de dados para montar
     * uma String contendo a especificação de todos os produtos
     * @return A String contendo a especificação de todos os produtos, ou null
     * caso um erro tenho ocorrido
     */
    private String montarStringExibicaoProdutos() {
        Iterator<Produto> it = null;
        try {
            it = this.loja.listarProdutosDisponíveis();
        } catch (SQLException ex) {
            Logger.getLogger(Controlador.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        if(it == null) {
            return null;
        }
        
        String exibicao = "Produtos disponíveis\n\n";
        
        while(it.hasNext()) {
            exibicao += it.next().getEspecificacaoProduto()+"\n";
        }
        return exibicao;
    }

    /**
     * Solicita a montagem de uma String contendo as especificações
     * de todos os kits cadastrados, manda para a visão exibir
     */
    @Override
    public void visualizarKitsDisponiveis() {
        Iterator<Kit> it = null;
        try {
            it = this.loja.listarKitsDisponiveis();
        } catch (SQLException ex) {
            Logger.getLogger(Controlador.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        if( it == null ) {
            return;
        }
        
        visao.exibirKits(it);
        
    }


    /**
     * Solicita a visão uma String contendo a requisição de uma busca,
     * após solicita ao domínio uma consulta ao banco para ver quais produtos
     * serão listados na pesquisa. Após monta uma String contendo o resultado
     * da busca, e manda para a visão exibir
     */
    @Override
    public void buscarProduto() {
        
        String busca = visao.buscarProduto();
        
        if(busca == null) {
            visao.exibirMsg("Busca cancelada");
        }
        
        Iterator<Produto> it = this.loja.buscarProdutoPorDescricao(busca);
        
        if(it == null) {
            visao.exibirMsg("Ocorreu um erro na busca");
            return;
        }
        
        if(!it.hasNext()) {
            visao.exibirMsg("Não foram encontrados resultados na busca");
            return;
        }
                
        visao.exibirProdutos(it);
    }

    @Override
    public void realizarAutenticacao(String nome,String senha) {
        Usuario usuario = this.loja.autenticaUsuario(nome, senha);
        if( usuario != null ) { // Autenticação bem sucedida
            this.loja.setUsuario(usuario);
            if( usuario.isAdministrador() ) {
               visao.setFabricaGUI( new FabricaGUIAdministrador() );
            } else {
                visao.setFabricaGUI( new FabricaGUIAtendente() );
            }
        } else { // Autenticação mal sucedida
            visao.exibirMsg("Senha ou nome de usuário inválido!");
        }
    }

    @Override
    public boolean efetuarLogin() {
        this.login.setVisible(true);

        this.loja.setUsuario(null);
        
        String usuario = login.getUsuario();
        String senha = login.getSenha();

        // Se usuário clicar botão fechar na autenticação
        if( login.isBotaoFechar() ) {
            return false;
        }

        realizarAutenticacao(usuario, senha);

        if( this.loja.getUsuario() == null ) {
            return false;
        } else {
            return true;
        }
    }

    public TelaLogin getLogin() {
        return login;
    }
    
    public void limparPainelCentral() {
        ((Principal)this.visao).limparPainelCentral();
    }
    
}
