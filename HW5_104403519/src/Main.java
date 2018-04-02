/*** 
 * @Author �J�͵�
 * �Ǹ�: 104403519
 * �t��: ���3A
 * HW5: �o���t��
***/
import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;
import java.util.stream.Collectors;
/*	��:�ڦ��bInvoice�s�W�@��getAmount()��method
	getAmount()=quantity*price (�`���B)
*/ 
public class Main {
    public static void main(String[] args)
    {
    	Invoice[] item = {
		         new Invoice(83, "Electric sander", 7, 57.98),
		         new Invoice(24, "Power saw",18 ,99.99),
		         new Invoice( 7, "Sledge hammer", 11, 21.50),
		         new Invoice(77, "Hammer", 76, 11.99),
		         new Invoice(39, "Lawn mower", 3, 79.50),
		         new Invoice(68, "Screwdriver", 106 ,6.99),
		         new Invoice(56, "Jig saw", 21, 11.00),
		         new Invoice( 3, "Wrench", 34, 7.50),
		         new Invoice(45, "Wrench", 13, 7.50),
		         new Invoice(22, "Hammer", 47, 11.99)};
    	List<Invoice> list = Arrays.asList(item);
    	Scanner s = new Scanner(System.in);
    	
    	while(true) { // ���ư���A�ҥH��while
	    	System.out.printf("Welcome to invoices management system.%nFunctions: Sort/Report/Select%nChoice(-1 to exit):");
	    	String x = s.next(); 
	    	// �p�GŪ��-1�N����
	    	if(x.equals("-1")) {
	    		System.exit(0);
	    	}
	    	else {
	    		if(x.equalsIgnoreCase("sort")) { //��sort
	    			System.out.println("��sort");
	    			System.out.println("Order by ID/Quantity/Price?");
	    			String orderby = s.next(); //Ū�n�έ��رƧ�
	    			if(orderby.equalsIgnoreCase("ID")) { //ID��
	    				Comparator<Invoice> sortbyID =
	    					Comparator.comparing(Invoice::getPartNumber);
	    				System.out.printf("%nInvoices Sorted by ID:%n");
	    					list.stream()
	    						.sorted(sortbyID)
	    						.forEach(System.out::println);
	    				System.out.println("");
	    			}
	    			else if(orderby.equalsIgnoreCase("Quantity")) { //�ƶq��
	    				Comparator<Invoice> sortbyQuantity =
	    					Comparator.comparing(Invoice::getQuantity);
	    				System.out.printf("%nInvoices Sorted by Quantity:%n");
	    					list.stream()
	    						.sorted(sortbyQuantity)
	    						.forEach(System.out::println);
	    				System.out.println("");
	    			}
	    			else if(orderby.equalsIgnoreCase("Price")) { //������
	    				Comparator<Invoice> sortbyPrice =
	    						Comparator.comparing(Invoice::getPrice);
	    				System.out.printf("%nInvoices Sorted by Price:%n");
	    						list.stream()
	    							.sorted(sortbyPrice)
	    							.forEach(System.out::println);
	    				System.out.println("");
	    			}
	    			else { //��J�����OID/Quantity/Price �w�]�ϥ�ID�Ƨ�
	    				Comparator<Invoice> sortbyID =
		    				Comparator.comparing(Invoice::getPartNumber);
		    			System.out.printf("%nYou Entered:"+orderby+" (No such option)%n(Default)Invoices Sorted by ID:%n");
		    				list.stream()
		    					 .sorted(sortbyID)
		    					 .forEach(System.out::println);
		    			System.out.println("");
	    			}
	    		}
	    		else if(x.equalsIgnoreCase("report")) { //��Report
	    			System.out.printf("%nInvoice group by description:%n");
	    			// �}�@��Map�Akey���ӫ~�W(������)�Avalue���`���B
	    			Map<String, Double> ItemMoney =
	    					list.stream()
	    						.collect(Collectors.groupingBy(Invoice::getPartDescription,TreeMap::new, Collectors.summingDouble(Invoice::getAmount)));
	    			Map<String,Double> sortedreport = sortByValue(ItemMoney); //���Ӫ��B(value)�ơA�ҥH��sort
	    					sortedreport.forEach(
	    							(product,money) ->
	    								System.out.printf("Description: %-15s Invoice Amount: %.2f%n",product,money)
	    							);
	    			System.out.println("");
	    		}
	    		else if(x.equalsIgnoreCase("select")) { //��Select
	    			System.out.printf("(�������A��)Input the range to show (min,max):");
	    			try { //�o��Ū (�Ʀr,�Ʀr) �榡���~�N��iexception
		    			String range = s.next();
		    			String[] rng = range.split(",");
	    				int min = Integer.parseInt(rng[0]);
		    			int max = Integer.parseInt(rng[1]);
		    			if(min<max) { //�̤p�ȥ����p��̤j��
		    				System.out.printf("%nInvoices mapped to description and invoice amount for invoices in the range "+min+"~"+max+"%n");
		    				// after�������z��᪺List�åH�`���B�Ƨ�
		    				List<Invoice> after =
		    						list.stream()
		    							.filter(i -> (i.getAmount()>=min && i.getAmount()<=max))
		    							.sorted(Comparator.comparing(Invoice::getAmount))
		    							.collect(Collectors.toList());
		    				after.forEach(invoice -> System.out.printf("Description: %-15s Invoice Amount: %.2f%n",invoice.getPartDescription(), invoice.getAmount()));
		    				System.out.println("");
		    			}
		    			else { //�p�G�̤p�ȨS����̤j�Ȥp�A���s����
		    				System.out.printf("min must be less than max !!!%n%n");
		    			}
		    			
		    		}
	    			catch(Exception exception) { //��J�d�򪺮榡���~�A���s����
	    				System.out.printf("Wrong Input!!!%n(�������A��)Please enter as the format: (min,max)%n%n");
	    				x = s.nextLine();
	    			}
	    		}
	    		else { // ��J�����Osort/report/select �s�L���s��J
	    			System.out.printf("Wrong Input. Enter again:%n%n");
	    		}
	    	}
    	}
    	
    }
    //  (�ӫ~��key�A�`���B��value)Report�����`���B�Ƨ� ���@��sort-by-value��method
    public static <K, V extends Comparable<? super V>> Map<K, V> sortByValue(Map<K, V> map) {
	    return map.entrySet()
	              .stream()
	              .sorted(Map.Entry.comparingByValue(/*Collections.reverseOrder()*/))
	              .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
    }
}
    
    
    