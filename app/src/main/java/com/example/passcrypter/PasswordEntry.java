package com.example.passcrypter;

public class PasswordEntry {
    String itemtitle;
    String password;
    int appimage;

    public String getItemtitle() {
        return itemtitle;
    }

    public void setItemtitle(String itemtitle) {
        this.itemtitle = itemtitle;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getAppimage() {
        return appimage;
    }

    public void setAppimage(int appimage) {
        this.appimage = appimage;
    }

    public PasswordEntry(String itemtitle, String password, int appimage) {
        this.itemtitle = itemtitle;
        this.password = password;
        this.appimage = appimage;

    }




}
