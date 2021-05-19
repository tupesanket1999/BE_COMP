import java.util.*;
import java.io.*;

public class expert_system
{
	Scanner sc=new Scanner(System.in);
	String symptoms[]=new String[15];
	String disease[]={"flu","typhoid","jaundice","cholera","malaria","measles","tuberculosis","dengue","chickenpox","covid 19"};
	String str;
	
	int nosymptoms,count,max;
	int i;
	int arr[]=new int[10];
	
	//function to get the symptoms from user
	public void getdata()
	{
		System.out.println("Enter your symptoms (Choose from following and enter q when done):\n1.cough\n2.fever\n3.weight_loss\n4.sweating\n5.chills\n6.fatigue\n7.shivering\n8.headache\n9.nausea\n10.vomiting\n11.loss of appetite\n12.rashes\n13.red spots\n14.stomach pain\n15.loose stools\n16.muscle aches\n17.congestion\n18.runny nose\n19.tiredness\n20.chest pain\n21.sore throat\n22.abdominal pain\n23.yellow skin colour\n24.dehydration\n25.lethargy\n26.diarrhoea\n27.conjunctivitis\n28.blister\n29.ulcers\n30.lack of hunger\n\nYour Symptoms:");
		
		nosymptoms=0;
		while(true)
		{
			str=sc.nextLine();
			if(str.equals("q"))
			{
				break;
			}
			else
			{
				symptoms[nosymptoms]=str;
				nosymptoms++;
			}
		}
	}
	
	//function to determine the disease
	public void calculate() throws Exception
	{
		arr[0]=getcount("./flu.txt");
		arr[1]=getcount("./typhoid.txt");
		arr[2]=getcount("./aundice.txt");
		arr[3]=getcount("./cholera.txt");
		arr[4]=getcount("./malaria.txt");
		arr[5]=getcount("./measles.txt");
		arr[6]=getcount("./tuberculosis.txt");
		arr[7]=getcount("./dengue.txt");
		arr[8]=getcount("./chickenpox.txt");
		arr[9]=getcount("./covid.txt");
		
		max=-1;
		for(i=0;i<10;i++)
		{
			if(max<arr[i])
			{
				max=arr[i];
			}
		}
		
		for(i=0;i<10;i++)
		{
			if(max==arr[i])
			{
				System.out.println("You may have "+disease[i]);
			}
		}
	}
	
	//function to determine the matching symptoms
	public int getcount(String addr) throws Exception
	{
		FileReader fr=new FileReader(addr);
		BufferedReader br=new BufferedReader(fr);
		
		String line;
		
		count=0;
		while((line=br.readLine())!=null)
		{
			if(search(line))
			{
				count++;
			}
		}
		return count;
	}
	
	//function to search the symptom
	public boolean search(String s)
	{
		for(i=0;i<nosymptoms;i++)
		{
			if(symptoms[i].equals(s))
			{
				return true;
			}
		}
		return false;
	}

	public static void main(String[] args) throws Exception
	{
		expert_system e=new expert_system();
		e.getdata();
		e.calculate();
	}
}
