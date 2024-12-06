package View;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

public class SellerComponentView {
	private Button[] sidebarButtons;
	private Button activeButton;
	private Button logoutButton;
	private Label profileNameLabel;

	public SellerComponentView() {
	}

	public VBox getSidebar(String username) {
		VBox sidebar = createSidebar(username);

		// Pastikan logoutButton diinisialisasi
		logoutButton = createLogoutButton();

		// Tambahkan logoutButton ke sidebar
		sidebar.getChildren().add(logoutButton);
		VBox.setMargin(logoutButton, new Insets(0, 0, 20, 0));
		return sidebar;
	}

	public Button getLogoutButton() {
		if (logoutButton == null) {
			System.err.println("Logout button is not initialized!");
		}
		return logoutButton;
	}

	public void setButtonAction(int index, Runnable action) {
		if (index >= 0 && index < sidebarButtons.length) {
			sidebarButtons[index].setOnAction(e -> {
				setActiveButton(sidebarButtons[index]);
				action.run();
			});
		}
	}

	public void setLogoutAction(Runnable action) {
		Button logoutButton = createLogoutButton();
		logoutButton.setOnAction(e -> {
			System.out.println("Logout clicked");
			action.run();
		});
	}

	private VBox createSidebar(String username) {
		VBox sidebar = new VBox(10);
		sidebar.setStyle("-fx-background-color: #2C3E50;");
		sidebar.setAlignment(Pos.TOP_CENTER);
		sidebar.setPrefWidth(250);

		// Profile Image
		ImageView profileImage = createProfileImage();

		// Profile Name
		Label profileName = createProfileNameLabel(username);

		// Sidebar Buttons
		String[][] buttonData = { { "View Items", "file:resources/grid.png" },
				{ "Upload Item", "file:resources/upload.png" }, { "View Offered Items", "file:resources/email.png" } };

		VBox buttonContainer = createButtonContainer(buttonData);

		// Spacer to push logout button to bottom
		Region spacer = new Region();
		VBox.setVgrow(spacer, Priority.ALWAYS);

		sidebar.getChildren().addAll(profileImage, profileName, buttonContainer, spacer);

		VBox.setMargin(profileImage, new Insets(30, 0, 10, 0));
		return sidebar;
	}

	private ImageView createProfileImage() {
		Image profileimg = new Image("file:resources/Seller_Image_Profile.png");
		ImageView profileImage = new ImageView(profileimg);
		profileImage.setFitHeight(100);
		profileImage.setFitWidth(100);
		profileImage.setPreserveRatio(true);

		// Add circular clip to profile image
		Circle clip = new Circle(50, 50, 50);
		profileImage.setClip(clip);

		return profileImage;
	}

	private Label createProfileNameLabel(String username) {
		Label profileName = new Label(username);
		profileName.setTextFill(Color.WHITE);
		profileName.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");
		return profileName;
	}

	private VBox createButtonContainer(String[][] buttonData) {
		VBox buttonContainer = new VBox(10);
		buttonContainer.setAlignment(Pos.CENTER);
		buttonContainer.setPadding(new Insets(20, 0, 0, 0));

		// Initialize buttons array
		sidebarButtons = new Button[buttonData.length];

		for (int i = 0; i < buttonData.length; i++) {
			Button btn = createSidebarButton(buttonData[i][0], buttonData[i][1]);

			// Store reference to buttons
			sidebarButtons[i] = btn;

			// Add click handler to set active button
			if (buttonData[i][0].contains("View Items"))
				btn.setOnAction(e -> setActiveButton(btn));

			buttonContainer.getChildren().add(btn);
		}

		// Set first button as active by default
		if (sidebarButtons.length > 0) {
			setActiveButton(sidebarButtons[0]);
		}

		return buttonContainer;
	}

	private Button createSidebarButton(String text, String iconPath) {
		Button btn = new Button(text);
		btn.setPrefWidth(220);
		btn.setPrefHeight(50);

		Image iconimg = new Image(iconPath);
		ImageView icon = new ImageView(iconimg);
		icon.setFitHeight(25);
		icon.setFitWidth(25);

		btn.setGraphic(icon);
		btn.setGraphicTextGap(15);

		// Default styling
		btn.setStyle(getButtonStyle("#34495E", false));

		// Hover effects
		btn.setOnMouseEntered(e -> {
			if (btn != activeButton) {
				btn.setStyle(getButtonStyle("#2C3E50", false));
			}
		});

		btn.setOnMouseExited(e -> {
			if (btn != activeButton) {
				btn.setStyle(getButtonStyle("#34495E", false));
			}
		});

		return btn;
	}

	private Button createLogoutButton() {
		Button logoutBtn = new Button("Logout");
		logoutBtn.setPrefWidth(220);
		logoutBtn.setPrefHeight(50);
		
		System.out.println("Logout button created: " + logoutBtn);
		
		Image logoutIcon = new Image("file:resources/logout.png");
		ImageView icon = new ImageView(logoutIcon);
		icon.setFitHeight(25);
		icon.setFitWidth(25);

		logoutBtn.setGraphic(icon);
		logoutBtn.setGraphicTextGap(15);

		logoutBtn.setStyle(getButtonStyle("#34495E", false));

		logoutBtn.setOnMouseEntered(e -> logoutBtn.setStyle(getButtonStyle("#34495E", true)));

		logoutBtn.setOnMouseExited(e -> logoutBtn.setStyle(getButtonStyle("#34495E", false)));

		return logoutBtn;
	}

	private String getButtonStyle(String backgroundColor, boolean isActive) {
		return String.format(
				"-fx-background-color: %s;" + "-fx-text-fill: white;" + "-fx-font-size: 14px;"
						+ "-fx-background-radius: 10px;"
						+ "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,%s), %s, 0, 0, 0);",
				backgroundColor, isActive ? "0.3" : "0.1", isActive ? "5" : "3");
	}

	private void setActiveButton(Button activeBtn) {
		for (Button btn : sidebarButtons) {
			btn.setStyle(getButtonStyle("#34495E", false));
		}

		activeBtn.setStyle(getButtonStyle("#2980B9", true));
		this.activeButton = activeBtn;
	}
}