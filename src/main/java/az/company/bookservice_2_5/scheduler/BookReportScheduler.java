package az.company.bookservice_2_5.scheduler;

import az.company.bookservice_2_5.service.BookReportService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.javacrumbs.shedlock.spring.annotation.SchedulerLock;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class BookReportScheduler {

    private final BookReportService bookReportService;

    @Scheduled(fixedRate = 5000) // every midnight
    @SchedulerLock(name = "dailyBookReport", lockAtLeastFor = "PT10S", lockAtMostFor = "30m")
    public void generateDailyReport() {
        bookReportService.generateDailyReport();
    }
}
