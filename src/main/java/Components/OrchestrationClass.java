package Components;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import calculations.PriceCalculations;

public class OrchestrationClass {

	public static void main(String[] args) throws InterruptedException, ExecutionException {
		loadPrices();
		Scanner sc = new Scanner(System.in);
		System.out.println("No of TestCases");
		int testCase =  sc.nextInt();
		ArrayList<HashMap<Component, Integer>> inputList = new ArrayList<HashMap<Component, Integer>>();
		while (testCase-- > 0) {
			createInput(sc, inputList);

		}

		runLogic(inputList);

	}

	public static List<Future<ConcurrentHashMap<String, Double>>> runLogic(ArrayList<HashMap<Component, Integer>> inputList)
			throws InterruptedException, ExecutionException {
		ExecutorService threadPool = Executors.newFixedThreadPool(10);
        List<Future<ConcurrentHashMap<String, Double>>> resultList = new ArrayList<Future<ConcurrentHashMap<String,Double>>>();
		for (int i = 0; i < inputList.size(); i++) {
			Future<ConcurrentHashMap<String, Double>> submit = threadPool
					.submit(new PriceCalculations(inputList.get(i)));
			ConcurrentHashMap<String, Double> concurrentHashMap = submit.get();
			resultList.add(submit);
			printOutPut(concurrentHashMap);
		}
		
		return resultList;
	}

	private static void createInput(Scanner sc, ArrayList<HashMap<Component, Integer>> inputList) {
		HashMap<Component, Integer> componentListToQuantity = new HashMap<Component, Integer>();

		int option = 0;
		while (option != 6) {
			System.out.println("Select Components & Quantity");
			System.out.println("1. Frame");
			System.out.println("2. Wheels");
			System.out.println("3. Handle Bar");
			System.out.println("4. Seat Component");
			System.out.println("5. Chain Assembly");
			System.out.println("6. Calculate");
			option = sc.nextInt();
			if (option == 1) {
				System.out.println("Enter Quantity");
				int quanity = sc.nextInt();
				componentListToQuantity.put(new Frame(), quanity);

			}
			if (option == 2) {
				System.out.println("Enter Quantity");
				int quanity = sc.nextInt();
				componentListToQuantity.put(new WheelComponent(), quanity);

			}
			if (option == 3) {
				System.out.println("Enter Quantity");
				int quanity = sc.nextInt();
				componentListToQuantity.put(new HandleBarComponent(), quanity);

			}
			if (option == 4) {
				System.out.println("Enter Quantity");
				int quanity = sc.nextInt();
				componentListToQuantity.put(new SeatComponent(), quanity);

			}
			if (option == 5) {
				System.out.println("Enter Quantity");
				int quanity = sc.nextInt();
				componentListToQuantity.put(new ChainAssembly(), quanity);

			}

		}

		inputList.add(componentListToQuantity);
	}

	private static void printOutPut(ConcurrentHashMap<String, Double> concurrentHashMap) {
		System.out.println("**********************************************");

		concurrentHashMap.entrySet().parallelStream().filter(entry -> {
			System.out.println(entry.getKey() + " " + entry.getValue());
			return true;
		}).count();

		System.out.println("**********************************************");
	}

	// Sample Prices for Test
	public  static void loadPrices() {
		Date today = new Date();
		System.out.println(today);

		Calendar cal = Calendar.getInstance();
		cal.setTime(today);

		cal.add(Calendar.DATE, 5);

		Date modifiedDate = cal.getTime();

		new Frame(new Date(), modifiedDate, 100);
		new WheelComponent(new Date(), modifiedDate, 300);
		new HandleBarComponent(new Date(), modifiedDate, 400);
		new SeatComponent(new Date(), modifiedDate, 600);
		new ChainAssembly(new Date(), modifiedDate, 200);

	}

}
