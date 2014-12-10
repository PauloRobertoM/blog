package blog.mbeans;

import java.io.Serializable;
import java.sql.SQLException;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import blog.dao.UsuarioDAO;
import blog.entidades.Usuario;

@ManagedBean(name="usuario")
@SessionScoped
public class UsuarioMB implements Serializable{
	
	private static final long serialVersionUID = -5373860882051675639L;
	private String login, senha;
	private Usuario usuarioB;
	private String mensagem;
	private boolean logado;

	public UsuarioMB() {
		super();
		this.mensagem = "";
		this.usuarioB = new Usuario();
		this.logado = false;
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
		return logado;
	}
	
	public boolean isNotLogado() {
		return logado;
	}
	
	public Usuario getUsuarioB() {
		return usuarioB;
	}
	
	public void setUsuarioB(Usuario usuarioB) {
		this.usuarioB = usuarioB;
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
	 
	//salva novo usuario
		public String addUsuario(){
			
			UsuarioDAO ud = new UsuarioDAO(); 
			try {
				ud.salvarUsuario(usuarioB);
				FacesContext context = FacesContext.getCurrentInstance();
				context.addMessage(null, new FacesMessage("Sucesso!", "Usu‡rio Salvo."));
				this.login = this.usuarioB.getLogin();
				this.senha = this.usuarioB.getSenha();
				this.autentica();
				return "index.jsf";
			} catch (SQLException e) {
				FacesContext context = FacesContext.getCurrentInstance();
				context.addMessage(null, new FacesMessage("Erro!", e.getMessage()));
				return null;
			}
		}
		
	 public String autentica() {
		 if (login!=null && !login.isEmpty() && senha!=null && !senha.isEmpty()) {
			 UsuarioDAO dao = new UsuarioDAO();
			 Usuario usr = dao.autenticar(login, senha);
			 if (usr != null) {
				 this.usuarioB = usr;
				 FacesContext context = FacesContext.getCurrentInstance();
				 context.addMessage(null, new FacesMessage("Bem vindo!",  "Sr. "+usuarioB.getNome()));
				 this.logado = true;
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
		 this.usuarioB = null;
		 this.logado = false;
		 FacesContext context = FacesContext.getCurrentInstance();
		 context.addMessage(null, new FacesMessage("Sucesso!",  "Usu‡rio desconectado !!! "));
		 return "index.jsf?faces-redirect=true";
	 }
	 
}
