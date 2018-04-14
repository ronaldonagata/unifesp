package util.encryptor;

public class ClasseTeste
{
    public static void main(String[] args)
    {
        SimpleDescriptor sdes = new SimpleDescriptor();
        Chaves chave = new Chaves();

        String stringK = "1010110010";
        String key1 = chave.gerarChaveK1(stringK);
        String key2 = chave.gerarChaveK2(stringK);

        String plainText = "01110000";
        String cipherText = sdes.decriptar(plainText, key1, key2);
        System.out.println(cipherText);
    }
}
