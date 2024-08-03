package ru.mudan.translatetask.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.mudan.translatetask.dao.ResultDAO;
import ru.mudan.translatetask.entity.Result;

@Service
public class ResultService {
    private final ResultDAO resultDAO;
    @Autowired
    public ResultService(ResultDAO resultDAO) {
        this.resultDAO = resultDAO;
    }
    public void save(String ipAddress, String input, String answer){
        Result result = new Result();
        result.setIpAddress(ipAddress);
        result.setInput(input);
        result.setAnswer(answer);
        resultDAO.save(result);
    }
}
