package GUI.Views;


import GUI.Components.ClientList;
import GUI.Components.OptionBar;
import GUI.Components.StatisticsView;
import GUI.Components.TopBar;
import GUI.Styler;
import Server.ServerSettings;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

import java.io.IOException;

public class SettingsView {

    BorderPane getSettingsView() {
        BorderPane borderPane = new BorderPane();
        borderPane.getStylesheets().add(Styler.globalCSS);
        HBox hBox = getSettingsPanel();
        VBox vBox = Styler.vContainer(hBox);
        vBox.setId("settingsView");
        borderPane.setTop(new TopBar().getTopBar());
        borderPane.setCenter(vBox);
        borderPane.setBottom(new StatisticsView().getStatisticsView());
        return borderPane;
    }

    private HBox getSettingsPanel() {
        VBox settingsPanel = new VBox(5);
        settingsPanel.setAlignment(Pos.CENTER);
        VBox.setVgrow(settingsPanel, Priority.ALWAYS);
        Label titleText = new Label("Settings");
        titleText = (Label) Styler.styleAdd(titleText, "title");
        titleText.setMaxWidth(Double.MAX_VALUE);
        titleText.setAlignment(Pos.CENTER);

        HBox title = Styler.hContainer(titleText);
        title.setAlignment(Pos.CENTER);

        Label maxConnections = new Label("Max Connections: ");
        maxConnections = (Label) Styler.styleAdd(maxConnections, "label-bright");
        String maxConnectionsNumber = ServerSettings.getMaxConnections() == Integer.MAX_VALUE ? "Max" : "" + ServerSettings.getMaxConnections();
        TextField maxConnectionsField = new TextField(maxConnectionsNumber);
        maxConnectionsField.setEditable(true);
        maxConnectionsField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                maxConnectionsField.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });

        Label refreshRate = new Label("Refresh Rate: ");
        refreshRate = (Label) Styler.styleAdd(refreshRate, "label-bright");
        TextField refreshRateField = new TextField("" + ServerSettings.getRefreshRate());
        refreshRateField.textProperty().addListener(((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                refreshRateField.setText(newValue.replaceAll("[^\\d]", ""));
            }
        }));

        Button saveSettings = new Button("Apply");
        saveSettings.setOnAction(event -> {
            if (Integer.parseInt(maxConnectionsField.getText()) != ServerSettings.getMaxConnections()) {
                ServerSettings.setMaxConnections(Integer.parseInt(maxConnectionsField.getText()));
            }
            if (!maxConnectionsField.getText().contains("Max") && Integer.parseInt(refreshRateField.getText()) != ServerSettings.getRefreshRate()) {
                ServerSettings.setRefreshRate(Integer.parseInt(refreshRateField.getText()));
            }
        });

        settingsPanel.getChildren().addAll(title, maxConnections, maxConnectionsField,
                refreshRate, refreshRateField, saveSettings);

        return Styler.hContainer(settingsPanel);
    }


}
