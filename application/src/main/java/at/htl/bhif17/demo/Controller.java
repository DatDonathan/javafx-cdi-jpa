package at.htl.bhif17.demo;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.scene.control.*;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import javax.persistence.PersistenceException;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Random;

@Dependent
@Transactional
public class Controller {

    Random random;
    @Inject
    PersonDao dao;

    public TextField idField;
    public TextField firstNameField;
    public TextField lastNameField;
    public TextField matNrField;
    public TableView<Person> table;

    public void initialize () {
        random = new Random();
        dao.addListener(m -> {
            List<Person> persons = m.getAll();
            System.out.println(persons);
            table.getItems().setAll(FXCollections.observableArrayList(persons));
        });
        table.setItems(FXCollections.observableArrayList(dao.getAll()));
        table.getSelectionModel().selectedItemProperty().addListener((p, o, n) -> {
            Person person = fromTextFields();
            if ((o == null || n == null || !o.equals(n)) && !checkBlank() && !person.equals(o)) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Do you want to save the values you inserted?");
                alert.setHeaderText("Do you want to save the values you inserted?");
                alert.setContentText("The changes you made will be lost otherwise!");
                alert.getButtonTypes().setAll(ButtonType.YES, ButtonType.NO);
                if (alert.showAndWait().get() == ButtonType.YES) {
                    save(person);
                }
            }
            if (n == null) {
                clearFields();
            }
            else {
                idField.setText(n.getId() + "");
                firstNameField.setText(n.getFirstName());
                lastNameField.setText(n.getLastName());
                matNrField.setText(n.getMatNr());
            }
        });
    }

    private boolean checkBlank () {
        return firstNameField.getText().isBlank() && lastNameField.getText().isBlank() && matNrField.getText().isBlank();
    }

    public void save (ActionEvent event) {
        Person person = fromTextFields();
        if (save(person)) {
            clearFields();
        }
    }

    private void clearFields() {
        idField.setText("");
        firstNameField.setText("");
        lastNameField.setText("");
        matNrField.setText("");
    }

    private Integer parse (String str) {
        Integer i = null;
        try {
            i = Integer.parseInt(str);
        }
        catch (NumberFormatException e) {

        }
        return i;
    }

    private boolean save(Person person) {
        boolean success = false;
        try {
            dao.save(person);
            success = true;
        }
        catch (PersistenceException e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Constraint violation");
            alert.setHeaderText("Couldn't update database because of constraint violations!");
            alert.setContentText(e.getLocalizedMessage());
            alert.showAndWait();
        }
        return success;
    }

    private Person fromTextFields () {
        return Person.builder().id(parse(idField.getText())).firstName(firstNameField.getText()).lastName(lastNameField.getText()).matNr(matNrField.getText()).build();
    }

    public void delete(ActionEvent actionEvent) {
        Person person = table.getSelectionModel().getSelectedItem();
        if (person != null) {
            dao.delete(person);
        }
    }

    public void create(ActionEvent actionEvent) {
        table.getSelectionModel().select(null);
    }
}
