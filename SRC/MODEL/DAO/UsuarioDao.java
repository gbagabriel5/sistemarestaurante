package model.dao;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import model.bean.Usuario;
import model.connection.ConnectionFactory2;

public class UsuarioDao {
    
    ConnectionFactory2 conex = new ConnectionFactory2();
    Usuario mod = new Usuario();
   
    
    public void SalvarUsuario(Usuario mod){
        conex.conexao();        
          try{
              PreparedStatement pstm = conex.con.prepareStatement("INSERT INTO usuario(usunome,ususenha,usutipo)VALUES(?,?,?)");
              pstm.setString(1,mod.getUsuUsuario());
              pstm.setString(2,mod.getUsuSenha());
              pstm.setString(3,mod.getUsuTipo());                  
              pstm.execute();
              JOptionPane.showMessageDialog(null, "Usuario inserido com Sucesso!");              
           }catch (SQLException ex){
              JOptionPane.showMessageDialog(null, "Erro ao inserir Usuario!\nErro:" +ex);              
          }        
        conex.desconecta();        
    }
    
       public Usuario ProcurarUsuario(Usuario mod){
            conex.conexao();
            conex.executaSql("SELECT *FROM usuario WHERE usunome LIKE'%"+mod.getUsuProcurar()+"%'");
        try{
           conex.rs.first();
             mod.setUsuCodigo(conex.rs.getInt("usucodigo"));
             mod.setUsuUsuario(conex.rs.getString("usunome"));
             mod.setUsuSenha(conex.rs.getString("ususenha"));
             mod.setUsuTipo(conex.rs.getString("usutipo"));             
        }catch(Exception erro){
             JOptionPane.showMessageDialog(null,"Usuario não cadastrado!");
        }        
        conex.desconecta();
        return mod; 
   }
       
       public void AlterarUsuario(Usuario mod){
        try {
            conex.conexao();
            PreparedStatement pstm = conex.con.prepareStatement("UPDATE usuario SET usunome=?,ususenha=?,usutipo=? where usucodigo=?");           
            pstm.setString(1, mod.getUsuUsuario());
            pstm.setString(2, mod.getUsuSenha());
            pstm.setString(3, mod.getUsuTipo());
            pstm.setInt(4, mod.getUsuCodigo());
            pstm.execute();
               JOptionPane.showMessageDialog(null, "Usuario alterados com Sucesso!");
         } catch (SQLException ex) {
               JOptionPane.showMessageDialog(null,"Erro de Alteração\n" +ex.getMessage());
         }        
        conex.desconecta();
    }
    
       public void ExcluirUsuario(Usuario mod){
        try {
            conex.conexao();
            PreparedStatement pstm = conex.con.prepareStatement("DELETE FROM usuario where usucodigo=?");
            pstm.setInt(1, mod.getUsuCodigo());
            pstm.execute();
            JOptionPane.showMessageDialog(null,"Dados excluidos com Sucesso!");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null,"Erro de Exclusão\n" +ex.getMessage());
        }        
         conex.desconecta();
    }    
}

