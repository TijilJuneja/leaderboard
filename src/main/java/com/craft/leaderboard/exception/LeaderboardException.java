package com.craft.leaderboard.exception;

import com.craft.leaderboard.enums.ApplicationErrors;
import com.craft.leaderboard.enums.ResourceLayer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.Errors;

import java.io.Serial;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LeaderboardException extends RuntimeException {
        @Serial
        private static final long serialVersionUID = 1787878786666L;
        private Errors errors;
        private String details;
        private String displayMessage;
        private String message;
        private HttpStatus httpStatus;
        private ResourceLayer resourceLayer;
        private ApplicationErrors applicationErrors;
        private Map<String, Object> errorMeta;

        public LeaderboardException(String displayMessage, ApplicationErrors applicationErrors, ResourceLayer resourceLayer) {
                this.applicationErrors = applicationErrors;
                this.displayMessage = displayMessage;
                this.resourceLayer = resourceLayer;
        }
}
