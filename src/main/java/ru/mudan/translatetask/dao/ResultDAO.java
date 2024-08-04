package ru.mudan.translatetask.dao;

import org.springframework.stereotype.Component;
import ru.mudan.translatetask.entity.Result;
import ru.mudan.translatetask.exceptions.ResultDaoException;
import ru.mudan.translatetask.util.ConnectionManager;

import java.sql.SQLException;
@Component
public class ResultDAO {
    private static final String INSERT_RESULT = "INSERT INTO result(ip_address,input,answer) VALUES(?,?,?);";
    private ResultDAO(){

    }
    public void save(Result resultOfTranslate) {
        try (var connection = ConnectionManager.get(); var preparedStatement = connection.prepareStatement(INSERT_RESULT)) {
            preparedStatement.setObject(1,resultOfTranslate.getIpAddress());
            preparedStatement.setObject(2,resultOfTranslate.getInput());
            preparedStatement.setObject(3,resultOfTranslate.getAnswer());
            preparedStatement.executeUpdate();
            System.out.println(preparedStatement);
        } catch (SQLException e) {
            throw new ResultDaoException("Error saving result to database", e);
        }
    }
}