package pl.dawidlisowski.phonebookSpring.models.forms;

import lombok.Data;

import javax.validation.constraints.Pattern;

@Data
public class ContactForm {
    @Pattern(regexp = "[a-zA-Z]{3,15}")
    private String name;
    @Pattern(regexp = "[a-zA-Z]{3,20}")
    private String surname;
    @Pattern(regexp = "[0-9]{9}")
    private String phone;
}
