package cen3024;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class SDLC_Assignment {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		StringBuilder html = new StringBuilder();		
		String valString = "";
		
		//Path to read file to with data
		Path readFile = Paths.get("The Project Gutenberg eBook of The Raven, by Edgar Allan Poe.htm");

		valString = ReadFile(readFile, html);
		
		String noHTMLString = valString.replaceAll("\\<.*?\\>", " ");
		String noExtraWhiteSpace = noHTMLString.replaceAll("\\s+"," ");
		
		String[] splitWords = noExtraWhiteSpace.split(" ");
		List<String> clearnSplitWords = new ArrayList<String>();
		LinkedHashMap<String, Integer> sortedMap = new LinkedHashMap<>();
        ArrayList<Integer> list = new ArrayList<>();
		Map<String, Integer> mapOfLists = new HashMap<String, Integer>();
		int count = 1;
		
	    //sort array
	    Arrays.sort(splitWords);
	
    	for (int i = 0; i < splitWords.length; i++) {
			
    		if(splitWords[i] != null && !splitWords[i].isBlank()) {
    			
    			//remove non-alphanumeric characters
    			clearnSplitWords.add(splitWords[i].replaceAll("[^a-zA-Z0-9]", ""));
    		}
		}
    	
    	String currentWord = clearnSplitWords.get(0);
    	
    	for (int i = 0; i < clearnSplitWords.size(); i++) {
			
			if(splitWords[i].equals(currentWord)){
	            count++;
	        } else{
	        	mapOfLists.put(currentWord, count);
	            count = 1;
	            currentWord = clearnSplitWords.get(i);
	        }
		}
    	
    	for (Map.Entry<String, Integer> entry : mapOfLists.entrySet()) {
            list.add(entry.getValue());
        }
    	
    	//Sort from desc order
    	Collections.sort(list, Collections.reverseOrder()); 
    	
        for (int num : list) {
            for (Entry<String, Integer> entry : mapOfLists.entrySet()) {
                if (entry.getValue().equals(num)) {
                    sortedMap.put(entry.getKey(), num);
                }
            }
        }
        
//        System.out.println(sortedMap);
        
        Iterator it = sortedMap.entrySet().iterator();
        int counter = 0;
        
        while (it.hasNext() && counter < 20) {
		
	        Map.Entry pair = (Map.Entry)it.next();
	        System.out.println(pair.getKey() + " x " + pair.getValue() + " times");
	        it.remove();
            
            counter++;
        }
	}
	
	//function to read data from file
	public static String ReadFile(Path file, StringBuilder html) {
		
		String result = "";
		
		try(BufferedReader bufferedReader = Files.newBufferedReader(file)){
			
			String htmlPoemStart = "<h1>The Raven</h1>";
			String htmlPoemEnd = "</div><!--end chapter-->";
			boolean matchFound = false;
			String line = bufferedReader.readLine();
			
			while (line != null) {
				line = bufferedReader.readLine();
				
				//ensure the line buffer is not null
				if(line != null) {
					
					if (line.equals(htmlPoemStart)) {
						matchFound = true;
					}
					
					if (line.equals(htmlPoemEnd)) {
						matchFound = false;
					}
				}
				
				if (matchFound) {
					html.append(line);
				}
			}
			
			result = html.toString();
			
			//close file after completion
			bufferedReader.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return result;
	}
}
