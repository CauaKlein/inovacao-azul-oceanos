package com.example.programags;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

class Usuario {
    int id;
    String nome;
    String email;
    String senha;
    String endereco;
    List<Transacao> transacoes;
    double saldoMoedas;

    Usuario() {
        this.transacoes = new ArrayList<>();
        this.saldoMoedas = 0;
    }

    public Usuario(int id, String nome, String email, String senha, String endereco) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.endereco = endereco;
        this.transacoes = new ArrayList<>();
        this.saldoMoedas = 0;
    }

    public void cadastrar() {
        System.out.println("Usuario cadastrado!");
    }

    public void solicitarTroca(Residuo residuo, double quantidade) {
        if (residuo == null) {
            System.out.println("Nenhum residuo cadastrado.");
            return;
        }

        if (residuo.isUtilizado()) {
            System.out.println("Este residuo ja foi utilizado em uma troca anterior.");
            return;
        }

        residuo.atualizarQuantidade(quantidade);
        Transacao transacao = new Transacao(this, residuo, quantidade);
        this.transacoes.add(transacao);
        this.saldoMoedas += transacao.getMoedas();
        residuo.setUtilizado(true);
        System.out.println("Troca realizada. Voce recebeu " + transacao.getMoedas() + " moedas.");
    }

    public void comprarOferta(Oferta oferta, Usuario usuario) {
        if (oferta.getCusto() <= this.saldoMoedas) {
            this.saldoMoedas -= oferta.getCusto();
            System.out.println("Oferta comprada: " + oferta.getDescricao());
        } else {
            System.out.println("Saldo insuficiente.");
        }
    }

    public double getSaldoTotal() {
        return saldoMoedas;
    }
}

class Residuo {
    int id;
    String tipo;
    double quantidade;
    boolean utilizado;

    Residuo() {}

    public Residuo(int id, String tipo, double quantidade) {
        this.id = id;
        this.tipo = tipo;
        this.quantidade = quantidade;
        this.utilizado = false;
    }

    public void atualizarQuantidade(double quantidade) {
        this.quantidade += quantidade;
    }

    public String getTipo() {
        return tipo;
    }

    public double getQuantidade() {
        return quantidade;
    }

    public boolean isUtilizado() {
        return utilizado;
    }

    public void setUtilizado(boolean utilizado) {
        this.utilizado = utilizado;
    }
}

class Oferta {
    int id;
    String descricao;
    double custo;

    Oferta() {}

    public Oferta(int id, String descricao, double custo) {
        this.id = id;
        this.descricao = descricao;
        this.custo = custo;
    }

    public void adicionarOferta() {
        // Implementar logica de adicionar oferta
    }

    public void atualizarOferta() {
        // Implementar logica de atualizar oferta
    }

    public String getDescricao() {
        return descricao;
    }

    public double getCusto() {
        return custo;
    }

    public int getId() {
        return id;
    }
}

class Empresa {
    int id;
    String nome;
    List<Oferta> ofertas;

    Empresa() {}

    public Empresa(int id, String nome) {
        this.id = id;
        this.nome = nome;
        this.ofertas = new ArrayList<>();
    }

    public void cadastrarOferta(Oferta oferta) {
        this.ofertas.add(oferta);
    }

    public void realizarTroca(Residuo residuo) {
        // Implementar logica de realizar troca
        System.out.println("Troca realizada pela empresa: " + this.nome);
    }

    public List<Oferta> getOfertas() {
        return ofertas;
    }

    public String getNome() {
        return nome;
    }
}

class Transacao {
    int id;
    static int nextId = 1;
    Usuario usuario;
    Residuo residuo;
    double moedas;
    Date data;

    Transacao() {}

    public Transacao(Usuario usuario, Residuo residuo, double quantidade) {
        this.id = nextId++;
        this.usuario = usuario;
        this.residuo = residuo;
        this.moedas = quantidade * 0.1; 
        this.data = new Date();
    }

    public double getMoedas() {
        return moedas;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public Residuo getResiduo() {
        return residuo;
    }

    public Date getData() {
        return data;
    }
}

public class App {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Criar empresas e ofertas
        Empresa nike = new Empresa(1, "Empresa Nike");
        Empresa uber = new Empresa(2, "Empresa Uber");
        Oferta oferta1 = new Oferta(1, "Desconto de 10%", 10.0);
        Oferta oferta2 = new Oferta(2, "Desconto de 20%", 20.0);
        List<Empresa> empresas = List.of(nike, uber);

        Usuario usuario = new Usuario();
        Residuo lixo = null;

        nike.cadastrarOferta(oferta1);
        uber.cadastrarOferta(oferta2);

        int opcao;

        do {
            System.out.println("\n=== Menu ===");
            System.out.println("1. Cadastrar Usuario");
            System.out.println("2. Cadastrar Residuo");
            System.out.println("3. Trocar Residuo por moedas");
            System.out.println("4. Verificar ofertas");
            System.out.println("5. Comprar Oferta");
            System.out.println("0. Sair");
            System.out.print("Escolha uma opcao: ");
            opcao = scanner.nextInt();

            switch (opcao) {
                case 1:
                    System.out.println("Cadastre um novo usuario:");
                    System.out.print("Nome: ");
                    String nome = scanner.next() + scanner.nextLine();
                    System.out.print("Email: ");
                    String email = scanner.next() + scanner.nextLine();
                    System.out.print("Senha: ");
                    String senha = scanner.next() + scanner.nextLine();
                    System.out.print("Endereco: ");
                    String endereco = scanner.next() + scanner.nextLine();
                    usuario.id = 1;
                    usuario.nome = nome;
                    usuario.endereco = endereco;
                    usuario.email = email;
                    usuario.senha = senha;
                    usuario.cadastrar();
                    break;
                case 2:
                    System.out.println("Cadastre o seu lixo coletado:");
                    System.out.print("Qual material voce recolheu? ");
                    String tipo = scanner.next() + scanner.nextLine();
                    System.out.print("Qual o peso em kilogramas? ");
                    double quantidade = scanner.nextInt();
                    lixo = new Residuo(1, tipo, quantidade);
                    break;
                case 3:
                    usuario.solicitarTroca(lixo, lixo != null ? lixo.getQuantidade() : 0);
                    break;
                case 4:
                    System.out.println("\nOfertas disponiveis:");

                    for (Empresa empresa : empresas) {
                        System.out.println("Ofertas da " + empresa.getNome() + ":");
                        for (Oferta oferta : empresa.getOfertas()) {
                            System.out.println(" - " + oferta.getDescricao() + " por " + oferta.getCusto() + " moedas");
                        }
                    }
                    break;
                case 5:
                    System.out.print("\nEscolha uma oferta (id): ");
                    int ofertaId = scanner.nextInt();
                    System.out.print("Quantidade de moedas para a oferta: ");

                    Oferta ofertaEscolhida = null;
                    for (Empresa empresa : empresas) {
                        for (Oferta oferta : empresa.getOfertas()) {
                            if (oferta.getId() == ofertaId) {
                                ofertaEscolhida = oferta;
                                break;
                            }
                        }
                    }
                    if (ofertaEscolhida != null) {
                        usuario.comprarOferta(ofertaEscolhida, usuario);
                    } else {
                        System.out.println("Oferta nao encontrada.");
                    }
                    System.out.println("Saldo de moedas restante: " + usuario.getSaldoTotal());
                    break;
                case 0:
                    System.out.println("Saindo...");
                    break;
                default:
                    System.out.println("Opcao invalida!");
            }
        } while (opcao != 0);

        scanner.close();
    }
}
