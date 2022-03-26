package Lab3;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


public class Interface extends Application{
        TextField f1 =new TextField();
        TextField f2 =new TextField();
        TextField f3 =new TextField();
        TextField f4 =new TextField();
        TextField f5 =new TextField();
        RandomAccessFile file;
        Button B1 = new Button("Add");
        Button B2 = new Button("First");
        Button B3 = new Button("Next");
        Button B4 = new Button("Previous");
        Button B5= new Button("Last");
        Button B6 = new Button("Update "); 
        long position =  0 ;
        Label Name = new Label("Name");
        Label Street = new Label ("Sreet");
        Label City = new Label ("City");
        Label State = new Label ("State");
        Label Zip = new Label ("Zip");

    @Override
    public void start (Stage primaryStage) throws FileNotFoundException, IOException , ClassNotFoundException{     
        f1.setPrefColumnCount(23);
        f2.setPrefColumnCount(23);
        f3.setPrefColumnCount(8);
        f4.setPrefColumnCount(2);
        f5.setPrefColumnCount(4);     
        Button B1 = new Button("Add");
        HBox paneName =  new HBox(10);
        paneName.getChildren().addAll(Name,f1);
        paneName.setAlignment(Pos.CENTER);       
        paneName.setPadding(new Insets(4)) ;      
        HBox paneStreet =  new HBox(10);
        paneStreet.getChildren().addAll(Street,f2);
        paneStreet.setAlignment(Pos.CENTER);
        paneStreet.setPadding(new Insets(4)) ;       
        HBox paneCity =  new HBox(3);
        paneCity.getChildren().addAll(City,f3,State,f4,Zip,f5);
        paneCity.setAlignment(Pos.CENTER);      
        paneCity.setPadding(new Insets(4)) ;      
        HBox paneBoutons = new HBox(8);
        paneBoutons.getChildren().addAll(B1,B2,B3,B4,B5,B6);
        paneBoutons.setAlignment(Pos.CENTER);
        paneBoutons.setPadding(new Insets(4)) ;
        VBox box = new VBox();
         box.getChildren().addAll(paneName,paneStreet,paneCity,paneBoutons);
        Scene scene = new Scene(box);
        primaryStage.setTitle("Address Book");
        primaryStage.setScene(scene);
        primaryStage.show();
        
        
        B1.setOnAction(e->{try {
            add();
            } 
            catch (IOException ex) {
                Logger.getLogger(Interface.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        B2.setOnAction(e->{try {
            first();
            } catch (IOException ex) {
                Logger.getLogger(Interface.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        B3.setOnAction(e->{try {
            next();
            } catch (IOException ex) {
                Logger.getLogger(Interface.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        B4.setOnAction(e->{try {
            previous();
            } catch (IOException ex) {
                Logger.getLogger(Interface.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        B5.setOnAction(e->{try {
            last();
            } catch (IOException ex) {
                Logger.getLogger(Interface.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        B6.setOnAction(e->{try {
            update();
            } catch (IOException ex) {
                Logger.getLogger(Interface.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
       
       
        
   
    }
    private void write(RandomAccessFile inout) throws IOException{
        inout.write(fixeBytes(f1.getText().getBytes(),32));
        inout.write(fixeBytes(f2.getText().getBytes(),32));
        inout.write(fixeBytes(f3.getText().getBytes(),20));
        inout.write(fixeBytes(f4.getText().getBytes(),2));
        inout.write(fixeBytes(f5.getText().getBytes(),5));
        System.out.println("Saved");
    }
 
    private void read(RandomAccessFile inout) throws IOException{
        int pos;
        byte[] name = new byte[32];
        pos=inout.read(name);
        this.f1.setText(new String(name));
        byte[] street = new byte[32];
        pos+=inout.read(street);
        this.f2.setText(new String(street));
        byte[] city = new byte[20];
        pos+=inout.read(city);
        this.f3.setText(new String(city));
        byte[] state = new byte[2];
        pos+=inout.read(state);
        this.f4.setText(new String(state));
        byte[] zip = new byte[5];
        pos+=inout.read(zip);
        this.f5.setText(new String(zip));
    }
    private void add() throws FileNotFoundException, IOException{
     file= new RandomAccessFile("test.dat", "rw");
     file.seek(file.length());
     write(file);
   
    }
    private void first() throws FileNotFoundException, IOException{
         file= new RandomAccessFile("test.dat", "rw");
         file.seek(0);
         read(file);
         this.position= 0 ;
    }
    private void last() throws FileNotFoundException, IOException{
         file= new RandomAccessFile("test.dat", "rw");
         if(file.length() != 0)
         {
             
             file.seek(file.length()-91);
             read(file);
             this.position= file.length()-91 ;
         }
         
         
    }
    private void next() throws FileNotFoundException, IOException{
         file= new RandomAccessFile("test.dat", "rw");
         if(this.position== file.length()-91)
         {
             this.position= 0 ;
             file.seek(this.position);
             read(file);
             
         }
         else
         {
             this.position+=91 ;
             file.seek(this.position) ;
             read(file);
         }
    }
    private void previous() throws FileNotFoundException, IOException{
         file= new RandomAccessFile("test.dat", "rw");
         if(this.position== 0)
         {
             this.position= file.length()-91 ;
             file.seek(this.position);
             read(file);
             
         }
         else
         {
             this.position-=91 ;
             file.seek(this.position) ;
             read(file);
         }
    }
    
    private byte[] fixeBytes(byte[] x, int n) {
            byte[] b = new byte[n];
            for (int i = 0; i < x.length; i++) {
                    b[i] = x[i];
            }
            return b;
	}
    private void update() throws FileNotFoundException, IOException{
     file= new RandomAccessFile("test.dat", "rw");
     file.seek(this.position);
     write(file);
   
    }
    
 
    public static void main(String[] args) {
        Application.launch(args);
    }
    }
