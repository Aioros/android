package com.aioros.taboogen;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
//import java.util.Random;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
//import org.json.JSONArray;
//import org.json.JSONException;
import org.json.JSONObject;

import android.os.AsyncTask;
import android.view.View;
import android.widget.TextView;

public class CardCreatorTask extends AsyncTask<Void, Void, List<String>> {
	
	private final TabooGenActivity tabooGenActivity;
	
	public CardCreatorTask(TabooGenActivity tabooGenActivity) {
		this.tabooGenActivity = tabooGenActivity;
	}

	public void onPreExecute() {
		tabooGenActivity.findViewById(R.id.createCard).setClickable(false);
		tabooGenActivity.findViewById(R.id.spinner).setVisibility(View.VISIBLE);
	}
	
	public boolean isRomanNumeral(String s) {
		return s.equals("I") || s.equals("II") || s.equals("III") || s.equals("IV") || s.equals("V") || s.equals("VI") || s.equals("VII") || 
				s.equals("VIII") || s.equals("IX") || s.equals("X") || s.equals("XI") || s.equals("XII") || s.equals("XIII") || s.equals("XIV") || 
				s.equals("XV") || s.equals("XVI") || s.equals("XVII") || s.equals("XVIII") || s.equals("XIX") || s.equals("XX") || s.equals("XXI");
	}
	
