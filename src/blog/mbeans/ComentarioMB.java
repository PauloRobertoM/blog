package blog.mbeans;

import java.io.Serializable;
import java.sql.SQLException;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

import blog.dao.ComentarioDAO;
import blog.entidades.Comentario;
import blog.exception.ClasseNaoFuncionaException;

@ManagedBean(name="coment")
@RequestScoped
public class ComentarioMB implements Serializable{

	private static final long serialVersionUID = 1L;

	private Comentario comentario = new Comentario();

	int postagem_id;

	public String salvar(){

		ComentarioDAO comentarioDAO = new ComentarioDAO();
		System.out.println("Entrou no metodo salvar");
		try {
			this.comentario.setPostagem_id(postagem_id);
			comentarioDAO.salvar(comentario);
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage("Sucesso!", "Comentario Salvo."));
			return "index.jsf?faces-redirect=true";
		} 
		catch (ClasseNaoFuncionaException e) {
			System.out.println("Passou no exception classe n‹o funciona de comentario");
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage("Erro!", e.getMessage()));
			return null;
		} 
		catch (SQLException e) {
			System.out.println("Passou no exception sql de comentario");
			System.out.println(e.getMessage());
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage("Erro!", e.getMessage()));
			return null;
		}	
	}


	public Comentario getComentario() {
		return comentario;
	}


	public void setComentario(Comentario comentario) {
		this.comentario = comentario;
	}


	public int getPostagem_id() {
		return postagem_id;
	}


	public void setPostagem_id(int postagem_id) {
		this.postagem_id = postagem_id;
	}

	


}
