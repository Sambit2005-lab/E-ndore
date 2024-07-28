package com.codexnovas.e_ndore;

public class Document {
    private String title;
    private String url;
    private String department;

    public Document() {
        // Default constructor required for calls to DataSnapshot.getValue(Document.class)
    }

    public Document(String title, String url, String department) {
        this.title = title;
        this.url = url;
        this.department = department;
    }

    public String getTitle() {
        return title;
    }

    public String getUrl() {
        return url;
    }

    public String getDepartment() {
        return department;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setDepartment(String department) {
        this.department = department;
    }
}