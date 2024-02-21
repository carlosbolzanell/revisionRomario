package Biblioteca.Usuarios;

import Biblioteca.Midia;
import Biblioteca.Midias.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract class Usuario {
    //Armazenar todos os usuários do sistema, independente da tipagem.
    private static int contMidias = 1;
    private final static ArrayList<Usuario> usuarios = new ArrayList<>();

    private String nome;
    private String usuario;
    private String senha;
    protected ArrayList<Midia> emprestimos = new ArrayList<>();

    public Usuario(String nome, String usuario, String senha) {
        this.nome = nome;
        this.usuario = usuario;
        this.senha = senha;
    }

    public String getEmprestimos() {
        if(emprestimos.isEmpty()){
            return "Não há midias emprestadas";
        }else{
            for (Midia midia:
                 emprestimos) {
                return contMidias + "- " + midia.getNome() +"\n" +
                        "   Código: "+ midia.getCodigo();
            }
            return "";
        }
    }
    public static Usuario getUsuario(String usuario){
        for (Usuario user:
             usuarios) {
            if(user.nome.equals(usuario)){
                return user;
            }
        }
        return null;
    }

    public boolean alterarSenha(String senha, String senhaNova) {
        if(senha.equals(this.senha)){
            this.senha = senhaNova;
            return true;
        }
        return false;
     }
     public void alterarNome(String nome){
        this.nome = nome;
     }
     public String getNome(){
        return nome;
     }

     public static void addUsuario(Usuario usuario) {
        usuarios.add(usuario);
     }
     public static void removeUsuario(Usuario usuario){usuarios.remove(usuario);}

    public static Usuario login(String user, String senha){
         for (Usuario userProcurado: usuarios) {
             if(userProcurado.usuario.equals(user) &&
             userProcurado.senha.equals(senha)){
                 return userProcurado;
             }
         }
         return null;
     }

    public String toString() {
        return "Usuario" + "\n"+
                "Nome=" + nome + '\n' +
                "Usuario='" + usuario;
    }

    protected abstract boolean addEmprestimo(Midia midia);

    protected int analalisarQuantidade(Midia midia){
        int qtd = 0;
        for (Midia midiaAnalise: emprestimos) {
            if(midiaAnalise instanceof DVD && midia instanceof DVD){
                qtd++;
            }
            if(midiaAnalise instanceof Livro && midia instanceof Livro){
                qtd++;
            }
            if(midiaAnalise instanceof Revista && midia instanceof Revista){
                qtd++;
            }
            if(midiaAnalise instanceof Jornal && midia instanceof Jornal){
                qtd++;
            }
        }
        return qtd;
    }

    public String consultarMidia(int codigo) {
        Midia midia = Midia.procurarMidia(codigo);
        if (midia == null) {
            return ("Mídia não encontrada");
        }
        return midia.toString();
    }
}
