package blog.entidades;


public class Comentario {
	private int id;
	private String texto;
	private String autor;
	private int postagem_id;

	
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
	public String getAutor() {
		return autor;
	}
	public void setAutor(String autor) {
		this.autor = autor;
	}
	public int getPostagem_id() {
		return postagem_id;
	}
	public void setPostagem_id(int postagem_id) {
		this.postagem_id = postagem_id;
	}

	
	
}
