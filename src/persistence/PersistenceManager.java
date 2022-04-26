package persistence;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;

import javax.swing.ImageIcon;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import models.EnumState;
import models.Match;

public class PersistenceManager {
	private static final String TAG_ENUM_FIRST = "enumFirst";
	private static final String TAG_ENUM_FIRSTBACK= "enumFirstBack";
	private static final String TAG_ENUM_SECOND = "enumSecond";
	private static final String TAG_ENUM_SECONDBACK= "enumSecondBack";
	private static final String TAG_ID = "id"; 
	private static final String TAG_NAME = "nameGamer";
	private static final String TAG_NUM_LIVES = "vidas";
	private static final String DB_PATH = "./db/list.json";
	private static final String DB_ID = "./db/id.json";
	private static final String DB_CAPTURES_PATH= "./db/captures";


	public static String getStringEnums(EnumState[][] enumstates) {
		String enumString="";
		for (int i = 0; i < enumstates.length; i++) {
			for (int j = 0; j < enumstates[0].length; j++) {
				enumString+= getRepresetationState(enumstates[i][j]);
			}
			enumString+=",";
		}
		return enumString;
	}
	public static EnumState[][] getEnums(String cadena) {
		EnumState[][] enumStates=new EnumState[13][20];
		String[] rows=cadena.split(",");
		
		for (int i = 0; i < rows.length; i++) {
			for (int j = 0; j < rows[i].length(); j++) {
				enumStates[i][j]=getState(Character.getNumericValue(rows[i].charAt(j)));
			}
		}
		return enumStates;
	}
	
	public static EnumState getState(int enms) {
		if(enms==0) {
			return null;
		}
		switch (enms) {
		case 1:
			return EnumState.DOG;
		case 2:
			return EnumState.FIELD;
		case 3:
			return EnumState.EARTH;
		case 4:
			return EnumState.ALARM;
		case 5:
			return EnumState.ARMADILLO;
		case 6:
			return EnumState.CAVE;
		default:
			return null;
		}
	}
	public static int getRepresetationState(EnumState enms) {
		if(enms==null) {
			return 0;
		}
		switch (enms) {
		case DOG:
			return 1;
		case FIELD:
			return 2;
		case EARTH:
			return 3;
		case ALARM:
			return 4;
		case ARMADILLO:
			return 5;
		case CAVE:
			return 6;
		default:
			return 0;
		}
	}
	public static ArrayList<ImageIcon> getCaptures() {
		ArrayList<ImageIcon> captures= new ArrayList<>();
		File archive= new File(DB_CAPTURES_PATH);
		if(!archive.exists()) {
			archive.mkdirs();
		}
		String[] list=archive.list();
		for (int i = 0; i < list.length; i++) {
			captures.add(new ImageIcon(DB_CAPTURES_PATH+"/"+list[i]));
		}
		return captures;
	}
	public Match getMatch(long id) {
		ArrayList<Match> list = readListMatchs();
		for (Match match : list) {
			if(match.getId()==id) {
				return match;
			}
		}
		return list.get(0);
	}
	public static ArrayList<Match> readListMatchs() {
		ArrayList<Match> list = new ArrayList<Match>();
		JSONParser parser = new JSONParser();
		try (FileReader reader = new FileReader(DB_PATH)) {
			BufferedReader bufferedReader = new BufferedReader(reader);
			String line = "";
			line = bufferedReader.readLine();
			while (line != null) {
				JSONObject profile = (JSONObject) parser.parse(line);
				Match match=new Match();
				match.setId((Long) profile.get(TAG_ID));
				match.setName((String) profile.get(TAG_NAME));
				match.setStatesFirstBack(getEnums((String)profile.get(TAG_ENUM_FIRSTBACK)));
				match.setStatesFirst(getEnums((String)profile.get(TAG_ENUM_FIRST)));
				match.setStatesSecond(getEnums((String)profile.get(TAG_ENUM_SECOND)));
				match.setStatesSecondBack(getEnums((String)profile.get(TAG_ENUM_SECONDBACK)));
				match.setNumberLives((Long)profile.get(TAG_NUM_LIVES));
				list.add(match);
				line = bufferedReader.readLine();
			}
			bufferedReader.close();
			reader.close();
			System.out.println(list.get(0));
			return list;
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}
	public static void write(Match match) {
	boolean isNew=false;
	File files=new File(DB_PATH);
	if(!files.exists()) {
		try {
			files.createNewFile();
			isNew=true;
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
		JSONObject jsonSquare= new JSONObject();
		long id=readIdFile()+1;
		writeIdFile(id);
		jsonSquare.put(TAG_ID, id);
		jsonSquare.put(TAG_NAME, match.getName());
		jsonSquare.put(TAG_ENUM_FIRSTBACK, getStringEnums(match.getStatesFirstBack()));
		jsonSquare.put(TAG_ENUM_FIRST,getStringEnums(match.getStatesFirst()));
		jsonSquare.put(TAG_ENUM_SECOND, getStringEnums(match.getStatesSecond()));
		jsonSquare.put(TAG_ENUM_SECONDBACK, getStringEnums(match.getStatesSecondBack()));
		jsonSquare.put(TAG_NUM_LIVES, match.getNumberLives());
		try (FileWriter file = new FileWriter(files, true)) {
			BufferedWriter writer = new BufferedWriter(file);
			if(!isNew) {
				writer.newLine();
			}
			writer.write(jsonSquare.toJSONString());
			writer.close();
			file.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static long readIdFile() {
		JSONParser parser = new JSONParser();
		File file=new File(DB_ID);
		if(!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
			}
			return 0;
		}else {
			try (Reader reader = new FileReader(DB_ID)) {
				BufferedReader bufferedReader = new BufferedReader(reader);
				String line = "";
				line = bufferedReader.readLine();
				JSONObject product = (JSONObject) parser.parse(line);
				long id = line != null ? (Long) product.get(TAG_ID) : 0;
				reader.close();
				bufferedReader.close();
				return id;
			} catch (IOException e) {
				e.printStackTrace();
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		return 0;
	}

	public static void writeIdFile(long id) {
		JSONObject jsonFile = new JSONObject();
		jsonFile.put(TAG_ID, id);
		try (FileWriter file = new FileWriter(DB_ID, false)) {
			file.write(jsonFile.toJSONString());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
