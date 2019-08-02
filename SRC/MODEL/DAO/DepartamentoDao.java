package model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import model.bean.Departamento;
import model.connection.ConnectionFactory;

public class DepartamentoDao {
    Connection con=null;
    
    String sql;
    ResultSet rs;
    
    PreparedStatement pstm;
    ArrayList<Departamento>lista=new ArrayList<>();
    
    public List<Departamento> ListaDepartamento(){
        try{
            con=ConnectionFactory.getConnection();
            sql="select depcod,depdesc from departamento order by depdesc;";
            pstm=con.prepareStatement(sql);
            rs=pstm.executeQuery(sql);
            while(rs.next()){
                Departamento dep=new Departamento();
                dep.setDepcod(rs.getInt(1));
                dep.setDepdesc(rs.getString(2));
                lista.add(dep);
            }
        }catch(Exception erro){
            JOptionPane.showMessageDialog(null, "ERRO DE RS"+erro.getMessage());                        
        }
        return lista;
    }
}
