package com.apps.a10119920latihan7api;

public class Kota {
    private Integer id;
    private String nama;

    public Kota(Integer id, String nama) {
        this.id = id;
        this.nama = nama;
    }

    public String GetNama() {
        return this.nama;
    }

    public Integer GetId() {
        return this.id;
    }
}