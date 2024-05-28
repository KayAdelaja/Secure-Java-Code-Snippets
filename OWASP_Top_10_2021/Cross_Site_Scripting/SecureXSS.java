import org.apache.commons.text.StringEscapeUtils;

public class SecureXSS {
    public String sanitizeInput(String userInput) {
        return StringEscapeUtils.escapeHtml4(userInput);
    }
}
