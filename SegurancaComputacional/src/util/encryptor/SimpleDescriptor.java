package util.encryptor;

public class SimpleDescriptor 
{
    private final int DIREITA = 4;
    private final int DIREITA_FINAL = 8;
    private final int ESQUERDA = 0;
    private final int ESQUERDA_FINAL = 4;

        Permutador permutador = new Permutador();

public String decriptar(String pString, String pKey1, String pKey2)
	    {
	        String permutar = permutador.ip(pString);
	        String processarDireita = processarLadoDireito(permutar, pKey2);
	        String processarEsquerda = processarLadoEsquerdo(permutar, processarDireita);

            String primeiro = permutador.trocarPosicao(processarEsquerda);

	        String processarDireita2 = processarLadoDireito(primeiro, pKey1);
	        String processarEsquerda2 = processarLadoEsquerdo(primeiro, processarDireita2);
	        
	        String decryptText = permutador.ipReverse(processarEsquerda2);
	        return decryptText;
	    }

	    public String encrypt(String pString, String pKey1, String pKey2)
	    {
	        String stringPermuted = permutador.ip(pString);
	        String processedRightSide = processarLadoDireito(stringPermuted, pKey1);
	        String processedLeftSide = processarLadoEsquerdo(stringPermuted, processedRightSide);
	        
	        String firstFinalString = permutador.trocarPosicao(processedLeftSide);
	        
	        String secondProcessedRightSide = processarLadoDireito(firstFinalString, pKey2);
	        String secondProcessedLeftSide = processarLadoEsquerdo(firstFinalString, secondProcessedRightSide);
	        
	        String encryptText = permutador.ipReverse(secondProcessedLeftSide);
	        return encryptText;
	    }

	    public String processarLadoDireito(String pString, String pKey)
	    {
	        String subStringRight = pString.substring(DIREITA, DIREITA_FINAL);

	        String permutarDireita = permutador.expandirPermutar(subStringRight);
	        String permutarXOR = permutador.XOR(permutarDireita, pKey);

	        String permutedXORRight = permutarXOR.substring(DIREITA, DIREITA_FINAL);
	        String permutedXORLeft = permutarXOR.substring(ESQUERDA, ESQUERDA_FINAL);

	        String s0 = permutador.aplicarSBox(permutedXORLeft, Permutador.S0_BOX);
	        String s1 = permutador.aplicarSBox(permutedXORRight, Permutador.S1_BOX);

	        String concatenar = s0.concat(s1);
	        String resultado = permutador.permutarP4(concatenar);
	        
	        return resultado;
	    }

	    public String processarLadoEsquerdo(String pString, String processedRightSide)
	    {
	        String stringEsquerda = pString.substring(ESQUERDA, ESQUERDA_FINAL);
	        String direita = pString.substring(DIREITA, DIREITA_FINAL);

	        String permutarXOR = permutador.XOR(processedRightSide, stringEsquerda);

	        String resultadoFinal = permutarXOR.concat(direita);

            return resultadoFinal;
        }
}
