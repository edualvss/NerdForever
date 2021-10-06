/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import control.Controlador;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author Eduardo
 */
public class Loja {
    
    private Usuario usuario;
    
    private Connection conexao;
    private Statement comando;
    
    /**
     * Inicia a loja realizando conexão com o banco de dados
     * @throws SQLException Exceção caso banco de dados indisponível
     */
    public Loja() throws SQLException {
        System.out.println("Tentando criar a conexão");
        conexao = DriverManager.getConnection(Controlador.URL_BANCO);
        System.out.println("Conexão criada");
        comando = conexao.createStatement();
    }
    
    
    
    /**
     * Solicita uma conexão com o banco de dados, e obtém a tabela de produtos
     * do banco.
     * Com os dados do banco, cria os produtos referentes as linhas do banco.
     * 
     * @return O iterador da lista que contém todos os produtos obtidos do banco de dados,
     * ou null caso algum erro ocorra.
     * @throws SQLException Exceção SQL 
     */
    public Iterator<Produto> listarProdutosDisponíveis() throws SQLException {
        String SQL_SELECT = "SELECT * FROM produtos";
        
        ResultSet rs = comando.executeQuery(SQL_SELECT);
        List<Produto> produtos = new ArrayList<Produto>();
        while(rs.next()) {
            Produto produto = new Produto(rs.getString("descricao"), rs.getString("distribuicaolinux"),
                    rs.getFloat("preco"), rs.getInt("codigoproduto"));
            produtos.add(produto);
        }
        if( produtos.isEmpty() ){
            return null;
        }
        return produtos.iterator();
        
    }
    
    /**
     * Cria uma referência para o arquivo que contém o cadastro dos kits.
     * Verifica se o arquivo existe, caso não exista retorna null.
     * Realiza a leitura do arquivo, para a montagem dos kits
     * Com os dados do arquivo, cria os kits referentes as linhas do arquivo.
     * 
     * @return O iterador da lista que contém todos os kits obtidos do arquivo,
     * ou null caso algum erro ocorra.
     */
    public Iterator<Kit> listarKitsDisponiveis() throws SQLException {
        
        File arquivo = new File(Controlador.ARQUIVO_TXT);
        
        if( !arquivo.exists() ) {
            return null;
        }
        
        List<Kit> kits = new ArrayList<Kit>();
        
        Scanner scan = null;
        try {
            scan = new Scanner(arquivo);
        } catch (FileNotFoundException ex) {
            return null;
        }
        
        while ( scan.hasNext() ) {
            String linha = scan.nextLine();
            String[] codigoProduto = linha.split(",");
            Kit kit = new Kit();
            for(int i = 0; i < codigoProduto.length; i++) {
                Produto produto = buscarProdutoPorCodigo( Integer.parseInt(codigoProduto[i]) );
                if(produto == null) {
                    return null;
                }
                kit.adicionarProdutoAoKit(produto);
            }
            kits.add(kit);
        }
        scan.close();
        
        return kits.iterator();
        
    }

    /**
     * Solicita uma conexão com o banco de dados para inserção de um
     * produto no banco
     * @param produto Produto que deve ser inserido no banco
     * @return true se o produto for inserido com sucesso, false caso contrário
     */
    public boolean adicionarNovoProduto(Produto produto) {
        try {
            String SQL_INSERT = "INSERT INTO produtos (codigoproduto,descricao,distribuicaolinux,preco) "
                    + "VALUES ("+produto.getCodigoProduto()+",'"+produto.getDescricao()+"','"
                    +produto.getDistribuicaoLinux()+"',"+produto.getPreco()+")";
            comando.executeUpdate(SQL_INSERT);
            return true;
        } catch (SQLException ex) {
            return false;
        }
        
    }
    
