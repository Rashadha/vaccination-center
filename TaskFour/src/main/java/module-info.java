module com.example.defer_cw_taskfour {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.apache.poi.ooxml;


    opens com.example.defer_cw_taskfour to javafx.fxml;
    exports com.example.defer_cw_taskfour;
}