package sk.fiit.phutumi.controllers;

import org.camunda.bpm.engine.delegate.DelegateTask;
import org.camunda.bpm.engine.delegate.TaskListener;
import org.springframework.stereotype.Service;

import javax.inject.Named;

@Named("processListener")
@Service
public class OrderToProcessListener implements TaskListener {
    @Override
    public void notify(final DelegateTask delegateTask){
        System.out.println("Hello");
    }
}
