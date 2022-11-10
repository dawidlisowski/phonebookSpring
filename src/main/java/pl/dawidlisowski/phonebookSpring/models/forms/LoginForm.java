package pl.dawidlisowski.phonebookSpring.models.forms;

import lombok.Data;

@Data
public class LoginForm {
    private String login;
    private String password;

    @Override
    public String toString() {
        return login +
                "\n" + password;
    }
}
