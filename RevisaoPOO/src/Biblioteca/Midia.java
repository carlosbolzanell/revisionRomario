package Biblioteca;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract class Midia {

    //diferença abstract para interface => a interface é mais focada em métodos, é como se fosse um
    // contrato onde é obrigado a seguir as declarações etc dos métodos
    private static final ArrayList<Midia> midias = new ArrayList<>();

    private String nome;
    private int codigo;
    private boolean emprestado = false;

    public Midia(String nome, int codigo){
        this.nome = nome;
        this.codigo = codigo;
    }

    public String getNome() {
        return nome;
    }

    public int getCodigo() {
        return codigo;
    }

    public static Midia procurarMidia (int codigo) {
        for (Midia midia: midias) {
            if(midia.codigo == codigo) {
                return midia;
            }
        }
        return null;
    }
    public static Midia procurarMidia (String nome) {
        for (Midia midia: midias) {
            if(midia.nome.equals(nome)) {
                return midia;
            }
        }
        return null;
    }
    public static List<Midia> getMidias(){
        return Collections.unmodifiableList(midias);
    }

    public boolean isEmprestado() {
        return emprestado;
    }
    public void alterarEmprestimo(){
        this.emprestado = !this.emprestado;
    }

    @Override
    public String toString() {
        return "Midia" +"\n" +
                "Nome: " + nome +"\n" +
                "Código: " +codigo +"\n" +
                "Disponivél: " + (emprestado ? "Não" : "Sim");
    }

    public static void addMidia(Midia midia){
        midias.add(midia);
    }
    public static void removeMidia(int codigo){
        for (Midia midia : midias){
            if(midia.codigo == codigo){
                midias.remove(midia);
                return;
            }
        }
    }
}
