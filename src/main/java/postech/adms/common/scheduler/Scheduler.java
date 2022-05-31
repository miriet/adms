package postech.adms.common.scheduler;

import com.sap.conn.jco.JCoException;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import postech.adms.common.time.SystemTime;
import postech.adms.service.jco.SapService;

import javax.annotation.Resource;

/*
*   스프링 설정 xml에 아래 부분 추가해야 함.
*   xmlns:task="http://www.springframework.org/schema/task
    http://www.springframework.org/schema/task
	http://www.springframework.org/schema/task/spring-task-3.0.xsd
*
*   <task:annotation-driven />
*/

@Service
public class Scheduler {

    @Resource
    private SapService sapService;

//    @Scheduled(fixedDelay = 3000) // fixedDelay 단위는 millisecond
@Scheduled(cron = "10 50 20 ? * FRI") // 매주 일요일 새벽 01:00에 실행함
    public void sampleScheduler(){
        System.out.println("Scheduler.collectDataFromJco() is called.");
    }


    @Scheduled(cron = "0 31 17 ? * SAT") // 매주 토요일 새벽 01:00에 실행함
    public void getJcoData(){
        System.out.println("Scheduler.getJcoData() is called.");
        String fromDate = SystemTime.getPlainDate(-8); // 8일전 == 지난주 일요일 날짜
        String toDate = SystemTime.getPlainDate(-1); // 1일전 == 지난주 토요일 날짜
        try {
            sapService.getMemberFromSAP(fromDate,toDate,"R");
            System.out.println("============================");
            System.out.println("=== Scheduler.getJcoData ===");
            System.out.println("=== fromDate:: " + fromDate +" ===");
            System.out.println("=== toDate:: " + toDate +" ===");
            System.out.println("============================");
        } catch (JCoException e) {
            System.out.println("============================");
            System.out.println("=== Scheduler.getJcoData ===");
            System.out.println("============================");
            e.printStackTrace();
        }
    }
}
