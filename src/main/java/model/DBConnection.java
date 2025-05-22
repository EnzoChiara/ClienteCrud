/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
/**
 *
 * @author enzoc
 */
public class DBConnection {

private static final String URL = "jdbc:mysql://localhost:3306/cadastro";
private static final String USER = "root";
private static final String PASSWORD = "root";

 /**
     * Retorna uma conexão válida.
     * @throws SQLException se falhar a conexão
     */

     public static Connection getConnection() throws SQLException{
         return DriverManager.getConnection(URL, USER,PASSWORD);
     }
    
}
