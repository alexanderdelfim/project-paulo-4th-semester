package gui;

import application.Main;
import gui.listeners.DataChangeListener;
import gui.util.Alerts;
import gui.util.Utils;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.entities.Cliente;
import model.entities.enums.Sexo;
import model.services.ClienteService;

public class ClienteListController implements Initializable, DataChangeListener {

    private ClienteService services;

    @FXML
    private TableView<Cliente> tableViewCliente;

    @FXML
    private TableColumn<Cliente, Integer> tableColumnId;

    @FXML
    private TableColumn<Cliente, String> tableColumnNome;

    @FXML
    private TableColumn<Cliente, String> tableColumnEmail;

    @FXML
    private TableColumn<Cliente, String> tableColumnCpf;

    @FXML
    private TableColumn<Cliente, String> tableColumnRg;

    @FXML
    private TableColumn<Cliente, Date> tableColumnDataNascimento;

    @FXML
    private TableColumn<Cliente, Date> tableColumnDataCadastro;

    @FXML
    private TableColumn<Cliente, Sexo> tableColumnSexo;

    @FXML
    private TableColumn<Cliente, String> tableColumnTelefone;

    @FXML
    private TableColumn<Cliente, String> tableColumnCelular;

    @FXML
    private TableColumn<Cliente, String> tableColumnEndereco;

    @FXML
    private TableColumn<Cliente, String> tableColumnCep;

    @FXML
    private TableColumn<Cliente, String> tableColumnBairro;

    @FXML
    private TableColumn<Cliente, String> tableColumnCidade;

    @FXML
    private Button btNovoCliente;

    private ObservableList<Cliente> obsList;

    @FXML
    public void onBtNovoClienteAction(ActionEvent event) {
        Stage parentStage = Utils.currentStage(event);
        Cliente obj = new Cliente();
        createDialogForm(obj, "/gui/CadastroClienteView.fxml", parentStage);
    }

    public void setClienteService(ClienteService service) {
        this.services = service;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initializeNodes();
    }

    private void initializeNodes() {
        tableColumnId.setCellValueFactory(new PropertyValueFactory<>("idCliente"));
        tableColumnNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        tableColumnEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        tableColumnCpf.setCellValueFactory(new PropertyValueFactory<>("cpf"));
        tableColumnRg.setCellValueFactory(new PropertyValueFactory<>("rg"));
        tableColumnDataNascimento.setCellValueFactory(new PropertyValueFactory<>("dataNascimento"));
        tableColumnDataCadastro.setCellValueFactory(new PropertyValueFactory<>("dataCadastro"));
        tableColumnSexo.setCellValueFactory(new PropertyValueFactory<>("sexo"));
        tableColumnTelefone.setCellValueFactory(new PropertyValueFactory<>("telefone"));
        tableColumnCelular.setCellValueFactory(new PropertyValueFactory<>("celular"));
        tableColumnEndereco.setCellValueFactory(new PropertyValueFactory<>("endereco"));
        tableColumnCep.setCellValueFactory(new PropertyValueFactory<>("cep"));
        tableColumnBairro.setCellValueFactory(new PropertyValueFactory<>("bairro"));
        tableColumnCidade.setCellValueFactory(new PropertyValueFactory<>("cidade"));

        Stage stage = (Stage) Main.getMainScene().getWindow();
        tableViewCliente.prefHeightProperty().bind(stage.heightProperty());

    }

    public void updateTableView() throws ParseException, SQLException {
        if (services == null) {
            throw new IllegalStateException("Service was null");
        }
        List<Cliente> list = services.findAll();
        obsList = FXCollections.observableArrayList(list);
        tableViewCliente.setItems(obsList);
    }

    private void createDialogForm(Cliente obj, String absoluteName, Stage parentStage) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(absoluteName));
            Pane pane = loader.load();
            
            CadastroClienteController controller = loader.getController();
            controller.setCliente(obj);
            controller.setClienteService(new ClienteService());
            controller.subcribeDataChandeListener(this);
            controller.updateFormData();

            Stage dialogStage = new Stage();
            dialogStage.setTitle("Cadastrar novo cliente");
            dialogStage.setScene(new Scene(pane));
            dialogStage.setResizable(false);
            dialogStage.initOwner(parentStage);
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.showAndWait();
            
        } catch (IOException e) {
            Alerts.showAlert("IOExceotion", "Erro ao carregar a tela", e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    @Override
    public void onDataChanged() {
        try {
            updateTableView();
        } catch (ParseException ex) {
            Logger.getLogger(ClienteListController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(ClienteListController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
