package util;

import java.util.Scanner;

enum alfabeto 
{
	a, b, c, d, e, f, g, h, i, j, k, l, m, n, o, p, q, r, s, t, u, v, w, x, y, z
}

public class CipherViginer 
{
	private static String entrada;
	private static String secret;

	public static String cifrar(String entrada, String secret) 
	{
		String palavra = new String();
		
		for (int i = 0; i < entrada.length(); i++) 
		{
			int posicaoEntrada = alfabeto.valueOf(String.valueOf(entrada.charAt(i))).ordinal();
			int posicaoSecret = alfabeto.valueOf(String.valueOf(secret.charAt(i))).ordinal();
			int posicao = (posicaoEntrada + posicaoSecret)%26;
			
			palavra += alfabeto.values()[posicao];
		}

		return palavra;
	}
	
	public static String chaveConcatenada(String entrada, String secret) 
	{
		long quantasVezesMaior = Math.round( entrada.length()/Double.valueOf(secret.length())+0.5d);
		
		for (int i = 0 ; i <quantasVezesMaior ; i++) 
		{
			secret += secret;
		}
		
		secret = secret.substring(0, entrada.length());
		
		return secret;
	}
		
	public static void main(String[] args) 
	{

		Scanner scan = new Scanner(System.in);

		System.out.println("Digite a palavra a ser cifrada");
		entrada = scan.nextLine().toLowerCase();

		System.out.println("Digite a palavra a frase secreta");
		secret = scan.nextLine().toLowerCase();
		
		secret = chaveConcatenada(entrada, secret);

		System.out.println(cifrar(entrada, secret).toUpperCase());
		
		scan.close();

	}
}
