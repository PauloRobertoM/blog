package blog.entidades;


public class Comentario {
	private int id;
	private String texto;
	private Postagem postagem;
	
	public Comentario(){
		postagem = new Postagem();
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTexto() {
		return texto;
	}
	public void setTexto(String texto) {
		this.texto = texto;
	}
	public Postagem getPostagem() {
		return postagem;
	}
	public void setPostagem(Postagem postagem) {
		this.postagem = postagem;
	}
	
	
}
