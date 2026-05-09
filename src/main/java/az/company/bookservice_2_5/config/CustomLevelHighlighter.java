package az.company.bookservice_2_5.config;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.pattern.color.ForegroundCompositeConverterBase;

import static ch.qos.logback.core.pattern.color.ANSIConstants.*;

public class CustomLevelHighlighter extends ForegroundCompositeConverterBase<ILoggingEvent> {

    @Override
    protected String getForegroundColorCode(ILoggingEvent event) {
        return switch (event.getLevel().toInt()) {
            case Level.ERROR_INT -> RED_FG;
            case Level.WARN_INT -> YELLOW_FG;
            case Level.INFO_INT -> GREEN_FG;
            case Level.DEBUG_INT -> BOLD + GREEN_FG;
            default -> DEFAULT_FG;
        };
    }
}
