import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;
import java.util.Stack;

public class Start {
	
	public static Stack<XMLNode> stackOpen =  new Stack<XMLNode>();
	public static Stack<XMLNode> stackClose =  new Stack<XMLNode>();
	public static Stack<XMLNode> stackTag =  new Stack<XMLNode>();
	public static Stack<XMLNode> stackXML = new Stack<XMLNode>();
	
	private final static String OPEN_TAG_MATCHER = "[ˆ<][a-zA-Z]{0,}[>$]";
	private final static String CLOSE_TAG_MATCHER = "[ˆ<][/][a-zA-Z]{0,}[>$]";
	private final static String OPEN_TAG_TEXT_MATCHER = "[ˆ<][a-zA-Z]{0,}[>$].{0,}";
	private final static String CLOSE_TAG_TEXT_MATCHER = ".{0,}[ˆ<][/][a-zA-Z]{0,}[>$]";
	private final static String GET_TAG_MATCHER = "[>][\\d\\D]{0,}[<][/][\\D]{0,}";
	
	public static void main(String[] args) throws IOException {
		File file =  new File("xmlFile.txt");
		
		
		FileReader fileReader = new FileReader(file);
		
		BufferedReader br = new BufferedReader(fileReader);
		
		String text;
			
		while((text = br.readLine()) != null) {
			String line = text.trim();
			
			if(hasOpenTag(line) && textHasValue(line) == false) {
				XMLNode xmlNode = new XMLNode(openTagValue(line), null, true, false, null, false);
				
				XMLNode xmlNodeLastElement = null;
				
				if(stackTag.empty() == false) {
					xmlNodeLastElement = stackTag.lastElement();
				}
				
				if(xmlNodeLastElement == null || xmlNodeLastElement.getParent() == null) {}
				
				if (xmlNodeLastElement != null && xmlNodeLastElement.getParent() == null 
						&& (xmlNodeLastElement.getTag().equals(xmlNode.getTag()) == false)) {
					xmlNode.setParent(xmlNodeLastElement);
				}

				if (xmlNodeLastElement != null && (xmlNodeLastElement.getTag().equals(xmlNode.getTag()) == true)) {
					xmlNode.setPartOfList(true);;
				}
				
				stackTag.add(xmlNode);
			}
			
			
			if(textHasValue(line)) {
				XMLNode xmlNode = new XMLNode(tagValueByTextHasValue(line), valueInsideTag(line), false, false, null, false);
				
				XMLNode xmlNodeLastElement = null;
				
				if(stackTag.empty() == false) {
					xmlNodeLastElement = stackTag.lastElement();
				}
				
				if(xmlNodeLastElement == null || xmlNodeLastElement.getParent() == null) {}
				
				if(xmlNodeLastElement != null && xmlNodeLastElement.getParent() == null 
						&& (xmlNodeLastElement.getTag().equals(xmlNode.getTag()) == false)) {
					xmlNode.setParent(xmlNodeLastElement);
				}
				
				if(xmlNodeLastElement != null && xmlNodeLastElement.getParent() == null 
						&& (xmlNodeLastElement.getTag().equals(xmlNode.getTag()) == true)) {
					xmlNode.setPartOfList(true);;
				}
				
				stackXML.add(xmlNode);
			}
		
		}
		
		br.close();

		String json = "";
		int index = 0;
		
		for (int i = 0; i < stackTag.size(); i++) {
			XMLNode xml = stackTag.get(i);
			if(xml.getParent() == null && xml.isPartOfList() == false) {
				json=json+xml.getTag()+":{";
			}
			
			if(xml.getParent() != null && (xml.getTag().equals(stackTag.get(i+1).getTag()))) {
				json=json+xml.getTag()+":[";
			}
			
			if(xml.getParent() == null && xml.isPartOfList()) {
				json=json+"{";
				for (index = 0; index < 6; index++) {
					XMLNode xmlObject = stackXML.get(index);
					json=json+"'"+xmlObject.getTag()+"'"+":"+"'"+xmlObject.getTextInside()+"'";
					if(index < 5){
						json=json+",";
					}
					
				}
				for (index = 0; index < 6; index++) {
					stackXML.remove(0);
				}
				json=json+"}";
			}
			
		}
		
		
		json=json+"}";
		
		System.out.println(json);
	
	}
	
	public static String valueInsideTag(String text) {
		return text.replaceAll(OPEN_TAG_MATCHER, "").replaceAll(CLOSE_TAG_MATCHER, "");
	}
	
	public static boolean textHasValue(String text) {
		return hasOpenTagWithValue(text) && hasCloseTagWithValue(text);
	}
	
	public static String tagValueByTextHasValue(String text) {
		return text.replaceAll(GET_TAG_MATCHER, "").replace("<", "");
	}
	
	public static String openTagValue(String text) {
		return text.replace("<", "").replace(">", "");
	}
	
	public static String closeTagValue(String text) {
		return text.replace("<\\/\\", "").replace(">\\", "");
	}
	
	public static boolean hasOpenTag(String text) {
		return text.matches(OPEN_TAG_MATCHER);
	}
	
	public static boolean hasCloseTag(String text) {
		return text.matches(CLOSE_TAG_MATCHER);
	}
	
	public static boolean hasOpenTagWithValue(String text) {
		return text.matches(OPEN_TAG_TEXT_MATCHER);
	}
	
	public static boolean hasCloseTagWithValue(String text) {
		return text.matches(CLOSE_TAG_TEXT_MATCHER);
	}

}