	public List<String> doInBackground(Void... unused) {
		BufferedReader in = null;
    	String page;
    	JSONObject jsonPage;
    	String title;
    	String niceTitle;
    	boolean wikipediaLinkFound;
    	
    	String[] taboo = new String[5];
    	int n;
    	//String[] categoryIds = "86137,81735,81731,110560,132824,81733,110817,92946,45768,131426,81736,89096,81737,110205,81767,160355".split(",");
    	
    	try {
    		do {
	    		do {
	    			
	    			wikipediaLinkFound = false;
		    		HttpClient client = new DefaultHttpClient();
		    		HttpGet request = new HttpGet();
		    		String NL = System.getProperty("line.separator");
		    		
		    		//Random r = new Random();
		    		//String categoryId = categoryIds[r.nextInt(categoryIds.length)];
		    		request.setURI(new URI("http://tools.wikimedia.de/~erwin85/randomarticle.php?lang=it&family=wiktionary&namespaces=0&categories=Acronimi%20in%20italiano%7CAggettivi%20in%20italiano%7CAvverbi%20in%20italiano%7CConiugazioni%20in%20italiano%7CEspressioni%20in%20italiano%7CForma%20di%20comparativo%20in%20italiano%7CInteriezioni%20in%20italiano%7CLocuzioni%20in%20italiano%7CPronomi%20in%20italiano%7CSostantivi%20in%20italiano%7C" +/* "Terminologia%20specializzata-IT%7C" +*/ "Verbi%20in%20italiano%7CServizio-IT&namespaces=-1&subcats=1&d=1"));
		    		HttpResponse response = client.execute(request);
		    		in = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
		    		StringBuffer sb = new StringBuffer("");
		    		String line = "";
		    		while (!(line = in.readLine()).contains("<title>"));
		    		niceTitle = line.substring(line.indexOf("<title>") + 7, line.indexOf(" - Wikizionario"));
		    		while (!(line = in.readLine()).contains("?title="));
		    		title = line.substring(line.indexOf("?title=") + 7, line.indexOf("&amp;action"));
		    		in.close();
		    		page = sb.toString();
		    		
		    		/*request.setURI(new URI("http://it.wiktionary.org/wiki/Speciale:PaginaCasuale"));
		    		HttpResponse response = client.execute(request);
		    		in = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
		    		StringBuffer sb = new StringBuffer("");
		    		String line = "";
		    		while (!(line = in.readLine()).contains("?title="));
		    		title = line.substring(line.indexOf("?title=") + 7, line.indexOf("&amp;action"));
		    		in.close();
		    				    		
		    		request.setURI(new URI("http://it.wiktionary.org/w/api.php?format=json&action=query&titles=" + title + "&prop=categories"));
		    		response = client.execute(request);
		    		in = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
		    		sb = new StringBuffer("");
		    		line = "";
		    		while ((line = in.readLine()) != null) {
		    			sb.append(line + NL);
		    		}
		    		in.close();
		    		page = sb.toString();
		    	    
		    		jsonPage = new JSONObject(page);
		    		String pageNumber = jsonPage.getJSONObject("query").getJSONObject("pages")
		    				.names().getString(0);
		    		String lastCat = "";
		    		try {
		    			JSONArray categories = jsonPage.getJSONObject("query").getJSONObject("pages").getJSONObject(pageNumber).getJSONArray("categories");
		    			lastCat = categories.getJSONObject(categories.length()-1).getString("title");
		    		} catch (JSONException e) {}*/
		    		
		    		//if (lastCat.endsWith(" in italiano")) {
		    			
			    		request.setURI(new URI("http://it.wikipedia.org/w/api.php?format=json&action=query&titles=" + title + "&prop=revisions&rvprop=content&redirects"));
			    		response = client.execute(request);
			    		in = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
			    		sb = new StringBuffer("");
			    		line = "";
			    		while ((line = in.readLine()) != null) {
			    			sb.append(line + NL);
			    		}
			    		in.close();
			    		page = sb.toString();
			    		
			    		jsonPage = new JSONObject(page);
			    		if (!page.contains("disambigua")) {
				    		String pageNumber = jsonPage.getJSONObject("query").getJSONObject("pages")
				    				.names().getString(0);	    		
				    		if (!jsonPage.getJSONObject("query").getJSONObject("pages")
				    				.getJSONObject(pageNumber).has("missing")) {
				    			wikipediaLinkFound = true;
				    		}
			    		}
			    		
		    		//}
		    		
	    		} while (!wikipediaLinkFound);
		    	
		    	String content = jsonPage.getJSONObject("query").getJSONObject("pages")
	    				.getJSONObject(jsonPage.getJSONObject("query").getJSONObject("pages").names().getString(0))
	    				.getJSONArray("revisions").getJSONObject(0).getString("*");
		    	if (content.toLowerCase().contains("'''" + niceTitle.toLowerCase() + "'''")) {
		    		content = content.substring(content.toLowerCase().indexOf("'''" + niceTitle + "'''"));
		    	}
		    	content = content.substring(content.indexOf("[["));
		    	n = 0;
		    	while (n<5 && content.contains("[[")) {
		    		content = content.substring(content.indexOf("[[") + 2);
		    		int ws = (content.indexOf(" ") == -1) ? Integer.MAX_VALUE : content.indexOf(" ");
		    		int pipe = (content.indexOf("|") == -1) ? Integer.MAX_VALUE : content.indexOf("|");
		    		int close = (content.indexOf("]]") == -1) ? Integer.MAX_VALUE : content.indexOf("]]");
		    		int end = Math.min(Math.min(ws, pipe), close);
		    		String candidate = content.substring(0, end);
		    		if (!candidate.contains(":") && !candidate.toLowerCase().equals(candidate.toUpperCase()) 
		    				&& !content.substring(0, end+1).toLowerCase().equals("lingua ") && !isRomanNumeral(candidate)) {
		    			boolean repeated = false;
		    			for (int k=0; k<n; k++) {
		    				if (taboo[k].equals(candidate.toUpperCase()))
		    					repeated = true;
		    			}
		    			if (!repeated)
		    				taboo[n++] = candidate.toUpperCase();
		    		}
		    		content = content.substring(content.indexOf("]]") + 2);
		    	}
		    	
    		} while (n<5);
    		
    		List<String> words = new ArrayList<String>();
    		words.add(niceTitle.toUpperCase());
    		for (int i=0; i<5; i++) {
    			words.add(taboo[i]);
    		}
    		return words;    		
    		
    	} catch (Exception e) { 
    		e.printStackTrace();
    	} finally {
    		if (in != null) {
    			try {
    				in.close();
    			} catch (IOException e) {
    				e.printStackTrace();
    			}
    		}
    	}
		return null;
	}

	public void onPostExecute(List<String> words) {
		tabooGenActivity.findViewById(R.id.spinner).setVisibility(View.GONE);
		((TextView) tabooGenActivity.findViewById(R.id.mainWord)).setText(words.get(0));
		((TextView) tabooGenActivity.findViewById(R.id.taboo1)).setText(words.get(1));
		((TextView) tabooGenActivity.findViewById(R.id.taboo2)).setText(words.get(2));
		((TextView) tabooGenActivity.findViewById(R.id.taboo3)).setText(words.get(3));
		((TextView) tabooGenActivity.findViewById(R.id.taboo4)).setText(words.get(4));
		((TextView) tabooGenActivity.findViewById(R.id.taboo5)).setText(words.get(5));
		tabooGenActivity.findViewById(R.id.createCard).setClickable(true);
	}
}
