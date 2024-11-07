import javax.swing.JFrame;

public class App extends JFrame {

    public App( ) {
        add(new MyFrame());
    }
    
    public static void main(String[] args) throws Exception {
        App MyApp = new App();
        MyApp.setSize(850,450);
        MyApp.setTitle("Escaping CHICK");
        MyApp.setVisible(true);
        MyApp.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        MyApp.setLocationRelativeTo(null);
    }
}
