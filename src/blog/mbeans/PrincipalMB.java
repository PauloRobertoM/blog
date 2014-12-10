package blog.mbeans;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import blog.dao.PostagemDAO;
import blog.entidades.Postagem;
import blog.exception.ClasseNaoFuncionaException;

@ManagedBean(name="principal")
@ViewScoped
public class PrincipalMB implements Serializable {
	
	private static final long serialVersionUID = 2181515008338707375L;
	private List<Postagem> postagens;
	PostagemDAO dao;
	Postagem selectPostagem;
	FacesContext context = FacesContext.getCurrentInstance();
	FacesMessage facesMessage;


	@PostConstruct
	public void init() {
		dao = new PostagemDAO();
		try {
			this.postagens = dao.buscarTodosUsuarioPostagem();
		} catch (ClasseNaoFuncionaException e) {
			facesMessage = new FacesMessage("Erro: "+e.getMessage()+" !!!");
		} catch (SQLException e) {
			facesMessage = new FacesMessage("Erro: "+e.getMessage()+" !!!");
		}
	}

	public List<Postagem> getPostagens() {
		return postagens;
	}
	public void setPostagens(List<Postagem> postagens) {
		this.postagens = postagens;
	}

	public Postagem getSelectPostagem() {
		return selectPostagem;
	}

	public void setSelectPostagem(Postagem selectPostagem) {
		this.selectPostagem = selectPostagem;
	}  
}
