package kr.or.tyson.yamlbot.command;

import kr.or.tyson.yamlbot.Artifact.Action;

import org.eclipse.swtbot.swt.finder.SWTBot;

public class SleepCommand extends AbstractCommand {
    
    int target;
    Action action;
    
    public SleepCommand(int target, Action action) {
        this.target = target;
        this.action = action;
    }
    
    @Override
    public SWTBot execute(SWTBot bot) throws InterruptedException {
        Thread.sleep(target);
        return bot;
    }

}
