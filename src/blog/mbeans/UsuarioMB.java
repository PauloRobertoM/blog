package blog.mbeans;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import blog.dao.UsuarioDAO;
import blog.entidades.Usuario;

@ManagedBean(name="usuario")
@SessionScoped
public class UsuarioMB {
	private String login, senha;
	private Usuario usuario;        
	private String mensagem;

	public UsuarioMB() {
		super();
		this.mensagem = "";
		this.usuario = null;
	}
	public String getLogin() {
		return login;
	}
	public void setLogin(String login) {
		this.login = login;
	}
	public String getSenha() {
		return senha;
	}
	public void setSenha(String senha) {
		this.senha = senha;
	}
	public boolean isLogado() {
		return usuario != null;
	}
	public boolean isNotLogado() {
		return usuario == null;
	}
	public Usuario getUsuario() {
		return usuario;
	}
	public void setUsuario(Usuario logado) {
		this.usuario = logado;
	}

	/**
	 * @return the mensagem
	 */
	 public String getMensagem() {
		return mensagem;
	}
	/**
	 * @param mensagem the mensagem to set
	 */
	 public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	 }
	 public String autentica() {
		 if (login!=null && !login.isEmpty() && senha!=null && !senha.isEmpty()) {
			 UsuarioDAO dao = new UsuarioDAO();
			 Usuario usr = dao.autenticar(login, senha);
			 if (usr != null) {
				 this.usuario = usr;
				 FacesContext context = FacesContext.getCurrentInstance();
				 context.addMessage(null, new FacesMessage("Bem vindo!",  "Sr. "+usuario.getNome()));
				 return "index.jsf";
			 } else {
				 this.mensagem = "Login e senha n‹o correspondem a um usu‡rio v‡lido!";
				 FacesContext context = FacesContext.getCurrentInstance();
				 context.addMessage(null, new FacesMessage("Erro!",  "Mensagem: " + mensagem));
			 }
		 } else {
			 this.mensagem = "Par‰metros inv‡lidos!";
			 FacesContext context = FacesContext.getCurrentInstance();
			 context.addMessage(null, new FacesMessage("Erro!",  "Mensagem: " + mensagem));
		 }
		 return "formLogin.jsf";
	 }

	 public String logout() {
		 this.usuario = null;
		 return "index.jsf?faces-redirect=true";
	 }

}
