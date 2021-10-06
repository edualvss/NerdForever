/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author Eduardo
 */
public class Produto {
    
    public static final String TABELA_PRODUTOS = "produtos";
    
    private String descricao;
    private String distribuicaoLinux;
    private float preco;
    private int codigoProduto;

    /**
     * Construtor que preenche todos os atributos
     * @param descricao Descrição do produto
     * @param distribuicaoLinux Distribuição linux ao qual o produto pertence
     * @param preco Preço do produto
     * @param codigoProduto Código do produto
     */
    public Produto(String descricao, String distribuicaoLinux, float preco, int codigoProduto) {
        this.descricao = descricao;
        this.distribuicaoLinux = distribuicaoLinux;
        this.preco = preco;
        this.codigoProduto = codigoProduto;
    }

    /**
     * Obter o código do produto
     * @return O código do produto
     */
    public int getCodigoProduto() {
        return codigoProduto;
    }

    /**
     * Obter a descrição do produto
     * @return A descrição do produto
     */
    public String getDescricao() {
        return descricao;
    }

    /**
     * Obter a distribuição linux do produto
     * @return A descrição da distribuição linux do produto
     */
    public String getDistribuicaoLinux() {
        return distribuicaoLinux;
    }
    
    /**
     * Obter o preço do produto
     * @return O preço do produto
     */
    public float getPreco() {
        return preco;
    }
    
    /**
     * Obtém a descrição completa do produto formatada em uma String
     * @return Uma String com a descrição completa do produto
     */
    public String getEspecificacaoProduto() {
        return ("Código: "+this.codigoProduto+". Produto: "+this.descricao+" da distribuição "
                +this.distribuicaoLinux+", preco: R$"+this.preco);
    }
    
    /**
     * Obter a descrição e o preço do produto
     * @return A descrição do produto com seu respectivo preço
     */
    public String getDescricaoPreco() {
        return ("Descricão: "+this.descricao+", preço: "+this.preco);
    }

}
