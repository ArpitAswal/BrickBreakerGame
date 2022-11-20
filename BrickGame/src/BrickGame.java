import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class BrickGame extends JFrame {

    public static void main(String[] args) {
        JFrame frame = new JFrame("BrickBreakerGame");// create a frame window and it title is BrickBreakerGame
        JPanel panel = new JPanel();//The JPanel is a simplest container class. It provides space in which an application can attach any other component. 
        panel.setLayout(new FlowLayout());//creates a flow layout with centered alignment and a default 5 unit horizontal and vertical gap.
        JLabel label = new JLabel();//create a label for displaying a text to the user and the object of JLabel class is a component for placing text in a container.
        label.setIcon(new ImageIcon("images.jfif")); //Sets the image to be displayed as an icon
        label.setBounds(0,0,500, 300); //Sets the location of the image on label
        JButton button=new JButton("PlayGame");//create a button so when we click it work which we define(function) on button and shown text on button
        button.setBounds(100,185,100,30);//set the bounding of button(x-coord,y-coord,width,height)
        button.addActionListener((ActionEvent e) -> {
           
            JFrame obj=new JFrame("Game On");//new frame create where we playing game
            StartGame game = new StartGame();//this is object of panel 
            obj.setBounds(0, 0, 1365, 700);
            obj.setResizable(false);//size can't be change
            obj.setVisible(true);//new frame visibility
            obj.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//Terminate program on close button
            obj.add(game);//panel add on frame
          
        });
      panel.add(label); //label add on panel
        frame.add(button);//button add on frame
        frame.add(panel);
        frame.setLocationRelativeTo(null);//By default, a JFrame can be displayed at the top-left position of a screen. We can display the center position of JFrame using the setLocationRelativeTo() method of Window class.
        frame.setBounds(510, 200, 315, 270); //Sets the position of the frame
        frame.setResizable(false);//size can't be change
        frame.setVisible(true); // Exhibit the frame(it hidden automatticaly to visible we have to set its visibility true)
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //Terminate program on close button

    }

}
/*We pass the “download.jfif” image location to the ImageIcon() method, where the image gets converted to a byte array. 
The array now gets converted to an Icon. Now, we add the Image Icon to our JFrame using the setIcon() method. We can also set 
the location and dimensions of the image using the setBounds() method and Dimension class respectively. Therefore, the image 
is now displayed on the JFrame.*/

/*The LayoutManagers are used to arrange components in a particular manner. The Java LayoutManagers facilitates us to control 
the positioning and size of the components in GUI forms. LayoutManager is an interface that is implemented by all the classes 
of layout managers.  */