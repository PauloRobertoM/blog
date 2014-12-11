package blog.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import blog.entidades.Comentario;
import blog.entidades.Postagem;
import blog.exception.ClasseNaoFuncionaException;

public class ComentarioDAO {
	private Connection con;  
	private Statement comando;

	public ComentarioDAO() {
		super();
	}

	private void conectar() throws ClasseNaoFuncionaException, SQLException {
		if (con == null) {
			try {  
				con = FabricaConexao.getConexao();  
				comando = con.createStatement();  
				System.out.println("Conectado!");  
			} 
			catch (ClassNotFoundException e) {
				throw new ClasseNaoFuncionaException("Erro ao carregar o driver: "+e.getMessage()); 
			} 
			catch (SQLException e) {  
				throw new SQLException(e.getMessage());  
			}  
		}
	} 

	public void fechar() {  
		try {  
			comando.close();  
			con.close();  
			System.out.println("Conex‹o Fechada");  
		} 
		catch (SQLException e) {  
			imprimeErro("Erro ao fechar conex‹o", e.getMessage());  
		}  
	}  

	private void imprimeErro(String msg, String msgErro) {   
		System.err.println(msg);  
		System.out.println(msgErro);  
	} 
	
	public void salvar(Comentario comentario) throws ClasseNaoFuncionaException, SQLException{
		conectar();
		try{
			String sql = "INSERT INTO Comentario (`texto`, `postagem_id`)" + "  VALUES (?,?)";

			PreparedStatement stmt = (PreparedStatement) con.prepareStatement(sql);
		
			stmt.setString(1, comentario.getTexto());
			stmt.setInt(2, comentario.getPostagem().getId());

			stmt.execute();
			stmt.close();

		}
		catch(SQLException e){
			throw new SQLException(e.getMessage());
		}

	}

	public List<Comentario> buscarComentarios() throws SQLException, ClasseNaoFuncionaException {
		conectar();
		ResultSet rs;  
		try { 
			String sql = "select a.id as id_user, b.id as id_coment, b.texto, b.Postagem_id from Postagem a inner join Comentario b on  a.id = b.Postagem_id";
			rs = comando.executeQuery(sql);
			List<Comentario> comentarios= new ArrayList<Comentario>();
			while (rs.next()) {  
				// pega todos os atributos da postagem
				Comentario c = new Comentario();
				c.getPostagem().setId(rs.getInt("id_post"));
				c.setId(rs.getInt("id_coment"));
				c.setTexto(rs.getString("texto"));
				comentarios.add(c);  
				System.out.println(c.getId());
				System.out.println(c.getTexto());
				System.out.println(c.getPostagem().getId());
			}
			return comentarios;
		} catch (SQLException e) {
			throw new SQLException(e.getMessage()); 
		}
	}
}
