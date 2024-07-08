package Codigos;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

abstract class Conta {
    protected int agencia;
    protected int numero;
    protected String nomeCliente;
    protected double saldo;

    public Conta(int agencia, int numero, String nomeCliente, double saldo) {
        this.agencia = agencia;
        this.numero = numero;
        this.nomeCliente = nomeCliente;
        this.saldo = saldo;
    }

    public String getNomeCliente() {
        return nomeCliente;
    }

    public double getSaldo() {
        return saldo;
    }

    public boolean sacar(double valor) {
        if (saldo >= valor) {
            saldo -= valor;
            return true;
        }
        return false;
    }

    public boolean transferir(Conta destino, double valor) {
        if (destino == null) {
            System.out.println("Conta de destino não existe.");
            return false;
        }

        if (sacar(valor)) {
            destino.depositar(valor);
            return true;
        }
        return false;
    }

    public void depositar(double valor) {
        saldo += valor;
    }

    @Override
    public String toString() {
        return "Conta{" +
                "agencia=" + agencia +
                ", numero=" + numero +
                ", nomeCliente='" + nomeCliente + '\'' +
                ", saldo=" + saldo +
                '}';
    }
}

class ContaCorrente extends Conta {
    public ContaCorrente(int agencia, int numero, String nomeCliente, double saldo) {
        super(agencia, numero, nomeCliente, saldo);
    }

    // Pode adicionar funcionalidades específicas para ContaCorrente
}

class ContaPoupanca extends Conta {
    public ContaPoupanca(int agencia, int numero, String nomeCliente, double saldo) {
        super(agencia, numero, nomeCliente, saldo);
    }

    // Pode adicionar funcionalidades específicas para ContaPoupanca
}

public class Banco {
    private List<Conta> contas;
    private Scanner scanner;

    public Banco() {
        contas = new ArrayList<>();
        scanner = new Scanner(System.in);
    }

    public void iniciar() {
        System.out.println("Bem-vindo ao Banco!");

        int opcao;
        do {
            exibirMenu();
            opcao = scanner.nextInt();
            scanner.nextLine(); // Limpar o buffer do scanner

            switch (opcao) {
                case 1:
                    entrarNaConta();
                    break;
                case 2:
                    criarConta();
                    break;
                case 3:
                    System.out.println("Saindo do programa. Até mais!");
                    break;
                default:
                    System.out.println("Opção inválida. Tente novamente.");
                    break;
            }
        } while (opcao != 3);

        scanner.close();
    }

    private void exibirMenu() {
        System.out.println("\nEscolha uma opção:");
        System.out.println("1 - Entrar em uma conta existente");
        System.out.println("2 - Criar uma nova conta");
        System.out.println("3 - Sair do programa");
    }

    private void entrarNaConta() {
        System.out.println("Digite o número da conta para entrar:");
        int numeroConta = scanner.nextInt();
        scanner.nextLine(); // Limpar o buffer do scanner

        Conta conta = encontrarConta(numeroConta);
        if (conta != null) {
            menuConta(conta);
        } else {
            System.out.println("Conta não encontrada.");
        }
    }

    private void criarConta() {
        System.out.println("Escolha o tipo de conta a ser criada:");
        System.out.println("1 - Conta Corrente");
        System.out.println("2 - Conta Poupança");
        int tipoConta = scanner.nextInt();
        scanner.nextLine(); // Limpar o buffer do scanner

        System.out.println("Digite o número da agência:");
        int agencia = scanner.nextInt();
        scanner.nextLine(); // Limpar o buffer do scanner

        System.out.println("Digite o número da conta:");
        int numero = scanner.nextInt();
        scanner.nextLine(); // Limpar o buffer do scanner

        System.out.println("Digite o nome do cliente:");
        String nomeCliente = scanner.nextLine();

        System.out.println("Digite o saldo inicial:");
        double saldoInicial = scanner.nextDouble();
        scanner.nextLine(); // Limpar o buffer do scanner

        if (tipoConta == 1) {
            contas.add(new ContaCorrente(agencia, numero, nomeCliente, saldoInicial));
        } else if (tipoConta == 2) {
            contas.add(new ContaPoupanca(agencia, numero, nomeCliente, saldoInicial));
        } else {
            System.out.println("Opção inválida. Conta não criada.");
        }
    }

    private void menuConta(Conta conta) {
        int opcao;
        do {
            exibirMenuConta(conta);
            opcao = scanner.nextInt();
            scanner.nextLine(); // Limpar o buffer do scanner

            switch (opcao) {
                case 1:
                    realizarSaque(conta);
                    break;
                case 2:
                    realizarDeposito(conta);
                    break;
                case 3:
                    realizarTransferencia(conta);
                    break;
                case 4:
                    System.out.println("Saindo da conta.");
                    break;
                default:
                    System.out.println("Opção inválida. Tente novamente.");
                    break;
            }
        } while (opcao != 4);
    }

    private void exibirMenuConta(Conta conta) {
        System.out.println("\nBem-vindo, " + conta.getNomeCliente() + "!");
        System.out.println("Escolha uma opção:");
        System.out.println("1 - Sacar");
        System.out.println("2 - Depositar");
        System.out.println("3 - Transferir");
        System.out.println("4 - Sair da conta");
    }

    private void realizarSaque(Conta conta) {
        System.out.println("Digite o valor a sacar:");
        double valor = scanner.nextDouble();
        scanner.nextLine(); // Limpar o buffer do scanner

        if (conta.sacar(valor)) {
            System.out.println("Saque realizado com sucesso. Novo saldo: " + conta.getSaldo());
        } else {
            System.out.println("Saldo insuficiente para realizar o saque.");
        }
    }

    private void realizarDeposito(Conta conta) {
        System.out.println("Digite o valor a depositar:");
        double valor = scanner.nextDouble();
        scanner.nextLine(); // Limpar o buffer do scanner

        conta.depositar(valor);
        System.out.println("Depósito realizado. Novo saldo: " + conta.getSaldo());
    }

    private void realizarTransferencia(Conta contaOrigem) {
        System.out.println("Digite o número da conta de destino:");
        int numeroDestino = scanner.nextInt();
        scanner.nextLine(); // Limpar o buffer do scanner

        Conta contaDestino = encontrarConta(numeroDestino);
        if (contaDestino != null && contaOrigem != contaDestino) {
            System.out.println("Digite o valor a transferir:");
            double valor = scanner.nextDouble();
            scanner.nextLine(); // Limpar o buffer do scanner

            if (contaOrigem.transferir(contaDestino, valor)) {
                System.out.println("Transferência realizada com sucesso.");
            } else {
                System.out.println("Transferência não realizada. Saldo insuficiente.");
            }
        } else {
            System.out.println("Conta de destino não encontrada ou inválida.");
        }
    }

    private Conta encontrarConta(int numeroConta) {
        for (Conta conta : contas) {
            if (conta.numero == numeroConta) {
                return conta;
            }
        }
        return null;
    }

    public void exibirContas() {
        if (contas.isEmpty()) {
            System.out.println("Nenhuma conta para exibir.");
        } else {
            for (Conta c : contas) {
                System.out.println(c);
            }
        }
    }

    public static void main(String[] args) {
        Banco banco = new Banco();
        banco.iniciar();
    }

}