package simplifiedRC4;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class SimplifiedRC4 extends Application {
	private Label label;
	private Button btn;
	private TextField textField;
	private String binary;
	private String stream = "";

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		primaryStage.setTitle("Simplified RC4");
		textField = new TextField();
		btn = new Button("Enter");
		label = new Label("Enter your key in the format a,b,c: ");

		// Scanner in = new Scanner(System.in);

		btn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				String key = textField.getText();
				int[] S = new int[8];
				int[] K = new int[8];
				for (int i = 0; i < 8; i++) // initialize the array
					S[i] = i;
				String[] keyParts = key.split(",");
				String key1 = keyParts[0];
				String key2 = keyParts[1];
				String key3 = keyParts[2];

				K[0] = Integer.parseInt(key1);
				K[1] = Integer.parseInt(key2);
				K[2] = Integer.parseInt(key3);

				for (int i = 3; i < 8; i++) {
					int mod = i % 3;
					K[i] = K[mod];
				}
				int j = 0;
				for (int i = 0; i < 8; i++) {
					j = (j + S[i] + K[i]) % 8;
					int temp = S[i];
					S[i] = S[j];
					S[j] = temp;
				}
				j = 0;
				for (int i = 0; i < keyParts.length; i++) {
					int l = (i + 1) % 8;
					j = (j + S[l]) % 8;
					int temp = S[l];
					S[l] = S[j];
					S[j] = temp;
					int k = (S[l] + S[j]) % 8;
					int keystream = S[k];
					binary = Integer.toBinaryString(keystream);
					while (binary.length() < 3) {
						binary = "0" + binary;
					}
					if (stream != null) {
						stream = stream.concat(binary);
					}
				}
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("Keystream");
				alert.setHeaderText(null);
				alert.setContentText("Keystream: " + stream);
				alert.showAndWait();
				textField.clear();
			}
		});

		BorderPane pane = new BorderPane();
		pane.setPadding(new Insets(40));
		VBox paneCenter = new VBox();
		paneCenter.setSpacing(10);
		pane.setCenter(paneCenter);
		paneCenter.getChildren().addAll(label, textField, btn);
		Scene scene = new Scene(pane, 400, 200);
		primaryStage.setScene(scene);
		primaryStage.show();
	}
}
