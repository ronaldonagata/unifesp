package util.encryptor;

import java.util.Arrays;
import java.util.List;

public class Permutador
{
    private static List<Integer> PERMUTADOR = Arrays.asList(1,5,2,0,3,7,4,6);
    private static List<Integer> PERMUTADOR_INVERSE = Arrays.asList(3,0,2,4,6,1,7,5); 
    private static List<Integer> EXPANDIR_PERMUTAR = Arrays.asList(3,0,1,2,1,2,3,0);
    private static List<Integer> P4 = Arrays.asList(1,3,2,0);

    public static int[][] S0_BOX = {{1, 0, 3, 2}, {3, 2, 1, 0}, {0, 2, 1, 3}, {3, 1, 3, 2}};

    public static int[][] S1_BOX = {{0, 1, 2, 3}, {2, 0, 1, 3}, {3, 0, 1, 0}, {2, 1, 0, 3}};

    public String ip(String pString)
    {
        StringBuilder resultado = new StringBuilder();
        for(Integer i : PERMUTADOR)
        {
            char value = pString.charAt(i);
            resultado.append(value);
        }
        return resultado.toString();
    }

    public String ipReverse(String pString)
    {
        StringBuilder permuted = new StringBuilder();
        for(Integer integer : PERMUTADOR_INVERSE)
        {
            char value = pString.charAt(integer);
            permuted.append(value);
        }
        return permuted.toString();
    }

    public String expandirPermutar(String pString)
    {
        StringBuilder resultado = new StringBuilder();
        for(Integer i : EXPANDIR_PERMUTAR)
        {
            char value = pString.charAt(i);
            resultado.append(value);
        }
        return resultado.toString();
    }

    public String XOR(String pString, String key)
    {
        StringBuilder permuted = new StringBuilder();
        for(int i = 0; i < pString.length(); i++)
        {
            permuted.append(pString.charAt(i) ^ key.charAt(i));
        }
        return permuted.toString();
    }

    public String aplicarSBox(String pString, int[][] S)
    {
        StringBuilder stringBuilderRow = new StringBuilder();
        String rowString = stringBuilderRow.append(pString.charAt(0)).append(pString.charAt(3)).toString();
        
        StringBuilder stringBuilderColumn = new StringBuilder();
        String columnString = stringBuilderColumn.append(pString.charAt(1)).append(pString.charAt(2)).toString();

        int decimalRightValue = Integer.parseInt(rowString, 2);
        int decimalLeftValue = Integer.parseInt(columnString, 2);

        int decimalBoxValue = S[decimalRightValue][decimalLeftValue];

        if(decimalBoxValue < 2)
        {
            StringBuilder binaryString = new StringBuilder();
            binaryString.append("0").append(Integer.toBinaryString(decimalBoxValue));
            return binaryString.toString();
        }
        return Integer.toBinaryString(decimalBoxValue);
    }

    public String permutarP4(String pString)
    {
        StringBuilder resultado = new StringBuilder();
        for(Integer posicao : P4)
        {
            char value = pString.charAt(posicao);
            resultado.append(value);
        }
        return resultado.toString();
    }

    public String trocarPosicao(String pString)
    {
        String stringRight = pString.substring(4,8);
        String stringLeft = pString.substring(0,4);

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(stringRight).append(stringLeft);

        return stringBuilder.toString();
    }
}
