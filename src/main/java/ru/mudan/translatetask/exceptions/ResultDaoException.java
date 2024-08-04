package ru.mudan.translatetask.exceptions;

import java.sql.SQLException;

public class ResultDaoException extends RuntimeException {
    public ResultDaoException(String message, SQLException cause) {
        super(message, cause);
    }
}
