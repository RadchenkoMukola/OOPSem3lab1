package com.example.oppsem2labjavae1.Core;

import com.example.oppsem2labjavae1.Core.Repository.DBRepository;
import com.example.oppsem2labjavae1.Core.Repository.Repository;
import lombok.Getter;

public class DBSetup {

    private static String DB_URL = "jdbc:postgresql://localhost:1234/postgres";
    private static String DB_USER = "postgres";
    private static String DB_PASSWORD = "mypass";

    @Getter
    private static DBSetup instance = new DBSetup();

    @Getter
    private Repository repository;

    private DBSetup() {
        repository = new DBRepository(DB_URL, DB_USER, DB_PASSWORD);
    }
}