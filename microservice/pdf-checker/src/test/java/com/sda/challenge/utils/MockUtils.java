package com.sda.challenge.utils;

import com.sda.challenge.domain.enums.StateEnum;
import com.sda.challenge.domain.model.kafka.CheckResultEvent;

import java.io.File;

public class MockUtils {
    public static CheckResultEvent getCheckResultEvent(){
        CheckResultEvent checkResultEvent = new CheckResultEvent();
        checkResultEvent.setName("FooFileName");
        checkResultEvent.setDetails("Mock Details");
        checkResultEvent.setState(StateEnum.OK);
        return checkResultEvent;
    }

    public static File getFile(String url){
        return new File(url);
    }

    public static File getFile(){
        return new File("src/test/java/com/sda/challenge/pdf/file-ok.pdf");
    }
}