    /**
     * Cria uma referência para o arquivo que contém o cadastro dos kits.
     * Caso o arquivo não exista, cria-o.
     * Se o arquivo existir, obtém os dados do arquivo, e monta uma String
     * contendo a descrição dos kits ja existentes.
     * Deleta o arquivo existente
     * Itera nos produtos do novo kit para montar o formato correto
     * da String que será escrita no novo arquivo.
     * Escreve a String contendo o novo kit, e os antigos
     * @param kit Kit a ser inserido no arquivo
     * @return true se o kit for inserido com sucesso, false caso contrário
     */
    public boolean adicionarNovoKit(Kit kit) {
        File arquivo = new File(Controlador.ARQUIVO_TXT);
        
        String escrita = "";
        if(!arquivo.exists()) {
            try {
                arquivo.createNewFile();
            } catch (IOException ex) {
                return false;
            }
        } else {
            try {
                Scanner scan = new Scanner(arquivo);
                while( scan.hasNext() ) {
                    escrita += scan.nextLine()+"\n";
                }
                scan.close();
            } catch (FileNotFoundException ex) {
                return false;
            }
        }
        
        arquivo.delete();
        
        Iterator<Produto> it = kit.getProdutosDoKit();
        
        for(;it.hasNext();) {
            escrita += it.next().getCodigoProduto();
            if(it.hasNext()) {
                escrita += ",";
            }
        }
        try {
            PrintStream ps = new PrintStream(arquivo);
            ps.print(escrita);
            ps.close();
        } catch (FileNotFoundException ex) {
            return false;
        }
        
        return true;
    }

    /**
     * Obtém o iterador da lista de produtos disponíveis e compara
     * o código dos produtos existentes com o código do parâmetro, em
     * busca de um produto existente através do código
     * @param codigoProduto Código a ser buscado nos produtos existentes
     * @return O produto buscado caso for encontrado, null caso não for encontrado
     */
    public Produto buscarProdutoPorCodigo(int codigoProduto) throws SQLException {
        Iterator<Produto> it = this.listarProdutosDisponíveis();
        
        if(it == null) {
            return null;
        }
        
        while( it.hasNext() ) {
            Produto produto = it.next();
            if(produto.getCodigoProduto() == codigoProduto) {
                return produto;
            }
        }
        return null;
    }
    
    /**
     * Solicita uma conexão com o banco de dados em busca de produtos
     * contendo a descrição passada por parâmetro
     * @param descricao O que deve ser buscado no banco de dados
     * @return Um iterador dos produtos encontrados na busca, ou  null caso
     * ocorra alguma erro
     */
    public Iterator<Produto> buscarProdutoPorDescricao(String descricao) {
        try {
            String comandoSQL = "SELECT * FROM produtos "
                    + "WHERE descricao LIKE ('%"+descricao+"%')";
            ResultSet rs = comando.executeQuery(comandoSQL);
            List<Produto> possiveisProdutos = new ArrayList<Produto>();
            for (;rs.next();) {
                Produto produto = new Produto(rs.getString("descricao"), rs.getString("distribuicaolinux"),
                    rs.getFloat("preco"), rs.getInt("codigoproduto"));
                possiveisProdutos.add(produto);
            }
            return possiveisProdutos.iterator();
        } catch (SQLException ex) {
            return null;
        }
        
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Usuario getUsuario() {
        return usuario;
    }
    
    
    
    public Usuario autenticaUsuario(String nomeUsuario,String senhaUsuario) {
        // Busca no banco de dados o usuario
        try {
            String comandoSQL = "SELECT * FROM usuarios "
                    + "WHERE nomeusuario LIKE ('%"+nomeUsuario+"%')";
            ResultSet rs = comando.executeQuery(comandoSQL);
            for (;rs.next();) {
                String nome = rs.getString("nomeusuario");
                String senha = rs.getString("senhausuario");
                if( nome.equals(nomeUsuario) &&
                        senha.equals(senhaUsuario) ) {
                    return new Usuario(rs.getInt("tipousuario"), nome, senha);
                }
            }
        } catch (SQLException ex) {
            return null;
        }
        return null;
    }
}
