package sitha.rupp.password;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

public class MainTestPassword {

	public static void main(String[] args) throws NoSuchAlgorithmException, InvalidKeySpecException {
		// TODO Auto-generated method stub
		GenerateHashPassword g= new GenerateHashPassword();
		String a =g.generateStorngPasswordHash("123456Aa!");
		System.out.println(a);
	}

}
