
package View;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

public class popupView {
    
    private static popupView instance;
    private popupView() {}
    
    public static synchronized popupView getInstance() {
        if (instance == null) {
            instance = new popupView();
        }
        return instance;
    }


    public void showSuccessPopup(String title, String message) {  
        Platform.runLater(() -> {  
            Stage popupStage = createStyledStage(title);  
            
            // Success icon (you can replace with a custom SVG or icon)  
            Label iconLabel = createIconLabel("✓", Color.GREEN);  
            
            Label titleLabel = new Label(title);  
            titleLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");  
            
            Label messageLabel = new Label(message);  
            messageLabel.setStyle("-fx-font-size: 14px;");  
            
            Button closeButton = createStyledButton("Close", Color.GREEN);  
            closeButton.setOnAction(e -> popupStage.close());  
            
            VBox layout = createPopupLayout(iconLabel, titleLabel, messageLabel, closeButton);  
            
            Scene scene = createPopupScene(layout);  
            popupStage.setScene(scene);
            applyEntranceAnimation(popupStage, layout);
            closeButton.setOnAction(e -> {  
                applyExitAnimation(popupStage, layout);  
            });
            popupStage.show();  
        });  
    }  

   
    public void showErrorPopup(String title, String message) {  
        Platform.runLater(() -> {  
            Stage popupStage = createStyledStage(title);  
            
            // Error icon  
            Label iconLabel = createIconLabel("✗", Color.RED);  
            
            Label titleLabel = new Label(title);  
            titleLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: red;");  
            
            Label messageLabel = new Label(message);  
            messageLabel.setStyle("-fx-font-size: 14px; -fx-text-fill: #333;");  
            
            Button closeButton = createStyledButton("Dismiss", Color.RED);  
            closeButton.setOnAction(e -> popupStage.close());  
            
            VBox layout = createPopupLayout(iconLabel, titleLabel, messageLabel, closeButton);  
            
            Scene scene = createPopupScene(layout);  
            popupStage.setScene(scene);
            
            applyEntranceAnimation(popupStage, layout);
            closeButton.setOnAction(e -> {  
                applyExitAnimation(popupStage, layout);  
            }); 
            popupStage.show();  
        });  
    }  

     
    public boolean showConfirmationPopup(String title, String message) {  
        Stage popupStage = createStyledStage(title);  
        
        // Confirmation icon  
        Label iconLabel = createIconLabel("?", Color.ORANGE);  
        
        Label titleLabel = new Label(title);  
        titleLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");  
        
        Label messageLabel = new Label(message);  
        messageLabel.setStyle("-fx-font-size: 14px;");  
        
        // Holder for user's choice  
        final boolean[] choice = {false};  
        
        
        Button yesButton = createStyledButton("Yes", Color.GREEN);  
        yesButton.setOnAction(e -> {  
            choice[0] = true;  
            popupStage.close();  
        });  
        
        Button noButton = createStyledButton("No", Color.RED);  
        noButton.setOnAction(e -> {  
            choice[0] = false;  
            popupStage.close();  
        });  
        
        HBox buttonBox = new HBox(15, yesButton, noButton);  
        buttonBox.setAlignment(Pos.CENTER);  
        
        VBox layout = createPopupLayout(iconLabel, titleLabel, messageLabel, buttonBox);  
        
        Scene scene = createPopupScene(layout);  
        popupStage.setScene(scene);
        applyEntranceAnimation(popupStage, layout);
        
        yesButton.setOnAction(e -> {  
            choice[0] = true;  
            applyExitAnimation(popupStage, layout);  
        }); 
        noButton.setOnAction(e -> {  
            choice[0] = false;  
            applyExitAnimation(popupStage, layout);  
        });
        popupStage.showAndWait();  
        
        return choice[0];  
    }  

    
    public String showInputPopup(String title, String message) {  
        Stage popupStage = createStyledStage(title);  
        
        // Input icon  
        Label iconLabel = createIconLabel("📝", Color.BLUE);  
        
        Label titleLabel = new Label(title);  
        titleLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");  
        
        Label messageLabel = new Label(message);  
        messageLabel.setStyle("-fx-font-size: 14px;");  
        
        TextField inputField = new TextField();  
        inputField.setStyle(  
            "-fx-background-radius: 5px;" +  
            "-fx-border-radius: 5px;" +  
            "-fx-border-color: #3498db;" +  
            "-fx-padding: 8px;"  
        );  
        inputField.setPromptText("Enter your input");  
        
        // Holder for user's input  
        final String[] input = {null};  
        
       
        Button submitButton = createStyledButton("Submit", Color.BLUE);  
        submitButton.setOnAction(e -> {  
            input[0] = inputField.getText();  
            popupStage.close();  
        });  
        
        Button cancelButton = createStyledButton("Cancel", Color.GRAY);  
        cancelButton.setOnAction(e -> popupStage.close());  
        
        HBox buttonBox = new HBox(15, submitButton, cancelButton);  
        buttonBox.setAlignment(Pos.CENTER);  
        
        VBox layout = createPopupLayout(iconLabel, titleLabel, messageLabel, inputField, buttonBox);  
        
        Scene scene = createPopupScene(layout);  
        popupStage.setScene(scene);  
        popupStage.showAndWait();  
        
        return input[0];  
    }  

