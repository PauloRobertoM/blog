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
			String sql = "INSERT INTO Comentario (autor, texto, Postagem_id)" + "  VALUES (?,?,?)";

			PreparedStatement stmt = (PreparedStatement) con.prepareStatement(sql);

			stmt.setString(1, comentario.getAutor());
			stmt.setString(2, comentario.getTexto());
			stmt.setInt(3, comentario.getPostagem_id());

			stmt.execute();
			stmt.close();

		}
		catch(SQLException e){
			throw new SQLException(e.getMessage());
		}

	}

	public List<Comentario> buscarComentarios(Postagem postagem) 
			throws ClasseNaoFuncionaException, SQLException{
		conectar();
		ResultSet rs;  
		try {  
			rs = comando.executeQuery("SELECT * FROM Comentario where Postagem_id = "+postagem.getId());
			List<Comentario> comentarios = new ArrayList<Comentario>();
			while (rs.next()) {  
				// pega todos os atributos os comentarios
				Comentario c = new Comentario();
				c.setId(rs.getInt("id"));
				c.setAutor(rs.getString("autor"));
				c.setTexto(rs.getString("texto"));
				c.setPostagem_id(rs.getInt("Postagem_id"));
				comentarios.add(c);  
			}
			return comentarios;
		} catch (SQLException e) {
			throw new SQLException(e.getMessage()); 
		}
	}
}
