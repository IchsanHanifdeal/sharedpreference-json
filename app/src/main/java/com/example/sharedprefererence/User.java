package com.example.sharedprefererence;

public class User {
    private String name;
    private int age;
    private String jurusan;
    private String prodi;
    private String email;

    public User() {
        // Konstruktor kosong diperlukan untuk Gson.
    }

    public User(String name, int age, String jurusan, String prodi, String email) {
        this.name = name;
        this.age = age;
        this.jurusan = jurusan;
        this.prodi = prodi;
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getJurusan() {
        return jurusan;
    }

    public void setJurusan(String jurusan) {
        this.jurusan = jurusan;
    }

    public String getProdi() {
        return prodi;
    }

    public void setProdi(String prodi) {
        this.prodi = prodi;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