    // Helper method to create a styled stage  
    private Stage createStyledStage(String title) {  
        Stage popupStage = new Stage();  
        popupStage.initStyle(StageStyle.UNDECORATED);  
        popupStage.initModality(Modality.APPLICATION_MODAL);  
        popupStage.setTitle(title);  
        popupStage.setAlwaysOnTop(true);  
        popupStage.centerOnScreen();  
        
        return popupStage;  
    }  

    // Helper method to create an icon label  
    private Label createIconLabel(String text, Color color) {  
        Label iconLabel = new Label(text);  
        iconLabel.setStyle(  
            "-fx-font-size: 36px;" +  
            "-fx-text-fill: " + toRGBCode(color) + ";" +  
            "-fx-background-color: " + toRGBCode(color.deriveColor(0, 0.2, 1, 0.2)) + ";" +  
            "-fx-background-radius: 50%;" +  
            "-fx-min-width: 80px;" +  
            "-fx-min-height: 80px;" +  
            "-fx-alignment: center;"  
        );  
        return iconLabel;  
    }  

    // Helper method to create a styled button  
    private Button createStyledButton(String text, Color color) {  
        Button button = new Button(text);  
        button.setStyle(  
            "-fx-background-color: " + toRGBCode(color) + ";" +  
            "-fx-text-fill: white;" +  
            "-fx-background-radius: 5px;" +  
            "-fx-padding: 8px 15px;" +  
            "-fx-font-weight: bold;"  
        );  
        
        // Hover effects  
        button.setOnMouseEntered(e -> button.setStyle(  
            "-fx-background-color: " + toRGBCode(color.darker()) + ";" +  
            "-fx-text-fill: white;" +  
            "-fx-background-radius: 5px;" +  
            "-fx-padding: 8px 15px;" +  
            "-fx-font-weight: bold;"  
        ));  
        
        button.setOnMouseExited(e -> button.setStyle(  
            "-fx-background-color: " + toRGBCode(color) + ";" +  
            "-fx-text-fill: white;" +  
            "-fx-background-radius: 5px;" +  
            "-fx-padding: 8px 15px;" +  
            "-fx-font-weight: bold;"  
        ));  
        
        return button;  
    }  

    // Helper method to create popup layout  
    private VBox createPopupLayout(javafx.scene.Node... nodes) {  
        VBox layout = new VBox(15);  
        layout.setAlignment(Pos.CENTER);  
        layout.setPadding(new Insets(20));  
        layout.setStyle(  
            "-fx-background-color: white;" +  
            "-fx-background-radius: 10px;" +  
            "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.15), 10, 0, 0, 5);"  
        );  
        layout.getChildren().addAll(nodes);  
        return layout;  
    }  

    // Helper method to create popup scene  
    private Scene createPopupScene(VBox layout) {  
        StackPane root = new StackPane(layout);  
        root.setStyle("-fx-background-color: transparent;");  
        
        Scene scene = new Scene(root);  
        scene.setFill(Color.TRANSPARENT);  
        
        return scene;  
    }
    
    private void applyEntranceAnimation(Stage popupStage, VBox layout) {  
        // Faster, more aggressive entrance  
        layout.setScaleX(0.5);  
        layout.setScaleY(0.5);  
        layout.setOpacity(0);  

        Timeline entranceTimeline = new Timeline(  
            new KeyFrame(Duration.ZERO,   
                new KeyValue(layout.scaleXProperty(), 0.5),  
                new KeyValue(layout.scaleYProperty(), 0.5),  
                new KeyValue(layout.opacityProperty(), 0)  
            ),  
            new KeyFrame(Duration.millis(200),   
                new KeyValue(layout.scaleXProperty(), 1.1),  
                new KeyValue(layout.scaleYProperty(), 1.1),  
                new KeyValue(layout.opacityProperty(), 0.9)  
            ),  
            new KeyFrame(Duration.millis(250),   
                new KeyValue(layout.scaleXProperty(), 1),  
                new KeyValue(layout.scaleYProperty(), 1),  
                new KeyValue(layout.opacityProperty(), 1)  
            )  
        );  
        entranceTimeline.play();  
    }  

    private void applyExitAnimation(Stage popupStage, VBox layout) {  
        Timeline exitTimeline = new Timeline(  
            new KeyFrame(Duration.ZERO,   
                new KeyValue(layout.scaleXProperty(), 1),  
                new KeyValue(layout.scaleYProperty(), 1),  
                new KeyValue(layout.opacityProperty(), 1)  
            ),  
            new KeyFrame(Duration.millis(150),   
                new KeyValue(layout.scaleXProperty(), 0.6),  
                new KeyValue(layout.scaleYProperty(), 0.6),  
                new KeyValue(layout.opacityProperty(), 0.2)  
            ),  
            new KeyFrame(Duration.millis(200),   
                new KeyValue(layout.scaleXProperty(), 0.3),  
                new KeyValue(layout.scaleYProperty(), 0.3),  
                new KeyValue(layout.opacityProperty(), 0)  
            )  
        );  
        
        exitTimeline.setOnFinished(e -> popupStage.close());  
        exitTimeline.play();  
    }  

    // Utility method to convert Color to CSS RGB  
    private String toRGBCode(Color color) {  
        return String.format("#%02X%02X%02X",   
            (int)(color.getRed() * 255),  
            (int)(color.getGreen() * 255),  
            (int)(color.getBlue() * 255)  
        );  
    }  
}

