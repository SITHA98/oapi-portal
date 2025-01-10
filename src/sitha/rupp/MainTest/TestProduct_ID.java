package sitha.rupp.MainTest;

import sitha.rupp.configuration.Application_Properties;
import sitha.rupp.model.rpt_0008_EndorsementModel;
import sitha.rupp.service.RPT_0010_Endorsement;

public class TestProduct_ID {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
//		String a="P/01/PV00/20/0135/000/00";
//		int l=a.length();
//		int l_7=l-7;
//		String tail=a.substring(l_7,24);
//		System.out.println("Tail:"+tail);
//		System.out.println("hi "+l_7);
//		String n_a = a.substring(0, l_7);
//		System.out.println("new string "+n_a);
//		String replace_n_a = n_a.replace("/", "");
//		System.out.println("replace_n_a:"+replace_n_a);
//		String originalPolicyNumber = replace_n_a+tail;
//		System.out.println("originalPolicyNumber:"+originalPolicyNumber);
		
		//Application_Properties.SERIAL=3;
		//RPT_0010_Endorsement rpt_0008 = new RPT_0010_Endorsement();
		//rpt_0008_EndorsementModel endModel = new rpt_0008_EndorsementModel();
		
		String p="P/01/HC00/20/0030/000/00";
		String s[]=p.split("/");
		//System.out.println(s[2]);
		//System.out.println(s[0]+s[1]+s[2]+s[3]+s[4]+"/"+s[5]+"/"+s[6]);
		
		String claimNumber="C01pa00210387";
		System.out.println(claimNumber.substring(3,5));
	}

}
