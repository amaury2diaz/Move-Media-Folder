package moveIt;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.StageStyle;


public class FXLauncher extends Application{
	private Stage stage = new Stage();
	private Scene scene;
	private String appName = "iTunes.exe";
	private String oldMediaFolderPath = Commands.getOldMediaFolderPath();
	private boolean isRunning = Commands.isRunning(appName);
	public static void main(String[] args) {launch(args);}
	@Override
	public void start(Stage primaryStage) throws Exception{
		stage = primaryStage;
		primaryStage.setTitle("MITPCS v1.00");
		
		if(isRunning){
		    scene = nonDefaultFirstPrompt();
		}
		else{
			scene = firstPrompt();
		}
		
		scene.getStylesheets().add("/view/StyleSheet.css");
		primaryStage.getIcons().add(new Image("http://www.keenada.com/students/diaz0064/lab1/pcl-logo.png"));
		primaryStage.initStyle(StageStyle.UNIFIED);  
		primaryStage.setScene(scene);
		primaryStage.setMaxHeight(163);
		primaryStage.setMaxWidth(550);
		primaryStage.show();
	}
	
	public Scene nonDefaultFirstPrompt(){
		Label text1 = new Label("To continue iTunes Should be closed");
		Label text2 = new Label("Select Continue to close it, select Cancel to exit");
	    text1.setFont(new Font("Arial",16));
	    text1.setTextFill(Color.YELLOW);
	    text2.setFont(new Font("Arial",16));
	    text2.setTextFill(Color.YELLOW);
	    Button affirmative = new Button("Continue");
		affirmative.setDefaultButton(true);
		affirmative.setPrefWidth(100);
		affirmative.setOnAction(new EventHandler<ActionEvent>(){
			public void handle(ActionEvent t){
				Commands.closeApp(appName);
				scene= firstPrompt();
				scene.getStylesheets().add("/view/StyleSheet.css");
				stage.setScene(scene);
			}
		});
		affirmative.setOnMouseEntered(event->scene.setCursor(Cursor.HAND));
		affirmative.setOnMouseExited(event->scene.setCursor(Cursor.DEFAULT));
		affirmative.getStyleClass().add("default-button");
		Button negative = new Button("Cancel");
		negative.setPrefWidth(100);
		negative.setOnAction(event -> System.exit(0));
		negative.setOnMouseEntered(event->scene.setCursor(Cursor.HAND));
		negative.setOnMouseExited(event->scene.setCursor(Cursor.DEFAULT));
		
		HBox hBox = new HBox(affirmative, negative);
		hBox.setSpacing(75.0);
		HBox.setMargin(affirmative,new Insets(0,0,0,75));
		VBox vBox = new VBox(text1,text2,hBox);
		
		
		return new Scene(vBox);
		
	}
	
	public Scene firstPrompt(){
		Label text = new Label("Do you want to move your iTunes Media Folder To PCShared?");
	    text.setFont(new Font("Arial",16));
	    text.setTextFill(Color.YELLOW);
		Button affirmative = new Button("Yes");
		affirmative.setDefaultButton(true);
		affirmative.setPrefWidth(100);
		affirmative.setOnAction(new EventHandler<ActionEvent>(){
			public void handle(ActionEvent t){
				Commands.changeRegistry();
				scene = moveFiles();
				scene.getStylesheets().add("/view/StyleSheet.css");
				stage.setScene(scene);
			}
		});
	
			affirmative.setOnMouseEntered(event->scene.setCursor(Cursor.HAND));
			affirmative.setOnMouseExited(event->scene.setCursor(Cursor.DEFAULT));
			affirmative.getStyleClass().add("default-button");
			
		Button negative = new Button("No");
		negative.setPrefWidth(100);
		negative.setOnAction(event -> System.exit(0));
		negative.setOnMouseEntered(event->scene.setCursor(Cursor.HAND));
		negative.setOnMouseExited(event->scene.setCursor(Cursor.DEFAULT));
		HBox hBox = new HBox(affirmative, negative);
		hBox.setSpacing(75.0);
		HBox.setMargin(affirmative,new Insets(0,0,0,75));
		VBox vBox = new VBox(text,hBox);
		vBox.setSpacing(75.0);
		
		return new Scene(vBox);
		
	}
	
	public Scene moveFiles(){
		Label text = new Label("Do you want to move all the music from your previous Media Folder?");
		text.setFont(new Font("Arial",16));
	    text.setTextFill(Color.YELLOW);
		Button affirmative = new Button("Yes");
		affirmative.setDefaultButton(true);
		affirmative.setPrefWidth(100);
		affirmative.setOnAction(new EventHandler<ActionEvent>(){
			public void handle(ActionEvent t){
				Commands.moveIt(oldMediaFolderPath);
				 try {
			            Thread.sleep(5000);
			        }
			        catch(InterruptedException ex) {
			        }
				scene = complete();
				scene.getStylesheets().add("/view/StyleSheet.css");
				stage.setScene(scene);
				
				
			}
		});
		
		affirmative.setOnMouseEntered(event->scene.setCursor(Cursor.HAND));
		affirmative.setOnMouseExited(event->scene.setCursor(Cursor.DEFAULT));
		affirmative.getStyleClass().add("default-button");
		
		Button negative = new Button("No");
		negative.setPrefWidth(100);
		negative.setOnAction(event -> System.exit(0));
		negative.setOnMouseEntered(event->scene.setCursor(Cursor.HAND));
		negative.setOnMouseExited(event->scene.setCursor(Cursor.DEFAULT));
		HBox hBox = new HBox(affirmative, negative);
		hBox.setSpacing(75.0);
		HBox.setMargin(affirmative,new Insets(0,0,0,75));
		VBox vBox = new VBox(text,hBox);
		vBox.setSpacing(75.0);
		
		return new Scene(vBox);
	}
	
	public Scene complete(){
		Label text = new Label("Process Complete!!!!!");
		text.setFont(new Font("Arial",16));
	    text.setTextFill(Color.YELLOW);
	    Button exit = new Button("Exit");
	    exit.setPrefWidth(100);
	    exit.setDefaultButton(true);
	    exit.setOnAction(event -> System.exit(0));
	    exit.setOnMouseEntered(event->scene.setCursor(Cursor.HAND));
		exit.setOnMouseExited(event->scene.setCursor(Cursor.DEFAULT));
		exit.getStyleClass().add("default-button");
	    HBox hBox = new HBox(exit);
	    HBox.setMargin(exit, new Insets(0,0,0,100));
	    VBox vBox = new VBox(text,hBox);
	    vBox.setSpacing(75.0);
		
		return new Scene(vBox);
	}}