package blog.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import blog.entidades.Postagem;
import blog.entidades.Usuario;
import blog.exception.ClasseNaoFuncionaException;

public class PostagemDAO {
	private Connection con;  
	private Statement comando;

	public PostagemDAO() {
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


	public void salvar(Postagem postagem) throws ClasseNaoFuncionaException, SQLException{
		conectar();
		try{

			String sql = "insert into Postagem " + "(titulo,texto,data,usuario_id)"
					+ " values (?,?,?,?)";

			PreparedStatement stmt = (PreparedStatement) con.prepareStatement(sql);

			stmt.setString(1, postagem.getTitulo());
			stmt.setString(2, postagem.getTexto());
			java.util.Date data = new java.util.Date();
			stmt.setDate(3, new Date(data.getTime()));
			stmt.setInt(4,postagem.getUsuario().getId());

			stmt.execute();
			stmt.close();

		}
		catch(SQLException e){
			throw new SQLException(e.getMessage());
		}

	}

	public List<Postagem> listarPostagens(Usuario usuario) throws ClasseNaoFuncionaException, SQLException {  
		conectar();
		ResultSet rs;  
		try {  
			rs = comando.executeQuery("SELECT * FROM Postagem where Usuario_id = "+usuario.getId());
			List<Postagem> postagens = new ArrayList<Postagem>();
			while (rs.next()) {  
				// pega todos os atributos da postagem
				Postagem p = new Postagem();
				p.setId(rs.getInt("id"));
				p.setTitulo(rs.getString("titulo"));
				p.setTexto(rs.getString("texto"));
				p.setData(rs.getDate("data"));
				ComentarioDAO comentarioDAO = new ComentarioDAO();
				p.setComentarios(comentarioDAO.buscarComentarios(p));
				postagens.add(p);  
			}
			return postagens;
		} catch (SQLException e) {
			throw new SQLException(e.getMessage()); 
		}
	}
	
	public List<Postagem> buscarTodosUsuarioPostagem() throws ClasseNaoFuncionaException, SQLException {  
		conectar();
		ResultSet rs;  
		try { 
			String sql = "select a.id as id_user, a.nome, b.id as id_post, b.titulo, b.texto, b.data, b.Usuario_id from Usuario a inner join Postagem b on  a.id = b.Usuario_id";
			rs = comando.executeQuery(sql);
			List<Postagem> postagens = new ArrayList<Postagem>();
			while (rs.next()) {  
				// pega todos os atributos da postagem
				Postagem p = new Postagem();
				p.getUsuario().setId(rs.getInt("id_user"));
				p.getUsuario().setNome(rs.getString("nome"));
				p.setId(rs.getInt("id_post"));
				p.setTitulo(rs.getString("titulo"));
				p.setTexto(rs.getString("texto"));
				p.setData(rs.getDate("data"));
				ComentarioDAO comentarioDAO = new ComentarioDAO();
				p.setComentarios(comentarioDAO.buscarComentarios(p));
				postagens.add(p);  
				System.out.println(p.getId());
				System.out.println(p.getTexto());
				System.out.println(p.getData());
				System.out.println(p.getUsuario().getNome());
				//System.out.println("Comentarios:");
				//System.out.println(p.getComentarios().get(1).getAutor());
			}
			return postagens;
		} catch (SQLException e) {
			throw new SQLException(e.getMessage()); 
		}
	} 
	
	public List<Postagem> buscarTodosUsuarioPostagemCometarios() 
						throws ClasseNaoFuncionaException, SQLException {  
		conectar();
		ResultSet rs;  
		try { 
			String sql = "select a.id as id_user, a.nome, b.id as id_post, b.texto as texto_postagem, "
					+ "b.data, c.id as id_comentario, c.autor, c.texto as texto_comentario from "
					+ "Usuario a inner join Postagem b on a.id = b.Usuario_id "
					+ "left join Comentario c on b.id = c.Postagem_id";
			rs = comando.executeQuery(sql);
			List<Postagem> postagens = new ArrayList<Postagem>();
			while (rs.next()) {  
				// pega todos os atributos da postagem
				Postagem p = new Postagem();
				p.getUsuario().setId(rs.getInt("id_user"));
				p.getUsuario().setNome(rs.getString("nome"));
				p.setId(rs.getInt("id_post"));
				p.setTitulo(rs.getString("titulo"));
				p.setTexto(rs.getString("texto"));
				p.setData(rs.getDate("data"));
				
				//p.setComentarios(comentarios);
				postagens.add(p);  
				System.out.println(p.getId());
				System.out.println(p.getTexto());
				System.out.println(p.getData());
				System.out.println(p.getUsuario().getNome());
			}
			return postagens;
		} catch (SQLException e) {
			throw new SQLException(e.getMessage()); 
		}
	} 


}