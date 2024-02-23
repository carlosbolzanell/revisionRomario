package Biblioteca;

import Biblioteca.Midias.*;
import Biblioteca.Usuarios.*;

import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    private static final Scanner sc = new Scanner(System.in);
    private static  Usuario usuarioLogado;
    public static void main(String[] args) {
        Usuario.addUsuario(new Cliente("carlos", "carlos", "123"));
        Usuario.addUsuario(new Atendente("victor", "victor", "1234"));
        Usuario.addUsuario(new Bibliotecario("karol", "karol", "12345"));
        Midia.addMidia(new Livro("Cronicas de Nárnia", 128));
        Midia.addMidia(new DVD("Frozen 3", 320));
        Midia.addMidia(new Revista("Panini", 872));
        Midia.addMidia(new Jornal("Jornal Amanhã", 3));
        do{
            System.out.println("Bem vindo à Biblioteca");
            System.out.println("""
                1- Cadastro de Usuário
                2- Login
                3- Sair
                """);
            int escolha = sc.nextInt();

            switch (escolha) {
                case 1 -> cadastro();
                case 2 -> login();
                case 3 -> System.exit(0);
            }
        }while (true);
    }

    private static void cadastro() {
        System.out.print("Digite seu nome: ");
        String nome = sc.next();
        System.out.print("Digite seu usuario: ");
        String usuario = sc.next();
        if(Usuario.getUsuario(usuario) != null){
            System.out.println("Nome de usuáro indisponível!");
            return;
        }
        System.out.print("Digite sua senha: ");
        String senha = sc.next();

        Usuario.addUsuario(new Cliente(nome, usuario, senha));
        System.out.println("Usuário adicionado com sucesso!");
    }

    public static void logout(){
        usuarioLogado = null;
    }

    private static void login() {
        do {
            System.out.print("Digite seu usuário: ");
            String usuario = sc.next();
            System.out.print("Digite sua senha: ");
            String senha = sc.next();

            usuarioLogado = Usuario.login(usuario, senha);

        } while (usuarioLogado == null);
        menuUsuario();
    }

    private static void menuUsuario() {
        do{
            System.out.print("""
                    1- Alterar nome
                    2- Alterar senha
                    3- Ver emprestimos
                    4- Ver perfil
                    5- Consultar disponibilidade de mídia
                    """);
            if (usuarioLogado instanceof Funcionario) {
                System.out.print("""
                        6- Cadastrar cliente 
                        7- Remover Usuário
                        8- Emprestar mídia
                        9- Devolver midia
                        10- Ver midias
                        """);
                if(usuarioLogado instanceof Bibliotecario) {
                    System.out.print("""
                            11- Cadastrar midias
                            12- Remover midia   
                            """);
                }
            }
            System.out.println("0- Logout");
            int escolha = sc.nextInt();
            switch (escolha){
                case 0 -> logout();
                case 1 -> alterarNome();
                case 2 -> alterarSenha();
                case 3 -> System.out.println(usuarioLogado.getEmprestimos());
                case 4 -> System.out.println(usuarioLogado);
                case 5 -> consultarMidia();
            }
            if(usuarioLogado instanceof Funcionario){
                switch (escolha){
                    case 6 -> cadastro();
                    case 7 -> removerUsuario();
                    case 8 -> emprestarMidia();
                    case 9 -> devolverMidia();
                    case 10 -> System.out.println(((Funcionario) usuarioLogado).verMidias());
                }
            }
            if(usuarioLogado instanceof Bibliotecario){
                switch (escolha){
                    case 11 -> cadastroMidia();
                    case 12 -> removeMidia();
                }
            }
        }while(usuarioLogado != null);
    }

    public static void alterarNome(){
        System.out.println("Qual nome você deseja colocar?");
        String nome = sc.next();
        if(nome.equals(usuarioLogado.getNome())){
            System.out.println("Nome não pode ser igual!");
        }else {
            usuarioLogado.alterarNome(nome);
            System.out.println("Nome alterado com sucesso");
        }
    }
    public static void alterarSenha(){
        System.out.println("Digite a senha atual");
        String senhaAtual = sc.next();
        System.out.println("Digite a senha nova");
        String senhaNova = sc.next();
        if(usuarioLogado.alterarSenha(senhaAtual, senhaNova)){
            System.out.println("Senha alterada com sucesso!");
        }else{
            System.out.println("Senha antiga errada!");
        }
    }
    public static void consultarMidia(){
        System.out.println("Digite o codigo da midia");
        int codigo = sc.nextInt();
        if(Midia.procurarMidia(codigo) == null){
            System.out.println("Midia inexistente no sistema!");
            return;
        }
        System.out.println(usuarioLogado.consultarMidia(codigo));
    }
    public static void removerUsuario(){
        System.out.println("Qual usuario você deseja remover?");
        String usuario = sc.next();
        if(Usuario.getUsuario(usuario) != null){
            Funcionario funcionario = (Funcionario)usuarioLogado;
            funcionario.removerUsuario(Usuario.getUsuario(usuario));
            System.out.println("Usuário removido com sucesso!");
        }else{
            System.out.println("Usuário não encontrado");
        }
    }
    public static void emprestarMidia(){
        System.out.print("Codigo da midia: ");
        int codigo = sc.nextInt();
        if(Midia.procurarMidia(codigo) == null){
            System.out.println("Midia inexistente no sistema!");
            return;
        }
        System.out.println("Midia: " + Midia.procurarMidia(codigo).toString());
        if(Midia.procurarMidia(codigo).isEmprestado()){
            System.out.println("Midia indisponivel para emprestimo");
            return;
        }
        System.out.print("Usuario para emprestimo: ");
        String usuario = sc.next();
        if(Usuario.getUsuario(usuario) == null){
            System.out.println("Usuário inexistente");
            return;
        }
        ((Funcionario)usuarioLogado).emprestarMidia(Midia.procurarMidia(codigo), Usuario.getUsuario(usuario));
        System.out.println("Midia emprestada com sucesso!");
    }
    public static void devolverMidia(){
        System.out.print("Codigo da midia: ");
        int codigo = sc.nextInt();
        if(Midia.procurarMidia(codigo) == null){
            System.out.println("Midia inexistente no sistema!");
            return;
        }
        if(!Midia.procurarMidia(codigo).isEmprestado()){
            System.out.println("Midia indisponivel para devolução");
            return;
        }
        System.out.println("Midia: " + Midia.procurarMidia(codigo).toString());
        System.out.print("Usuario para devolução: ");
        String usuario = sc.next();
        if(Usuario.getUsuario(usuario) != null){
            Funcionario funcionario = (Funcionario)usuarioLogado;
            if(funcionario.devolverMidia(Midia.procurarMidia(codigo), Usuario.getUsuario(usuario))){
                System.out.println("Midia devolvida com sucesso!");
            }else{
                System.out.println("Midia não disponível nos itens do usuario");
            }
        }else{
            System.out.println("Usuário inexistente");
        }
    }
    public static void cadastroMidia(){
        System.out.print("Nome da midia: ");
        String nome = sc.nextLine();
        String nome1 = sc.nextLine();
        if(Midia.procurarMidia(nome) != null){
            System.out.println("Midia já existente!");
            return;
        }
        System.out.print("Codigo da midia: ");
        int codigo = sc.nextInt();
        if(Midia.procurarMidia(codigo) != null){
            System.out.println("Codigo já existente no sistema!");
            return;
        }
        System.out.println("""
                Qual é essa mídia?
                1 - Livro
                2 - Revista
                3 - DVD
                4 - Jornal
                """);
        int escolha = sc.nextInt();
        switch (escolha){
            case 1 -> ((Bibliotecario) usuarioLogado).cadastrarMidia(new Livro(nome, codigo));
            case 2 -> ((Bibliotecario) usuarioLogado).cadastrarMidia(new Revista(nome, codigo));
            case 3 -> ((Bibliotecario) usuarioLogado).cadastrarMidia(new DVD(nome, codigo));
            case 4 -> ((Bibliotecario) usuarioLogado).cadastrarMidia(new Jornal(nome, codigo));
            default -> System.out.println("Informe um numero correto");
        }
        System.out.println("Midia cadastrada com sucesso!");
    }
    public static void removeMidia(){
        System.out.println("Qual o código da midia?");
        int codigo = sc.nextInt();
        if(Midia.procurarMidia(codigo) == null){
            System.out.println("Midia inexistente no sistema!");
            return;
        }
        ((Bibliotecario) usuarioLogado).removerMidia(codigo);
        System.out.println("Midia removida com sucesso!");
    }
}
