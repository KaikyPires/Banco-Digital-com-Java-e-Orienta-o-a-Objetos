package Codigos;

import java.util.ArrayList;
import java.util.List;

public class Conta {
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