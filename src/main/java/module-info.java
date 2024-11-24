module ttn.developer.fuzzylogicproject {
    requires javafx.controls;
    requires javafx.fxml;
    /*
        This dependency was installed locally using maven cli.
        You can get it in: https://jfuzzylogic.sourceforge.net/html/index.html
    */
    requires jfuzzylogic;


    opens ttn.developer.fuzzylogicproject to javafx.fxml;
    exports ttn.developer.fuzzylogicproject;
}