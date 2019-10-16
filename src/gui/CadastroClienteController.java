package gui;

import db.DbException;
import gui.listeners.DataChangeListener;
import gui.util.Alerts;
import gui.util.Constraints;
import gui.util.Utils;
import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import model.entities.Cliente;
import model.entities.enums.Sexo;
import model.services.ClienteService;

public class CadastroClienteController implements Initializable {

    private Cliente entity;

    private ClienteService service;

    private List<DataChangeListener> dataChangeListeners = new ArrayList<>();

    @FXML
    private TextField txtNome;

    @FXML
    private TextField txtEmail;

    @FXML
    private TextField txtCpf;

    @FXML
    private TextField txtRg;

    @FXML
    private DatePicker datePickDataNascimento;

    @FXML
    private ComboBox<Sexo> cbSexo;

    @FXML
    private TextField txtTelefone;

    @FXML
    private TextField txtCelular;

    @FXML
    private TextField txtEndereco;

    @FXML
    private TextField txtCep;

    @FXML
    private TextField txtBairro;

    @FXML
    private TextField txtCidade;

    @FXML
    private Button btSalvar;

    @FXML
    private Button btSCancelar;

    @FXML
    public void onBtSalvarAction(ActionEvent event) {
        if (entity == null) {
            throw new IllegalStateException("A entidade está nula.");
        }
        if (service == null) {
            throw new IllegalStateException("O serviço está nulo.");
        }
        try {
            entity = getFormData();
            service.saveOrUpdate(entity);
            notifyDataChangeListeners();
            Utils.currentStage(event).close();
        } catch (DbException e) {
            Alerts.showAlert("Erro ao salvar o objeto", null, e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    @FXML
    public void onBtCancelarAction(ActionEvent event) {
        Utils.currentStage(event).close();
    }

    private List<Sexo> listSexo = new ArrayList<>();

    private ObservableList<Sexo> observableListSexo;

    public void setCliente(Cliente entity) {
        this.entity = entity;
    }

    public void setClienteService(ClienteService service) {
        this.service = service;
    }

    public void subcribeDataChandeListener(DataChangeListener listener) {
        dataChangeListeners.add(listener);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initializeNodes();
        loadComboBoxSexo();
    }

    public void loadComboBoxSexo() {
        listSexo.add(Sexo.M);
        listSexo.add(Sexo.F);

        observableListSexo = FXCollections.observableArrayList(listSexo);
        cbSexo.setItems(observableListSexo);

    }

    private void initializeNodes() {
        Constraints.setTextFieldMaxLength(txtNome, 100);
        Constraints.setTextFieldMaxLength(txtEmail, 100);
        Constraints.setTextFieldMaxLength(txtCpf, 14);
        Constraints.setTextFieldInteger(txtCpf);
        Constraints.setTextFieldMaxLength(txtRg, 12);
        Constraints.setTextFieldMaxLength(txtTelefone, 14);
        Constraints.setTextFieldInteger(txtTelefone);
        Constraints.setTextFieldMaxLength(txtCelular, 15);
        Constraints.setTextFieldInteger(txtCelular);
        Constraints.setTextFieldMaxLength(txtEndereco, 150);
        Constraints.setTextFieldMaxLength(txtCep, 10);
        Constraints.setTextFieldInteger(txtCep);
        Constraints.setTextFieldMaxLength(txtBairro, 50);
        Constraints.setTextFieldMaxLength(txtCidade, 50);
    }

    public void updateFormData() {
        if (entity == null) {
            throw new IllegalStateException("A entidade e nula.");
        }
        txtNome.setText(entity.getNome());
        txtEmail.setText(entity.getEmail());
        txtCpf.setText(entity.getCpf());
        txtRg.setText(entity.getRg());
        //   datePickDataNascimento.setValue();
        cbSexo.setValue(entity.getSexo());
        txtNome.setText(entity.getTelefone());
        txtNome.setText(entity.getCelular());
        txtNome.setText(entity.getEndereco());
        txtNome.setText(entity.getCep());
        txtNome.setText(entity.getBairro());
        txtNome.setText(entity.getCidade());
    }

    private Cliente getFormData() {
        Cliente obj = new Cliente();

        obj.setNome(txtNome.getText());
        obj.setEmail(txtEmail.getText());
        obj.setCpf(txtNome.getText());
        obj.setRg(txtNome.getText());

        LocalDate dataPk = datePickDataNascimento.getValue();
        Calendar dataC = Calendar.getInstance();

        obj.setDataNascimento(Date.valueOf(dataPk));
        obj.setDataCadastro(dataC.getTime());
        obj.setSexo(cbSexo.getValue());
        obj.setTelefone(txtTelefone.getText());
        obj.setCelular(txtCelular.getText());
        obj.setEndereco(txtEndereco.getText());
        obj.setCep(txtCep.getText());
        obj.setBairro(txtBairro.getText());
        obj.setCidade(txtCidade.getText());

        return obj;
    }

    private void notifyDataChangeListeners() {
        for (DataChangeListener listener : dataChangeListeners) {
            listener.onDataChanged();
        }
    }

}
