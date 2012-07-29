
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import resources.split_testHelper;
import com.rational.test.ft.*;
import com.rational.test.ft.object.interfaces.*;
import com.rational.test.ft.object.interfaces.SAP.*;
import com.rational.test.ft.object.interfaces.WPF.*;
import com.rational.test.ft.object.interfaces.dojo.*;
import com.rational.test.ft.object.interfaces.siebel.*;
import com.rational.test.ft.object.interfaces.flex.*;
import com.rational.test.ft.script.*;
import com.rational.test.ft.value.*;
import com.rational.test.ft.vp.*;

/**
 * Description   : Functional Test Script
 * @author Administrator
 */
public class splitsearchtest extends split_testHelper
{
	/**
	 * Script Name   : <b>split_test</b>
	 * Generated     : <b>Mar 8, 2009 8:36:46 PM</b>
	 * Description   : Functional Test Script
	 * Original Host : WinNT Version 5.2  Build 3790 (S)
	 * 
	 * @since  2009/03/08
	 * @author Administrator
	 */
	Writer output = null;
	public void testMain(Object[] args) 
	{
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
	    Date date = new Date();
	    File file = new File("C:\\WORKING\\RTS\\RTS_RFT\\splitsearch\\write.txt");
	    try{
			output = new BufferedWriter(new FileWriter(file));					
	}catch(Exception e1){System.out.println("error");}

		String place[]={
				"Mattekere",
				 "Hebbal",
				"TumkurRoad",
				"BellaryRoad",
				"AshokNagar",
				"Mattadahalli",
				"SankeyTank",
				"IISc",
				"GovtSoapFactory",
				"SubahdarChhatamRoad",
				"MilkColony",
				"Malleshweram",
				"Richard'sTown",
				"Madigarpalaya",
				"BangaloreEast",
				"Maharaja'sPalace",
				"Boyapalya",
				"CleavelandTown",
				"venkatarangiengar_rd",
				"Second_LinkRoad",
				"RajajiNagar",
				"ErrappaBlock",
				"Cox_town",
				"Bangalore_cantt",
				"St_John_Hill",
				"Nehru_Nagar",
				"Odderpalya",
				"Dayanand_Nagar",
				"Vasant_Nagar",
				"Bhashyam_Nagar",
				"Indira_Nagar",
				"Gandhi_Nagar",
				"Magadi_Road",
				"Cubbon_Park",
				"Majestic",
				"Hoshalli",
				"Brigade_Road",
				"Avenue_Road",
				"Trinity_Chruch",
				"Kantirawa_Stadium",
				"Richmond_Nagar",
				"Victoria_Road",
				"Mission_Road",
				"City_Market",
				"Chamrajpet",
				"Shanti_Nagar",
				"Azad_nagar",
				"Agram",
				"Domlur",
				"BapujiNagar",
				"LalBagh_Fort_Road",
				"KampeGuda_Nagar",
				"Nitsarda_Road",
				"Annepalya",
				"HombeGowda_Nagar",
				"Mysore_Rd",
				"Vivekanagar",
				"GandhiBazar",
				"LalBaghGardens",
				"Grass_Farm",
				"KrishnaPark",
				"VaniVilasRoad",
				"Siddapura",
				"HanumantaNagar",
				"BullTemple",
				"AnnekaiRoad",
				"HosurRoad",
				"Banshankari",
				"3rdMainRd",
				"JayaNagar",
				"sudduguntapalya",
				"sarakki_agrahar",
				"KanakPuraRd",
				"BannerGattaRd",
				"ElectronicsCity"
		};
		
		System.out.println("Start Time: "+dateFormat.format(date));	

		// Frame: Split Search Application
		radiusText().click(atPoint(64,9));
		splitSearch().inputChars("200");
		
		for(int i=0;i<place.length;i++)
		{
			//jComboBox().click(atText("Yashwantpur"));
		jComboBox().click();
		jComboBox().click(atText(place[i]));
		//jList().click(SCROLL_DOWNBUTTON);
		jList().click();
		jList().click(atText("HOTEL"));
		//jList2().click(SCROLL_DOWNBUTTON);
		//jList2().click(SCROLL_DOWNBUTTON);
		//jList2().click(atText("HOSPITAL"));
		jList2().click();
		jList2().click(atText("MALL"));
		jList3().click();
		jList3().click(atText("ATM"));
		jList4().click();
		jList4().click(atText("HOSPITAL"));
		//jList3().setState(Action.vScroll(36));
		//jList3().setState(Action.vScroll(54));
		//jList3().drag(atText("MALL"));
		//jList4().click(atText("ATM"));
		//radiusText().click(atPoint(64,9));
		//splitSearch().inputChars("200");
		//radiusText().click(atText("200"));
		getPoints().click();
		getOptimalPath().click();
		splitSearch().inputChars("");
		try {
			date = new Date();
			//output.write(place[i]+"\t"+"200"+"\t"+dateFormat.format(date)+"\n");
			output.write(place[i]+"\t"+"200"+"\tHOTEL MALL ATM HOSPITAL\t"+dateFormat.format(date)+"\n");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	  /*  if(i==place.length-1)	{
	    	
	    	try {
	    		output.close();
	    	} catch (IOException e) {
	    		// TODO Auto-generated catch block
	    		e.printStackTrace();
	    	}
	    	break;
//	    	System.exit(0);
	    	}

*/
		
	  }
		try {
			output.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

