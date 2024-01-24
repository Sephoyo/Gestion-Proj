/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gestionproj;

import javax.swing.Icon;

/**
 *
 * @author joseph.baert
 */
public class Model_Card {
    private Icon icon;
    private String title;
    private String values;
    private String description;

    // Getter for icon
    public Icon getIcon() {
        return icon;
    }

    // Setter for icon
    public void setIcon(Icon icon) {
        this.icon = icon;
    }

    // Getter for title
    public String getTitle() {
        return title;
    }

    // Setter for title
    public void setTitle(String title) {
        this.title = title;
    }

    // Getter for values
    public String getValues() {
        return values;
    }

    // Setter for values
    public void setValues(String values) {
        this.values = values;
    }

    // Getter for description
    public String getDescription() {
        return description;
    }

    // Setter for description
    public void setDescription(String description) {
        this.description = description;
    }
    
    public Model_Card(Icon icon, String title,String values, String description){
        this.icon=icon;
        this.title=title;
        this.values=values;
        this.description=description;
    }
    
    
}
