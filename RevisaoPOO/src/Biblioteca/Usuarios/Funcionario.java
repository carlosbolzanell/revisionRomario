package Biblioteca.Usuarios;

import Biblioteca.Midia;

import java.util.List;

public abstract class Funcionario extends Usuario {

    private int matricula;
    private double salario;

    public abstract void cadastrarUsuario(Usuario usuario);
    public abstract void removerUsuario(Usuario usuario);

    public boolean emprestarMidia(Midia midia, Usuario usuario){
        if(!midia.isEmprestado()){
            if(usuario.addEmprestimo(midia)){
                midia.alterarEmprestimo();
                return true;
            }
        }
        return false;
    }
    @Override
    protected boolean addEmprestimo(Midia midia){
        int qtd = analalisarQuantidade(midia);
        if(qtd < 5){
            emprestimos.add(midia);
            return true;
        }
        return false;
    }

    public List<Midia> verMidias(){
        return Midia.getMidias();
    }

    public boolean devolverMidia(Midia midia, Usuario usuario){
        if(usuario.emprestimos.contains(midia)){
            usuario.emprestimos.remove(midia);
            midia.alterarEmprestimo();
            return true;
        }
        return false;

    }

    @Override
    public String toString() {
        return super.toString() + "Funcionario{" +
                "matricula=" + matricula +
                ", salario=" + salario +
                '}';
    }

    public Funcionario(String nome, String usuario, String senha) {
        super(nome, usuario, senha);
    }

}
