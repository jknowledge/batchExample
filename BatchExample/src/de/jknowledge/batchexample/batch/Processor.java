package de.jknowledge.batchexample.batch;

import javax.batch.api.chunk.ItemProcessor;

import de.jknowledge.batchexample.model.BruttoModel;
import de.jknowledge.batchexample.model.NettoModel;

public class Processor implements ItemProcessor {
	
	@Override
	public Object processItem(Object obj) throws Exception {
		NettoModel nettoObj = (NettoModel) obj;
		
		Float price = nettoObj.getPrice();
		Float tax = (nettoObj.getTax()/100) * price;
		Float priceWithTax = price + tax;
		
		BruttoModel bruttoObj = new BruttoModel();
		bruttoObj.setName(nettoObj.getName());
		bruttoObj.setPrice(priceWithTax);
		
		return bruttoObj;
	}

}
