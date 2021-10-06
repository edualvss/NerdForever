/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 *
 * @author Eduardo
 */
public class Kit {
    
    private List<Produto> produtosDoKit = new ArrayList<Produto>();
    private final float valorDescontoProduto = 0.98f;
    private float precoDoKit = 0;
    
    /**
     * Construtor padrão, não faz nada além de criar a instância do objeto
     */
    public Kit() {
    }

    /**
     * Adiciona um produto ao kit desde que o mesmo não exista ainda.
     * E já calcula o seu preço com o desconto de kit
     * @param produto Produto a ser inserido no kit
     */
    public void adicionarProdutoAoKit(Produto produto) {
        if(!produtosDoKit.contains(produto)) {
            produtosDoKit.add(produto);
            this.precoDoKit += calcularPrecoComDescontoDeKit(produto);
        }
    }

    /**
     * Cálcula o novo preço de um produto com o desconto de kit
     * @param produto Produto que deve ser aplicado o desconto
     * @return O novo preço do produto com o desconto aplicado
     */
    private float calcularPrecoComDescontoDeKit(Produto produto) {
        return ( produto.getPreco() * this.valorDescontoProduto );
    }
    
    /**
     * Monta uma String contendo a especificação completa do kit
     * @return A String com especificação completa do kit
     */
    public String getEspecificacaoKit() {
        String especificacao = "";
        for(int i = 0; i < this.produtosDoKit.size(); i++) {
            especificacao += "Produto "+(i+1)+" do kit\n"+this.produtosDoKit.get(i).getEspecificacaoProduto()+"\n";
        }
        especificacao += "\nPreco Total do Kit: "+this.precoDoKit;
        
        return especificacao;
    }

    /**
     * Obter a lista de produtos do kit, através do seu iterador
     * @return O iterador da lista de produtos
     */
    public Iterator<Produto> getProdutosDoKit() {
        return this.produtosDoKit.iterator();
    }

    public float getPrecoDoKit() {
        return precoDoKit;
    }
    
    
    
}
