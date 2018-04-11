package util.encryptor;

public class TestSimpleDes
{
	public static void main(String[] args)
	{
	    SimpleDescriptor sdes = new SimpleDescriptor();
	    KeyGenerator keyGenerator = new KeyGenerator();
	    String stringK = "1010000010";
	    String key1 = keyGenerator.generateK1(stringK);
	    String key2 = keyGenerator.generateK2(stringK);
	    String plainText = "01110000";
	    String cipherText = sdes.decrypt(plainText, key1, key2);
	    System.out.println(cipherText);
    }
}
