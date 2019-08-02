package model.dao;

import java.util.List;
import javax.swing.JComboBox;
import model.bean.Departamento;
import model.bean.Funcionario;
import model.bean.GrupoProduto;
import model.bean.Produto;

public class Servicos {
     public void preencherComboDepartamento(JComboBox comboDep){
        DepartamentoDao depdao= new DepartamentoDao();
        List<Departamento> listagem= depdao.ListaDepartamento();
        for(Departamento d:listagem){
            comboDep.addItem(d);
        }
    }
     public void preencherComboTipoProd(JComboBox comboTipo){
         GrupoDao grpdao= new GrupoDao();
        List<GrupoProduto> listagem= grpdao.ListaTipoPro();
        for(GrupoProduto g:listagem){
            comboTipo.addItem(g);
        }
    }
      public void preencherComboFunc(JComboBox comboBairro){
        FuncionarioDao funcdao= new FuncionarioDao();
        List<Funcionario> listagem= funcdao.ListaFunc();
        for(Funcionario f:listagem){
            comboBairro.addItem(f);              
        }
    }
       public void preencherComboPro(JComboBox comboProduto){
        ProdutoDao prodao= new ProdutoDao();
        List<Produto> listagem= prodao.ListaPro();
        for(Produto p:listagem){
            comboProduto.addItem(p);              
        }
    }
}