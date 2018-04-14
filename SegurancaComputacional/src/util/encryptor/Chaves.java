package util.encryptor;

import java.util.Arrays;
import java.util.List;

public class Chaves
{
    public static List<Integer> P08 = Arrays.asList(5, 2, 6, 3, 7, 4, 9, 8);
    public static List<Integer> P10 = Arrays.asList(2, 4, 1, 6, 3, 9, 0, 8, 7, 5);
    

    public String rotacionarEsquerda(String pString, int times)
    {
        String stringEsquerda = pString.substring(0, 5);
        String stringDireita = pString.substring(5, 10);

        String esquerda = stringEsquerda.substring(0, times);
        String direita = stringEsquerda.substring(times, stringEsquerda.length());

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(direita).append(esquerda);
        String ladoEsquerdo = stringBuilder.toString();

        String ladoEsquerdo2 = stringDireita.substring(0, times);
        String direita2 = stringDireita.substring(times, stringDireita.length());

        StringBuilder st = new StringBuilder();
        st.append(direita2).append(ladoEsquerdo2);
        String rightPart = st.toString();

        StringBuilder stringBuilderConcat = new StringBuilder();
        stringBuilderConcat.append(ladoEsquerdo).append(rightPart);
        return stringBuilderConcat.toString();
    }

    public String gerarChaveK1(String pString)
    {
        StringBuilder permutar = new StringBuilder();
        for(Integer i : P10)
        {
            char value = pString.charAt(i);
            permutar.append(value);
        }

        String stringRotacao = rotacionarEsquerda(permutar.toString(), 1);

        StringBuilder chave = new StringBuilder();

        for(Integer i : P08)
        {
            char value = stringRotacao.charAt(i);
            chave.append(value);
        }

        return chave.toString();
    }

    public String gerarChaveK2(String pString)
    {
        StringBuilder permutar = new StringBuilder();
        for(Integer integer : P10)
        {
            char value = pString.charAt(integer);
            permutar.append(value);
        }

        String chaveDeConcatenacao = rotacionarEsquerda(permutar.toString(), 1);

        String outraChave = rotacionarEsquerda(chaveDeConcatenacao, 2);

        StringBuilder stringBuilderFinal = new StringBuilder();

        for(Integer bit : P08)
        {
            char value = outraChave.charAt(bit);
            stringBuilderFinal.append(value);
        }

        return stringBuilderFinal.toString();
    }
}
