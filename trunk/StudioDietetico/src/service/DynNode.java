package service;

import java.util.ArrayList;

import org.eclipse.swt.widgets.TreeItem;
import org.hibernate.Criteria;

public class DynNode {
	
	public String idMap;
	private TreeItem treeNode;
	private String pathClass;
	private ArrayList<String> filters;
	private boolean isIstantiated = false;

	public DynNode(TreeItem node) {
		treeNode = node;
		if (treeNode.getParentItem()!=null) {
			idMap = treeNode.getParentItem().getText().concat("_").concat(treeNode.getText());
		} else {
			idMap = "radiceAlbero".concat("_").concat(treeNode.getText());
		}		
	}	
	
	public TreeItem getTreeNode() {
		return treeNode;
	}

	public String getPathClass() {
		return pathClass;
	}

	public void setPathClass(String pathClass) {
		this.pathClass = pathClass;
	}

	public ArrayList<String> getFilters() {
		return filters;
	}
	
	public void addFilter(String filter){
		filters.add(filter);
	}

	public String getIdMap() {
		return idMap;
	}

	public boolean isIstantiated() {
		return isIstantiated;
	}

	public void setIstantiated(boolean isIstantiated) {
		this.isIstantiated = isIstantiated;
	}
	
}
