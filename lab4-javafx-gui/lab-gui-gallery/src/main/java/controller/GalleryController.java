package controller;


import javafx.beans.Observable;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;

import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import model.Gallery;
import model.Photo;

import static javafx.beans.binding.Bindings.unbindBidirectional;


public class GalleryController {

    @FXML
    private ListView<Photo> imagesListView;

    @FXML
    private TextField imageNameField;

    @FXML
    private ImageView imageView;

    @FXML
    public void initialize() {
        imagesListView.setCellFactory(param -> new ListCell<>() {
            @Override
            protected void updateItem(Photo item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setText(null);
                    setGraphic(null);
                } else {
                    ImageView photoIcon = new ImageView(item.getPhotoData());
                    photoIcon.setPreserveRatio(true);
                    photoIcon.setFitHeight(50);
                    setGraphic(photoIcon);
                }
            }
        });

        imagesListView.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> {
                    if(oldValue != null) {
                        imageNameField.textProperty().unbindBidirectional(oldValue.nameProperty());
                    }
                    bindSelectedPhoto(newValue);
                }
        );
    }

    public void setModel(Gallery gallery) {
//        bindSelectedPhoto(gallery.getPhotos().get(0));
        imagesListView.setItems(gallery.getPhotos());
        imagesListView.getSelectionModel().select(0);

    }

    private void bindSelectedPhoto(Photo selectedPhoto) {
        imageView.imageProperty().bind(selectedPhoto.photoDataProperty());
        imageNameField.textProperty().bindBidirectional(selectedPhoto.nameProperty());
    }
}

