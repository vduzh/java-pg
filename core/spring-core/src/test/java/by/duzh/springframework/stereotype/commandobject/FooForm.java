package by.duzh.springframework.stereotype.commandobject;

import org.springframework.web.multipart.MultipartFile;

/**
 * This is a POJO that is used to collect all information on a form. It contains data only. It is also called
 * a Command Object in some Spring tutorials.
 *
 * Form Backing Object
 */

public class FooForm {
    private String name;
    private MultipartFile file;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public MultipartFile getFile() {
        return file;
    }

    public void setFile(MultipartFile file) {
        this.file = file;
    }
}
