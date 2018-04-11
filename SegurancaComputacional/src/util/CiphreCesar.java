package util;

import java.util.Scanner;
import java.util.stream.Stream;

public class CiphreCesar 
{
	public static void main(String[] args) 
	{
		Scanner input  = new Scanner(System.in);
		
		System.out.println("Digite uma frase");
		String entrada = input.nextLine();

		System.out.println("Digite o deslocamento");
		int deslocamento = input.nextInt();
			
		// Convertendo em stream de caracters
		Stream<Character> streamCharacter = entrada.chars().mapToObj(caracter -> (char)(caracter + deslocamento));
	
		// Para cada carácter adicionar o deslocamento
		streamCharacter.forEach(System.out::print);
		
		input.close();
	}
}
