package TestView;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class testing{
    private double xOffset = 0;
    private double yOffset = 0;

    
    public testing(Stage primaryStage) {
        BorderPane root = createMainLayout(primaryStage);
        
        Scene scene = new Scene(root, 1050, 576);
        scene.setFill(Color.TRANSPARENT);
        
        primaryStage.initStyle(StageStyle.TRANSPARENT);
        primaryStage.setScene(scene);
        
        // Make window draggable
        makeDraggable(root, primaryStage);
        
        primaryStage.show();
    }

    private BorderPane createMainLayout(Stage primaryStage) {
        BorderPane mainLayout = new BorderPane();
        
        // Left Sidebar
        VBox sidebar = createSidebar(primaryStage);
        mainLayout.setLeft(sidebar);
        
        // Right Content Area
        StackPane contentArea = createContentArea();
        mainLayout.setCenter(contentArea);
        
        // Overall styling (Set background to white)
        mainLayout.setStyle("-fx-background-color: white;");
        mainLayout.setEffect(new DropShadow(10, Color.web("#1b1eeb")));
        
        return mainLayout;
    }

    private VBox createSidebar(Stage primaryStage) {
        VBox sidebar = new VBox(10);
        // Change sidebar background to white
        sidebar.setStyle("-fx-background-color: white;");
        sidebar.setAlignment(Pos.TOP_CENTER);
        sidebar.setPrefWidth(256);
        
        // Profile Image
        Image profileimg = new Image("file:resources/Seller_Image_Profile.png");
        ImageView profileImage = new ImageView(profileimg);
        profileImage.setFitHeight(73);
        profileImage.setFitWidth(67);
        
        Label profileName = new Label("Jimmy Fallon");
        profileName.setTextFill(Color.web("#000000")); // Black text for contrast
        
        // Sidebar Buttons
        String[][] buttonData = {
            {"Overview", "file:resources/Seller_Image_Profile.png"},
            {"Orders", "file:resources/Seller_Image_Profile.png"},
            {"Customers", "file:resources/Seller_Image_Profile.png"},
            {"Menus", "file:resources/Seller_Image_Profile.png"},
            {"Packages", "file:resources/Seller_Image_Profile.png"},
            {"Settings", "file:resources/Seller_Image_Profile.png"},
            {"Sign Out", "file:resources/Seller_Image_Profile.png"}
        };
        
        sidebar.getChildren().addAll(
            profileImage, 
            profileName
        );
        
        for (String[] buttonInfo : buttonData) {
            Button btn = createSidebarButton(buttonInfo[0], buttonInfo[1]);
            sidebar.getChildren().add(btn);
        }
        
        VBox.setMargin(profileImage, new Insets(50, 0, 10, 0));
        
        return sidebar;
    }

    private StackPane createContentArea() {
        StackPane contentArea = new StackPane();
        // Change content area background to white
        contentArea.setStyle("-fx-background-color: white;");
        
        // Overview Panel
        VBox overviewPanel = createOverviewPanel();
        contentArea.getChildren().add(overviewPanel);
        
        return contentArea;
    }

    private VBox createOverviewPanel() {
        VBox overviewPanel = new VBox(10);
        // Change the overview panel background to white
        overviewPanel.setStyle("-fx-background-color: white;");
        
        // Search TextField
        TextField searchField = new TextField();
        searchField.setPromptText("search");
        searchField.setPrefSize(183, 27);
        searchField.setStyle(
            "-fx-background-color: white;" +  // White background for text field
            "-fx-border-color: #B7C3D7;" +
            "-fx-border-radius: 2em;"
        );
        
        Label titleLabel = new Label("Restaurant Orders");
        titleLabel.setTextFill(Color.web("#000000")); // Black text
        
        // Statistics Boxes
        HBox statsBox = createStatsBox();
        
        // Table Header
        HBox tableHeader = createTableHeader();
        
        // Scrollable Items
        ScrollPane itemsScrollPane = createItemsScrollPane();
        
        overviewPanel.getChildren().addAll(
            searchField, 
            titleLabel, 
            statsBox, 
            tableHeader, 
            itemsScrollPane
        );
        
        return overviewPanel;
    }

    private HBox createStatsBox() {
        HBox statsBox = new HBox(50);
        statsBox.setAlignment(Pos.CENTER_LEFT);
        
        String[][] statsData = {
            {"22000", "Total Orders", "#2a73ff"},
            {"1500", "Total delivered", "#26bfbc"},
            {"1000", "Pending Orders", "#fa5f7e"},
            {"780", "Orders on Hold", "#de0000"}
        };
        
        for (String[] stat : statsData) {
            VBox statBox = new VBox(5);
            statBox.setAlignment(Pos.CENTER);
            
            Label valueLabel = new Label(stat[0]);
            valueLabel.setTextFill(Color.web(stat[2]));
            valueLabel.setFont(Font.font("System Bold", 26));
            
            Label titleLabel = new Label(stat[1]);
            titleLabel.setTextFill(Color.web("#000000")); // Black text
            
            statBox.getChildren().addAll(valueLabel, titleLabel);
            statsBox.getChildren().add(statBox);
        }
        
        return statsBox;
    }

    private HBox createTableHeader() {
        HBox header = new HBox(80);
        header.setAlignment(Pos.CENTER_LEFT);
        header.setStyle("-fx-background-color: white;");
        
        String[] headers = {"Company Name", "Airport", "Delivery Date", "Amount", "Status"};
        
        for (String headerText : headers) {
            Label label = new Label(headerText);
            label.setTextFill(Color.web("#000000")); // Black text
            header.getChildren().add(label);
        }
        
        return header;
    }

    private ScrollPane createItemsScrollPane() {
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setStyle("-fx-background-color: white;");
        
        VBox itemsContainer = new VBox(5);
        itemsContainer.setStyle("-fx-background-color: white;");
        
        // Create 10 sample items
        for (int i = 0; i < 10; i++) {
            HBox item = createItemRow();
            itemsContainer.getChildren().add(item);
        }
        
        scrollPane.setContent(itemsContainer);
        scrollPane.setPrefHeight(320);
        scrollPane.setPrefWidth(746);
        
        return scrollPane;
    }

    private HBox createItemRow() {
        HBox itemRow = new HBox(80);
        itemRow.setAlignment(Pos.CENTER_LEFT);
        itemRow.setPrefHeight(53);
        itemRow.setPrefWidth(695);
        itemRow.setStyle(
            "-fx-background-color: white;" + // White background for the item row
            "-fx-background-radius: 5;"
        );
        
        Image iconimage = new Image("file:resources/Seller_Image_Profile.png");
        ImageView icon = new ImageView(iconimage);
        icon.setFitHeight(31);
        icon.setFitWidth(25);
        
        Label[] labels = {
            new Label("KeepToo"),
            new Label("JFK"),
            new Label("10/11/2018"),
            new Label("$200")
        };
        
        for (Label label : labels) {
            label.setTextFill(Color.web("#000000")); // Black text
        }
        
        Button statusButton = new Button("active");
        statusButton.setStyle(
            "-fx-border-color: #2A73FF;" +
            "-fx-border-radius: 20;" +
            "-fx-background-color: transparent;"
        );
        
        itemRow.getChildren().addAll(icon);
        itemRow.getChildren().addAll(labels);
        itemRow.getChildren().add(statusButton);
        
        return itemRow;
    }


    private Button createSidebarButton(String text, String iconPath) {
        Button btn = new Button(text);
        btn.setPrefWidth(259);
        btn.setPrefHeight(42);
        
        Image iconimg = new Image(iconPath);
        ImageView icon = new ImageView(iconimg);
        icon.setFitHeight(23);
        icon.setFitWidth(27);
        
        btn.setGraphic(icon);
        btn.setGraphicTextGap(22);
        
        // Styling
        btn.setStyle(
            "-fx-background-color: #05071F;" +
            "-fx-text-fill: #e7e5e5;"
        );
        
        btn.setOnMouseEntered(e -> btn.setStyle(
            "-fx-background-color: #10165F;" +
            "-fx-text-fill: #e7e5e5;"
        ));
        
        btn.setOnMouseExited(e -> btn.setStyle(
            "-fx-background-color: #05071F;" +
            "-fx-text-fill: #e7e5e5;"
        ));
        
        return btn;
    }
    
    private void makeDraggable(BorderPane root, Stage stage) {
        root.setOnMousePressed(event -> {
            xOffset = event.getSceneX();
            yOffset = event.getSceneY();
        });
        
        root.setOnMouseDragged(event -> {
            stage.setX(event.getScreenX() - xOffset);
            stage.setY(event.getScreenY() - yOffset);
        });
    }
}
