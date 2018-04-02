/*** 
 * @Author 侯凱翔
 * 學號: 104403519
 * 系級: 資管3A
 * HW5: 發票系統
***/
import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;
import java.util.stream.Collectors;
/*	註:我有在Invoice新增一個getAmount()的method
	getAmount()=quantity*price (總金額)
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
    	
    	while(true) { // 重複執行，所以用while
	    	System.out.printf("Welcome to invoices management system.%nFunctions: Sort/Report/Select%nChoice(-1 to exit):");
	    	String x = s.next(); 
	    	// 如果讀到-1就結束
	    	if(x.equals("-1")) {
	    		System.exit(0);
	    	}
	    	else {
	    		if(x.equalsIgnoreCase("sort")) { //做sort
	    			System.out.println("做sort");
	    			System.out.println("Order by ID/Quantity/Price?");
	    			String orderby = s.next(); //讀要用哪種排序
	    			if(orderby.equalsIgnoreCase("ID")) { //ID排
	    				Comparator<Invoice> sortbyID =
	    					Comparator.comparing(Invoice::getPartNumber);
	    				System.out.printf("%nInvoices Sorted by ID:%n");
	    					list.stream()
	    						.sorted(sortbyID)
	    						.forEach(System.out::println);
	    				System.out.println("");
	    			}
	    			else if(orderby.equalsIgnoreCase("Quantity")) { //數量排
	    				Comparator<Invoice> sortbyQuantity =
	    					Comparator.comparing(Invoice::getQuantity);
	    				System.out.printf("%nInvoices Sorted by Quantity:%n");
	    					list.stream()
	    						.sorted(sortbyQuantity)
	    						.forEach(System.out::println);
	    				System.out.println("");
	    			}
	    			else if(orderby.equalsIgnoreCase("Price")) { //價錢排
	    				Comparator<Invoice> sortbyPrice =
	    						Comparator.comparing(Invoice::getPrice);
	    				System.out.printf("%nInvoices Sorted by Price:%n");
	    						list.stream()
	    							.sorted(sortbyPrice)
	    							.forEach(System.out::println);
	    				System.out.println("");
	    			}
	    			else { //輸入的不是ID/Quantity/Price 預設使用ID排序
	    				Comparator<Invoice> sortbyID =
		    				Comparator.comparing(Invoice::getPartNumber);
		    			System.out.printf("%nYou Entered:"+orderby+" (No such option)%n(Default)Invoices Sorted by ID:%n");
		    				list.stream()
		    					 .sorted(sortbyID)
		    					 .forEach(System.out::println);
		    			System.out.println("");
	    			}
	    		}
	    		else if(x.equalsIgnoreCase("report")) { //做Report
	    			System.out.printf("%nInvoice group by description:%n");
	    			// 開一個Map，key為商品名(不重複)，value為總金額
	    			Map<String, Double> ItemMoney =
	    					list.stream()
	    						.collect(Collectors.groupingBy(Invoice::getPartDescription,TreeMap::new, Collectors.summingDouble(Invoice::getAmount)));
	    			Map<String,Double> sortedreport = sortByValue(ItemMoney); //按照金額(value)排，所以先sort
	    					sortedreport.forEach(
	    							(product,money) ->
	    								System.out.printf("Description: %-15s Invoice Amount: %.2f%n",product,money)
	    							);
	    			System.out.println("");
	    		}
	    		else if(x.equalsIgnoreCase("select")) { //做Select
	    			System.out.printf("(不必打括號)Input the range to show (min,max):");
	    			try { //這邊讀 (數字,數字) 格式錯誤就丟進exception
		    			String range = s.next();
		    			String[] rng = range.split(",");
	    				int min = Integer.parseInt(rng[0]);
		    			int max = Integer.parseInt(rng[1]);
		    			if(min<max) { //最小值必須小於最大值
		    				System.out.printf("%nInvoices mapped to description and invoice amount for invoices in the range "+min+"~"+max+"%n");
		    				// after為做完篩選後的List並以總金額排序
		    				List<Invoice> after =
		    						list.stream()
		    							.filter(i -> (i.getAmount()>=min && i.getAmount()<=max))
		    							.sorted(Comparator.comparing(Invoice::getAmount))
		    							.collect(Collectors.toList());
		    				after.forEach(invoice -> System.out.printf("Description: %-15s Invoice Amount: %.2f%n",invoice.getPartDescription(), invoice.getAmount()));
		    				System.out.println("");
		    			}
		    			else { //如果最小值沒有比最大值小，重新執行
		    				System.out.printf("min must be less than max !!!%n%n");
		    			}
		    			
		    		}
	    			catch(Exception exception) { //輸入範圍的格式錯誤，重新執行
	    				System.out.printf("Wrong Input!!!%n(不必打括號)Please enter as the format: (min,max)%n%n");
	    				x = s.nextLine();
	    			}
	    		}
	    		else { // 輸入的不是sort/report/select 叫他重新輸入
	    			System.out.printf("Wrong Input. Enter again:%n%n");
	    		}
	    	}
    	}
    	
    }
    //  (商品為key，總金額為value)Report按照總金額排序 做一個sort-by-value的method
    public static <K, V extends Comparable<? super V>> Map<K, V> sortByValue(Map<K, V> map) {
	    return map.entrySet()
	              .stream()
	              .sorted(Map.Entry.comparingByValue(/*Collections.reverseOrder()*/))
	              .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
    }
}
    
    
    