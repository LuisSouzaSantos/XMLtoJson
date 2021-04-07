
public class XMLNode {
	
	private String tag;
	private String textInside;
	private boolean openTag;
	private boolean closeTag;
	private XMLNode parent;
	private boolean isPartOfList;
	
	
	
	
	public XMLNode(String tag, String textInside, boolean openTag, boolean closeTag, XMLNode parent,
			boolean isPartOfList) {
		this.tag = tag;
		this.textInside = textInside;
		this.openTag = openTag;
		this.closeTag = closeTag;
		this.parent = parent;
		this.isPartOfList = isPartOfList;
	}

	public String getTag() {
		return tag;
	}
	
	public void setTag(String tag) {
		this.tag = tag;
	}
	
	public String getTextInside() {
		return textInside;
	}
	
	public void setTextInside(String textInside) {
		this.textInside = textInside;
	}
	
	public boolean isOpenTag() {
		return openTag;
	}
	
	public void setOpenTag(boolean openTag) {
		this.openTag = openTag;
	}
	
	public boolean isCloseTag() {
		return closeTag;
	}
	
	public void setCloseTag(boolean closeTag) {
		this.closeTag = closeTag;
	}
	
	public XMLNode getParent() {
		return parent;
	}
	
	public void setParent(XMLNode parent) {
		this.parent = parent;
	}

	public boolean isPartOfList() {
		return isPartOfList;
	}

	public void setPartOfList(boolean isPartOfList) {
		this.isPartOfList = isPartOfList;
	}

	@Override
	public String toString() {
		String string = "XMLNode [tag=" + tag + ", textInside=" + textInside + ", openTag=" + openTag + ", closeTag=" + closeTag +", PartOfList="+isPartOfList;
	
		if(parent != null) {
			string = string + ", parent=" + parent.getTag() + "]";
		}
		
		return string;
		
	}
	
	
	

}
