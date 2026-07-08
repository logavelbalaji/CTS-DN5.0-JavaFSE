import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
public class QuizController {
    @Autowired
    private AttemptService attemptService;

    @GetMapping(value = "/attempts/{userId}/{attemptId}", produces = "text/html")
    public String getAttemptDetails(@PathVariable int userId, @PathVariable int attemptId) {
        Attempt attempt = attemptService.getAttempt(userId, attemptId);
        if (attempt == null) {
            return "<h3>Attempt not found</h3>";
        }
        StringBuilder html = new StringBuilder();
        html.append("<html><head><style>")
            .append("body { font-family: sans-serif; background-color: #f7f9fa; color: #333; padding: 20px; }")
            .append(".card { background: white; border-radius: 8px; box-shadow: 0 4px 6px rgba(0,0,0,0.1); padding: 20px; margin-bottom: 20px; max-width: 800px; }")
            .append("h3 { margin-top: 0; color: #0288d1; }")
            .append("table { width: 100%; border-collapse: collapse; margin-top: 10px; }")
            .append("th, td { text-align: left; padding: 8px; border-bottom: 1px solid #ddd; }")
            .append("th { background-color: #e1f5fe; }")
            .append(".selected { font-weight: bold; color: #2e7d32; }")
            .append("</style></head><body>")
            .append("<h2>Quiz Attempt Details</h2>")
            .append("<p><b>Username:</b> ").append(attempt.getUser().getName()).append("</p>")
            .append("<p><b>Attempted Date:</b> ").append(attempt.getDate()).append("</p>");
        for (AttemptQuestion aq : attempt.getAttemptQuestions()) {
            html.append("<div class='card'>")
                .append("<h3>").append(aq.getQuestion().getText()).append("</h3>")
                .append("<table><thead><tr><th>Option</th><th>Score</th><th>Selected</th></tr></thead><tbody>");
            for (AttemptOption ao : aq.getAttemptOptions()) {
                String rowClass = ao.isSelected() ? "class='selected'" : "";
                html.append("<tr ").append(rowClass).append(">")
                    .append("<td>").append(ao.getOption().getText()).append("</td>")
                    .append("<td>").append(ao.getOption().getScore()).append("</td>")
                    .append("<td>").append(ao.isSelected() ? "✔" : "").append("</td>")
                    .append("</tr>");
            }
            html.append("</tbody></table></div>");
        }
        html.append("</body></html>");
        return html.toString();
    }
}
