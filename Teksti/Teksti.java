

public class Teksti {
	

	private static final long serialVersionUID = 1L;

	public static void main(String[] args) {
		
		//Let's create a model and an user interface and get started
		Model m = new Model();
		UserInterface1 u = new UserInterface1(m);
		m.setUi(u);
		u.setVisible(true);
		
	}
	
}
