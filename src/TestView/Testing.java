package TestView;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class Testing extends StackPane {

    // UI Components
    private Button closeButton;
    private Button minimizeButton;
    private Label usernameLabel;
    private Button homeButton;
    private Button addEmployeeButton;
    private Button salaryButton;
    private Button logoutButton;
    private Label homeTotalEmployees;
    private Label homeTotalPresents;
    private Label homeTotalInactiveEm;

    // Constructor to build the UI
    public Testing(Stage primaryStage) {
        // Set preferred size
        this.setPrefSize(1100, 600);

        // Main AnchorPane
        AnchorPane mainForm = new AnchorPane();
        mainForm.setPrefSize(200, 200);

        // BorderPane
        BorderPane borderPane = new BorderPane();
        borderPane.setPrefSize(1100, 600);
        AnchorPane.setTopAnchor(borderPane, 0.0);
        AnchorPane.setBottomAnchor(borderPane, 0.0);
        AnchorPane.setLeftAnchor(borderPane, 0.0);
        AnchorPane.setRightAnchor(borderPane, 0.0);

        // Top Region
        AnchorPane topAnchorPane = createTopRegion(primaryStage);
        borderPane.setTop(topAnchorPane);

        // Left Navigation
        AnchorPane leftAnchorPane = createLeftNavigation();
        borderPane.setLeft(leftAnchorPane);

        // Center Content
        AnchorPane centerAnchorPane = createCenterContent();
        borderPane.setCenter(centerAnchorPane);

        // Add BorderPane to Main Form
        mainForm.getChildren().add(borderPane);

        // Add Main Form to StackPane
        this.getChildren().add(mainForm);
    }

    // Method to create the Top Region
    private AnchorPane createTopRegion(Stage primaryStage) {
        AnchorPane topAnchorPane = new AnchorPane();
        topAnchorPane.setPrefHeight(45.0);
        topAnchorPane.getStyleClass().add("semi-top-form");
        

        AnchorPane innerTopAnchorPane = new AnchorPane();
        innerTopAnchorPane.setPrefSize(1100, 35.0);
        innerTopAnchorPane.getStyleClass().add("top-form");
        
        AnchorPane.setTopAnchor(innerTopAnchorPane, 0.0);
        AnchorPane.setBottomAnchor(innerTopAnchorPane, 8.0);
        AnchorPane.setLeftAnchor(innerTopAnchorPane, 0.0);
        AnchorPane.setRightAnchor(innerTopAnchorPane, 0.0);

        // Icon Placeholder (Using Unicode character)
        Label usersIcon = new Label("\uD83D\uDC65"); // 👥
        usersIcon.setLayoutX(14.0);
        usersIcon.setLayoutY(24.0);
        usersIcon.setFont(Font.font("Arial", 24));

        // Title Label
        Label titleLabel = new Label("Employee Management System");
        titleLabel.setLayoutX(41.0);
        titleLabel.setLayoutY(10.0);
        titleLabel.setFont(Font.font("Tahoma", 14));

        // Close Button
        closeButton = new Button("X");
        closeButton.setLayoutX(1010.0);
        closeButton.setLayoutY(2.0);
        closeButton.setPrefSize(60.0, 24.0);
        closeButton.getStyleClass().add("close");
        
        closeButton.setOnAction(e -> Platform.exit());

        // Minimize Button
        minimizeButton = new Button("-");
        minimizeButton.setLayoutX(993.0);
        minimizeButton.setLayoutY(2.0);
        minimizeButton.setPrefSize(45.0, 24.0);
        minimizeButton.getStyleClass().add("minimize");
        
        minimizeButton.setOnAction(e -> primaryStage.setIconified(true));

        // Add components to innerTopAnchorPane
        innerTopAnchorPane.getChildren().addAll(usersIcon, titleLabel, closeButton, minimizeButton);

        // Add innerTopAnchorPane to topAnchorPane
        topAnchorPane.getChildren().add(innerTopAnchorPane);

        return topAnchorPane;
    }

    // Method to create the Left Navigation
    private AnchorPane createLeftNavigation() {
        AnchorPane leftAnchorPane = new AnchorPane();
        leftAnchorPane.setPrefSize(211, 555);

        AnchorPane navAnchorPane = new AnchorPane();
        navAnchorPane.setPrefSize(214, 555);
        navAnchorPane.getStyleClass().add("nav-form");
        
        AnchorPane.setTopAnchor(navAnchorPane, 0.0);
        AnchorPane.setBottomAnchor(navAnchorPane, 0.0);
        AnchorPane.setLeftAnchor(navAnchorPane, 0.0);
        AnchorPane.setRightAnchor(navAnchorPane, 0.0);

        // User Icon Placeholder
        Label userIcon = new Label("\uD83D\uDC64"); // 👤
        userIcon.setTextFill(Color.WHITE);
        userIcon.setLayoutX(73.0);
        userIcon.setLayoutY(105.0);
        userIcon.setFont(Font.font("Arial", 48));

        // Welcome Labels
        Label welcomeLabel = new Label("Welcome,");
        welcomeLabel.setLayoutX(69.0);
        welcomeLabel.setLayoutY(117.0);
        welcomeLabel.setTextFill(Color.WHITE);
        welcomeLabel.setFont(Font.font("Tahoma", 17));

        usernameLabel = new Label("MarcoMan");
        usernameLabel.setLayoutX(9.0);
        usernameLabel.setLayoutY(138.0);
        usernameLabel.setPrefSize(194.0, 23.0);
        usernameLabel.setTextFill(Color.WHITE);
        usernameLabel.setAlignment(Pos.CENTER);
        usernameLabel.setFont(Font.font("Arial Bold", 20));

        // Line Separator
        Line line = new Line();
        line.setStartX(-100.0);
        line.setEndX(69.0);
        line.setLayoutX(121.0);
        line.setLayoutY(178.0);
        line.setStroke(Color.WHITE);

        // Navigation Buttons
        homeButton = createNavButton("Home", "\u2302"); // 🏠 (Alternative: Using Unicode)
        addEmployeeButton = createNavButton("Add Employee", "\u2795"); // ➕
        salaryButton = createNavButton("Employee Salary", "\uD83D\uDCB0"); // 💰

        // Logout Button
        logoutButton = new Button("\uD83D\uDD19 Logout"); // 🔙 Logout
        logoutButton.setLayoutX(16.0);
        logoutButton.setLayoutY(509.0);
        logoutButton.setPrefSize(180.0, 35.0);
        logoutButton.getStyleClass().addAll("logout", "shadow");
        
        logoutButton.setOnAction(e -> logout());

        // Add components to navAnchorPane
        navAnchorPane.getChildren().addAll(userIcon, welcomeLabel, usernameLabel, line, homeButton, addEmployeeButton, salaryButton, logoutButton);

        // Add navAnchorPane to leftAnchorPane
        leftAnchorPane.getChildren().add(navAnchorPane);

        return leftAnchorPane;
    }

    // Helper method to create navigation buttons
    private Button createNavButton(String text, String icon) {
        Button button = new Button(text);
        button.setPrefSize(180.0, 35.0);
        button.getStyleClass().add("nav-btn");
        
        button.setOnAction(e -> switchForm(e));

        // Combining icon and text
        button.setText(icon + "  " + text);
        button.setAlignment(Pos.CENTER_LEFT);
        button.setFont(Font.font("Arial", 14));

        // Positioning buttons based on text
        switch (text) {
            case "Home":
                button.setLayoutX(16.0);
                button.setLayoutY(207.0);
                break;
            case "Add Employee":
                button.setLayoutX(16.0);
                button.setLayoutY(242.0);
                break;
            case "Employee Salary":
                button.setLayoutX(16.0);
                button.setLayoutY(278.0);
                break;
            default:
                break;
        }

        return button;
    }

    // Method to create the Center Content
    private AnchorPane createCenterContent() {
        AnchorPane centerAnchorPane = new AnchorPane();
        centerAnchorPane.setPrefSize(200, 200);

        // Home Form
        AnchorPane homeForm = createHomeForm();
        // Add Employee Form
        AnchorPane addEmployeeForm = createAddEmployeeForm();
        // Salary Form
        AnchorPane salaryForm = createSalaryForm();

        centerAnchorPane.getChildren().addAll(homeForm, addEmployeeForm, salaryForm);

        return centerAnchorPane;
    }

    // Method to create the Home Form
    private AnchorPane createHomeForm() {
        AnchorPane homeForm = new AnchorPane();
        homeForm.setLayoutX(63.0);
        homeForm.setLayoutY(87.0);
        homeForm.setPrefSize(889.0, 555.0);

        AnchorPane homeInnerAnchorPane = new AnchorPane();
        homeInnerAnchorPane.setLayoutX(13.0);
        homeInnerAnchorPane.setLayoutY(17.0);
        homeInnerAnchorPane.setPrefSize(863.0, 521.0);
        homeInnerAnchorPane.getStyleClass().addAll("white-bg", "shadow");
      
        // Cards
        AnchorPane homeCardsAnchorPane = createHomeCards();
        // Bar Chart
        BarChart<String, Number> homeChart = createHomeChart();

        homeInnerAnchorPane.getChildren().addAll(homeCardsAnchorPane, homeChart);
        homeForm.getChildren().add(homeInnerAnchorPane);

        return homeForm;
    }

    // Method to create Home Cards
    private AnchorPane createHomeCards() {
        AnchorPane homeCardsAnchorPane = new AnchorPane();
        homeCardsAnchorPane.setPrefSize(863.0, 155.0);
        homeCardsAnchorPane.getStyleClass().addAll("white-bg", "shadow");
       

        // Total Employees Card
        AnchorPane totalEmployeesCard = new AnchorPane();
        totalEmployeesCard.setLayoutX(14.0);
        totalEmployeesCard.setLayoutY(10.0);
        totalEmployeesCard.setPrefSize(254.0, 136.0);
        totalEmployeesCard.getStyleClass().add("card");
        

        Label totalEmployeesIcon = new Label("\uD83D\uDC65"); // 👥
        totalEmployeesIcon.setTextFill(Color.WHITE);
        totalEmployeesIcon.setLayoutX(14.0);
        totalEmployeesIcon.setLayoutY(85.0);
        totalEmployeesIcon.setFont(Font.font("Arial", 36));

        Label totalEmployeesLabel = new Label("Total Employees");
        totalEmployeesLabel.setLayoutX(77.0);
        totalEmployeesLabel.setLayoutY(92.0);
        totalEmployeesLabel.setTextFill(Color.WHITE);
        totalEmployeesLabel.setFont(Font.font("Tahoma", 17));

        homeTotalEmployees = new Label("0");
        homeTotalEmployees.setId("home_totalEmployees");
        homeTotalEmployees.setLayoutX(87.0);
        homeTotalEmployees.setLayoutY(14.0);
        homeTotalEmployees.setPrefSize(148.0, 21.0);
        homeTotalEmployees.setTextFill(Color.WHITE);
        homeTotalEmployees.setAlignment(Pos.CENTER_RIGHT);
        homeTotalEmployees.setFont(Font.font("Arial", 25));

        totalEmployeesCard.getChildren().addAll(totalEmployeesIcon, totalEmployeesLabel, homeTotalEmployees);

        // Total Presents Card
        AnchorPane totalPresentsCard = new AnchorPane();
        totalPresentsCard.setLayoutX(305.0);
        totalPresentsCard.setLayoutY(10.0);
        totalPresentsCard.setPrefSize(254.0, 136.0);
        totalPresentsCard.getStyleClass().add("card");
        
        Label totalPresentsIcon = new Label("\u2714\uFE0F"); // ✔️
        totalPresentsIcon.setTextFill(Color.WHITE);
        totalPresentsIcon.setLayoutX(14.0);
        totalPresentsIcon.setLayoutY(90.0);
        totalPresentsIcon.setFont(Font.font("Arial", 36));

        Label totalPresentsLabel = new Label("Total Presents");
        totalPresentsLabel.setLayoutX(77.0);
        totalPresentsLabel.setLayoutY(97.0);
        totalPresentsLabel.setTextFill(Color.WHITE);
        totalPresentsLabel.setFont(Font.font("Tahoma", 17));

        homeTotalPresents = new Label("0");
        homeTotalPresents.setId("home_totalPresents");
        homeTotalPresents.setLayoutX(87.0);
        homeTotalPresents.setLayoutY(19.0);
        homeTotalPresents.setPrefSize(148.0, 21.0);
        homeTotalPresents.setTextFill(Color.WHITE);
        homeTotalPresents.setAlignment(Pos.CENTER_RIGHT);
        homeTotalPresents.setFont(Font.font("Arial", 25));

        totalPresentsCard.getChildren().addAll(totalPresentsIcon, totalPresentsLabel, homeTotalPresents);

        // Total Inactive Employees Card
        AnchorPane totalInactiveEmCard = new AnchorPane();
        totalInactiveEmCard.setLayoutX(595.0);
        totalInactiveEmCard.setLayoutY(10.0);
        totalInactiveEmCard.setPrefSize(254.0, 136.0);
        totalInactiveEmCard.getStyleClass().add("card");
        
        Label totalInactiveEmIcon = new Label("\u274C"); // ❌
        totalInactiveEmIcon.setTextFill(Color.WHITE);
        totalInactiveEmIcon.setLayoutX(14.0);
        totalInactiveEmIcon.setLayoutY(90.0);
        totalInactiveEmIcon.setFont(Font.font("Arial", 36));

        Label totalInactiveEmLabel = new Label("Total Inactive Employees");
        totalInactiveEmLabel.setLayoutX(60.0);
        totalInactiveEmLabel.setLayoutY(97.0);
        totalInactiveEmLabel.setTextFill(Color.WHITE);
        totalInactiveEmLabel.setFont(Font.font("Tahoma", 16));

        homeTotalInactiveEm = new Label("0");
        homeTotalInactiveEm.setId("home_totalInactiveEm");
        homeTotalInactiveEm.setLayoutX(90.0);
        homeTotalInactiveEm.setLayoutY(19.0);
        homeTotalInactiveEm.setPrefSize(148.0, 21.0);
        homeTotalInactiveEm.setTextFill(Color.WHITE);
        homeTotalInactiveEm.setAlignment(Pos.CENTER_RIGHT);
        homeTotalInactiveEm.setFont(Font.font("Arial", 25));

        totalInactiveEmCard.getChildren().addAll(totalInactiveEmIcon, totalInactiveEmLabel, homeTotalInactiveEm);

        // Add all cards to homeCardsAnchorPane
        homeCardsAnchorPane.getChildren().addAll(totalEmployeesCard, totalPresentsCard, totalInactiveEmCard);

        return homeCardsAnchorPane;
    }

    // Method to create the Bar Chart in Home Form
    private BarChart<String, Number> createHomeChart() {
        CategoryAxis xAxis = new CategoryAxis();
        xAxis.setSide(javafx.geometry.Side.BOTTOM);

        NumberAxis yAxis = new NumberAxis();
        yAxis.setSide(javafx.geometry.Side.LEFT);

        BarChart<String, Number> homeChart = new BarChart<>(xAxis, yAxis);
        homeChart.setId("home_chart");
        homeChart.setLayoutX(87.0);
        homeChart.setLayoutY(204.0);
        homeChart.setPrefSize(690.0, 281.0);
        homeChart.setTitle("Employees Data Chart");

        return homeChart;
    }

    // Method to create the Add Employee Form
    private AnchorPane createAddEmployeeForm() {
        AnchorPane addEmployeeForm = new AnchorPane();
        addEmployeeForm.setId("addEmployee_form");
        addEmployeeForm.setLayoutX(35.0);
        addEmployeeForm.setLayoutY(78.0);
        addEmployeeForm.setPrefSize(889.0, 555.0);
        addEmployeeForm.setVisible(false);
        AnchorPane.setTopAnchor(addEmployeeForm, 0.0);
        AnchorPane.setBottomAnchor(addEmployeeForm, 0.0);
        AnchorPane.setLeftAnchor(addEmployeeForm, 0.0);
        AnchorPane.setRightAnchor(addEmployeeForm, 0.0);

        AnchorPane addEmployeeInnerAnchorPane = new AnchorPane();
        addEmployeeInnerAnchorPane.setLayoutX(15.0);
        addEmployeeInnerAnchorPane.setLayoutY(14.0);
        addEmployeeInnerAnchorPane.setPrefSize(859.0, 528.0);
        
        // Table and Search
        AnchorPane addEmployeeTableAnchorPane = new AnchorPane();
        addEmployeeTableAnchorPane.setPrefSize(859.0, 303.0);
        addEmployeeTableAnchorPane.getStyleClass().addAll("white-bg", "shadow");
        

        TableView<Employee> addEmployeeTableView = new TableView<>();
        addEmployeeTableView.setId("addEmployee_tableView");
        addEmployeeTableView.setLayoutX(10.0);
        addEmployeeTableView.setLayoutY(62.0);
        addEmployeeTableView.setPrefSize(840.0, 227.0);
        addEmployeeTableView.setOnMouseClicked(e -> addEmployeeSelect(e));

        // Define Columns
        TableColumn<Employee, String> colEmployeeID = new TableColumn<>("Employee ID");
        colEmployeeID.setId("addEmployee_col_employeeID");
        colEmployeeID.setPrefWidth(84.0);

        TableColumn<Employee, String> colFirstName = new TableColumn<>("First Name");
        colFirstName.setId("addEmployee_col_firstName");
        colFirstName.setPrefWidth(111.0);

        TableColumn<Employee, String> colLastName = new TableColumn<>("Last Name");
        colLastName.setId("addEmployee_col_lastName");
        colLastName.setPrefWidth(126.0);

        TableColumn<Employee, String> colGender = new TableColumn<>("Gender");
        colGender.setId("addEmployee_col_gender");
        colGender.setPrefWidth(100.0);

        TableColumn<Employee, String> colPhoneNum = new TableColumn<>("Phone #");
        colPhoneNum.setId("addEmployee_col_phoneNum");
        colPhoneNum.setPrefWidth(125.0);

        TableColumn<Employee, String> colPosition = new TableColumn<>("Position");
        colPosition.setId("addEmployee_col_position");
        colPosition.setPrefWidth(154.0);

        TableColumn<Employee, String> colDate = new TableColumn<>("Date Member");
        colDate.setId("addEmployee_col_date");
        colDate.setPrefWidth(139.0);

        addEmployeeTableView.getColumns().addAll(colEmployeeID, colFirstName, colLastName, colGender, colPhoneNum, colPosition, colDate);

        // Search Field
        TextField addEmployeeSearch = new TextField();
        addEmployeeSearch.setId("addEmployee_search");
        addEmployeeSearch.setLayoutX(14.0);
        addEmployeeSearch.setLayoutY(14.0);
        addEmployeeSearch.setPrefSize(277.0, 30.0);
        addEmployeeSearch.setPromptText("Search");
        addEmployeeSearch.getStyleClass().add("search");
        
        addEmployeeSearch.setOnKeyTyped(e -> addEmployeeSearch(e));

        // Search Icon Placeholder
        Label searchIcon = new Label("\uD83D\uDD0D"); // 🔍
        searchIcon.setLayoutX(21.0);
        searchIcon.setLayoutY(35.0);
        searchIcon.setFont(Font.font("Arial", 16));

        addEmployeeTableAnchorPane.getChildren().addAll(addEmployeeTableView, addEmployeeSearch, searchIcon);

        // Employee Details Labels and Fields
        Label labelEmployeeID = new Label("Employee ID:");
        labelEmployeeID.setLayoutX(23.0);
        labelEmployeeID.setLayoutY(332.0);
        labelEmployeeID.setFont(Font.font("Tahoma", 14));

        TextField addEmployeeEmployeeID = new TextField();
        addEmployeeEmployeeID.setId("addEmployee_employeeID");
        addEmployeeEmployeeID.setLayoutX(117.0);
        addEmployeeEmployeeID.setLayoutY(329.0);
        addEmployeeEmployeeID.setPrefSize(172.0, 25.0);
        addEmployeeEmployeeID.getStyleClass().add("textfield");
        

        Label labelFirstName = new Label("First Name:");
        labelFirstName.setLayoutX(35.0);
        labelFirstName.setLayoutY(371.0);
        labelFirstName.setFont(Font.font("Tahoma", 14));

        TextField addEmployeeFirstName = new TextField();
        addEmployeeFirstName.setId("addEmployee_firstName");
        addEmployeeFirstName.setLayoutX(117.0);
        addEmployeeFirstName.setLayoutY(367.0);
        addEmployeeFirstName.setPrefSize(172.0, 25.0);
        addEmployeeFirstName.getStyleClass().add("textfield");
        

        Label labelLastName = new Label("Last Name:");
        labelLastName.setLayoutX(36.0);
        labelLastName.setLayoutY(409.0);
        labelLastName.setFont(Font.font("Tahoma", 14));

        TextField addEmployeeLastName = new TextField();
        addEmployeeLastName.setId("addEmployee_lastName");
        addEmployeeLastName.setLayoutX(117.0);
        addEmployeeLastName.setLayoutY(405.0);
        addEmployeeLastName.setPrefSize(172.0, 25.0);
        addEmployeeLastName.getStyleClass().add("textfield");
        

        Label labelGender = new Label("Gender:");
        labelGender.setLayoutX(57.0);
        labelGender.setLayoutY(447.0);
        labelGender.setFont(Font.font("Tahoma", 14));

        ComboBox<String> addEmployeeGender = new ComboBox<>();
        addEmployeeGender.setId("addEmployee_gender");
        addEmployeeGender.setLayoutX(117.0);
        addEmployeeGender.setLayoutY(444.0);
        addEmployeeGender.setPrefWidth(150.0);
        addEmployeeGender.setPromptText("Choose");
        addEmployeeGender.getStyleClass().add("textfield");
        
        addEmployeeGender.setOnAction(e -> addEmployeeGenderList(e));

        Label labelPhoneNum = new Label("Phone #:");
        labelPhoneNum.setLayoutX(354.0);
        labelPhoneNum.setLayoutY(333.0);
        labelPhoneNum.setFont(Font.font("Tahoma", 14));

        TextField addEmployeePhoneNum = new TextField();
        addEmployeePhoneNum.setId("addEmployee_phoneNum");
        addEmployeePhoneNum.setLayoutX(423.0);
        addEmployeePhoneNum.setLayoutY(329.0);
        addEmployeePhoneNum.setPrefSize(172.0, 25.0);
        addEmployeePhoneNum.getStyleClass().add("textfield");
        

        Label labelPosition = new Label("Position:");
        labelPosition.setLayoutX(356.0);
        labelPosition.setLayoutY(371.0);
        labelPosition.setFont(Font.font("Tahoma", 14));

        ComboBox<String> addEmployeePosition = new ComboBox<>();
        addEmployeePosition.setId("addEmployee_position");
        addEmployeePosition.setLayoutX(423.0);
        addEmployeePosition.setLayoutY(367.0);
        addEmployeePosition.setPrefWidth(150.0);
        addEmployeePosition.setPromptText("Choose");
        addEmployeePosition.getStyleClass().add("textfield");
        
        addEmployeePosition.setOnAction(e -> addEmployeePositionList(e));

        // Image Import Section
        AnchorPane imageAnchorPane = new AnchorPane();
        imageAnchorPane.setLayoutX(716.0);
        imageAnchorPane.setLayoutY(316.0);
        imageAnchorPane.setPrefSize(101.0, 127.0);
        imageAnchorPane.getStyleClass().addAll("white-bg", "shadow");
        imageAnchorPane.getStylesheets().add(getClass().getResource("dashboardDesign.css").toExternalForm());

        ImageView addEmployeeImage = new ImageView();
        addEmployeeImage.setId("addEmployee_image");
        addEmployeeImage.setFitHeight(127.0);
        addEmployeeImage.setFitWidth(101.0);
        addEmployeeImage.setPickOnBounds(true);
        addEmployeeImage.setPreserveRatio(true);

        Button importBtn = new Button("Import");
        importBtn.setId("addEmployee_importBtn");
        importBtn.setLayoutY(101.0);
        importBtn.setPrefWidth(101.0);
        importBtn.getStyleClass().add("import-btn");
        importBtn.getStylesheets().add(getClass().getResource("dashboardDesign.css").toExternalForm());
        importBtn.setOnAction(e -> addEmployeeInsertImage(e));

        imageAnchorPane.getChildren().addAll(addEmployeeImage, importBtn);

        // Action Buttons
        Button addBtn = new Button("Add");
        addBtn.setId("addEmployee_addBtn");
        addBtn.setLayoutX(701.0);
        addBtn.setLayoutY(469.0);
        addBtn.setPrefSize(94.0, 41.0);
        addBtn.getStyleClass().add("add-btn");
        addBtn.getStylesheets().add(getClass().getResource("dashboardDesign.css").toExternalForm());
        addBtn.setOnAction(e -> addEmployeeAdd(e));

        Button updateBtn = new Button("Update");
        updateBtn.setId("addEmployee_updateBtn");
        updateBtn.setLayoutX(588.0);
        updateBtn.setLayoutY(469.0);
        updateBtn.setPrefSize(94.0, 41.0);
        updateBtn.getStyleClass().add("update-btn");
        updateBtn.getStylesheets().add(getClass().getResource("dashboardDesign.css").toExternalForm());
        updateBtn.setOnAction(e -> addEmployeeUpdate(e));

        Button deleteBtn = new Button("Delete");
        deleteBtn.setId("addEmployee_deleteBtn");
        deleteBtn.setLayoutX(433.0);
        deleteBtn.setLayoutY(469.0);
        deleteBtn.setPrefSize(94.0, 41.0);
        deleteBtn.getStyleClass().add("delete-btn");
        deleteBtn.getStylesheets().add(getClass().getResource("dashboardDesign.css").toExternalForm());
        deleteBtn.setOnAction(e -> addEmployeeDelete(e));

        Button clearBtn = new Button("Clear");
        clearBtn.setId("addEmployee_clearBtn");
        clearBtn.setLayoutX(320.0);
        clearBtn.setLayoutY(469.0);
        clearBtn.setPrefSize(94.0, 41.0);
        clearBtn.getStyleClass().add("clear-btn");
        clearBtn.getStylesheets().add(getClass().getResource("dashboardDesign.css").toExternalForm());
        clearBtn.setOnAction(e -> addEmployeeReset(e));

        // Add all components to addEmployeeInnerAnchorPane
        addEmployeeInnerAnchorPane.getChildren().addAll(
                addEmployeeTableAnchorPane,
                labelEmployeeID, addEmployeeEmployeeID,
                labelFirstName, addEmployeeFirstName,
                labelLastName, addEmployeeLastName,
                labelGender, addEmployeeGender,
                labelPhoneNum, addEmployeePhoneNum,
                labelPosition, addEmployeePosition,
                imageAnchorPane,
                addBtn, updateBtn, deleteBtn, clearBtn
        );

        addEmployeeForm.getChildren().add(addEmployeeInnerAnchorPane);

        return addEmployeeForm;
    }

    // Method to create the Salary Form
    private AnchorPane createSalaryForm() {
        AnchorPane salaryForm = new AnchorPane();
        salaryForm.setId("salary_form");
        salaryForm.setLayoutX(75.0);
        salaryForm.setLayoutY(78.0);
        salaryForm.setPrefSize(889.0, 555.0);
        salaryForm.setVisible(false);
        AnchorPane.setTopAnchor(salaryForm, 0.0);
        AnchorPane.setBottomAnchor(salaryForm, 0.0);
        AnchorPane.setLeftAnchor(salaryForm, 0.0);
        AnchorPane.setRightAnchor(salaryForm, 0.0);

        // Left Side: Employee Details
        AnchorPane salaryInnerAnchorPane = new AnchorPane();
        salaryInnerAnchorPane.setLayoutX(14.0);
        salaryInnerAnchorPane.setLayoutY(16.0);
        salaryInnerAnchorPane.setPrefSize(283.0, 523.0);
        salaryInnerAnchorPane.getStyleClass().addAll("white-bg", "shadow");
        salaryInnerAnchorPane.getStylesheets().add(getClass().getResource("dashboardDesign.css").toExternalForm());

        Label salaryLabelEmployeeID = new Label("Employee ID:");
        salaryLabelEmployeeID.setLayoutX(27.0);
        salaryLabelEmployeeID.setLayoutY(37.0);
        salaryLabelEmployeeID.setFont(Font.font(14));

        TextField salaryEmployeeID = new TextField();
        salaryEmployeeID.setId("salary_employeeID");
        salaryEmployeeID.setLayoutX(120.0);
        salaryEmployeeID.setLayoutY(35.0);
        salaryEmployeeID.setPrefSize(150.0, 25.0);
        salaryEmployeeID.getStyleClass().add("textfield");
        salaryEmployeeID.getStylesheets().add(getClass().getResource("dashboardDesign.css").toExternalForm());

        Label salaryLabelFirstName = new Label("First Name:");
        salaryLabelFirstName.setLayoutX(38.0);
        salaryLabelFirstName.setLayoutY(73.0);
        salaryLabelFirstName.setFont(Font.font(14));

        Label salaryFirstName = new Label();
        salaryFirstName.setId("salary_firstName");
        salaryFirstName.setLayoutX(120.0);
        salaryFirstName.setLayoutY(70.0);
        salaryFirstName.setPrefSize(150.0, 25.0);
        salaryFirstName.getStyleClass().add("label-info");
        salaryFirstName.getStylesheets().add(getClass().getResource("dashboardDesign.css").toExternalForm());

        Label salaryLabelLastName = new Label("Last Name:");
        salaryLabelLastName.setLayoutX(39.0);
        salaryLabelLastName.setLayoutY(109.0);
        salaryLabelLastName.setFont(Font.font(14));

        Label salaryLastName = new Label();
        salaryLastName.setId("salary_lastName");
        salaryLastName.setLayoutX(120.0);
        salaryLastName.setLayoutY(106.0);
        salaryLastName.setPrefSize(150.0, 25.0);
        salaryLastName.getStyleClass().add("label-info");
        salaryLastName.getStylesheets().add(getClass().getResource("dashboardDesign.css").toExternalForm());

        Label salaryLabelPosition = new Label("Position:");
        salaryLabelPosition.setLayoutX(55.0);
        salaryLabelPosition.setLayoutY(148.0);
        salaryLabelPosition.setFont(Font.font(14));

        Label salaryPosition = new Label("Label");
        salaryPosition.setId("salary_position");
        salaryPosition.setLayoutX(120.0);
        salaryPosition.setLayoutY(145.0);
        salaryPosition.setPrefSize(150.0, 25.0);
        salaryPosition.getStyleClass().add("label-info");
        salaryPosition.getStylesheets().add(getClass().getResource("dashboardDesign.css").toExternalForm());

        Label salaryLabelSalary = new Label("Salary ($):");
        salaryLabelSalary.setLayoutX(44.0);
        salaryLabelSalary.setLayoutY(185.0);
        salaryLabelSalary.setFont(Font.font(14));

        TextField salarySalary = new TextField();
        salarySalary.setId("salary_salary");
        salarySalary.setLayoutX(120.0);
        salarySalary.setLayoutY(182.0);
        salarySalary.setPrefSize(150.0, 25.0);
        salarySalary.getStyleClass().add("textfield");
        salarySalary.getStylesheets().add(getClass().getResource("dashboardDesign.css").toExternalForm());

        // Update and Clear Buttons
        Button salaryUpdateBtn = new Button("Update");
        salaryUpdateBtn.setId("salary_updateBtn");
        salaryUpdateBtn.setLayoutX(156.0);
        salaryUpdateBtn.setLayoutY(251.0);
        salaryUpdateBtn.setPrefSize(99.0, 38.0);
        salaryUpdateBtn.getStyleClass().add("update-btn");
        salaryUpdateBtn.getStylesheets().add(getClass().getResource("dashboardDesign.css").toExternalForm());
        salaryUpdateBtn.setOnAction(e -> salaryUpdate(e));

        Button salaryClearBtn = new Button("Clear");
        salaryClearBtn.setId("salary_clearBtn");
        salaryClearBtn.setLayoutX(28.0);
        salaryClearBtn.setLayoutY(251.0);
        salaryClearBtn.setPrefSize(99.0, 38.0);
        salaryClearBtn.getStyleClass().add("clear-btn");
        salaryClearBtn.getStylesheets().add(getClass().getResource("dashboardDesign.css").toExternalForm());
        salaryClearBtn.setOnAction(e -> salaryReset(e));

        salaryInnerAnchorPane.getChildren().addAll(
                salaryLabelEmployeeID, salaryEmployeeID,
                salaryLabelFirstName, salaryFirstName,
                salaryLabelLastName, salaryLastName,
                salaryLabelPosition, salaryPosition,
                salaryLabelSalary, salarySalary,
                salaryUpdateBtn, salaryClearBtn
        );

        // Right Side: Salary Table
        AnchorPane salaryTableAnchorPane = new AnchorPane();
        salaryTableAnchorPane.setLayoutX(321.0);
        salaryTableAnchorPane.setLayoutY(16.0);
        salaryTableAnchorPane.setPrefSize(554.0, 523.0);
        salaryTableAnchorPane.getStyleClass().addAll("white-bg", "shadow");
        salaryTableAnchorPane.getStylesheets().add(getClass().getResource("dashboardDesign.css").toExternalForm());

        TableView<SalaryRecord> salaryTableView = new TableView<>();
        salaryTableView.setId("salary_tableView");
        salaryTableView.setLayoutX(11.0);
        salaryTableView.setLayoutY(11.0);
        salaryTableView.setPrefSize(532.0, 501.0);
        salaryTableView.setOnMouseClicked(e -> salarySelect(e));

        // Define Columns
        TableColumn<SalaryRecord, String> salaryColEmployeeID = new TableColumn<>("Employee ID");
        salaryColEmployeeID.setId("salary_col_employeeID");
        salaryColEmployeeID.setPrefWidth(75.0);

        TableColumn<SalaryRecord, String> salaryColFirstName = new TableColumn<>("First Name");
        salaryColFirstName.setId("salary_col_firstName");
        salaryColFirstName.setPrefWidth(99.0);

        TableColumn<SalaryRecord, String> salaryColLastName = new TableColumn<>("Last Name");
        salaryColLastName.setId("salary_col_lastName");
        salaryColLastName.setPrefWidth(106.0);

        TableColumn<SalaryRecord, String> salaryColPosition = new TableColumn<>("Position");
        salaryColPosition.setId("salary_col_position");
        salaryColPosition.setPrefWidth(134.0);

        TableColumn<SalaryRecord, String> salaryColSalary = new TableColumn<>("Salary ($)");
        salaryColSalary.setId("salary_col_salary");
        salaryColSalary.setPrefWidth(117.0);

        salaryTableView.getColumns().addAll(salaryColEmployeeID, salaryColFirstName, salaryColLastName, salaryColPosition, salaryColSalary);

        salaryTableAnchorPane.getChildren().add(salaryTableView);

        // Add to salaryForm
        salaryForm.getChildren().addAll(salaryInnerAnchorPane, salaryTableAnchorPane);

        return salaryForm;
    }

    // Placeholder method for switching forms
    private void switchForm(ActionEvent event) {
        // Implement form switching logic here
        // For example, show/hide different AnchorPanes based on the button clicked
    }

    // Placeholder method for logout
    private void logout() {
        // Implement logout logic here
        // For example, return to login screen
    }

    // Placeholder method for Add Employee Select
    private void addEmployeeSelect(MouseEvent e) {
        // Implement selection logic here
    }

    // Placeholder method for Add Employee Search
    private void addEmployeeSearch(KeyEvent e) {
        // Implement search logic here
    }

    // Placeholder method for Add Employee Gender List
    private void addEmployeeGenderList(ActionEvent event) {
        // Implement gender list logic here
    }

    // Placeholder method for Add Employee Position List
    private void addEmployeePositionList(ActionEvent event) {
        // Implement position list logic here
    }

    // Placeholder method for Add Employee Insert Image
    private void addEmployeeInsertImage(ActionEvent event) {
        // Implement image insertion logic here
    }

    // Placeholder method for Add Employee Add
    private void addEmployeeAdd(ActionEvent event) {
        // Implement add employee logic here
    }

    // Placeholder method for Add Employee Update
    private void addEmployeeUpdate(ActionEvent event) {
        // Implement update employee logic here
    }

    // Placeholder method for Add Employee Delete
    private void addEmployeeDelete(ActionEvent event) {
        // Implement delete employee logic here
    }

    // Placeholder method for Add Employee Reset
    private void addEmployeeReset(ActionEvent event) {
        // Implement reset form logic here
    }

    // Placeholder method for Salary Select
    private void salarySelect(MouseEvent e) {
        // Implement salary record selection logic here
    }

    // Placeholder method for Salary Update
    private void salaryUpdate(ActionEvent event) {
        // Implement salary update logic here
    }

    // Placeholder method for Salary Reset
    private void salaryReset(ActionEvent event) {
        // Implement salary form reset logic here
    }

    // Main Application to display the DashboardView
    public static void main(String[] args) {
        Application.launch(DashboardApp.class, args);
    }
}

// Example Employee class (You need to define appropriate properties and methods)
class Employee {
    // Define properties, constructors, getters, and setters
    // Example:
    // private String employeeID;
    // private String firstName;
    // private String lastName;
    // private String gender;
    // private String phoneNum;
    // private String position;
    // private String dateMember;
    
    // Add constructors, getters, and setters here
}

// Example SalaryRecord class (You need to define appropriate properties and methods)
class SalaryRecord {
    // Define properties, constructors, getters, and setters
    // Example:
    // private String employeeID;
    // private String firstName;
    // private String lastName;
    // private String position;
    // private double salary;
    
    // Add constructors, getters, and setters here
}

// Application class to launch the DashboardView
class DashboardApp extends Application {
    @Override
    public void start(Stage primaryStage) {
        Testing dashboardView = new Testing(primaryStage);
        Scene scene = new Scene(dashboardView);
        scene.getStylesheets().add(getClass().getResource("dashboardDesign.css").toExternalForm());
        primaryStage.setScene(scene);
        primaryStage.setTitle("Employee Management System");
        primaryStage.show();
    }
}
