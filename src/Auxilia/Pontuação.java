package Auxilia;

import java.util.Scanner;

public class Pontuação {

	Scanner sc = new Scanner(System.in);

	private String nome;
	private int pontos;

	public Pontuação(String nome, int pontos) {
		super();
		this.nome = nome;
		this.pontos = pontos;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public int getPontos() {
		return pontos;
	}

	public void setPontos(int pontos) {
		this.pontos = pontos;
	}

	public void printarrank() {
		System.out.println("Nome: " + nome + "\nPontos: " + pontos + "\n------");
	}

}
