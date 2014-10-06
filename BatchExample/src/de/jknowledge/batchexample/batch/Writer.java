package de.jknowledge.batchexample.batch;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.util.List;

import javax.batch.api.chunk.AbstractItemWriter;

import de.jknowledge.batchexample.model.BruttoModel;

public class Writer extends AbstractItemWriter {
	
	private static final String BRUTTO_FILE_PATH = "/home/christian/temp/brutto.csv";

	private FileWriter writer;
	
	private Integer lineNumber = Integer.valueOf(-1);
	
	@Override
	public void open(Serializable checkpoint) throws Exception {
		StringBuffer sb = new StringBuffer();
		try(	InputStream in  = new FileInputStream(BRUTTO_FILE_PATH); 
				BufferedReader reader = new BufferedReader(new InputStreamReader(in))) {
			
			if (checkpoint != null) {
				lineNumber = (Integer) checkpoint;
			} 
			
			for (int i=0; i<=lineNumber; i++) {
				String line = reader.readLine();
				if(line != null) {
					sb.append(line);
					sb.append("\n");
				}	
			}
		}
		
		writer = new FileWriter(BRUTTO_FILE_PATH);
		writer.append(sb);
		
	}
	
	@Override
	public void writeItems(List<Object> objects) throws Exception {
		for(Object o : objects) {
			if(o != null && o instanceof BruttoModel) {
				BruttoModel bruttoObj = (BruttoModel) o;
				writer.append(bruttoObj.getName());
				writer.append(" ");
				writer.append(String.valueOf(bruttoObj.getPrice()));
				writer.append("\n");
				lineNumber++;
			}
		}
	}
	

	@Override
	public void close() throws Exception {
		writer.flush();
		writer.close();
	}
	
	@Override
	public Serializable checkpointInfo() throws Exception {
		return lineNumber;
	}

}
