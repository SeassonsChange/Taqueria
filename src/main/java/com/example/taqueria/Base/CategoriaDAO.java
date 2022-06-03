package com.example.taqueria.Base;


import com.example.taqueria.Categoria;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class CategoriaDAO {
    ObservableList<Categoria> listCategoria= FXCollections.<Categoria>observableArrayList();
    Connection conn=null;
    public CategoriaDAO(Connection conn) {
        this.conn = conn;
    }

    public ObservableList getAll() {
        listCategoria.clear();
        try{
            if(conn!=null){
                String query = "select * from categoria";
                Statement st = conn.createStatement();
                ResultSet rs = st.executeQuery(query);
                while (rs.next()) {
                    listCategoria.add(new Categoria(rs.getInt("id_categoria"),rs.getString("nombre")));
                }
            }else {
                System.out.println("No se establecio conexcion");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return listCategoria;
    }
}