module me.mispz {
    requires javafx.controls;
    requires javafx.fxml;
    requires jakarta.persistence;
    requires org.xerial.sqlitejdbc;
    requires org.hibernate.orm.core;
    requires jakarta.transaction;
    requires jakarta.cdi;

    opens me.mispz.gui to javafx.fxml, org.xerial.sqlitejdbc, jakarta.persistence, org.hibernate.orm.core, jakarta.cdi;
    exports me.mispz.gui;

    opens me.mispz.entities to javafx.fxml, org.xerial.sqlitejdbc, jakarta.persistence, org.hibernate.orm.core, jakarta.cdi;
    exports me.mispz.entities;

    opens me.mispz.util to javafx.fxml, org.xerial.sqlitejdbc, jakarta.persistence, org.hibernate.orm.core, jakarta.cdi;
    exports me.mispz.util;

}