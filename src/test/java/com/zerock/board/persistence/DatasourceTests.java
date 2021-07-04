package com.zerock.board.persistence;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.sql.DataSource;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class DatasourceTests {

    @Autowired
    DataSource dataSource;

    @Test
    public void datasourceTest(){
        assertNotNull(dataSource);
    }
}
