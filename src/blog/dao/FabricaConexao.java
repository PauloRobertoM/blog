package blog.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class FabricaConexao {

        private static String MySQLDriver;
        private static String URL;
        private static String NOME;
        private static String SENHA;
        private Connection con;
        
        public static void bancoLocal(){
        	MySQLDriver = "com.mysql.jdbc.Driver";
        	URL = "jdbc:mysql://localhost:3306/dsweb_blog";
        	NOME = "root";
        	SENHA = "ninocafe";
        }
        
        public static void bancoRemoto(){
        	MySQLDriver = "com.mysql.jdbc.Driver";
        	URL = "jdbc:mysql://mysql.leiros.eti.br:3306/leiros01";
        	NOME = "leiros01";
        	SENHA = "ninocafe";
        }

        public static Connection getConexao() throws ClassNotFoundException, SQLException { 
        		bancoLocal();
                Class.forName(MySQLDriver);
                return DriverManager.getConnection(URL, NOME, SENHA);  
        }  
        
        public Connection getConnection(){
            if(con==null){
                try {
                    con = DriverManager.getConnection(URL,NOME,SENHA);
                } catch (SQLException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
            return con;
        }
        
}  