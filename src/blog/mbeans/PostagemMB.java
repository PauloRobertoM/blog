package blog.mbeans;

import java.io.Serializable;
import java.sql.SQLException;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

import blog.dao.PostagemDAO;
import blog.entidades.Postagem;
import blog.entidades.Usuario;
import blog.exception.ClasseNaoFuncionaException;

@ManagedBean(name="post")
@RequestScoped
public class PostagemMB implements Serializable {
	
	private static final long serialVersionUID = 5929340144434244195L;
	private Postagem postagem = new Postagem();
	
	@ManagedProperty(value = "#{usuario}")
    private UsuarioMB usuarioMB = new UsuarioMB();
	
	public String salvar(){
		
		PostagemDAO postagemDAO = new PostagemDAO();
		System.out.println("Entrou no metodo salvar");
		try {
			Usuario usuario = usuarioMB.getUsuario();
			postagem.setUsuario(usuario);
			postagemDAO.salvar(postagem);
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage("Sucesso!", "Postagem Salva."));
			return "index.jsf?faces-redirect=true";
		} 
		catch (ClasseNaoFuncionaException e) {
			System.out.println("Passou no exception classe n‹o funciona de postagem");
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage("Erro!", e.getMessage()));
			return null;
		} 
		catch (SQLException e) {
			System.out.println("Passou no exception sql de postagem");
			System.out.println(e.getMessage());
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage("Erro!", e.getMessage()));
			return null;
		}	
	}
	
	/**
	 * @return the postagem
	 */
	public Postagem getPostagem() {
		return postagem;
	}

	/**
	 * @param postagem the postagem to set
	 */
	public void setPostagem(Postagem postagem) {
		this.postagem = postagem;
	}
	
	public void setUsuarioMB(UsuarioMB usuarioMB) {
        this.usuarioMB = usuarioMB;
	}
	
	public String verificaSessao(){
		if(usuarioMB.isLogado()){
			System.out.println("Passou no verificaSessao true");
			return null;
		}
		else{
			System.out.println("Passou no verificaSessao false");
			return "formLogin.jsf?faces-redirect=true";
		}
			
	}
}
