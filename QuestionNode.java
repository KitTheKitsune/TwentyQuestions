
public class QuestionNode<String> {
	private String data;
	private QuestionNode<String> left;
	private QuestionNode<String> right;
	
	public QuestionNode(String d, QuestionNode<String> l, QuestionNode<String> r) {
		this.data = d;
		this.left = l;
		this.right = r;
	}
	
	public QuestionNode(String d) {
		this.data = d;
		this.left = null;
		this.right = null;
	}
	
	public String getData() {
		return this.data;
	}
	
	public QuestionNode<String> getLeft(){
		return this.left;
	}
	
	public QuestionNode<String> getRight(){
		return this.right;
	}
	
	public void setData(String newData) {
		this.data = newData;
	}
	
	public void setLeft(QuestionNode<String> newLeft) {
		this.left = newLeft;
	}
	
	public void setRight(QuestionNode<String> newRight) {
		this.right = newRight;
	}
}
	
