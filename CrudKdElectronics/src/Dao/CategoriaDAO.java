
package Dao;

import Conexion.ConexionBD;
import java.sql.PreparedStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import Excepciones.CategoriaException;
import Excepciones.ConexionException;
import Modelos.Categoria;
import java.util.ArrayList;

public class CategoriaDAO {

    public ArrayList<Categoria> listarCategorias() throws ConexionException, CategoriaException{

        ArrayList<Categoria> categorias = new ArrayList<>();

        String sql ="select idcategoria, nombrecategoria, descripcion from categoria";
        
        try(
    Connection conexion = ConexionBD.conectar();
    PreparedStatement consulta = conexion.prepareStatement(sql);
    ResultSet resultado = consulta.executeQuery()
    ){
    while(resultado.next()){
        Categoria categoria = new Categoria();

        categoria.setIdCategoria(resultado.getString("idcategoria"));
        categoria.setNombreCategoria(resultado.getString("nombrecategoria"));
        categoria.setDescripcion(resultado.getString("descripcion"));

        categorias.add(categoria);
    }
}catch (SQLException e){
    throw new CategoriaException("Error al listar las categorias: "+e.getMessage());
}
        return categorias;

}

public boolean existeCategoria(String idCategoria) throws ConexionException, CategoriaException{

    String sql ="select idcategoria from categoria where idcategoria=? ";

    try(
        Connection conexion = ConexionBD.conectar();
        PreparedStatement consulta = conexion.prepareStatement(sql)){

        consulta.setString(1, idCategoria);

        try( ResultSet resultado = consulta.executeQuery()){
            return resultado.next();
        }
    }catch (SQLException e){
        throw new CategoriaException("Error al verificar la categoria: "+e.getMessage());
    }
}
    
}
