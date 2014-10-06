package de.jknowledge.batchexample.batch;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;

import javax.batch.api.chunk.AbstractItemReader;

import de.jknowledge.batchexample.model.NettoModel;

public class Reader extends AbstractItemReader {
	
	private static final String NETTO_FILE_PATH = "/home/christian/temp/netto.csv";
	
	private Integer lineNumber = Integer.valueOf(0);
	
	private String line;
	
	private InputStream in;
	
	BufferedReader br = null;
	
	@Override
	public Object readItem() throws Exception {
		Object result = null;
		if (line != null) {
			String[] fields = line.split("[, \t\r\n]+");
			if(fields.length > 0) {
				NettoModel itemObj = new NettoModel();
				itemObj.setName(String.valueOf(fields[0]));
				itemObj.setPrice(Float.valueOf(fields[1]));
				itemObj.setTax(Float.valueOf(fields[2]));
				result = itemObj;
			}	
			lineNumber++;
			line = br.readLine();
		}
		return result;
	}
	
	@Override
	public void open(Serializable checkpoint) throws Exception {
		System.out.println("open file " + NETTO_FILE_PATH);
		in  = new FileInputStream(NETTO_FILE_PATH);
		br = new BufferedReader(new InputStreamReader(in));

		if (checkpoint != null) {
			lineNumber = (Integer) checkpoint;
		} 
		
		for (int i=0; i<=lineNumber; i++) {
			line = br.readLine();
		}		
	}
	
	@Override
	public void close() throws Exception {
		br.close();
		in.close();
	}
	
	@Override
	public Serializable checkpointInfo() throws Exception {
		return lineNumber;
	}

}
