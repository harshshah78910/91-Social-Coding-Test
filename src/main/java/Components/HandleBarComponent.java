package Components;

import java.util.Date;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Optional;

public class HandleBarComponent implements Component {

	private Date startDate;
	private Date endDate;
	private double price;

	static final HashMap<IntervalPricing, Double> pricingMap = new HashMap<IntervalPricing, Double>();

	HandleBarComponent(Date startDate, Date endDate, double price) {
		this.startDate = startDate;
		this.endDate = endDate;
		this.price = price;
		pricingMap.put(new IntervalPricing(startDate, endDate), price);
	}
	
	HandleBarComponent()
	{
		
	}

	public double price(final Date currentDate) {

		Optional<Entry<IntervalPricing, Double>> findFirst = pricingMap.entrySet().parallelStream().filter(entry -> {
			IntervalPricing key = entry.getKey();
			if (key.getStartDate().before(currentDate) && key.getEndDate().after(currentDate))
				return true;
			return false;
		}).findFirst();

		if (findFirst.isPresent()) {
			return findFirst.get().getValue();
		}

		return 0;
	}

	public Date getStartDate() {
		return startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public double getPrice() {
		return price;
	}

	@Override
	public String toString() {
		return "HandleBarComponent";
	}

}
