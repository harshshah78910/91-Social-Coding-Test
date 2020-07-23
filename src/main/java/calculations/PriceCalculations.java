package calculations;

import java.util.Date;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;

import Components.Component;

public class PriceCalculations implements Callable<ConcurrentHashMap<String, Double>> {

	
	HashMap<Component, Integer> componentListToQuantity;
	
	public PriceCalculations(HashMap<Component, Integer> componentList)
	{
		this.componentListToQuantity = componentList;
	}
	
	
	@Override
	public ConcurrentHashMap<String, Double> call() throws Exception {
		
		ConcurrentHashMap<String, Double> componentPrices = new ConcurrentHashMap<String, Double>();
		Double runningTotal = 0.0;
		
		Set<Entry<Component,Integer>> entrySet = componentListToQuantity.entrySet();
		
		for(Entry<Component,Integer> entry : entrySet)
		{
			Component component = entry.getKey();
			Integer quantity = entry.getValue();
			
			double unitPrice = component.price(new Date());
			double totalPrice = unitPrice * quantity;
			runningTotal += totalPrice;
			if(null != componentPrices.get(component.toString()))
			{
				componentPrices.put(component.toString(), componentPrices.get(component.toString()) + totalPrice);
			}
			else
			{
				componentPrices.put(component.toString(), totalPrice);
			}
			
		}
		
		componentPrices.put("TotalPrice", runningTotal);
		return componentPrices;
		
	}

}
