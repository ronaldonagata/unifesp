package util.encryptor;

public class TestSimpleDes
{
	public static void main(String[] args)
	{
	    SimpleDescriptor sdes = new SimpleDescriptor();
	    Chaves keyGenerator = new Chaves();
	    String stringK = "1010000010";
	    String key1 = keyGenerator.gerarChaveK1(stringK);
	    String key2 = keyGenerator.gerarChaveK2(stringK);
	    String plainText = "01110000";
	    String cipherText = sdes.decriptar(plainText, key1, key2);
	    System.out.println(cipherText);
    }
}
