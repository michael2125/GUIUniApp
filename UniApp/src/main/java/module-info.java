module com.unifx {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;
    opens com.unifx to javafx.fxml;
    exports com.unifx;
}