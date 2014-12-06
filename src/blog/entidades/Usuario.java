package blog.entidades;

import java.util.List;

public class Usuario {
        private int id;
        private String nome, login, senha;
        private List<Postagem> postagens;
        
        public Usuario() {
                super();
        }
        public int getId() {
                return id;
        }
        public void setId(int id) {
                this.id = id;
        }
        public String getNome() {
                return nome;
        }
        public void setNome(String nome) {
                this.nome = nome;
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
        public List<Postagem> getPostagens() {
                return postagens;
        }
        public void setPostagens(List<Postagem> postagens) {
                this.postagens = postagens;
        }
}
