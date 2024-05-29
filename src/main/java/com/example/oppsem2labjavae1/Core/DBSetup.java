package com.example.oppsem2labjavae1.Core;

import com.example.oppsem2labjavae1.Core.Repository.DBRepository;
import com.example.oppsem2labjavae1.Core.Repository.Repository;
import lombok.Getter;

public class DBSetup {
    // TODO: Possibly read from json or smth like that
    private static String DB_URL = "jdbc:postgresql://postgres:5432/dev";
    private static String DB_USER = "postgres";
    private static String DB_PASSWORD = "postgres";

    @Getter
    private static DBSetup instance = new DBSetup();

    @Getter
    private Repository repository;

    private DBSetup() {
        repository = new DBRepository(DB_URL, DB_USER, DB_PASSWORD);
    }
}