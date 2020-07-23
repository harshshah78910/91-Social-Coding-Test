package test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import Components.ChainAssembly;
import Components.Component;
import Components.Frame;
import Components.OrchestrationClass;
import Components.SeatComponent;
import Components.WheelComponent;

class OrchestrationTest {

	
	OrchestrationClass orchestrationClass = new OrchestrationClass();
	

	
	@Test
	void runLogicTest() throws InterruptedException, ExecutionException {
		orchestrationClass.loadPrices();
		ArrayList<HashMap<Component, Integer>> inputList = new ArrayList<HashMap<Component,Integer>>();
		HashMap<Component, Integer> map = new HashMap<Component, Integer>();	
		map.put(new Frame(), 1);
		map.put(new SeatComponent(), 1);
		map.put(new ChainAssembly(), 1);
		map.put(new WheelComponent(), 1);
		inputList.add(map);
		System.out.println("Junit Example");
		List<Future<ConcurrentHashMap<String,Double>>> runLogic = orchestrationClass.runLogic(inputList);
		ConcurrentHashMap<String,Double> concurrentHashMap = runLogic.get(0).get();
		Double result = concurrentHashMap.get("TotalPrice");
		assertEquals(1200, result);
	}

}
