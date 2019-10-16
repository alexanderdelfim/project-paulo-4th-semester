package gui;

import application.Main;
import gui.util.Alerts;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ResourceBundle;
import java.util.function.Consumer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import model.services.ClienteService;

public class PrincipalViewController implements Initializable {

    @FXML
    private MenuItem menuItemCliente;

    @FXML
    private MenuItem menuItemFuncionario;
    
    @FXML
    private MenuItem menuItemSobre;

    @FXML
    public void onMenuItemClienteAction() throws ParseException {
        loadView("/gui/ClienteListView.fxml", (ClienteListController controller) -> {
            controller.setClienteService(new ClienteService());
            try {
                controller.updateTableView();
            } catch (ParseException ex) {
                Logger.getLogger(PrincipalViewController.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SQLException ex) {
                Logger.getLogger(PrincipalViewController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }

    @FXML
    public void onMenuItemFuncionarioAction() {
        System.out.println("Menu funcionario");
    }
    
    @FXML
    public void onMenuItemSobreAction() {
        loadView("/gui/SobreView.fxml", x -> {});
    }

    @Override
    public void initialize(URL uri, ResourceBundle rb) {

    }

    private synchronized <T> void loadView(String absoluteName, Consumer<T> initializingAction) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(absoluteName));
            VBox newVBox = loader.load();
            
            Scene mainScene = Main.getMainScene();
            VBox mainVBox = (VBox)((ScrollPane) mainScene.getRoot()).getContent();
            
            Node mainMenu = mainVBox.getChildren().get(0);
            mainVBox.getChildren().clear();
            mainVBox.getChildren().add(mainMenu);
            mainVBox.getChildren().addAll(newVBox.getChildren());
            
            T controller = loader.getController();
            initializingAction.accept(controller);
            
        } catch (IOException e) {
            Alerts.showAlert("IO Exception", "Erro ao carregar a tela", e.getMessage(), Alert.AlertType.ERROR);
        }
    }

}
