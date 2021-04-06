import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Stack;

public class Start {
	
	public static Stack<XMLNode> stackOpen =  new Stack<XMLNode>();
	public static Stack<XMLNode> stackClose =  new Stack<XMLNode>();
	public static Stack<XMLNode> stackValue =  new Stack<XMLNode>();
	public static Stack<XMLNode> stackXML = new Stack<XMLNode>();
	
	private final static String OPEN_TAG_MATCHER = "[ˆ<][a-zA-Z]{0,}[>$]";
	private final static String CLOSE_TAG_MATCHER = "[ˆ<][/][a-zA-Z]{0,}[>$]";
	private final static String OPEN_TAG_TEXT_MATCHER = "[ˆ<][a-zA-Z]{0,}[>$].{0,}";
	private final static String CLOSE_TAG_TEXT_MATCHER = ".{0,}[ˆ<][/][a-zA-Z]{0,}[>$]";
	
	public static void main(String[] args) throws IOException {
		File file =  new File("xmlFile.txt");
		
		FileReader fileReader = new FileReader(file);
		
		BufferedReader br = new BufferedReader(fileReader);
		
		String text;
		String json = "";
			
		while((text = br.readLine()) != null) {
			String line = text.trim();
			if(hasOpenTag(line) && textHasValue(line) == false) {
				XMLNode xmlNode = new XMLNode(openTagValue(line), null, true, false, null, false);
				
				XMLNode xmlNodeLastElement = null;
				
				if(stackXML.empty() == false) {
					xmlNodeLastElement = stackXML.lastElement();
				}
				
				if(xmlNodeLastElement == null || xmlNodeLastElement.getParent() == null) {
					
				}
				
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
			
			if(hasCloseTag(line) && textHasValue(line) == false) {
//				XMLNode xmlNode = new XMLNode(openTagValue(line), null, false, true, null, false);
//				stackClose.add(xmlNode);
				//XMLNode xmlNode = new XMLNode(line, text, false, false, null);
				//System.out.println("CLOSE: "+line);
				//stackOpen.add(line);
			}
			
			if(textHasValue(line)) {
				XMLNode xmlNode = new XMLNode(openTagValue(line), valueInsideTag(line), false, false, null, false);
				
				XMLNode xmlNodeLastElement = null;
				
				if(stackXML.empty() == false) {
					xmlNodeLastElement = stackXML.lastElement();
				}
				
				if(xmlNodeLastElement == null || xmlNodeLastElement.getParent() == null) {
					
				}
				
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
		stackXML.stream().forEach(System.out::print);
	
	}
	
	public static String valueInsideTag(String text) {
		return text.replaceAll(OPEN_TAG_MATCHER, "").replaceAll(CLOSE_TAG_MATCHER, "");
	}
	
	public static boolean textHasValue(String text) {
		return hasOpenTagWithValue(text) && hasCloseTagWithValue(text);
	}
	
	public static String openTagValueByTextHasValue(String text) {
		return text.replaceAll(OPEN_TAG_MATCHER, "");
	}
	
	public static String openTagValue(String text) {
		return text.replace("<\\", "").replace(">\\", "");
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
